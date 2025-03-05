<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="基金ID" prop="fundId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.fundId"-->
      <!--          placeholder="请输入基金ID"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="日利率" prop="interestRate">
        <el-input
          v-model="queryParams.interestRate"
          placeholder="请输入日利率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="周期" prop="cycle">
        <el-input
          v-model="queryParams.cycle"
          placeholder="请输入周期"
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['interestrate:interestrate:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['interestrate:interestrate:edit']"-->
      <!--        >修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          plain-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['interestrate:interestrate:remove']"-->
      <!--        >删除-->
      <!--        </el-button>-->
      <!--      </el-col>-->

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      show-summary max-height="750" v-loading="loading" :data="interestrateList"
      :summary-method="getSummaries"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="ID" align="center" prop="id" />-->
      <!--      <el-table-column label="基金ID" align="center" prop="fundId" />-->
      <el-table-column label="周期(天)" align="center" prop="cycle">
        <template slot-scope="scope">
          <span>第{{ scope.row.cycle }}次收益</span>
        </template>
      </el-table-column>
      <el-table-column label="日利率(%)" align="center" prop="interestRate"/>
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
        </template>
      </el-table-column>
    </el-table>

    <!--    <pagination-->
    <!--      v-show="total>0"-->
    <!--      :total="total"-->
    <!--      :page.sync="queryParams.pageNum"-->
    <!--      :limit.sync="queryParams.pageSize"-->
    <!--      @pagination="getList"-->
    <!--    />-->

    <!-- 添加或修改日收益对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="基金ID" prop="fundId">-->
        <!--          <el-input v-model="form.fundId" placeholder="请输入基金ID"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="周期(天)" prop="cycle">
          <el-input :disabled="true" v-model="form.cycle" placeholder="请输入周期"/>
        </el-form-item>
        <el-form-item label="日利率" prop="interestRate">
          <el-input v-model="form.interestRate" placeholder="请输入日利率"/>
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
import {
  addInterestrate,
  delInterestrate,
  getInterestrate,
  listInterestrate,
  updateInterestrate
} from "@/api/fund/interestrate";

export default {
  name: "Interestrate",
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
      // 日收益表格数据
      interestrateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fundId: null,
        interestRate: null,
        cycle: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fundId: [
          {required: true, message: "基金ID不能为空", trigger: "blur"}
        ],
        interestRate: [
          {required: true, message: "日利率不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间/申请时间不能为空", trigger: "blur"}
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
  watch: {
    '$route': {
      handler(to, from) {
        this.getList();
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    /** 查询日收益列表 */
    getList() {
      this.loading = true;
      this.queryParams.fundId = this.$route.query.fundId;
      listInterestrate(this.queryParams).then(response => {
        this.interestrateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getSummaries(param) {
      const {columns, data} = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总价';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
        }
      });
      sums[1] = '--';
      sums[2] = Number(sums[2]).toFixed(2) + '%';
      return sums;
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
        fundId: null,
        interestRate: null,
        cycle: null,
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
      this.title = "添加日收益";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInterestrate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改日收益";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInterestrate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInterestrate(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除日收益编号为"' + ids + '"的数据项？').then(function () {
        return delInterestrate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('interestrate/interestrate/export', {
        ...this.queryParams
      }, `interestrate_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
