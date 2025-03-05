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
      <el-form-item label="AI交易名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入AI交易名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ai_record_status"
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['ai:record:add']"-->
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
      <!--          v-hasPermi="['ai:record:edit']"-->
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
      <!--          v-hasPermi="['ai:record:remove']"-->
      <!--        >删除-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['ai:record:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-button
        size="mini"
        type="text"
        icon="el-icon-edit"
        @click="handleUpdate"
        v-hasPermi="['ai:record:position']"
      >建仓
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable="checkStatus" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
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
      <el-table-column label="AI交易名称" align="center" prop="name"/>
      <el-table-column label="交易成功率(%)" align="center" prop="tradeSucRate"/>
      <el-table-column label="预期收益(%)" align="center" prop="futureEarningsRate"/>
      <el-table-column label="交易周期(天)" align="center" prop="tradeCycle"/>
      <el-table-column label="买入金额" align="center" prop="buyAmt"/>
      <el-table-column label="实际收益" align="center" prop="incomeAmt"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ai_record_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status == 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateStatus(scope.row,2)"
            v-hasPermi="['ai:record:edit']"
          >同意
          </el-button>
          <el-button
            v-if="scope.row.status == 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateStatus(scope.row,3)"
            v-hasPermi="['ai:record:edit']"
          >拒绝
          </el-button>
          <el-button
            v-if="scope.row.status == 2"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ai:record:position']"
          >建仓
          </el-button>
          <el-button
            v-if="scope.row.status == 2"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateStatus(scope.row,4)"
            v-hasPermi="['ai:record:position']"
          >结算
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ai:record:remove']"
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

    <!-- 添加或修改AI交易记录对话框 -->
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="open" width="1500px" append-to-body>
      <el-form ref="form" :model="positionInfo" :rules="rules" label-width="80px">
        <el-row>
          <el-form-item label="AI交易ID">
            <el-tag v-for="id in positionInfo.aiRecordIds" :key="id">{{ id }}</el-tag>

          </el-form-item>
        </el-row>
        <el-row v-for="(stock,index) in positionInfo.stocks" :key="index">
          <el-col :span="3">
            <el-form-item label="股票GID">
              <el-input v-model="stock.stockGid" placeholder="请输入股票GID"/>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label="买入数量">
              <el-input v-model="stock.num" placeholder="请输入股票GID"/>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label="买入价格">
              <el-input v-model="stock.buyAmt" placeholder="请输入股票GID"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="买入时间" prop="publishTime">
              <el-date-picker clearable
                              v-model="stock.buyTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择买入时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label="卖出价格">
              <el-input v-model="stock.sellAmt" placeholder="请输入股票GID"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="卖出时间" prop="publishTime">
              <el-date-picker clearable
                              v-model="stock.sellTime"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择买入时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <br/>
          </el-col>
          <el-col :span="4">
            <el-button @click="positionInfo.stocks.push({})">+</el-button>
            <el-button @click="positionInfo.stocks.pop()">-</el-button>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {aiPosition, delRecord, listRecord, updateRecord} from "@/api/ai/record";

export default {
  name: "Record",
  dicts: ['ai_record_status'],
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
      // AI交易记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        marketId: null,
        aiTradeId: null,
        memberId: null,
        name: null,
        tradeSucRate: null,
        futureEarningsRate: null,
        tradeCycle: null,
        buyAmt: null,
        incomeAmt: null,
        status: null,
      },
      // 表单参数
      form: {},
      positionInfo: {},
      // 表单校验
      rules: {
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询AI交易记录列表 */
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
        marketId: null,
        aiTradeId: null,
        memberId: null,
        name: null,
        tradeSucRate: null,
        futureEarningsRate: null,
        tradeCycle: null,
        buyAmt: null,
        incomeAmt: null,
        status: null,
        createTime: null,
        updateTime: null
      };
      this.positionInfo = {
        aiRecordIds: null,
        stocks: null,
      }
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
    checkStatus(row, index) {
      return row.status === '2';
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加AI交易记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      if (row && row.id) {
        this.positionInfo.aiRecordIds = [row.id];
        this.positionInfo.stocks = [{}];
        this.open = true;
        this.title = "建仓";
      }
      if (this.ids && this.ids.length !== 0) {
        this.positionInfo.aiRecordIds = this.ids;
        this.positionInfo.stocks = [{}];
        this.open = true;
        this.title = "建仓";
      }
      if (!this.positionInfo.aiRecordIds || this.positionInfo.aiRecordIds.length === 0) {
        this.$modal.msgError("请选择记录");
      }

    },
    /** 修改按钮操作 */
    updateStatus(row, status) {
      this.$modal.confirm('是否审批记录').then(function () {
        return updateRecord({
          id: row.id,
          status: status,
        });
      }).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      }).catch(() => {
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          aiPosition(this.positionInfo).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除AI交易记录编号为"' + ids + '"的数据项？').then(function () {
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ai/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
