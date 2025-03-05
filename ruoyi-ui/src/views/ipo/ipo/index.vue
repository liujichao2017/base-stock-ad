<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="新股名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入新股名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申购代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入申购代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="发行价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入发行价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="绑定GID" prop="stockGid">
        <el-input
          v-model="queryParams.stockGid"
          placeholder="请输入绑定GID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.ipo_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="申购日期" prop="applyTime">
        <el-date-picker clearable
                        v-model="queryParams.applyTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择申购日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="公布日期" prop="publishTime">
        <el-date-picker clearable
                        v-model="queryParams.publishTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择公布日期">
        </el-date-picker>
      </el-form-item>
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
      <el-form-item label="市场" prop="marketId">
        <MarketSelect @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="上架状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择上架状态" clearable>
          <el-option
            v-for="dict in dict.type.ipo_status"
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
          v-hasPermi="['stock:ipo:add']"
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
          v-hasPermi="['stock:ipo:edit']"
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
          v-hasPermi="['stock:ipo:remove']"
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
      <!--          v-hasPermi="['stock:ipo:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ipoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="新股名称" align="center" prop="name"/>
      <el-table-column label="申购代码" align="center" prop="code"/>
      <el-table-column label="发行价格" align="center" prop="price"/>
      <!--      <el-table-column label="绑定GID" align="center" prop="stockGid" />-->
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ipo_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="存放展示性字段" align="center" prop="extra" /> -->
      <el-table-column label="申购日期" align="center" prop="applyTime"/>
      <el-table-column label="公布日期" align="center" prop="publishTime"/>
      <el-table-column label="购买时间区间" align="center" prop="startBuyTime">
        <template slot-scope="scope">
          <span>{{ scope.row.startBuyTime + '~' + scope.row.endBuyTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上架状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ipo_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:ipo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:ipo:remove']"
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

    <!-- 添加或修改IPO(新股)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="新股名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入新股名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申购代码" prop="code">
              <el-input v-model="form.code" placeholder="请输入申购代码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发行价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入发行价格"/>
            </el-form-item>
          </el-col>
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="绑定GID" prop="stockGid">-->
          <!--              <el-input v-model="form.stockGid" placeholder="请输入绑定GID" />-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio
              v-for="dict in dict.type.ipo_type"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- <el-form-item label="存放展示性字段" prop="extra">
          <el-input v-model="form.extra" type="textarea" placeholder="请输入内容" />
        </el-form-item> -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="申购日期" prop="applyTime">
              <el-date-picker clearable
                              v-model="form.applyTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择申购日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公布日期" prop="publishTime">
              <el-date-picker clearable
                              v-model="form.publishTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择公布日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
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
          <el-col :span="12">
            <el-form-item label="市场" prop="marketId">
              <MarketSelect @input="(val)=>{form.marketId = val}"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上架状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.ipo_status"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addIpo, delIpo, getIpo, listIpo, updateIpo} from "@/api/ipo/ipo";

export default {
  name: "Ipo",
  dicts: ['ipo_status', 'ipo_type'],
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
      // IPO(新股)表格数据
      ipoList: [],
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
        price: null,
        stockGid: null,
        type: null,
        extra: null,
        applyTime: null,
        publishTime: null,
        startBuyTime: null,
        endBuyTime: null,
        marketId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "新股名称不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "申购代码不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "发行价格不能为空", trigger: "blur"}
        ],
        stockGid: [
          {required: true, message: "绑定GID不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "类型不能为空", trigger: "change"}
        ],
        extra: [
          {required: true, message: "存放展示性字段不能为空", trigger: "blur"}
        ],
        applyTime: [
          {required: true, message: "申购日期不能为空", trigger: "blur"}
        ],
        publishTime: [
          {required: true, message: "公布日期不能为空", trigger: "blur"}
        ],
        startBuyTime: [
          {required: true, message: "开始购买时间不能为空", trigger: "blur"}
        ],
        endBuyTime: [
          {required: true, message: "购买结束时间不能为空", trigger: "blur"}
        ],
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "上架状态不能为空", trigger: "change"}
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
  created() {
    this.getList();
  },
  methods: {
    /** 查询IPO(新股)列表 */
    getList() {
      this.loading = true;
      listIpo(this.queryParams).then(response => {
        this.ipoList = response.rows;
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
        price: null,
        stockGid: null,
        type: '1',
        extra: null,
        applyTime: null,
        publishTime: null,
        startBuyTime: null,
        endBuyTime: null,
        marketId: null,
        status: 0,
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
      this.title = "添加IPO(新股)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIpo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改IPO(新股)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIpo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIpo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除IPO(新股)编号为"' + ids + '"的数据项？').then(function () {
        return delIpo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/ipo/export', {
        ...this.queryParams
      }, `ipo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
