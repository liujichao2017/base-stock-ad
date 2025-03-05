<template>
  <div>
    <el-card class="box-card">
      <el-row>
        <el-col class="flex flex-start" :span="12">
          <span>今日注册用户:</span>
          <p class="number">{{ data.todayRegisterMemberNum }}</p>
        </el-col>
        <el-col class="flex" :span="12">
          <span>累计注册用户:</span>
          <p class="number">{{ data.totalRegisterMemberNum }}</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="flex flex-start not-btn" :span="12">
          <span>冻结用户数:</span>
          <p class="number">{{ data.freezeMemberNum }}</p>
        </el-col>
        <el-col class="flex" :span="12">
          <span>充值用户数:</span>
          <p class="number">{{ data.rechargeMemberNum }}</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {countMember} from '@/api/dashboard/dashboard'

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
      countMember(this.query).then(res => {
        this.data = res.data;
      });
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
  .flex {
    display: flex;
    text-align: right;
    justify-content: end;
    align-items: center;
    span {
      color: dimgray;
      margin-right: 5px;
    }
    .number {
      font-weight: bold;
      margin: 0;
    }
  }
  .flex-start {
    margin-bottom: 10px;
    text-align: left;
    justify-content: start;
  }
  .not-btn {
    margin-bottom: 0;
  }
</style>
