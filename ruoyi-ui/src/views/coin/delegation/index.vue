<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="交易对" prop="symbol">
        <el-input
          v-model="queryParams.symbol"
          placeholder="请输入交易对"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="方向" prop="direction">
        <el-select v-model="queryParams.direction" placeholder="请选择方向" clearable>
          <el-option
            v-for="dict in dict.type.coin_direction"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="价格类型" prop="priceType">
        <el-select v-model="queryParams.priceType" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.coin_contract_delegation_price_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="交割类型" prop="deliveryType">
        <el-select v-model="queryParams.deliveryType" placeholder="请选择交割类型" clearable>
          <el-option
            v-for="dict in dict.type.coin_contract_delegation_delivery_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.coin_contract_delegation_status"
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
      <!--          v-hasPermi="['coin:delegation:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['coin:delegation:edit']"-->
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
      <!--          v-hasPermi="['coin:delegation:remove']"-->
      <!--        >删除-->
      <!--        </el-button>-->
      <!--      </el-col>-->

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :default-expand-all="true" :data="delegationList"
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
                <el-form-item label="杠杆">
                  <el-tag>{{ scope.row.level }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="方向">
                  <el-tag>
                    <dict-tag :options="dict.type.coin_direction" :value="scope.row.direction"/>
                  </el-tag>
                </el-form-item>
              </el-col>
              <el-col v-if="scope.row.deliveryType === '2'" :span="12">
                <el-form-item label="交割配置配置">
                  <el-tag>{{ scope.row.deliveryName }}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="3">
                <el-form-item label="手续费">
                  <el-tag>{{ scope.row.feeAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col v-if="scope.row.stopProfitAmt" :span="3">
                <el-form-item label="止盈价格">
                  <el-tag>{{ scope.row.stopProfitAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col v-if="scope.row.stopLossAmt" :span="3">
                <el-form-item label="止损价格">
                  <el-tag>{{ scope.row.stopLossAmt }}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="会员ID" align="center" prop="memberId" :show-overflow-tooltip="true"/>
      <el-table-column label="会员名称" align="center" prop="memberRealName"/>
      <el-table-column label="会员手机" align="center" prop="memberPhone"/>
      <el-table-column label="代理" align="center" prop="userId">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}/{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交易对" align="center" prop="symbol"/>
      <el-table-column label="价格类型" align="center" prop="priceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coin_contract_delegation_price_type" :value="scope.row.priceType"/>
        </template>
      </el-table-column>
      <el-table-column label="限价" align="center" prop="limitPrice"/>
      <el-table-column label="交割类型" align="center" prop="deliveryType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coin_contract_delegation_delivery_type" :value="scope.row.deliveryType"/>
        </template>
      </el-table-column>
      <el-table-column label="交割配置配置" align="center" prop="deliveryId"/>
      <el-table-column label="张数" align="center" prop="sheetNum"/>
      <el-table-column label="币数" align="center" prop="coinNum"/>
      <el-table-column label="总价" align="center" prop="totalAmt"/>
      <el-table-column label="保证金" align="center" prop="marginAmt"/>

      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coin_contract_delegation_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-edit"-->
      <!--            @click="handleUpdate(scope.row)"-->
      <!--            v-hasPermi="['coin:delegation:edit']"-->
      <!--          >修改-->
      <!--          </el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['coin:delegation:remove']"-->
      <!--          >删除-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改虚拟币合约委托对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户ID" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入客户ID"/>
        </el-form-item>
        <el-form-item label="交易对" prop="symbol">
          <el-input v-model="form.symbol" placeholder="请输入交易对"/>
        </el-form-item>
        <el-form-item label="方向" prop="direction">
          <el-select v-model="form.direction" placeholder="请选择方向">
            <el-option
              v-for="dict in dict.type.coin_direction"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="priceType">
          <el-select v-model="form.priceType" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.coin_contract_delegation_price_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="限价" prop="limitPrice">
          <el-input v-model="form.limitPrice" placeholder="请输入限价"/>
        </el-form-item>
        <el-form-item label="交割类型" prop="deliveryType">
          <el-select v-model="form.deliveryType" placeholder="请选择交割类型">
            <el-option
              v-for="dict in dict.type.coin_contract_delegation_delivery_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="交割配置配置" prop="deliveryId">
          <el-input v-model="form.deliveryId" placeholder="请输入交割配置配置"/>
        </el-form-item>
        <el-form-item label="币数" prop="coinNum">
          <el-input v-model="form.coinNum" placeholder="请输入币数"/>
        </el-form-item>
        <el-form-item label="张数" prop="sheetNum">
          <el-input v-model="form.sheetNum" placeholder="请输入张数"/>
        </el-form-item>
        <el-form-item label="杠杆" prop="level">
          <el-input v-model="form.level" placeholder="请输入杠杆"/>
        </el-form-item>
        <el-form-item label="总价" prop="totalAmt">
          <el-input v-model="form.totalAmt" placeholder="请输入总价"/>
        </el-form-item>
        <el-form-item label="保证金" prop="marginAmt">
          <el-input v-model="form.marginAmt" placeholder="请输入保证金"/>
        </el-form-item>
        <el-form-item label="手续费" prop="feeAmt">
          <el-input v-model="form.feeAmt" placeholder="请输入手续费"/>
        </el-form-item>
        <el-form-item label="止盈价格" prop="stopProfitAmt">
          <el-input v-model="form.stopProfitAmt" placeholder="请输入止盈价格"/>
        </el-form-item>
        <el-form-item label="止损价格" prop="stopLossAmt">
          <el-input v-model="form.stopLossAmt" placeholder="请输入止损价格"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.coin_contract_delegation_status"
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
import {addDelegation, delDelegation, getDelegation, listDelegation, updateDelegation} from "@/api/coin/delegation";

export default {
  name: "Delegation",
  dicts: ['coin_direction', 'coin_contract_delegation_status', 'coin_contract_delegation_price_type', 'coin_contract_delegation_delivery_type'],
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
      // 虚拟币合约委托表格数据
      delegationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: null,
        userId: null,
        memberPhone: null,
        symbol: null,
        direction: null,
        priceType: null,
        limitPrice: null,
        deliveryType: null,
        deliveryId: null,
        coinNum: null,
        sheetNum: null,
        level: null,
        totalAmt: null,
        marginAmt: null,
        feeAmt: null,
        stopProfitAmt: null,
        stopLossAmt: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        coinNum: [
          {required: true, message: "币数不能为空", trigger: "blur"}
        ],
        sheetNum: [
          {required: true, message: "张数不能为空", trigger: "blur"}
        ],
        level: [
          {required: true, message: "杠杆不能为空", trigger: "blur"}
        ],
        totalAmt: [
          {required: true, message: "总价不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询虚拟币合约委托列表 */
    getList() {
      this.loading = true;
      listDelegation(this.queryParams).then(response => {
        this.delegationList = response.rows;
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
        symbol: null,
        direction: null,
        priceType: null,
        limitPrice: null,
        deliveryType: null,
        deliveryId: null,
        coinNum: null,
        sheetNum: null,
        level: null,
        totalAmt: null,
        marginAmt: null,
        feeAmt: null,
        stopProfitAmt: null,
        stopLossAmt: null,
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
      this.title = "添加虚拟币合约委托";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDelegation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改虚拟币合约委托";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDelegation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDelegation(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除虚拟币合约委托编号为"' + ids + '"的数据项？').then(function () {
        return delDelegation(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('coin/delegation/export', {
        ...this.queryParams
      }, `delegation_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
