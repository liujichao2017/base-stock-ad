<template>
  <div class="mt">
    <el-row>
      <el-form :model="query" ref="queryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="市场">
          <MarketSelect :select="true" ref="MarketSelect" @input="(val) => query.marketId = val"/>
        </el-form-item>
        <el-form-item label="团队">
          <AgentSelect ref="AgentSelectByTreeId" @val="onChangeTreeId"/>
        </el-form-item>
        <el-form-item label="个人">
          <AgentSelect ref="AgentSelectByUserId" @val="onChangeUserId"/>
        </el-form-item>

        <el-form-item>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <div class="px">
      <el-row :gutter="20">
        <el-col :span="8">
          <CountMember :query="query"/>
        </el-col>
        <el-col :span="8">
          <CountRecharge :query="query"/>
        </el-col>
        <el-col :span="8">
          <CountWithdrawal :query="query"/>
        </el-col>
      </el-row>
    </div>

    <div class="px">
      <el-row :gutter="20">
        <el-col :span="12">
          <CountFunds :query="query"/>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import CountMember from "@/views/dashboard/count/CountMember.vue";
import CountRecharge from "@/views/dashboard/count/CountRecharge.vue";
import CountWithdrawal from "@/views/dashboard/count/CountWithdrawal.vue";
import CountFunds from "@/views/dashboard/count/CountFunds.vue";

export default {
  name: "Index",
  components: {CountFunds, CountWithdrawal, CountRecharge, CountMember},
  data() {
    return {
      // 版本号
      version: "3.8.8",
      query: {
        marketId: null,
        userId: null,
        treeId: null,
      }
    };
  },
  mounted() {
    this.getMarketList()
  },
  methods: {
    resetQuery() {
      this.query = {
        marketId: null,
        userId: null,
        treeId: null,
      }
      this.$refs.AgentSelectByUserId.reset();
      this.$refs.AgentSelectByTreeId.reset();
      this.$refs.MarketSelect.reset();
    },
    onChangeTreeId(val) {
      this.query.treeId = val;
      this.query.userId = null;
      this.$refs.AgentSelectByUserId.reset();
    },
    onChangeUserId(val) {
      this.query.treeId = null;
      this.query.userId = val;
      this.$refs.AgentSelectByTreeId.reset();
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}

.mt {
  margin-top: 10px;
}

.px {
  padding: 0 20px;
}
</style>

