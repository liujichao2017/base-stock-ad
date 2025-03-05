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
      <el-form-item label="市场" prop="marketId">
        <MarketSelect @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="会员可见" prop="visible">
        <el-select v-model="queryParams.visible" placeholder="会员可见" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作源" prop="source">
        <el-select v-model="queryParams.source" placeholder="请选择操作源" clearable>
          <el-option
            v-for="dict in dict.type.funds_source"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作类型" prop="operateType">
        <el-select v-model="queryParams.operateType" placeholder="请选择操作类型" clearable>
          <el-option
            v-for="dict in dict.type.funds_operate_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作ID" >
        <el-input
          v-model="queryParams.sourceId"
          placeholder="请输入操作ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fundslogList" @selection-change="handleSelectionChange">
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
      </el-table-column>      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="操作源" align="center" prop="source">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.funds_source" :value="scope.row.source"/>
        </template>
      </el-table-column>
      <el-table-column label="操作类型" align="center" prop="operateType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.funds_operate_type" :value="scope.row.operateType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作金额" align="center" prop="amt"/>
      <el-table-column label="会员可见" align="center" prop="visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.visible"/>
        </template>
      </el-table-column>

      <el-table-column label="可用余额" align="center" prop="enableAmt"/>
      <el-table-column label="占用金额" align="center" prop="occupancyAmt"/>
      <el-table-column label="冻结金额" align="center" prop="freezeAmt"/>
      <el-table-column label="盈利金额" align="center" prop="profitAmt"/>
      <el-table-column label="源记录ID" align="center" prop="sourceId"/>
      <el-table-column width="200px" label="操作的内容" align="center" prop="content"/>
      <el-table-column label="操作时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['funds:fundslog:edit']"
          >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改资金动态对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员可见" prop="visible">
          <el-select v-model="form.visible" placeholder="请选择会员可见">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import {addFundslog, delFundslog, getFundslog, listFundslog, updateFundslog} from "@/api/funds/fundslog";
import MarketSelect from "@/components/marketSelect/index.vue";
import MarketSpan from "@/components/marketSpan/index.vue";

export default {
  name: "Fundslog",
  components: {MarketSelect, MarketSpan},
  dicts: ['funds_operate_type', 'funds_source', 'yes_no'],
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
      // 资金动态表格数据
      fundslogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: null,
        marketId: null,
        amt: null,
        visible: null,
        source: null,
        operateType: null,
        enableAmt: null,
        occupancyAmt: null,
        freezeAmt: null,
        profitAmt: null,
        content: null,
        contentJson: null,
        sourceId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
        ],
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        amt: [
          {required: true, message: "操作金额不能为空", trigger: "blur"}
        ],
        content: [
          {
            required: true,
            message: "操作的内容,根据交易的名称,记录ID等信息拼接字符串, 用于后端展示不能为空",
            trigger: "blur"
          }
        ],
        contentJson: [
          {
            required: true,
            message: "操作的内容存放JSON,根据交易的名称,产品代码,等信息拼接JSON, 用于前端展示, 标准格式定义 name code 不能为空",
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
    /** 查询资金动态列表 */
    getList() {
      this.loading = true;
      listFundslog(this.queryParams).then(response => {
        this.fundslogList = response.rows;
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
        marketId: null,
        amt: null,
        visible: null,
        source: null,
        operateType: null,
        enableAmt: null,
        occupancyAmt: null,
        freezeAmt: null,
        profitAmt: null,
        content: null,
        contentJson: null,
        sourceId: null,
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
      this.title = "添加资金动态";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFundslog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资金动态";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFundslog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFundslog(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除资金动态编号为"' + ids + '"的数据项？').then(function () {
        return delFundslog(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('funds/fundslog/export', {
        ...this.queryParams
      }, `fundslog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
