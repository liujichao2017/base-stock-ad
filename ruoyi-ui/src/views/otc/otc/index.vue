<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场" prop="marketId">
        <MarketSelect @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="股票名称" prop="stockName">
        <el-input
          v-model="queryParams.stockName"
          placeholder="请输入股票名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="股票代码" prop="stockCode">
        <el-input
          v-model="queryParams.stockCode"
          placeholder="请输入股票代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="股票GID" prop="stockGid">
        <el-input
          v-model="queryParams.stockGid"
          placeholder="请输入股票GID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="交易密码" prop="password">
        <el-input
          v-model="queryParams.password"
          placeholder="请输入交易密码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="开始购买时间: 09:00" prop="startBuyTime">
        <el-input
          v-model="queryParams.startBuyTime"
          placeholder="请输入开始购买时间: 09:00"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="购买结束时间 15:30" prop="endBuyTime">
        <el-input
          v-model="queryParams.endBuyTime"
          placeholder="请输入购买结束时间 15:30"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="上架状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择上架状态" clearable>
          <el-option
            v-for="dict in dict.type.otc_status"
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
          v-hasPermi="['stock:otc:add']"
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
          v-hasPermi="['stock:otc:edit']"
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
          v-hasPermi="['stock:otc:remove']"
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
      <!--          v-hasPermi="['stock:otc:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="otcList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="股票名称" align="center" prop="stockName" :show-overflow-tooltip="true"/>
      <el-table-column label="股票代码" align="center" prop="stockCode" :show-overflow-tooltip="true"/>
      <el-table-column label="股票GID" align="center" prop="stockGid" :show-overflow-tooltip="true"/>
      <el-table-column label="价格" align="center" prop="price"/>
      <!-- <el-table-column label="类型：备用" align="center" prop="type" /> -->
      <el-table-column label="交易密码" align="center" prop="password" :show-overflow-tooltip="true"/>
      <el-table-column label="购买时间区间" align="center" prop="startBuyTime">
        <template slot-scope="scope">
          <span>{{ scope.row.startBuyTime + '~' + scope.row.endBuyTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上架状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.otc_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间/申请时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="存放展示性字段" align="center" prop="extra" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:otc:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:otc:remove']"
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

    <!-- 添加或修改otc(大宗)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="市场" prop="marketId">
              <MarketSelect @input="(val)=>{form.marketId = val}"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上架状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.otc_status"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="股票名称" prop="stockName">-->
          <!--              <el-input v-model="form.stockName" placeholder="请输入股票名称"/>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="股票代码" prop="stockCode">-->
          <!--              <el-input v-model="form.stockCode" placeholder="请输入股票代码"/>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="股票GID" prop="stockGid">
              <el-input v-model="form.stockGid" placeholder="请输入股票GID"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入价格"/>
            </el-form-item>
          </el-col>
        </el-row>


        <!-- <el-form-item label="交易密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入交易密码" />
        </el-form-item> -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="开始购买时间" prop="startBuyTime">
              <el-time-select
                v-model="form.startBuyTime"
                :picker-options="{
                  start: '00:00',
                  step: '00:15',
                  end: '23:59'
                }"
                placeholder="选择时间">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购买结束时间" prop="endBuyTime">
              <el-time-select
                v-model="form.endBuyTime"
                :picker-options="{
                  start: '00:00',
                  step: '00:15',
                  end: '23:59'
                }"
                placeholder="选择时间">
              </el-time-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最少持有时间(分钟)" prop="sellTime">
              <el-input v-model="form.sellTime" placeholder="请输入最少持有时间(分钟)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-form-item label="存放展示性字段" prop="extra">
          <el-input v-model="form.extra" type="textarea" placeholder="请输入内容" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addOtc, delOtc, getOtc, listOtc, updateOtc} from "@/api/otc/otc";

export default {
  name: "Otc",
  dicts: ['otc_status'],
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
      // otc(大宗)表格数据
      otcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        marketId: null,
        stockName: null,
        stockCode: null,
        stockGid: null,
        price: null,
        type: null,
        password: null,
        startBuyTime: null,
        endBuyTime: null,
        status: null,
        extra: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        marketId: [
          {required: true, message: "市场不能为空", trigger: "change"}
        ],
        stockName: [
          {required: true, message: "股票名称不能为空", trigger: "blur"}
        ],
        stockCode: [
          {required: true, message: "股票代码不能为空", trigger: "blur"}
        ],
        stockGid: [
          {required: true, message: "股票GID不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "价格不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "类型：备用不能为空", trigger: "change"}
        ],
        password: [
          {required: true, message: "交易密码不能为空", trigger: "blur"}
        ],
        startBuyTime: [
          {required: true, message: "开始购买时间不能为空", trigger: "blur"}
        ],
        endBuyTime: [
          {required: true, message: "购买结束时间不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "上架状态不能为空", trigger: "change"}
        ],
        sellTime: [
          {required: true, message: "最小持有时间", trigger: "change"}
        ],
        createTime: [
          {required: true, message: "创建时间/申请时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  watch: {
    '$route'(to, from) {

      this.getList()
    }
  },
  methods: {
    /** 查询otc(大宗)列表 */
    getList() {
      this.loading = true;
      this.queryParams.type = this.$route.query.type;
      listOtc(this.queryParams).then(response => {
        this.otcList = response.rows;
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
        marketId: null,
        stockName: null,
        stockCode: null,
        stockGid: null,
        price: null,
        type: null,
        password: null,
        startBuyTime: null,
        sellTime: 0,
        endBuyTime: null,
        status: 0,
        createTime: null,
        updateTime: null,
        extra: null
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
      this.title = "添加otc(大宗)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOtc(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改otc(大宗)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.type = this.$route.query.type;
          if (this.form.id != null) {
            updateOtc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOtc(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除otc(大宗)编号为"' + ids + '"的数据项？').then(function () {
        return delOtc(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/otc/export', {
        ...this.queryParams
      }, `otc_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
