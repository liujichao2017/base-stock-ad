<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场ID" prop="marketId">
        <MarketSelect ref="marketSelect1" @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="股票名称" prop="stockName">
        <el-input
          v-model="queryParams.stockName"
          placeholder="请输入股票名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="股票代码" prop="stockCode">
        <el-input
          v-model="queryParams.stockCode"
          placeholder="请输入股票代码"
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
          v-hasPermi="['borrow:product:add']"
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
          v-hasPermi="['borrow:product:edit']"
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
          v-hasPermi="['borrow:product:remove']"
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
<!--          v-hasPermi="['borrow:product:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="股票名称" align="center" prop="stockName"/>
      <el-table-column label="股票代码" align="center" prop="stockCode"/>
      <el-table-column label="价格" align="center" prop="price"/>
      <el-table-column label="最低购买额度" align="center" prop="minBuyAmt"/>
      <el-table-column label="总获利率(%)" align="center" prop="rebate"/>
      <el-table-column label="周期天数" align="center" prop="cycle"/>

      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需求张数" align="center" prop="needNumber"/>
      <el-table-column label="已借张数" align="center" prop="borrowNumber"/>
      <el-table-column label="股数/张" align="center" prop="num"/>
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
            v-hasPermi="['borrow:product:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['borrow:product:remove']"
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

    <!-- 添加或修改存股借卷配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="市场" prop="marketId">
          <MarketSelect ref="marketSelect" @input="(val)=>{form.marketId = val}"/>
        </el-form-item>
        <el-form-item label="股票名称" prop="stockName">
          <el-input v-model="form.stockName" placeholder="请输入股票名称"/>
        </el-form-item>
        <el-form-item label="股票代码" prop="stockCode">
          <el-input v-model="form.stockCode" placeholder="请输入股票代码"/>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="最低购买额度" prop="minBuyAmt">
          <el-input v-model="form.minBuyAmt" placeholder="请输入最低购买额度"/>
        </el-form-item>
        <el-form-item label="总获利率(%)" prop="rebate">
          <el-input v-model="form.rebate" placeholder="请输入总获利率(%)"/>
        </el-form-item>
        <el-form-item label="周期天数" prop="cycle">
          <el-input v-model="form.cycle" placeholder="请输入周期天数"/>
        </el-form-item>

        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
                          v-model="form.startTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="需求张数" prop="needNumber">
          <el-input v-model="form.needNumber" placeholder="请输入需求张数"/>
        </el-form-item>
        <el-form-item label="股数/张" prop="num">
          <el-input v-model="form.num" placeholder="请输入股数/张"/>
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
import {addProduct, delProduct, getProduct, listProduct, updateProduct} from "@/api/borrow/product";

export default {
  name: "Product",
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
      // 存股借卷配置表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stockName: null,
        marketId: null,
        stockCode: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stockName: [
          {required: true, message: "股票名称不能为空", trigger: "blur"}
        ],
        stockCode: [
          {required: true, message: "股票代码不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "价格不能为空", trigger: "blur"}
        ],
        minBuyAmt: [
          {required: true, message: "最低购买额度不能为空", trigger: "blur"}
        ],
        rebate: [
          {required: true, message: "总获利率(%)不能为空", trigger: "blur"}
        ],
        cycle: [
          {required: true, message: "周期天数不能为空", trigger: "blur"}
        ],
        marketId: [
          {required: true, message: "市场不能为空", trigger: "blur"}
        ],
        startTime: [
          {required: true, message: "开始时间不能为空", trigger: "blur"}
        ],
        endTime: [
          {required: true, message: "结束时间不能为空", trigger: "blur"}
        ],
        needNumber: [
          {required: true, message: "需求张数不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
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
    /** 查询存股借卷配置列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
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
        stockName: null,
        stockCode: null,
        price: null,
        minBuyAmt: null,
        rebate: null,
        cycle: null,
        marketId: null,
        startTime: null,
        endTime: null,
        needNumber: null,
        borrowNumber: null,
        num: null,
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
      this.$refs.marketSelect1.reset();
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
      this.title = "添加存股借卷配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProduct(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改存股借卷配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
          this.$refs.marketSelect.reset();
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除存股借卷配置编号为"' + ids + '"的数据项？').then(function () {
        return delProduct(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('borrow/product/export', {
        ...this.queryParams
      }, `product_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
