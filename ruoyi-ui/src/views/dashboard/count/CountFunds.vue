<template>
  <div>
    <el-card class="box-card">
      <el-row>
        <el-col class="flex flex-start" :span="12">
          <span>可用资金:</span>
          <span class="number">{{ data.totalEnableAmount }}</span>
        </el-col>
        <el-col class="flex" :span="12">
          <span>占用资金:</span>
          <span class="number">{{ data.totalOccupancyAmt }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="flex flex-start not-btn" :span="12">
          <span>冻结资金:</span>
          <span class="number">{{ data.totalFreezeAmt }}</span>
        </el-col>
        <el-col class="flex" :span="12">
          <span>盈利资金:</span>
          <span class="number">{{ data.totalProfitAmt }}</span>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {countFunds} from '@/api/dashboard/dashboard'

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
        countFunds(this.query).then(res => {
          this.data = res.data;
        });
      }

    }
  }
}
</script>
