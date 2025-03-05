<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分组" prop="group">
        <el-select v-model="queryParams.group" placeholder="请选择值类型" clearable>
          <el-option
            v-for="group in groupList"
            :key="group"
            :label="group"
            :value="group"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="键值" prop="key">
        <el-input
          v-model="queryParams.key"
          placeholder="请输入键值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="值类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择值类型" clearable>
          <el-option
            v-for="dict in dict.type.common_config_type"
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
          v-hasPermi="['config:common:add']"
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
          v-hasPermi="['config:common:edit']"
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
          v-hasPermi="['config:common:remove']"
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
      <!--          v-hasPermi="['config:common:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commonList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="分组" align="center" prop="group"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="键值" align="center" prop="key"/>
      <el-table-column label="值类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.common_config_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="对值" align="center" prop="val">
        <template slot-scope="scope">
          <img v-if="scope.row.type === 'image'" style="width: 50px" :src="imagesUrl + scope.row.val" alt="暂无"/>
          <span v-if="scope.row.type === 'richText'">请在修改中查看</span>
          <span v-else>{{ scope.row.val }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['config:common:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['config:common:remove']"
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

    <!-- 添加或修改通用配置对话框 -->
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分组" prop="group">
          <el-autocomplete
            class="inline-input"
            v-model="form.group"
            :fetch-suggestions="querySearch"
            placeholder="请输入内容"
          >
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="KEY" prop="key">
          <el-input v-model="form.key" placeholder="请输入键值"/>
        </el-form-item>
        <el-form-item label="值类型" prop="type">
          <el-select @change="onSelectType" v-model="form.type" placeholder="请选择值类型">
            <el-option
              v-for="dict in dict.type.common_config_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :disabled="form.type == null" label="值" prop="val">
          <editor :disabled="form.type == null" v-if="form.type === 'richText'" v-model="form.val" :min-height="192"/>
          <FileUpload :disabled="form.type == null" v-if="form.type === 'image'" @input="(val)=>{form.val = val}"
                      ref="fileUploadRef"/>
          <el-input :disabled="form.type == null" v-if="form.type === 'json' || form.type === 'array'"
                    v-model="form.val" type="textarea"
                    placeholder="请输入内容"/>
          <el-input :disabled="form.type == null" v-if="form.type === 'str' || form.type === null" v-model="form.val"
                    placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {addCommon, delCommon, getCommon, getGroup, listCommon, updateCommon} from "@/api/config/common";

export default {
  name: "Common",
  dicts: ['common_config_type'],
  data() {
    return {
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
      // 通用配置表格数据
      commonList: [],
      groupList: [],
      groupSearch: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        group: null,
        key: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        group: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        key: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        val: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    onSelectType() {
      this.form.val = null;
    },
    /** 查询通用配置列表 */
    getList() {
      this.loading = true;
      listCommon(this.queryParams).then(response => {
        this.commonList = response.rows;
        this.total = response.total;
        this.group();
        this.loading = false;
      });
    },
    group() {
      getGroup().then(response => {
        this.groupList = response.data;
        this.groupSearch = [];
        this.groupList.forEach((val) => {
          this.groupSearch.push({
            value: val,
          })
        })
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.$refs.fileUploadRef.resetFile();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        group: null,
        key: null,
        type: null,
        val: null,
        remark: null
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
      this.title = "添加通用配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCommon(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改通用配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCommon(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.$refs.fileUploadRef.resetFile();
            });
          } else {
            addCommon(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.$refs.fileUploadRef.resetFile();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除通用配置编号为"' + ids + '"的数据项？').then(function () {
        return delCommon(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('config/common/export', {
        ...this.queryParams
      }, `common_${new Date().getTime()}.xlsx`)
    },
    querySearch(queryString, cb) {

      let results = queryString ? this.groupSearch.filter(this.createFilter(queryString)) : this.groupSearch;
      console.log(results)
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
  }
};
</script>
