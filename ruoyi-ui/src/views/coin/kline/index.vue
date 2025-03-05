<template>
  <div class="app-container">
    <el-row>
      <h1>交易对: {{ coin.symbol }} 币名: {{ coin.bcdn }} </h1>
    </el-row>
    <el-row>
      <el-radio-group @change="getKline" v-model="queryParams.period">
        <el-radio-button
          v-for="dict in dict.type.coin_kline_period"
          :label="dict.value"
        >
          {{ dict.label }}
        </el-radio-button>
      </el-radio-group>
      <el-button icon="el-icon-refresh" size="mini" @click="getKline">刷新</el-button>
    </el-row>
    <el-row>
      <div class="echarts" id="echarts" style="width: 100%;height: 800px"></div>
    </el-row>
  </div>
</template>

<script>
import {addKline, delKline, getKline, listKline, listKlineByApi, updateKline} from "@/api/coin/kline";
import * as echarts from 'echarts';

export default {
  name: "Kline",
  dicts: ['coin_kline_period'],
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
      // 虚拟币K线表格数据
      klineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      coin: {
        symbol: null,
        bcdn: null
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        symbol: null,
        period: null,
        ts: null,
        count: null,
        amount: null,
        open: null,
        close: null,
        low: null,
        high: null,
        vol: null,
      },
      myChart: {},
      option: {
        title: {
          text: 'K线数据'
        },
        tooltip: {
          formatter(res) {
            let data = res.data;
            return `<div>开盘: ${data[1]}<br/>收盘: ${data[2]}<br/>最低: ${data[3]}<br/>最高: ${data[4]}</div>`
          }
        },
        legend: {},
        xAxis: {
          data: ['2017-10-24', '2017-10-25', '2017-10-26', '2017-10-27']
        },
        yAxis: {},
        series: [
          {
            type: 'candlestick',
            data: [
              [20, 34, 10, 38],
              [40, 35, 30, 50],
              [31, 38, 33, 44],
              [38, 15, 5, 42]
            ]
          }
        ]
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ]
      }
    };
  },
  watch: {
    '$route': {
      handler(to, from) {
        this.coin.symbol = this.$route.query.symbol
        this.coin.bcdn = this.$route.query.bcdn
        this.getList();
      },
      deep: true,
      immediate: true
    }
  },
  created() {


  },
  mounted() {
    this.queryParams.period = '1day';
    this.initKline();
    this.getKline();
  },
  methods: {
    initKline() {
      // 指定图表的配置项和数据
      this.myChart = echarts.init(document.getElementById('echarts'));
      window.addEventListener("resize", () => {
        this.myChart.resize();
      })
    },
    /** 查询虚拟币K线列表 */
    getKline() {
      this.loading = true;
      this.queryParams.symbol = this.$route.query.symbol;
      listKlineByApi(this.queryParams).then(response => {
        this.option.xAxis.data = response.data.time
        this.option.series[0].data = response.data.infos

        this.myChart.setOption(this.option)
        this.myChart.resize();
        this.loading = false;
      });
    },
    /** 查询虚拟币K线列表 */
    getList() {
      this.loading = true;
      listKline(this.queryParams).then(response => {
        this.klineList = response.rows;
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
        symbol: null,
        period: null,
        ts: null,
        count: null,
        amount: null,
        open: null,
        close: null,
        low: null,
        high: null,
        vol: null,
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
      this.title = "添加虚拟币K线";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getKline(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改虚拟币K线";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateKline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addKline(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除虚拟币K线编号为"' + ids + '"的数据项？').then(function () {
        return delKline(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('coin/kline/export', {
        ...this.queryParams
      }, `kline_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
