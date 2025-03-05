<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场ID" prop="marketId">
        <MarketSelect ref="marketSelect" @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="代理" prop="userId">
        <AgentSelect @val="(val)=>queryParams.userId = val"></AgentSelect>
      </el-form-item>
      <el-form-item label="会员Id">
        <el-input
          v-model="queryParams.memberId"
          placeholder="请输入会员Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员手机号">
        <el-input
          v-model="queryParams.memberPhone"
          placeholder="请输入会员手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.member_borrow_record_status"
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
          v-hasPermi="['borrow:record:add']"
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
          v-hasPermi="['borrow:record:edit']"
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
          v-hasPermi="['borrow:record:remove']"
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
<!--          v-hasPermi="['borrow:record:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :default-expand-all="true" :data="recordList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="3">
                <el-form-item label="总获利">
                  <el-tag>{{ scope.row.totalIncome }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="每天应该返利">
                  <el-tag>{{ scope.row.dayIncome }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="股数/张">
                  <el-tag>{{ scope.row.num }}</el-tag>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="周期结束时间">
                  <el-tag>{{ parseTime(scope.row.cycleTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</el-tag>
                </el-form-item>
              </el-col>


            </el-row>
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="6">
                <el-form-item label="买入时间">
                  <el-tag>{{ parseTime(scope.row.buyTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col v-if="scope.row.sellTime" :span="6">
                <el-form-item label="卖出时间">
                  <el-tag>{{ parseTime(scope.row.sellTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="股票名称" align="center" prop="stockName"/>
      <el-table-column label="股票代码" align="center" prop="stockCode"/>
      <el-table-column label="会员ID" align="center" prop="memberId" :show-overflow-tooltip="true"/>
      <el-table-column label="会员名称" align="center" prop="memberRealName"/>
      <el-table-column label="会员手机" align="center" prop="memberPhone"/>
      <el-table-column label="代理" align="center" prop="userId">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}/{{ scope.row.userId }}</span>
        </template>
      </el-table-column>

      <el-table-column label="买入张数" align="center" prop="buyNum"/>
      <el-table-column label="通过张数" align="center" prop="applyNum"/>
      <el-table-column label="买入价格" align="center" prop="buyPrice"/>
      <el-table-column label="买入总价" align="center" prop="buyAmt"/>


      <el-table-column label="周期" align="center" prop="cycle"/>

      <el-table-column label="日利率(%)" align="center" prop="rebate"/>
      <el-table-column label="到账收益" align="center" prop="income"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.member_borrow_record_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status == 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['borrow:record:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['borrow:record:remove']"
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

    <!-- 添加或修改借劵记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="股票名称" prop="stockName">-->
        <!--          <el-input disabled v-model="form.stockName" placeholder="请输入股票名称" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="股票代码" prop="stockCode">-->
        <!--          <el-input v-model="form.stockCode" placeholder="请输入股票代码" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="客户id" prop="memberId">-->
        <!--          <el-input v-model="form.memberId" placeholder="请输入客户id" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="股数/张" prop="num">-->
        <!--          <el-input v-model="form.num" placeholder="请输入股数/张" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入张数" prop="buyNum">-->
        <!--          <el-input v-model="form.buyNum" placeholder="请输入买入张数" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入价格" prop="buyPrice">-->
        <!--          <el-input v-model="form.buyPrice" placeholder="请输入买入价格" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入总价" prop="buyAmt">-->
        <!--          <el-input v-model="form.buyAmt" placeholder="请输入买入总价" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入时间" prop="buyTime">-->
        <!--          <el-date-picker clearable-->
        <!--            v-model="form.buyTime"-->
        <!--            type="date"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="请选择买入时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="卖出时间" prop="sellTime">-->
        <!--          <el-date-picker clearable-->
        <!--            v-model="form.sellTime"-->
        <!--            type="date"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="请选择卖出时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="周期" prop="cycle">-->
        <!--          <el-input v-model="form.cycle" placeholder="请输入周期" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="周期结束时间" prop="cycleTime">-->
        <!--          <el-date-picker clearable-->
        <!--            v-model="form.cycleTime"-->
        <!--            type="date"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="请选择周期结束时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="日利率(%)" prop="rebate">-->
        <!--          <el-input v-model="form.rebate" placeholder="请输入日利率(%)" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="总获利" prop="totalIncome">-->
        <!--          <el-input v-model="form.totalIncome" placeholder="请输入总获利" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="每天应该返利" prop="dayIncome">-->
        <!--          <el-input v-model="form.dayIncome" placeholder="请输入每天应该返利" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="到账收益" prop="income">-->
        <!--          <el-input v-model="form.income" placeholder="请输入到账收益" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="市场" prop="marketId">-->
        <!--          <el-input v-model="form.marketId" placeholder="请输入市场" />-->
        <!--        </el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.member_borrow_record_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.status == 2" label="通过张数" prop="applyNum">
          <el-input v-model="form.applyNum" placeholder="请输入通过张数"/>
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
import {addRecord, delRecord, getRecord, listRecord, updateRecord} from "@/api/borrow/record";

export default {
  name: "Record",
  dicts: ['member_borrow_record_status'],
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
      // 借劵记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stockName: null,
        stockCode: null,
        memberId: null,
        userId: null,
        memberPhone: null,
        marketId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
    /** 查询借劵记录列表 */
    getList() {
      this.loading = true;
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        memberId: null,
        num: null,
        buyNum: null,
        applyNum: null,
        buyPrice: null,
        buyAmt: null,
        buyTime: null,
        sellTime: null,
        cycle: null,
        cycleTime: null,
        rebate: null,
        totalIncome: null,
        dayIncome: null,
        income: null,
        marketId: null,
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
      this.title = "添加借劵记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改借劵记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
      this.$modal.confirm('是否删除记录?(谨慎操作!)').then(function () {
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('borrow/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
