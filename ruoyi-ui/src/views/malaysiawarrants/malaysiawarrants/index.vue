<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="认股权证名称" prop="dwSymbol">
        <el-input
          v-model="queryParams.dwSymbol"
          placeholder="请输入认股权证名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="underlyingName">
        <el-input
          v-model="queryParams.underlyingName"
          placeholder="请输入名称"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['malaysiawarrants:malaysiawarrants:add']"
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
          v-hasPermi="['malaysiawarrants:malaysiawarrants:edit']"
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
          v-hasPermi="['malaysiawarrants:malaysiawarrants:remove']"
        >删除
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="malaysiawarrantsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="认股权证名称" align="center" prop="dwSymbol"/>
      <!--      <el-table-column label="SPEE_tc.KL" align="center" prop="ric" />-->
      <el-table-column label="元数据" align="center" prop="underlyingSymbol"/>
      <el-table-column label="名称" align="center" prop="underlyingName"/>
      <!--      <el-table-column label="SPEE.KL" align="center" prop="underlyingRic" />-->
      <!--      <el-table-column label="类型 C/P" align="center" prop="type" />-->
      <el-table-column label="行使价" align="center" prop="exercisePrice"/>
      <el-table-column label="行使比率" align="center" prop="convRatio"/>
      <!--      <el-table-column label="0.200" align="center" prop="dwps" />-->
      <!--      <el-table-column label="Macquarie" align="center" prop="issuer" />-->
      <el-table-column label="增量(%)" align="center" prop="delta"/>
      <el-table-column label="隐含波动率(%)" align="center" prop="impliedVolalitiy"/>
      <el-table-column label="敏感度" align="center" prop="sensitivity"/>
      <!--      <el-table-column label="-0.52" align="center" prop="theta" />-->
      <el-table-column label="有效杠杆" align="center" prop="effectiveGearing"/>
      <!--      <el-table-column label="28 Apr 25" align="center" prop="ltDate" />-->
      <!--      <el-table-column label="OTM" align="center" prop="moneyness" />-->
      <!--      <el-table-column label="4" align="center" prop="moneynessPercent" />-->
      <!--      <el-table-column label="4% OTM" align="center" prop="moneynessC" />-->
      <el-table-column label="买入量" align="center" prop="bidVolume"/>
      <!--      <el-table-column label="311" align="center" prop="bidVolumeF" />-->
      <el-table-column label="买入价" align="center" prop="bidPrice"/>
      <!--      <el-table-column label="0.040" align="center" prop="bidPriceF" />-->
      <el-table-column label="价格变化(%)" align="center" prop="priceChange"/>
      <!--      <el-table-column label="-11.1" align="center" prop="priceChangeF" />-->
      <el-table-column label="卖价" align="center" prop="askPrice"/>
      <!--      <el-table-column label="0.045" align="center" prop="askPriceF" />-->
      <el-table-column label="卖出量" align="center" prop="askVolume"/>
      <!--      <el-table-column label="85" align="center" prop="askVolumeF" />-->
      <el-table-column label="交易量" align="center" prop="tradeVolume"/>
      <!--      <el-table-column label="4" align="center" prop="tradeVolumeF" />-->
      <el-table-column label="最高流动性" align="center" prop="highresp"/>
      <el-table-column label="低时间衰减" align="center" prop="lowtimedecay"/>
      <!--      <el-table-column label="17 Sep 24" align="center" prop="listDate" />-->
      <!--      <el-table-column label="false" align="center" prop="appchexpry" />-->
      <el-table-column label="新授权" align="center" prop="newwarrant"/>
      <el-table-column label="库存售罄" align="center" prop="soldout"/>
      <el-table-column label="最快响应" align="center" prop="moreresponsive"/>
      <el-table-column label="到期" align="center" prop="maturity"/>
      <!--      <el-table-column label="5326CE" align="center" prop="ticker" />-->
      <el-table-column label="基础价格" align="center" prop="underlyingPrice"/>
      <!--      <el-table-column label="99SMART-CE" align="center" prop="dsplyNmll" />-->
      <el-table-column label="高效杠杆" align="center" prop="higheffectivegearing"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['malaysiawarrants:malaysiawarrants:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['malaysiawarrants:malaysiawarrants:remove']"
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

    <!-- 添加或修改malaysiawarrants对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="认股权证名称" prop="dwSymbol">
          <el-input v-model="form.dwSymbol" placeholder="请输入认股权证名称"/>
        </el-form-item>
        <!--        <el-form-item label="SPEE_tc.KL" prop="ric">-->
        <!--          <el-input v-model="form.ric" placeholder="请输入SPEE_tc.KL" />-->
        <!--        </el-form-item>-->
        <el-form-item label="元数据" prop="underlyingSymbol">
          <el-input v-model="form.underlyingSymbol" placeholder="请输入元数据"/>
        </el-form-item>
        <el-form-item label="名称" prop="underlyingName">
          <el-input v-model="form.underlyingName" placeholder="请输入名称"/>
        </el-form-item>
        <!--        <el-form-item label="SPEE.KL" prop="underlyingRic">-->
        <!--          <el-input v-model="form.underlyingRic" placeholder="请输入SPEE.KL" />-->
        <!--        </el-form-item>-->
        <el-form-item label="行使价" prop="exercisePrice">
          <el-input v-model="form.exercisePrice" placeholder="请输入行使价"/>
        </el-form-item>
        <el-form-item label="行使比率" prop="convRatio">
          <el-input v-model="form.convRatio" placeholder="请输入行使比率"/>
        </el-form-item>
        <!--        <el-form-item label="0.200" prop="dwps">-->
        <!--          <el-input v-model="form.dwps" placeholder="请输入0.200" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="Macquarie" prop="issuer">-->
        <!--          <el-input v-model="form.issuer" placeholder="请输入Macquarie" />-->
        <!--        </el-form-item>-->
        <el-form-item label="增量(%)" prop="delta">
          <el-input v-model="form.delta" placeholder="请输入增量(%)"/>
        </el-form-item>
        <el-form-item label="隐含波动率(%)" prop="impliedVolalitiy">
          <el-input v-model="form.impliedVolalitiy" placeholder="请输入隐含波动率(%)"/>
        </el-form-item>
        <el-form-item label="敏感度" prop="sensitivity">
          <el-input v-model="form.sensitivity" placeholder="请输入敏感度"/>
        </el-form-item>
        <!--        <el-form-item label="-0.52" prop="theta">-->
        <!--          <el-input v-model="form.theta" placeholder="请输入-0.52" />-->
        <!--        </el-form-item>-->
        <el-form-item label="有效杠杆" prop="effectiveGearing">
          <el-input v-model="form.effectiveGearing" placeholder="请输入有效杠杆"/>
        </el-form-item>
        <!--        <el-form-item label="28 Apr 25" prop="ltDate">-->
        <!--          <el-input v-model="form.ltDate" placeholder="请输入28 Apr 25" />-->
        <!--        </el-form-item>-->
        <el-form-item label="OTM" prop="moneyness">
          <el-input v-model="form.moneyness" placeholder="请输入OTM"/>
        </el-form-item>
        <!--        <el-form-item label="4" prop="moneynessPercent">-->
        <!--          <el-input v-model="form.moneynessPercent" placeholder="请输入4" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="4% OTM" prop="moneynessC">-->
        <!--          <el-input v-model="form.moneynessC" placeholder="请输入4% OTM" />-->
        <!--        </el-form-item>-->
        <el-form-item label="买入量" prop="bidVolume">
          <el-input v-model="form.bidVolume" placeholder="请输入买入量"/>
        </el-form-item>
        <!--        <el-form-item label="311" prop="bidVolumeF">-->
        <!--          <el-input v-model="form.bidVolumeF" placeholder="请输入311" />-->
        <!--        </el-form-item>-->
        <el-form-item label="买入价" prop="bidPrice">
          <el-input v-model="form.bidPrice" placeholder="请输入买入价"/>
        </el-form-item>
        <!--        <el-form-item label="0.040" prop="bidPriceF">-->
        <!--          <el-input v-model="form.bidPriceF" placeholder="请输入0.040" />-->
        <!--        </el-form-item>-->
        <el-form-item label="价格变化(%)" prop="priceChange">
          <el-input v-model="form.priceChange" placeholder="请输入价格变化(%)"/>
        </el-form-item>
        <!--        <el-form-item label="-11.1" prop="priceChangeF">-->
        <!--          <el-input v-model="form.priceChangeF" placeholder="请输入-11.1" />-->
        <!--        </el-form-item>-->
        <el-form-item label="卖价" prop="askPrice">
          <el-input v-model="form.askPrice" placeholder="请输入卖价"/>
        </el-form-item>
        <!--        <el-form-item label="0.045" prop="askPriceF">-->
        <!--          <el-input v-model="form.askPriceF" placeholder="请输入0.045" />-->
        <!--        </el-form-item>-->
        <el-form-item label="卖出量" prop="askVolume">
          <el-input v-model="form.askVolume" placeholder="请输入卖出量"/>
        </el-form-item>
        <!--        <el-form-item label="85" prop="askVolumeF">-->
        <!--          <el-input v-model="form.askVolumeF" placeholder="请输入85" />-->
        <!--        </el-form-item>-->
        <el-form-item label="交易量" prop="tradeVolume">
          <el-input v-model="form.tradeVolume" placeholder="请输入交易量"/>
        </el-form-item>
        <!--        <el-form-item label="4" prop="tradeVolumeF">-->
        <!--          <el-input v-model="form.tradeVolumeF" placeholder="请输入4" />-->
        <!--        </el-form-item>-->
        <el-form-item label="最高流动性" prop="highresp">
          <el-input v-model="form.highresp" placeholder="请输入最高流动性"/>
        </el-form-item>
        <el-form-item label="低时间衰减" prop="lowtimedecay">
          <el-input v-model="form.lowtimedecay" placeholder="请输入低时间衰减"/>
        </el-form-item>
        <!--        <el-form-item label="17 Sep 24" prop="listDate">-->
        <!--          <el-input v-model="form.listDate" placeholder="请输入17 Sep 24" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="false" prop="appchexpry">-->
        <!--          <el-input v-model="form.appchexpry" placeholder="请输入false" />-->
        <!--        </el-form-item>-->
        <el-form-item label="新授权" prop="newwarrant">
          <el-input v-model="form.newwarrant" placeholder="请输入新授权"/>
        </el-form-item>
        <el-form-item label="库存售罄" prop="soldout">
          <el-input v-model="form.soldout" placeholder="请输入库存售罄"/>
        </el-form-item>
        <el-form-item label="最快响应" prop="moreresponsive">
          <el-input v-model="form.moreresponsive" placeholder="请输入最快响应"/>
        </el-form-item>
        <el-form-item label="到期" prop="maturity">
          <el-input v-model="form.maturity" placeholder="请输入到期"/>
        </el-form-item>
        <!--        <el-form-item label="5326CE" prop="ticker">-->
        <!--          <el-input v-model="form.ticker" placeholder="请输入5326CE" />-->
        <!--        </el-form-item>-->
        <el-form-item label="基础价格" prop="underlyingPrice">
          <el-input v-model="form.underlyingPrice" placeholder="请输入基础价格"/>
        </el-form-item>
        <!--        <el-form-item label="99SMART-CE" prop="dsplyNmll">-->
        <!--          <el-input v-model="form.dsplyNmll" placeholder="请输入99SMART-CE" />-->
        <!--        </el-form-item>-->
        <el-form-item label="高效杠杆" prop="higheffectivegearing">
          <el-input v-model="form.higheffectivegearing" placeholder="请输入高效杠杆"/>
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
  addMalaysiawarrants,
  delMalaysiawarrants,
  getMalaysiawarrants,
  listMalaysiawarrants,
  updateMalaysiawarrants
} from "@/api/malaysiawarrants/malaysiawarrants";

export default {
  name: "Malaysiawarrants",
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
      // malaysiawarrants表格数据
      malaysiawarrantsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dwSymbol: null,
        underlyingName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询malaysiawarrants列表 */
    getList() {
      this.loading = true;
      listMalaysiawarrants(this.queryParams).then(response => {
        this.malaysiawarrantsList = response.rows;
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
        dwSymbol: null,
        ric: null,
        underlyingSymbol: null,
        underlyingName: null,
        underlyingRic: null,
        type: null,
        exercisePrice: null,
        convRatio: null,
        dwps: null,
        issuer: null,
        delta: null,
        impliedVolalitiy: null,
        sensitivity: null,
        theta: null,
        effectiveGearing: null,
        ltDate: null,
        moneyness: null,
        moneynessPercent: null,
        moneynessC: null,
        bidVolume: null,
        bidVolumeF: null,
        bidPrice: null,
        bidPriceF: null,
        priceChange: null,
        priceChangeF: null,
        askPrice: null,
        askPriceF: null,
        askVolume: null,
        askVolumeF: null,
        tradeVolume: null,
        tradeVolumeF: null,
        highresp: null,
        lowtimedecay: null,
        listDate: null,
        appchexpry: null,
        newwarrant: null,
        soldout: null,
        moreresponsive: null,
        maturity: null,
        ticker: null,
        underlyingPrice: null,
        dsplyNmll: null,
        higheffectivegearing: null,
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
      this.ids = selection.map(item => item.dwSymbol)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加malaysiawarrants";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const dwSymbol = row.dwSymbol || this.ids
      getMalaysiawarrants(dwSymbol).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改malaysiawarrants";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dwSymbol != null) {
            updateMalaysiawarrants(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMalaysiawarrants(this.form).then(response => {
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
      const dwSymbols = row.dwSymbol || this.ids;
      this.$modal.confirm('是否确认删除malaysiawarrants编号为"' + dwSymbols + '"的数据项？').then(function () {
        return delMalaysiawarrants(dwSymbols);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('malaysiawarrants/malaysiawarrants/export', {
        ...this.queryParams
      }, `malaysiawarrants_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
