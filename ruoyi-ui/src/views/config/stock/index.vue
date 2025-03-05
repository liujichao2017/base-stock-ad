<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名字" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名字"
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
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['market:market:edit']"
        >修改
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="marketList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="名字" align="center" prop="name"/>
      <el-table-column label="货币缩写" align="center" prop="currency"/>
      <el-table-column label="主市场" align="center" prop="isMaster">
        <template slot-scope="scope">
          <span v-if="scope.row.isMaster ==='1'">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column label="主对本市场货币汇率" align="center" prop="mainExchangeRate"/>
      <el-table-column label="本对主市场货币汇率" align="center" prop="slaveExchangeRate"/>
      <el-table-column label="时区" align="center" prop="timeZone"/>
      <el-table-column label="涨停" align="center" prop="chgPctLimit"/>
      <el-table-column label="充值" align="center" prop="isRecharge">
        <template slot-scope="scope">
          <span v-if="scope.row.isRecharge ==='1'">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column label="提现" align="center" prop="isWithdraw">
        <template slot-scope="scope">
          <span v-if="scope.row.isWithdraw ==='1'">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
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

    <!-- 添加或修改市场对话框 -->
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="主市场对本市场货币汇率" prop="mainExchangeRate">
          <el-input v-model="form.mainExchangeRate" placeholder="主市场对本市场货币汇率"/>
        </el-form-item>
        <!--        <el-form-item label="本市场对主市场货币汇率" prop="slaveExchangeRate">-->
        <!--          <el-input v-model="form.slaveExchangeRate" placeholder="本市场对主市场货币汇率"/>-->
        <!--        </el-form-item>-->

        <el-form-item label="时区" prop="timeZone">
          <el-input v-model="form.timeZone" placeholder="请输入时区"/>
        </el-form-item>
        <el-form-item label="上午开始交易时间" prop="transAmBegin">
          <el-input v-model="form.transAmBegin" placeholder="请输入上午开始交易时间"/>
        </el-form-item>
        <el-form-item label="上午结束交易时间" prop="transAmEnd">
          <el-input v-model="form.transAmEnd" placeholder="请输入上午结束交易时间"/>
        </el-form-item>
        <el-form-item label="下午开始交易时间" prop="transPmBegin">
          <el-input v-model="form.transPmBegin" placeholder="请输入下午开始交易时间"/>
        </el-form-item>
        <el-form-item label="下午结束交易时间" prop="transPmEnd">
          <el-input v-model="form.transPmEnd" placeholder="请输入下午结束交易时间"/>
        </el-form-item>
        <el-form-item label="买入手续费" prop="buyFee">
          <el-input v-model="form.buyFee" placeholder="请输入买入手续费"/>
        </el-form-item>
        <el-form-item label="卖出手续费" prop="sellFee">
          <el-input v-model="form.sellFee" placeholder="请输入卖出手续费"/>
        </el-form-item>
        <el-form-item label="买入印花税" prop="otherFee">
          <el-input v-model="form.otherFee" placeholder="请输入印花税"/>
        </el-form-item>
        <el-form-item label="卖出印花税" prop="sellOtherFee">
          <el-input v-model="form.sellOtherFee" placeholder="请输入印花税"/>
        </el-form-item>
        <el-form-item label="股票最小购买数量" prop="minNum">
          <el-input v-model="form.minNum" placeholder="请输入股票最小购买数量"/>
        </el-form-item>
        <el-form-item label="股票最大购买数量" prop="maxNum">
          <el-input v-model="form.maxNum" placeholder="请输入股票最大购买数量"/>
        </el-form-item>
        <el-form-item label="最少持有时间(分钟)" prop="sellTime">
          <el-input v-model="form.sellTime" placeholder="请输入最少持有时间(分钟)"/>
        </el-form-item>
        <el-form-item label="涨停" prop="chgPctLimit">
          <el-input v-model="form.chgPctLimit" placeholder="请输入涨停"/>
        </el-form-item>
        <el-form-item label="充值" prop="isRecharge">
          <el-select v-model="form.isRecharge" placeholder="充值" clearable :style="{width: '100%'}">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提现" prop="isWithdraw">
          <el-select v-model="form.isWithdraw" placeholder="提现" clearable :style="{width: '100%'}">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
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
import {addMarket, delMarket, getMarket, listMarket, updateMarket} from "@/api/market/market";

export default {
  name: "Market",
  data() {
    return {
      imagesUrl: Global.gtpUrl,
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
      // 市场表格数据
      marketList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        currency: null,
        countryImg: null,
        isMaster: null,
        mainExchangeRate: null,
        slaveExchangeRate: null,
        dataSourceMark: null,
        dataSourceJson: null,
        timeZone: null,
        transAmBegin: null,
        transAmEnd: null,
        transPmBegin: null,
        transPmEnd: null,
        buyFee: null,
        sellFee: null,
        otherFee: null,
        sellOtherFee: null,
        minNum: null,
        maxNum: null,
        sellTime: null,
        chgPctLimit: null,
        isRecharge: null,
        isWithdraw: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "名字不能为空", trigger: "blur"}
        ],
        currency: [
          {required: true, message: "货币缩写不能为空", trigger: "blur"}
        ],
        countryImg: [
          {required: true, message: "国旗图片不能为空", trigger: "blur"}
        ],
        isMaster: [
          {required: true, message: "主市场 see YNEnum, 0: 否. 1: 是不能为空", trigger: "blur"}
        ],
        mainExchangeRate: [
          {required: true, message: "主市场对本市场货币汇率, 主市场填1不能为空", trigger: "blur"}
        ],
        slaveExchangeRate: [
          {required: true, message: "从市场(本市场)对主市场货币汇率, 主市场填1不能为空", trigger: "blur"}
        ],
        dataSourceMark: [
          {required: true, message: "数据源标记 see StockDataSourceEnum不能为空", trigger: "blur"}
        ],
        dataSourceJson: [
          {required: true, message: "数据源配置JSON不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询市场列表 */
    getList() {
      this.loading = true;
      listMarket(this.queryParams).then(response => {
        this.marketList = response.rows;
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
        currency: null,
        countryImg: null,
        isMaster: null,
        mainExchangeRate: null,
        slaveExchangeRate: null,
        dataSourceMark: null,
        dataSourceJson: null,
        timeZone: null,
        transAmBegin: null,
        transAmEnd: null,
        transPmBegin: null,
        transPmEnd: null,
        buyFee: null,
        sellFee: null,
        otherFee: null,
        sellOtherFee: null,
        minNum: null,
        maxNum: null,
        sellTime: null,
        chgPctLimit: null,
        updateTime: null,
        createTime: null,
        isRecharge: null,
        isWithdraw: null
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
      this.title = "添加市场";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMarket(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMarket(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMarket(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除市场编号为"' + ids + '"的数据项？').then(function () {
        return delMarket(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('market/market/export', {
        ...this.queryParams
      }, `market_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
