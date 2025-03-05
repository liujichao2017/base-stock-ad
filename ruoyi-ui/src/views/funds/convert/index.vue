<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="代理" prop="userId">
        <AgentSelect @val="(val)=>queryParams.userId = val"></AgentSelect>
      </el-form-item>
      <el-form-item label="会员Id" >
        <el-input
          v-model="queryParams.memberId"
          placeholder="请输入会员Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员手机号" >
        <el-input
          v-model="queryParams.memberPhone"
          placeholder="请输入会员手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转出市场" prop="fromId">
        <MarketSelect @input="(val)=>{queryParams.fromId = val}"/>
      </el-form-item>
      <el-form-item label="转入市场" prop="toId">
        <MarketSelect @input="(val)=>{queryParams.toId = val}"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="convertList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="实盘" align="center" prop="accountType">
        <template slot-scope="scope">
          <span v-if="scope.row.accountType === '1'">模拟</span>
          <span v-if="scope.row.accountType === '0'">实盘</span>
        </template>
      </el-table-column>
      <el-table-column label="会员ID" align="center" prop="memberId" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="会员名称" align="center" prop="memberRealName"/>
      <el-table-column label="会员手机" align="center" prop="memberPhone"/>
      <el-table-column label="代理" align="center" prop="userId">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}/{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="转出市场" align="center" prop="fromId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.fromId"/>
        </template>
      </el-table-column>
      <el-table-column label="转入市场" align="center" prop="toId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.toId"/>
        </template>
      </el-table-column>
      <el-table-column label="转出金额" align="center" prop="fromAmt"/>
      <el-table-column label="转入金额" align="center" prop="toAmt"/>
      <el-table-column label="汇率" align="center" prop="exchangeRate"/>
      <el-table-column label="操作时间" align="center" prop="createTime"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改兑换记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员Id" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入会员Id"/>
        </el-form-item>
        <el-form-item label="转出市场" prop="fromId">
          <el-input v-model="form.fromId" placeholder="请输入转出市场"/>
        </el-form-item>
        <el-form-item label="转入市场" prop="toId">
          <el-input v-model="form.toId" placeholder="请输入转入市场"/>
        </el-form-item>
        <el-form-item label="转出金额" prop="fromAmt">
          <el-input v-model="form.fromAmt" placeholder="请输入转出金额"/>
        </el-form-item>
        <el-form-item label="转入金额" prop="toAmt">
          <el-input v-model="form.toAmt" placeholder="请输入转入金额"/>
        </el-form-item>
        <el-form-item label="汇率: 转出市场 : 转入市场, 韩对美0.00072, 美兑韩: 1382.28" prop="exchangeRate">
          <el-input v-model="form.exchangeRate"
                    placeholder="请输入汇率: 转出市场 : 转入市场, 韩对美0.00072, 美兑韩: 1382.28"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addConvert, delConvert, getConvert, listConvert, updateConvert} from "@/api/funds/convert";
import MarketSelect from "@/components/marketSelect/index.vue";
import MarketSpan from "@/components/marketSpan/index.vue";

export default {
  name: "Convert",
  components: {MarketSpan, MarketSelect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 兑换记录表格数据
      convertList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: null,
        fromId: null,
        toId: null,
        fromAmt: null,
        toAmt: null,
        exchangeRate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
        ],
        fromId: [
          {required: true, message: "转出市场不能为空", trigger: "blur"}
        ],
        toId: [
          {required: true, message: "转入市场不能为空", trigger: "blur"}
        ],
        exchangeRate: [
          {
            required: true,
            message: "汇率: 转出市场 : 转入市场, 韩对美0.00072, 美兑韩: 1382.28不能为空",
            trigger: "blur"
          }
        ],
        createTime: [
          {required: true, message: "创建时间/注册时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询兑换记录列表 */
    getList() {
      this.loading = true;
      listConvert(this.queryParams).then(response => {
        this.convertList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        memberId: null,
        fromId: null,
        toId: null,
        fromAmt: null,
        toAmt: null,
        exchangeRate: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加兑换记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getConvert(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改兑换记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateConvert(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConvert(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除兑换记录编号为"' + ids + '"的数据项？').then(function () {
        return delConvert(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('funds/convert/export', {
        ...this.queryParams
      }, `convert_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
