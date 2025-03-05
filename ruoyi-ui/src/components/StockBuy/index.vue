<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-divider content-position="center">创建持仓</el-divider>
      <el-row>
        <el-col :span="6">
          <el-form-item label="持仓类型">
            <el-select v-model="form.mode" placeholder="请选择持仓类型" clearable>
              <el-option
                v-for="dict in dict.type.cc_type"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="选择客户">
            <el-input v-model="form.phone" size="small" placeholder="请输入手机号或客户ID">
              <el-button slot="append" icon="el-icon-search" @click="searchMember()"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form :model="stock" size="small" :inline="true" label-width="68px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="手机号">
              <span>{{ member.phone }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="用户名">
              <span>{{ member.realName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属代理">
              <span>{{ member.userName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="账号类型">
              <dict-tag :options="dict.type.kh_zh_type" :value="member.accountType"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-table :data="member.funds" style="width: 100%" height="250">
          <el-table-column label="国家" align="center" prop="marketName"/>
          <el-table-column label="可用余额" align="center" prop="enableAmt"/>
          <el-table-column label="盈利金额" align="center" prop="profitAmt"/>
          <el-table-column label="占用金额" align="center" prop="occupancyAmt"/>
          <el-table-column label="冻结金额" align="center" prop="freezeAmt"/>
        </el-table>
      </el-form>
      <el-divider content-position="center">股票信息</el-divider>
      <el-row>
        <el-col :span="6">
          <el-form-item label="选择股票">
            <el-input v-model="form.stockGid" size="small" placeholder="请输入股票CODE或股票GID">
              <el-button slot="append" icon="el-icon-search" @click="searchStock()"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form :model="stock" size="small" :inline="true" label-width="68px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="名称">
              <span>{{ stock.name }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="代码">
              <span>{{ stock.code }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="现价">
              <span>{{ stock.last }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="涨跌">
              <span>{{ stock.chg }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider content-position="center">下单信息</el-divider>
      <el-row>
        <el-col :span="12">
          <el-form-item label="买入时间">
            <el-date-picker
              v-model="form.buyOrderTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择买入时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="买入价格">
            <el-input v-model="form.buyOrderPrice" size="small" style="width: 220px;" placeholder="请输入买入价格"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="杠杆倍数">
            <el-input v-model="form.lever" size="small" style="width: 220px;" placeholder="请输入杠杆倍数"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="买入方向">
            <el-radio-group v-model="form.direction">
              <el-radio
                v-for="dict in dict.type.stock_direction"
                :key="dict.value"
                :label="dict.value"
              >{{ dict.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="买入股数">
            <el-input v-model="form.num" size="small" style="width: 220px;" placeholder="请输入杠杆倍数">
              <el-button slot="append">股</el-button>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="btn">
      <el-button type="primary" @click="submitForm" size="small">立 即 创 建</el-button>
      <el-button @click="submitForm" size="small">重 置</el-button>
    </div>
  </div>
</template>

<script>

import {addPosition} from "@/api/stock/position";
import {getLastStock} from "@/api/stock/stock";
import {seaMember} from "@/api/member/member";

export default {
  name: "marketSpan",
  dicts: ['cc_type', 'c_fx', 'kh_zh_type', 'stock_direction'],
  props: {
    witeId: {
      type: Number,
      default: ""
    },
  },
  watch: {
    witeId: {
      handler(val) {
        console.log(val)
      },
      deep: true,
      immediate: true
    }
  },
  data() {
    return {
      marketList: [],
      val: null,
      funds: [],
      form: {
        memberId: null,
        phone: null,
        stockGid: null,
        mode: 1,
        buyOrderTime: null,
        lever: 1,
        num: null,
        buyOrderPrice: null,
        direction: '1',
        witeId: null
      },
      member: {},
      stock: {},
      rules: {
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
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
      }
    }
  },
  mounted() {
  },
  methods: {
    searchMember() {
      seaMember(this.form.phone).then(res => {
        this.member = res.data;
        this.form.memberId = res.data.id;
      })
    },
    searchStock() {
      getLastStock(this.form.stockGid).then(res => {
        this.stock = res.data
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addPosition(this.form).then(res => {
            this.$modal.msgSuccess("建仓成功");
          })
        }
      });
    },
  },
};
</script>
<style lang="css" scoped>
.btn {
  text-align: center;
  margin: 10px 0 60px 0;
}

.el-divider {
  margin: 20px 0 50px 0;
  font-size: 20em;
}

.el-divider__text {
  position: absolute;
  background-color: #FFFFFF;
  padding: 0 20px;
  font-weight: 500;
  color: #303133;
  font-size: 18px;
}
</style>
