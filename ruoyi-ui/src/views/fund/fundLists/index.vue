<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="基金名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入基金名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基金代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入基金代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.fund_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上架" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择上架" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fund:fundLists:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fund:fundLists:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fund:fundLists:remove']"
        >删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['fund:fundLists:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fundListsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="基金名称" align="center" prop="name"/>
<!--      <el-table-column label="基金代码" align="center" prop="code"/>-->
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fund_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="最低购买金额" align="center" prop="minAmt"/>
      <el-table-column label="最高购买金额" align="center" prop="maxAmt"/>
      <el-table-column label="开始购买时间" align="center" prop="startBuyTime"/>
      <el-table-column label="购买结束时间" align="center" prop="endBuyTime"/>
      <el-table-column label="利率(%)" align="center" prop="interestRate"/>
      <el-table-column label="周期" align="center" prop="cycle"/>
      <!--      <el-table-column label="存放展示性字段" align="center" prop="extra"/>-->
      <el-table-column label="上架" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间/申请时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fund:fundLists:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="$router.push('/fund/interestrate?fundId='+scope.row.id)"
            v-hasPermi="['fund:fundLists:edit']"
          >日利率
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="$router.push('/fund/fundstockrecord?fundId='+scope.row.id)"
          >股票记录
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fund:fundLists:remove']"
          >删除
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

    <!-- 添加或修改基金产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="基金名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入基金名称"/>
        </el-form-item>
<!--        <el-form-item label="基金代码" prop="code">-->
<!--          <el-input v-model="form.code" placeholder="请输入基金代码"/>-->
<!--        </el-form-item>-->
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.fund_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最低购买金额" prop="minAmt">
          <el-input v-model="form.minAmt" placeholder="请输入最低购买金额"/>
        </el-form-item>
        <el-form-item label="最高购买金额" prop="maxAmt">
          <el-input v-model="form.maxAmt" placeholder="请输入最高购买金额"/>
        </el-form-item>
        <el-form-item label="开始购买时间" prop="startBuyTime">
          <el-time-select
            v-model="form.startBuyTime"
            :picker-options="{
                  start: '00:00',
                  step: '00:15',
                  end: '23:59'
                }"
            placeholder="选择时间">
          </el-time-select>
        </el-form-item>
        <el-form-item label="购买结束时间" prop="endBuyTime">
          <el-time-select
            v-model="form.endBuyTime"
            :picker-options="{
                  start: '00:00',
                  step: '00:15',
                  end: '23:59'
                }"
            placeholder="选择时间">
          </el-time-select>
        </el-form-item>
        <el-form-item label="利率(%)" prop="interestRate">
          <el-input v-model="form.interestRate" placeholder="请输入利率(%)"/>
        </el-form-item>
        <el-form-item label="周期" prop="cycle">
          <el-input v-model="form.cycle" placeholder="请输入周期"/>
        </el-form-item>
        <!--        <el-form-item label="存放展示性字段" prop="extra">-->
        <!--          <el-input v-model="form.extra" type="textarea" placeholder="请输入内容"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="上架" prop="status">
          <el-select v-model="form.status" placeholder="请选择上架">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
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
import {addFundLists, delFundLists, getFundLists, listFundLists, updateFundLists} from "@/api/fund/fundLists";

export default {
  name: "FundLists",
  dicts: ['fund_type', 'yes_no'],
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
      // 基金产品表格数据
      fundListsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        code: null,
        type: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "基金名称不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "基金代码不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "类型不能为空", trigger: "change"}
        ],
        minAmt: [
          {required: true, message: "最低购买金额不能为空", trigger: "blur"}
        ],
        maxAmt: [
          {required: true, message: "最高购买金额不能为空", trigger: "blur"}
        ],
        startBuyTime: [
          {required: true, message: "开始购买时间不能为空", trigger: "blur"}
        ],
        endBuyTime: [
          {required: true, message: "购买结束时间不能为空", trigger: "blur"}
        ],
        interestRate: [
          {required: true, message: "利率(%)不能为空", trigger: "blur"}
        ],
        cycle: [
          {required: true, message: "周期不能为空", trigger: "blur"}
        ],
        extra: [
          {required: true, message: "存放展示性字段不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "上架不能为空", trigger: "change"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询基金产品列表 */
    getList() {
      this.loading = true;

      listFundLists(this.queryParams).then(response => {
        this.fundListsList = response.rows;
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
        name: null,
        code: null,
        type: null,
        minAmt: null,
        maxAmt: null,
        startBuyTime: null,
        endBuyTime: null,
        interestRate: null,
        cycle: null,
        extra: null,
        status: null,
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
      this.title = "添加基金产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFundLists(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基金产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.$confirm('修改收益或周期,将会重置日收益?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              updateFundLists(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            }).catch(() => {

            });

          } else {
            addFundLists(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除基金产品编号为"' + ids + '"的数据项？').then(function () {
        return delFundLists(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('fund/fundLists/export', {
        ...this.queryParams
      }, `fundLists_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>
