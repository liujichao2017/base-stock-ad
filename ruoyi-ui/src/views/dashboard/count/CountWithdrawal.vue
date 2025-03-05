<template>
  <div>
    <el-card class="box-card">
      <el-row>
        <el-col class="flex flex-start">
          <span>今日提现:</span>
          <span class="number">{{ data.todayWithdrawalAmount }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="flex flex-start not-btn">
          <span>总提现:</span>
          <span class="number">{{ data.totalWithdrawalAmount }}</span>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {countWithdrawal} from '@/api/dashboard/dashboard'

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
        countWithdrawal(this.query).then(res => {
          this.data = res.data;
        });
      }
    }
  }
}
</script>
