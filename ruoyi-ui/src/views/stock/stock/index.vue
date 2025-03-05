<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场ID" prop="marketId">
        <MarketSelect ref="MarketSelect" @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="股票名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入股票名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="股票代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入股票代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="股票GID" prop="gid">
        <el-input
          v-model="queryParams.gid"
          placeholder="请输入股票GID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.stock_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="禁止交易" prop="isLock">
        <el-select v-model="queryParams.isLock" placeholder="请选择禁止交易" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="显示" prop="isShow">
        <el-select v-model="queryParams.isShow" placeholder="请选择显示" clearable>
          <el-option
            v-for="dict in dict.type.yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="热门" prop="isPopular">
        <el-select v-model="queryParams.isPopular" placeholder="请选择热门" clearable>
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :default-expand-all="true" :data="stockList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-row>
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="3">
                <el-form-item label="最高">
                  <el-tag>{{ props.row.high }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="最低">
                  <el-tag>{{ props.row.low }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="今开">
                  <el-tag>{{ props.row.open }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="昨收">
                  <el-tag>{{ props.row.close }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="成交量">
                  <el-tag>{{ props.row.volume }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="成交额">
                  <el-tag>{{ props.row.amounts }}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="股票GID" align="center" prop="gid"/>
      <el-table-column label="股票名称" align="center" prop="name"/>
      <el-table-column label="股票代码" align="center" prop="code"/>
      <el-table-column label="交易所" align="center" prop="exchanges"/>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.stock_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="最新价" align="center" prop="last"/>
      <el-table-column label="涨跌额" align="center" prop="chg"/>
      <el-table-column label="涨跌幅" align="center" prop="chgPct"/>
      <el-table-column label="禁止交易" align="center" prop="isLock">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isLock"/>
        </template>
      </el-table-column>
      <el-table-column label="显示" align="center" prop="isShow">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isShow"/>
        </template>
      </el-table-column>
      <el-table-column label="热门" align="center" prop="isPopular">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isPopular"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:stock:edit']"
          >修改
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

    <!-- 添加或修改股票产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="股票名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入股票名称"/>
        </el-form-item>
        <el-form-item label="股票代码" prop="code">
          <el-input :disabled="true" v-model="form.code" placeholder="请输入股票代码"/>
        </el-form-item>
        <el-form-item label="股票GID" prop="gid">
          <el-input :disabled="true" v-model="form.gid" placeholder="请输入股票GID"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select :disabled="true" v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.stock_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="市场ID" prop="marketId">
          <MarketSelect ref="MarketSelect1" :disabled="true" :marketId="form.marketId"
                        @input="(val)=>{form.marketId = val}"/>
        </el-form-item>
        <el-form-item label="禁止交易" prop="isLock">
          <el-select v-model="form.isLock" placeholder="请选择禁止交易">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="显示" prop="isShow">
          <el-select v-model="form.isShow" placeholder="请选择显示">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="热门" prop="isPopular">
          <el-select v-model="form.isPopular" placeholder="请选择热门">
            <el-option
              v-for="dict in dict.type.yes_no"
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
import {addStock, delStock, getStock, listStock, updateStock} from "@/api/stock/stock";
import MarketSelect from "@/components/marketSelect/index.vue";
import MarketSpan from "@/components/marketSpan/index.vue";

export default {
  name: "Stock",
  components: {MarketSpan, MarketSelect},
  dicts: ['stock_type', 'yes_no'],
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
      // 股票产品表格数据
      stockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        code: null,
        exchanges: null,
        gid: null,
        type: '1',
        marketId: null,
        isLock: null,
        isShow: null,
        isPopular: null,
        last: null,
        chg: null,
        chgPct: null,
        high: null,
        low: null,
        open: null,
        close: null,
        volume: null,
        amounts: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          {required: true, message: "类型不能为空", trigger: "change"}
        ],
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        isLock: [
          {required: true, message: "禁止交易不能为空", trigger: "change"}
        ],
        isShow: [
          {required: true, message: "显示不能为空", trigger: "change"}
        ],
        isPopular: [
          {required: true, message: "热门不能为空", trigger: "change"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询股票产品列表 */
    getList() {
      this.loading = true;
      listStock(this.queryParams).then(response => {
        this.stockList = response.rows;
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
        name: null,
        code: null,
        exchanges: null,
        gid: null,
        type: null,
        marketId: null,
        isLock: null,
        isShow: null,
        isPopular: null,
        last: null,
        chg: null,
        chgPct: null,
        high: null,
        low: null,
        open: null,
        close: null,
        volume: null,
        amounts: null,
        updateTime: null,
        createTime: null
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
      this.open = true;
      this.title = "添加股票产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStock(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改股票产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStock(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStock(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
          this.$refs.MarketSelect1.reset();
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除股票产品编号为"' + ids + '"的数据项？').then(function () {
        return delStock(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/stock/export', {
        ...this.queryParams
      }, `stock_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
