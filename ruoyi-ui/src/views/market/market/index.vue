<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名字" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货币缩写" prop="currency">
        <el-input
          v-model="queryParams.currency"
          placeholder="请输入货币缩写"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['market:market:add']"
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
          v-hasPermi="['market:market:edit']"
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
          v-hasPermi="['market:market:remove']"
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
      <!--          v-hasPermi="['market:market:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="marketList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="名字" align="center" prop="name"/>
      <el-table-column label="国家缩写" align="center" prop="country"/>
      <el-table-column label="货币缩写" align="center" prop="currency"/>
      <el-table-column label="国旗图片" align="center" prop="countryImg">
        <template slot-scope="scope">
            <span>
              <img style="width: 50px" :src="imagesUrl + scope.row.countryImg" alt="暂无"/>
            </span>
        </template>
      </el-table-column>
      <el-table-column label="主市场" align="center" prop="isMaster">
        <template slot-scope="scope">
          <span v-if="scope.row.isMaster ==='1'">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column label="市场类型" align="center" prop="type">
        <template slot-scope="scope">
          <span v-if="scope.row.type ==='stock'">股票市场</span>
          <span v-if="scope.row.type ==='coin_usdt'">U本位虚拟币</span>
        </template>
      </el-table-column>
      <el-table-column label="数据源" align="center" prop="dataSourceMark">
        <template slot-scope="scope">
          <span v-if="scope.row.dataSourceMark ==='JS'">匠山</span>
          <span v-if="scope.row.dataSourceMark ==='LT'">路透</span>
          <span v-if="scope.row.dataSourceMark ==='CNA'">A股</span>
          <span v-if="scope.row.dataSourceMark ==='HK'">港股</span>
          <span v-if="scope.row.dataSourceMark ==='COIN'">虚拟币-火币</span>
          <span v-if="scope.row.dataSourceMark ==='QY'">启源</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort"/>
      <el-table-column label="上线" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status ==='1'">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['market:market:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['market:market:remove']"
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

    <!-- 添加或修改市场对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入名字"/>
        </el-form-item>
        <el-form-item label="国家缩写" prop="country">
          <el-input v-model="form.country" placeholder="请输入国家缩写"/>
        </el-form-item>
        <el-form-item label="货币缩写" prop="currency">
          <el-input v-model="form.currency" placeholder="请输入货币缩写"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入货币缩写"/>
        </el-form-item>
        <el-form-item label="国旗图片" prop="countryImg">
          <FileUpload @input="onFleInfo" ref="fileUploadRef"/>
        </el-form-item>
        <el-form-item label="主市场" prop="isMaster">
          <el-select v-model="form.isMaster" placeholder="请输入主市场" clearable :style="{width: '100%'}">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据源" prop="dataSourceMark">
          <el-select v-model="form.dataSourceMark" placeholder="请输入数据源标" clearable :style="{width: '100%'}">
            <el-option label="匠山" value="JS"></el-option>
            <el-option label="路透" value="LT"></el-option>
            <el-option label="A股" value="CNA"></el-option>
            <el-option label="港股" value="HK"></el-option>
            <el-option label="启源" value="QY"></el-option>
          </el-select>
        </el-form-item>
        <div v-if="form.dataSourceMark === 'HK'">
          <el-form-item label="牛熊股">
            <el-select v-model="json.bearbull" placeholder="是否开启" clearable :style="{width: '100%'}">
              <el-option label="开启" value="1"></el-option>
              <el-option label="关闭" value="0"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="form.dataSourceMark === 'JS'">
          <el-form-item label="域名">
            <el-input v-model="json.domain" placeholder="域名"/>
          </el-form-item>
          <el-form-item label="国家ID">
            <el-input v-model="json.countryId" placeholder="国家ID"/>
          </el-form-item>
          <el-form-item label="key">
            <el-input v-model="json.key" placeholder="key"/>
          </el-form-item>
        </div>
        <div v-if="form.dataSourceMark === 'QY'">
          <el-form-item label="域名">
            <el-input v-model="json.domain" placeholder="域名"/>
          </el-form-item>
          <el-form-item label="国家ID">
            <el-input v-model="json.countryId" placeholder="国家ID"/>
          </el-form-item>
          <el-form-item label="key">
            <el-input v-model="json.key" placeholder="key"/>
          </el-form-item>
        </div>
        <div v-if="form.dataSourceMark === 'LT'">
          <el-form-item label="股票域名">
            <el-input v-model="json.stockDomain" placeholder="股票域名"/>
          </el-form-item>
          <el-form-item label="K线域名">
            <el-input v-model="json.klineDomain" placeholder="K线域名"/>
          </el-form-item>
          <el-form-item label="国家ID">
            <el-input v-model="json.countryId" placeholder="key"/>
          </el-form-item>
          <el-form-item v-if="json.countryId === 'ASTK.KL'" label="认股权证">
            <el-select v-model="json.malaysiawarrants" placeholder="是否开启" clearable :style="{width: '100%'}">
              <el-option label="开启" value="1"></el-option>
              <el-option label="关闭" value="0"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="form.dataSourceMark === 'CNA'">
          <el-form-item label="licence">
            <el-input v-model="json.licence" placeholder="licence"/>
          </el-form-item>
        </div>
        <el-form-item label="上线" prop="isMaster">
          <el-select v-model="form.status" placeholder="请输入主市场" clearable :style="{width: '100%'}">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="同步节假日名称" prop="syncName">
          <el-input v-model="form.syncName" placeholder="同步节假日名称"/>
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
import {addMarket, delMarket, getMarket, listMarket, updateMarket} from "@/api/market/market";

export default {
  name: "Market",
  data() {
    return {
      // 遮罩层
      imagesUrl: Global.gtpUrl,
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
      // 市场表格数据
      marketList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        currency: null,
        countryImg: null,
        isMaster: null,
        dataSourceMark: null,
        dataSourceJson: null,
      },
      fileInfo: null,
      // 表单参数
      form: {},
      json: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "名字不能为空", trigger: "blur"}
        ],
        currency: [
          {required: true, message: "货币缩写不能为空", trigger: "blur"}
        ],
        countryImg: [
          {required: true, message: "国旗图片不能为空", trigger: "blur"}
        ],
        isMaster: [
          {required: true, message: "主市场不能为空", trigger: "blur"}
        ],
        mainExchangeRate: [
          {required: true, message: "主市场对本市场货币汇率不能为空", trigger: "blur"}
        ],
        slaveExchangeRate: [
          {required: true, message: "本市场对主市场货币汇率不能为空", trigger: "blur"}
        ],
        dataSourceMark: [
          {required: true, message: "数据源标记不能为空", trigger: "blur"}
        ],
        dataSourceJson: [
          {required: true, message: "数据源配置JSON不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询市场列表 */
    getList() {
      this.loading = true;
      listMarket(this.queryParams).then(response => {
        this.marketList = response.rows;
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
        currency: null,
        sort: null,
        country: null,
        status: null,
        syncName: null,
        countryImg: null,
        isMaster: null,
        dataSourceMark: null,
        dataSourceJson: null,
      };
      this.json = {
        domain: null,
        key: null,
        stockDomain: null,
        klineDomain: null,
        countryId: null,
        licence: null,
      }
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
      this.title = "添加市场";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMarket(id).then(response => {
        this.form = response.data;
        this.json = JSON.parse(response.data.dataSourceJson)
        this.open = true;
        this.title = "修改市场";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.dataSourceJson = JSON.stringify(this.json)
          if (this.form.id != null) {
            updateMarket(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMarket(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
          this.$refs.fileUploadRef.resetFile();
        }
      });
    },
    onFleInfo(res) {
      this.form.countryImg = res;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除市场编号为"' + ids + '"的数据项？').then(function () {
        return delMarket(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('market/market/export', {
        ...this.queryParams
      }, `market_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
