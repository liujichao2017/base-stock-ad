<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="持仓号" prop="positionSn">
        <el-input
          v-model="queryParams.positionSn"
          placeholder="请输入持仓号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员Id" prop="memberId">
        <el-input
          v-model="queryParams.memberId"
          placeholder="请输入会员Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员手机号" prop="memberId">
        <el-input
          v-model="queryParams.memberPhone"
          placeholder="请输入会员手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="代理" prop="userId">
        <AgentSelect @val="(val)=>queryParams.userId = val"></AgentSelect>
      </el-form-item>
      <el-form-item label="市场ID" prop="marketId">
        <MarketSelect ref="MarketSelect" @input="(val)=>{queryParams.marketId = val}"/>
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
      <el-form-item label="股票GID" prop="stockGid">
        <el-input
          v-model="queryParams.stockGid"
          placeholder="请输入股票GID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="是否锁仓" prop="isLock">
        <el-select v-model="queryParams.isLock" placeholder="请选择是否锁仓" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="来源" prop="source">
        <el-select v-model="queryParams.source" placeholder="请选择来源来源" clearable>
          <el-option
            v-for="dict in dict.type.funds_source"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="来源ID" prop="sourceId">
        <el-input
          v-model="queryParams.sourceId"
          placeholder="请输入来源ID"
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
      <!--          v-hasPermi="['stock:position:add']"-->
      <!--        >建仓-->
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
      <!--          v-hasPermi="['stock:position:remove']"-->
      <!--        >删除-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :default-expand-all="true" :data="positionList"
              @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="3">
                <el-form-item label="总金额">
                  <el-tag>{{ props.row.totalAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="本金">
                  <el-tag>{{ props.row.principalAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="购买方向">
                  <dict-tag :options="dict.type.stock_direction" :value="props.row.direction"/>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="杠杆">
                  <el-tag>{{ props.row.lever }}</el-tag>
                </el-form-item>
              </el-col>

            </el-row>
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>

              <el-col :span="3">
                <el-form-item label="买入手续费">
                  <el-tag>{{ props.row.buyFee }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="卖出手续费">
                  <el-tag>{{ props.row.sellFee }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="其他手续费">
                  <el-tag>{{ props.row.otherFee }}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="3">
                <el-form-item label="来源">
                  <dict-tag :options="dict.type.funds_source" :value="props.row.source"/>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="来源ID">
                  <el-tag>{{ props.row.sourceId }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col v-if="props.row.lockMsg" :span="3">
                <el-form-item label="锁仓原因">
                  <el-tag>{{ props.row.lockMsg }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="平仓时间">
                  <el-tag v-if="props.row.sellTimeLimit">{{ props.row.sellTimeLimit }}</el-tag>
                  <el-tag v-else> 跟随系统</el-tag>
                </el-form-item>
              </el-col>


            </el-row>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="市场" width="50px" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="实盘" width="50px" align="center" prop="accountType">
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
      <el-table-column label="持仓号" align="center" prop="positionSn"/>
      <el-table-column label="股票名称" align="center" prop="stockName"/>
      <el-table-column label="股票代码" align="center" prop="stockCode"/>
      <el-table-column label="股票GID" width="80px" align="center" prop="stockGid"/>
      <el-table-column v-if="queryParams.state == 1" width="80px" label="现价" align="center" prop="sellOrderPrice"/>
      <el-table-column label="买入价格" align="center" prop="buyOrderPrice"/>
      <el-table-column label="购买时间" align="center" prop="buyOrderTime" width="180"/>
      <el-table-column v-if="queryParams.state == 0" label="卖出价格" align="center" prop="sellOrderPrice"/>
      <el-table-column v-if="queryParams.state == 0" label="卖出时间" align="center" prop="sellOrderTime"
                       width="180"/>

      <el-table-column label="买入数量" align="center" prop="num"/>
      <el-table-column label="盈亏" align="center" prop="profitAndLose"/>
      <el-table-column label="总盈亏" align="center" prop="allProfitAndLose"/>
      <el-table-column label="是否锁仓" align="center" prop="isLock">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isLock"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="queryParams.state == 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:position:edit']"
          >锁仓
          </el-button>
          <el-button
            v-if="queryParams.state == 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="sell(scope.row)"
            v-hasPermi="['stock:position:sell']"
          >强制平仓
          </el-button>
          <el-button
            v-if="queryParams.state == 1"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:position:remove']"
          >删除退款
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

    <!--    <StockBuy ref="stockBuy" :getList="getList"/>-->

    <!-- 添加或修改用户持仓对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="持仓号" prop="positionSn">-->
        <!--          <el-input v-model="form.positionSn" placeholder="请输入持仓号"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="会员Id" prop="memberId">-->
        <!--          <el-input v-model="form.memberId" placeholder="请输入会员Id"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="市场ID" prop="marketId">-->
        <!--          <el-input v-model="form.marketId" placeholder="请输入市场ID"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="股票名称" prop="stockName">-->
        <!--          <el-input v-model="form.stockName" placeholder="请输入股票名称"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="股票代码" prop="stockCode">-->
        <!--          <el-input v-model="form.stockCode" placeholder="请输入股票代码"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="股票GID" prop="stockGid">-->
        <!--          <el-input v-model="form.stockGid" placeholder="请输入股票GID"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="购买时间" prop="buyOrderTime">-->
        <!--          <el-date-picker clearable-->
        <!--                          v-model="form.buyOrderTime"-->
        <!--                          type="date"-->
        <!--                          value-format="yyyy-MM-dd"-->
        <!--                          placeholder="请选择购买时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入价格" prop="buyOrderPrice">-->
        <!--          <el-input v-model="form.buyOrderPrice" placeholder="请输入买入价格"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="卖出时间" prop="sellOrderTime">-->
        <!--          <el-date-picker clearable-->
        <!--                          v-model="form.sellOrderTime"-->
        <!--                          type="date"-->
        <!--                          value-format="yyyy-MM-dd"-->
        <!--                          placeholder="请选择卖出时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="卖出价格" prop="sellOrderPrice">-->
        <!--          <el-input v-model="form.sellOrderPrice" placeholder="请输入卖出价格"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="购买方向" prop="direction">-->
        <!--          <el-input v-model="form.direction" placeholder="请输入购买方向"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入数量" prop="num">-->
        <!--          <el-input v-model="form.num" placeholder="请输入买入数量"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="杠杆" prop="lever">-->
        <!--          <el-input v-model="form.lever" placeholder="请输入杠杆"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="总金额" prop="totalAmt">-->
        <!--          <el-input v-model="form.totalAmt" placeholder="请输入总金额"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="本金" prop="principalAmt">-->
        <!--          <el-input v-model="form.principalAmt" placeholder="请输入本金"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="买入手续费" prop="buyFee">-->
        <!--          <el-input v-model="form.buyFee" placeholder="请输入买入手续费"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="卖出手续费" prop="sellFee">-->
        <!--          <el-input v-model="form.sellFee" placeholder="请输入卖出手续费"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="其他手续费" prop="otherFee">-->
        <!--          <el-input v-model="form.otherFee" placeholder="请输入其他手续费"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="盈亏" prop="profitAndLose">-->
        <!--          <el-input v-model="form.profitAndLose" placeholder="请输入盈亏"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="总盈亏" prop="allProfitAndLose">-->
        <!--          <el-input v-model="form.allProfitAndLose" placeholder="请输入总盈亏"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="是否锁仓" prop="isLock">
          <el-select v-model="form.isLock" placeholder="请选择是否锁仓">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="锁仓时间" prop="sellOrderTime">
          <el-date-picker clearable
                          v-model="form.sellTimeLimit"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="锁仓时间">
          </el-date-picker>
          <br/>
          <el-tag>优先级低于是否锁仓</el-tag><br/>
          <el-tag>当 是否锁车 为否 才生效</el-tag>
        </el-form-item>
        <el-form-item label="锁仓原因" prop="lockMsg">
          <el-input v-model="form.lockMsg" placeholder="请输入锁仓原因"/>
        </el-form-item>
        <!--        <el-form-item label="来源" prop="source">-->
        <!--          <el-select v-model="form.source" placeholder="请选择来源">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.funds_source"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--              :value="dict.value"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="来源ID" prop="sourceId">-->
        <!--          <el-input v-model="form.sourceId" placeholder="请输入来源ID"/>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addPosition, delPosition, getPosition, listPosition, sellPosition, updatePosition} from "@/api/stock/position";
import MarketSelect from "@/components/marketSelect/index.vue";
import MarketSpan from "@/components/marketSpan/index.vue";
import StockBuy from "@/components/StockBuy/index.vue";
import AgentSelect from "@/components/AgentSelect/index.vue";

export default {
  name: "Position",
  components: {AgentSelect, MarketSpan, MarketSelect, StockBuy},
  dicts: ['funds_source', 'yes_no', 'stock_direction'],
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
      // 用户持仓表格数据
      positionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        state: this.$route.query.state,
        positionSn: null,
        memberId: null,
        marketId: null,
        stockName: null,
        stockCode: null,
        stockGid: null,
        buyOrderTime: null,
        buyOrderPrice: null,
        sellOrderTime: null,
        sellOrderPrice: null,
        direction: null,
        num: null,
        lever: null,
        totalAmt: null,
        principalAmt: null,
        buyFee: null,
        sellFee: null,
        otherFee: null,
        profitAndLose: null,
        allProfitAndLose: null,
        isLock: null,
        lockMsg: null,
        source: null,
        sourceId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        positionSn: [
          {required: true, message: "持仓号不能为空", trigger: "blur"}
        ],
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
        ],
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        stockName: [
          {required: true, message: "股票名称不能为空", trigger: "blur"}
        ],
        stockCode: [
          {required: true, message: "股票代码不能为空", trigger: "blur"}
        ],
        stockGid: [
          {required: true, message: "股票GID不能为空", trigger: "blur"}
        ],
        buyOrderTime: [
          {required: true, message: "购买时间不能为空", trigger: "blur"}
        ],
        buyOrderPrice: [
          {required: true, message: "买入价格不能为空", trigger: "blur"}
        ],
        num: [
          {required: true, message: "买入数量不能为空", trigger: "blur"}
        ],
        lever: [
          {required: true, message: "杠杆不能为空", trigger: "blur"}
        ],
        totalAmt: [
          {required: true, message: "总金额不能为空", trigger: "blur"}
        ],
        principalAmt: [
          {required: true, message: "本金不能为空", trigger: "blur"}
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
    '$route'(to, from) {


      this.getList()
    }
  },
  methods: {
    /** 查询用户持仓列表 */
    getList() {
      this.loading = true;
      this.queryParams.state = this.$route.query.state;
      listPosition(this.queryParams).then(response => {
        this.positionList = response.rows;
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
        positionSn: null,
        memberId: null,
        marketId: null,
        stockName: null,
        stockCode: null,
        stockGid: null,
        buyOrderTime: null,
        buyOrderPrice: null,
        sellOrderTime: null,
        sellOrderPrice: null,
        direction: null,
        num: null,
        lever: null,
        totalAmt: null,
        principalAmt: null,
        sellTimeLimit: null,
        buyFee: null,
        sellFee: null,
        otherFee: null,
        profitAndLose: null,
        allProfitAndLose: null,
        isLock: null,
        lockMsg: null,
        source: null,
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
      this.$refs.MarketSelect.reset();
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
      this.$refs.stockBuy.open = true;
      this.title = "添加用户持仓";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPosition(id).then(response => {
        this.form.id = response.data.id;
        this.form.isLock = response.data.isLock;
        this.form.sellTimeLimit = response.data.sellTimeLimit;
        this.form.lockMsg = response.data.lockMsg;
        this.open = true;
        this.title = "修改用户持仓";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePosition(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPosition(this.form).then(response => {
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
      this.$modal.confirm('删除持仓单,就只会退回本金').then(function () {
        return delPosition(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 删除按钮操作 */
    sell(row) {
      const id = row.id;
      this.$prompt('请输入平仓价格,不填则为最新价格', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        sellPosition({
          id: id,
          sellOrderPrice: value,
        }).then(response => {
          this.$modal.msgSuccess("平仓成功");
          this.getList();
        });
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/position/export', {
        ...this.queryParams
      }, `position_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
