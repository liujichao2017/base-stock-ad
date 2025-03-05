<template>
  <div>
    <el-card class="box-card">
      <el-row>
        <el-col class="flex flex-start">
          <span>今日充值:</span>
          <p class="number">{{ data.todayRechargeAmount }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="flex flex-start not-btn">
          <span>总充值:</span>
          <p class="number">{{ data.totalRechargeAmount }}</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {countRecharge, countWithdrawal} from '@/api/dashboard/dashboard'

export default {
  props: {
    query: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      data: {}
    }
  },
  watch: {
    query: {
      handler: function (newVal, oldVal) {
        this.getData();
      },
      deep: true,
      immediate: true
    },

  },
  mounted() {
    this.$nextTick(() => {
      this.getData()
    })
  },
  methods: {
    getData() {
      if (this.query.marketId) {
        countRecharge(this.query).then(res => {
          this.data = res.data;
        });
      }

    }
  }
}
</script>
