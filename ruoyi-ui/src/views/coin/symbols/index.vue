<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="交易对" prop="symbol">
        <el-input
          v-model="queryParams.symbol"
          placeholder="请输入交易对"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.coin_symbols_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="前段显示" prop="hot">
        <el-select v-model="queryParams.hot" placeholder="请选择前段显示" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="币种类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择币种类型" clearable>
          <el-option
            v-for="dict in dict.type.coin_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="币种名称" prop="bcdn">
        <el-input
          v-model="queryParams.bcdn"
          placeholder="请输入基础币种显示名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="可交易" prop="tradeState">
        <el-select v-model="queryParams.tradeState" placeholder="请选择可交易" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
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
      <!--          v-hasPermi="['coin:symbols:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['coin:symbols:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['coin:symbols:remove']"
        >删除
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="symbolsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="交易对" align="center" prop="symbol"/>
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coin_symbols_status" :value="scope.row.state"/>
        </template>
      </el-table-column>
      <el-table-column label="前段显示" align="center" prop="hot">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.hot"/>
        </template>
      </el-table-column>
      <el-table-column label="每张币数" align="center" prop="sheet"/>
      <el-table-column label="交易手续费" align="center" prop="feeAmt"/>
      <el-table-column label="币种类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coin_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="币种类型" align="center" prop="bcdn"/>
      <el-table-column label="计价币种类型" align="center" prop="qcdn"/>

      <el-table-column label="可交易" align="center" prop="tradeState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.tradeState"/>
        </template>
      </el-table-column>

      <el-table-column label="最新价" align="center" prop="price"/>
      <el-table-column label="开盘" align="center" prop="open"/>
      <el-table-column label="收盘价" align="center" prop="close"/>
      <!--      <el-table-column label="交易次数" align="center" prop="counts" />-->
      <el-table-column label="最低" align="center" prop="low"/>
      <el-table-column label="最高" align="center" prop="high"/>
      <el-table-column label="交易量" align="center" prop="amount"/>
      <!--      <el-table-column label="币种计量的交易量" align="center" prop="vol" />-->
      <!--      <el-table-column label="当前的最高买价" align="center" prop="bid" />-->
      <!--      <el-table-column label="最高买价对应的量" align="center" prop="bidSize" />-->
      <!--      <el-table-column label="当前的最低卖价" align="center" prop="ask" />-->
      <!--      <el-table-column label="最低卖价对应的量" align="center" prop="askSize" />-->
      <!--      <el-table-column label="最新成交价对应的量" align="center" prop="lastSize" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['coin:symbols:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['coin:symbols:remove']"
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

    <!-- 添加或修改交易对对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="交易对" prop="symbol">
          <el-input :disabled="true" v-model="form.symbol" placeholder="请输入交易对"/>
        </el-form-item>
        <el-form-item label="币种类型" prop="bcdn">
          <el-input :disabled="true" v-model="form.bcdn" placeholder="请输入基础币种显示名称"/>
        </el-form-item>
        <el-form-item label="前段显示" prop="hot">
          <el-select v-model="form.hot" placeholder="请选择前段显示">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="每张币数" prop="sheet">
          <el-input v-model="form.sheet" placeholder="请输入1张合约的币数"/>
        </el-form-item>
        <el-form-item label="交易手续费" prop="feeAmt">
          <el-input v-model="form.feeAmt" placeholder="请输入交易手续费"/>
        </el-form-item>
        <el-form-item label="可交易" prop="tradeState">
          <el-select v-model="form.tradeState" placeholder="请选择可交易">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
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
import {addSymbols, delSymbols, getSymbols, listSymbols, updateSymbols} from "@/api/coin/symbols";

export default {
  name: "Symbols",
  dicts: ['coin_type', 'coin_symbols_status', 'yes_no'],
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
      // 交易对表格数据
      symbolsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        symbol: null,
        state: null,
        hot: '1',
        sheet: null,
        feeAmt: null,
        type: null,
        bcdn: null,
        qcdn: null,
        icon: null,
        tradeState: null,
        sn: null,
        price: null,
        open: null,
        close: null,
        amount: null,
        counts: null,
        low: null,
        high: null,
        vol: null,
        bid: null,
        bidSize: null,
        ask: null,
        askSize: null,
        lastSize: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        symbol: [
          {required: true, message: "交易对不能为空", trigger: "blur"}
        ],
        state: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        hot: [
          {required: true, message: "前段显示不能为空", trigger: "change"}
        ],
        sheet: [
          {required: true, message: "每张币数不能为空", trigger: "blur"}
        ],
        feeAmt: [
          {required: true, message: "交易手续费不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "币种类型不能为空", trigger: "change"}
        ],
        tradeState: [
          {required: true, message: "可交易不能为空", trigger: "change"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询交易对列表 */
    getList() {
      this.loading = true;
      listSymbols(this.queryParams).then(response => {
        this.symbolsList = response.rows;
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
        state: null,
        hot: null,
        sheet: null,
        feeAmt: null,
        type: null,
        bcdn: null,
        qcdn: null,
        icon: null,
        tradeState: null,
        sn: null,
        price: null,
        open: null,
        close: null,
        amount: null,
        counts: null,
        low: null,
        high: null,
        vol: null,
        bid: null,
        bidSize: null,
        ask: null,
        askSize: null,
        lastSize: null,
        createTime: null,
        updateTime: null,
        enable: null
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
      this.title = "添加交易对";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSymbols(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改交易对";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSymbols(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSymbols(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除交易对编号为"' + ids + '"的数据项？').then(function () {
        return delSymbols(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('coin/symbols/export', {
        ...this.queryParams
      }, `symbols_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
