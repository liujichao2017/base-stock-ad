<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="股票代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="股票代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否显示" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择是否显示" clearable>
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fundstockrecord:fundstockrecord:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fundstockrecord:fundstockrecord:edit']"
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
          v-hasPermi="['fundstockrecord:fundstockrecord:remove']"
        >删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['fundstockrecord:fundstockrecord:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fundstockrecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fund_stock_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="基金名称" align="center" prop="fundName"/>
      <el-table-column label="基金代码" align="center" prop="fundCode"/>
      <el-table-column label="股票名称" align="center" prop="stockName"/>
      <el-table-column label="股票代码" align="center" prop="stockCode"/>
      <el-table-column label="股票类型" align="center" prop="stockType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bearbull_status" :value="scope.row.stockType"/>
        </template>
      </el-table-column>
      <el-table-column label="是否显示" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fundstockrecord:fundstockrecord:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fundstockrecord:fundstockrecord:remove']"
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

    <!-- 添加或修改基金股票子项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择股票类型">
            <el-option
              v-for="dict in dict.type.fund_stock_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.type !== 'ml'" label="股票代码" prop="code">
          <el-select
            v-model="form.code"
            filterable
            remote
            reserve-keyword
            placeholder="请输入股票代码"
            :remote-method="queryStockHkBearbull"
            :loading="selectLoading">
            <el-option
              v-for="item in stockHkBearbulls"
              :key="item.sym"
              :label="item.sym + ' | ' + item.desp+' | ' + (item.type ==='P'?'熊市':'牛市')"
              :value="item.sym">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.type === 'ml'" label="认股权证名称" prop="code">
          <el-select
            v-model="form.code"
            filterable
            remote
            reserve-keyword
            placeholder="请输入股票代码"
            :remote-method="queryMalaysiawarrants"
            :loading="selectLoading">
            <el-option
              v-for="item in malaysiawarrants"
              :key="item.sym"
              :label="item.dwSymbol + ' | ' + item.underlyingName"
              :value="item.dwSymbol">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否显示" prop="status">
          <el-select v-model="form.status" placeholder="请选择是否显示">
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
import {
  addFundstockrecord,
  delFundstockrecord,
  getFundstockrecord,
  listFundstockrecord,
  updateFundstockrecord
} from "@/api/fundstockrecord/fundstockrecord";
import {listHkbearbull} from "@/api/hkbearbull/hkbearbull";
import {listMalaysiawarrants} from "@/api/malaysiawarrants/malaysiawarrants";

export default {
  name: "Fundstockrecord",
  dicts: ['fund_stock_type', 'yes_no', 'bearbull_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      selectLoading: false,
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
      // 基金股票子项表格数据
      fundstockrecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fundId: null,
        code: null,
        status: null,
      },
      // 表单参数
      form: {},
      stockHkBearbulls: {},
      malaysiawarrants: {},
      // 表单校验
      rules: {
        fundId: [
          {required: true, message: "基金ID不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "关联的code不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "股票类型不能为空", trigger: "change"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        createTime: [
          {required: true, message: "创建时间/申请时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ]
      }
    };
  },
  watch: {
    '$route': {
      handler(to, from) {
        this.getList();
      },
      deep: true,
      immediate: true
    }
  },
  created() {
    this.getList();
  },
  methods: {
    queryStockHkBearbull(query) {
      if (query !== '') {
        this.selectLoading = true;
        setTimeout(() => {
          listHkbearbull({sym: query}).then(res => {
            this.selectLoading = false;
            this.stockHkBearbulls = res.rows;
          });
        }, 200);
      } else {
        this.stockHkBearbulls = [];
      }
    },
    queryMalaysiawarrants(query) {
      if (query !== '') {
        this.selectLoading = true;
        setTimeout(() => {
          listMalaysiawarrants({dwSymbol: query}).then(res => {
            this.selectLoading = false;
            this.malaysiawarrants = res.rows;
          });
        }, 200);
      } else {
        this.malaysiawarrants = [];
      }
    },
    /** 查询基金股票子项列表 */
    getList() {
      this.loading = true;
      this.queryParams.fundId = this.$route.query.fundId;
      listFundstockrecord(this.queryParams).then(response => {
        this.fundstockrecordList = response.rows;
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
        fundId: null,
        code: null,
        type: null,
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
      this.title = "添加基金股票子项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFundstockrecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基金股票子项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.fundId = this.$route.query.fundId
          if (this.form.id != null) {
            updateFundstockrecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFundstockrecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除基金股票子项编号为"' + ids + '"的数据项？').then(function () {
        return delFundstockrecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('fundstockrecord/fundstockrecord/export', {
        ...this.queryParams
      }, `fundstockrecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
