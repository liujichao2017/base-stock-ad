<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="股票代码" prop="sym">
        <el-input
          v-model="queryParams.sym"
          placeholder="请输入股票代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="股票名称" prop="desp">
        <el-input
          v-model="queryParams.desp"
          placeholder="请输入股票名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.bearbull_status"
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
          v-hasPermi="['hkbearbull:hkbearbull:add']"
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
          v-hasPermi="['hkbearbull:hkbearbull:edit']"
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
          v-hasPermi="['hkbearbull:hkbearbull:remove']"
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
<!--          v-hasPermi="['hkbearbull:hkbearbull:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hkbearbullList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="股票代码" align="center" prop="sym"/>
      <el-table-column label="股票名称" align="center" prop="desp"/>
      <el-table-column label="热门" align="center" prop="rank"/>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bearbull_status" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="相关资产" align="center" prop="udly"/>
      <el-table-column label="发行" align="center" prop="issuer"/>
      <el-table-column label="现价" align="center" prop="last"/>
      <el-table-column label="跌涨额" align="center" prop="chg"/>
      <el-table-column label="跌涨幅" align="center" prop="pctchg"/>
      <el-table-column label="成交额" align="center" prop="turn"/>
      <el-table-column label="溢价" align="center" prop="premi"/>
      <el-table-column label="行使价" align="center" prop="strike"/>
      <el-table-column label="回收价" align="center" prop="calllv"/>
      <el-table-column label="杠杆" align="center" prop="gear"/>
      <el-table-column label="换股比率" align="center" prop="enratio"/>
      <el-table-column label="街货(%)" align="center" prop="pctout"/>
      <el-table-column label="街货量" align="center" prop="outq"/>
      <el-table-column label="最后交易日" align="center" prop="ldate"/>
      <el-table-column label="sign" align="center" prop="sign"/>
      <el-table-column label="mostatus" align="center" prop="mostatus"/>
      <el-table-column label="历史最高" align="center" prop="highlow"/>
      <el-table-column label="价内/外" align="center" prop="movalue"/>
      <el-table-column label="每手股数" align="center" prop="lots"/>
      <el-table-column label="距回收价" align="center" prop="spcall"/>
      <el-table-column label="距回收价(%)" align="center" prop="spcallpct"/>
      <el-table-column label="剩余交易日" align="center" prop="day"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hkbearbull:hkbearbull:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hkbearbull:hkbearbull:remove']"
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

    <!-- 添加或修改牛熊市股对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="股票名称" prop="desp">
          <el-input v-model="form.desp" placeholder="请输入股票名称"/>
        </el-form-item>
        <el-form-item label="热门" prop="rank">
          <el-input v-model="form.rank" placeholder="请输入热门"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.bearbull_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="相关资产" prop="udly">
          <el-input v-model="form.udly" placeholder="请输入相关资产"/>
        </el-form-item>
        <el-form-item label="发行" prop="issuer">
          <el-input v-model="form.issuer" placeholder="请输入发行"/>
        </el-form-item>
        <el-form-item label="现价" prop="last">
          <el-input v-model="form.last" placeholder="请输入现价"/>
        </el-form-item>
        <el-form-item label="跌涨额" prop="chg">
          <el-input v-model="form.chg" placeholder="请输入跌涨额"/>
        </el-form-item>
        <el-form-item label="跌涨幅" prop="pctchg">
          <el-input v-model="form.pctchg" placeholder="请输入跌涨幅"/>
        </el-form-item>
        <el-form-item label="成交额" prop="turn">
          <el-input v-model="form.turn" placeholder="请输入成交额"/>
        </el-form-item>
        <el-form-item label="溢价" prop="premi">
          <el-input v-model="form.premi" placeholder="请输入溢价"/>
        </el-form-item>
        <el-form-item label="行使价" prop="strike">
          <el-input v-model="form.strike" placeholder="请输入行使价"/>
        </el-form-item>
        <el-form-item label="回收价" prop="calllv">
          <el-input v-model="form.calllv" placeholder="请输入回收价"/>
        </el-form-item>
        <el-form-item label="杠杆" prop="gear">
          <el-input v-model="form.gear" placeholder="请输入杠杆"/>
        </el-form-item>
        <el-form-item label="换股比率" prop="enratio">
          <el-input v-model="form.enratio" placeholder="请输入换股比率"/>
        </el-form-item>
        <el-form-item label="街货(%)" prop="pctout">
          <el-input v-model="form.pctout" placeholder="请输入街货(%)"/>
        </el-form-item>
        <el-form-item label="街货量" prop="outq">
          <el-input v-model="form.outq" placeholder="请输入街货量"/>
        </el-form-item>
        <el-form-item label="最后交易日" prop="ldate">
          <el-input v-model="form.ldate" placeholder="请输入最后交易日"/>
        </el-form-item>
        <el-form-item label="sign" prop="sign">
          <el-input v-model="form.sign" placeholder="请输入sign"/>
        </el-form-item>
        <el-form-item label="历史最高" prop="highlow">
          <el-input v-model="form.highlow" placeholder="请输入历史最高"/>
        </el-form-item>
        <el-form-item label="价内/外" prop="movalue">
          <el-input v-model="form.movalue" placeholder="请输入价内/外"/>
        </el-form-item>
        <el-form-item label="每手股数" prop="lots">
          <el-input v-model="form.lots" placeholder="请输入每手股数"/>
        </el-form-item>
        <el-form-item label="距回收价" prop="spcall">
          <el-input v-model="form.spcall" placeholder="请输入距回收价"/>
        </el-form-item>
        <el-form-item label="距回收价(%)" prop="spcallpct">
          <el-input v-model="form.spcallpct" placeholder="请输入距回收价(%)"/>
        </el-form-item>
        <el-form-item label="剩余交易日" prop="day">
          <el-input v-model="form.day" placeholder="请输入剩余交易日"/>
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
  addHkbearbull,
  delHkbearbull,
  getHkbearbull,
  listHkbearbull,
  updateHkbearbull
} from "@/api/hkbearbull/hkbearbull";

export default {
  name: "Hkbearbull",
  dicts: ['bearbull_status'],
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
      // 牛熊市股表格数据
      hkbearbullList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sym: null,
        desp: null,
        rank: null,
        type: null,
        udly: null,
        issuer: null,
        last: null,
        chg: null,
        pctchg: null,
        turn: null,
        premi: null,
        strike: null,
        calllv: null,
        gear: null,
        enratio: null,
        pctout: null,
        outq: null,
        ldate: null,
        sign: null,
        mostatus: null,
        highlow: null,
        movalue: null,
        lots: null,
        spcall: null,
        spcallpct: null,
        day: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          {required: true, message: "创建时间/注册时间不能为空", trigger: "blur"}
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
    /** 查询牛熊市股列表 */
    getList() {
      this.loading = true;
      listHkbearbull(this.queryParams).then(response => {
        this.hkbearbullList = response.rows;
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
        sym: null,
        desp: null,
        rank: null,
        type: null,
        udly: null,
        issuer: null,
        last: null,
        chg: null,
        pctchg: null,
        turn: null,
        premi: null,
        strike: null,
        calllv: null,
        gear: null,
        enratio: null,
        pctout: null,
        outq: null,
        ldate: null,
        sign: null,
        mostatus: null,
        highlow: null,
        movalue: null,
        lots: null,
        spcall: null,
        spcallpct: null,
        day: null,
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
      this.ids = selection.map(item => item.sym)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加牛熊市股";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sym = row.sym || this.ids
      getHkbearbull(sym).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改牛熊市股";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sym != null) {
            updateHkbearbull(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHkbearbull(this.form).then(response => {
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
      const syms = row.sym || this.ids;
      this.$modal.confirm('是否确认删除牛熊市股编号为"' + syms + '"的数据项？').then(function () {
        return delHkbearbull(syms);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hkbearbull/hkbearbull/export', {
        ...this.queryParams
      }, `hkbearbull_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
