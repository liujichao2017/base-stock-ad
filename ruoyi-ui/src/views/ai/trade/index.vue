<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场ID" prop="marketId">
        <MarketSelect ref="marketSelect" @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="AI交易名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入AI交易名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['ai:trade:add']"
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
          v-hasPermi="['ai:trade:edit']"
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
          v-hasPermi="['ai:trade:remove']"
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
<!--          v-hasPermi="['ai:trade:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tradeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="AI交易名称" align="center" prop="name"/>
      <el-table-column label="最少买入金额" align="center" prop="minBuyAmt"/>
      <el-table-column label="交易成功率(%)" align="center" prop="tradeSucRate"/>
      <el-table-column label="预期收益(%)" align="center" prop="futureEarningsRate"/>
      <el-table-column label="交易周期(天)" align="center" prop="tradeCycle"/>
      <el-table-column label="上架" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ai:trade:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ai:trade:remove']"
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

    <!-- 添加或修改AI交易对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="市场" prop="marketId">
          <MarketSelect :marketId="form.marketId" ref="marketSelect1"
                        @input="(val)=>{form.marketId = val}"/>
        </el-form-item>
        <el-form-item label="AI交易名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入AI交易名称"/>
        </el-form-item>
        <el-form-item label="最少买入金额" prop="minBuyAmt">
          <el-input v-model="form.minBuyAmt" placeholder="请输入最少买入金额"/>
        </el-form-item>
        <el-form-item label="交易成功率(%)" prop="tradeSucRate">
          <el-input v-model="form.tradeSucRate" placeholder="请输入交易成功率(%)"/>
        </el-form-item>
        <el-form-item label="预期收益(%)" prop="futureEarningsRate">
          <el-input v-model="form.futureEarningsRate" placeholder="请输入预期收益(%)"/>
        </el-form-item>
        <el-form-item label="交易周期(天)" prop="tradeCycle">
          <el-input v-model="form.tradeCycle" placeholder="请输入交易周期(天)"/>
        </el-form-item>
        <el-form-item label="上架" prop="status">
          <el-select v-model="form.status" placeholder="请选择上架">
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
import {addTrade, delTrade, getTrade, listTrade, updateTrade} from "@/api/ai/trade";

export default {
  name: "Trade",
  dicts: ['yes_no'],
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
      // AI交易表格数据
      tradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        marketId: null,
        name: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "AI交易名称不能为空", trigger: "blur"}
        ],
        minBuyAmt: [
          {required: true, message: "最少买入金额不能为空", trigger: "blur"}
        ],
        tradeSucRate: [
          {required: true, message: "交易成功率(%)不能为空", trigger: "blur"}
        ],
        futureEarningsRate: [
          {required: true, message: "预期收益(%)不能为空", trigger: "blur"}
        ],
        tradeCycle: [
          {required: true, message: "交易周期(天)不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "上架不能为空", trigger: "change"}
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
  methods: {
    /** 查询AI交易列表 */
    getList() {
      this.loading = true;
      listTrade(this.queryParams).then(response => {
        this.tradeList = response.rows;
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
        marketId: null,
        name: null,
        minBuyAmt: null,
        tradeSucRate: null,
        futureEarningsRate: null,
        tradeCycle: null,
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
      this.$refs.marketSelect.reset();
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
      this.title = "添加AI交易";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTrade(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改AI交易";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTrade(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTrade(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
          this.$refs.marketSelect1.reset();
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除AI交易编号为"' + ids + '"的数据项？').then(function () {
        return delTrade(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ai/trade/export', {
        ...this.queryParams
      }, `trade_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
