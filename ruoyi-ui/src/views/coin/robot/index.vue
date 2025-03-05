<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <h1>交易对: {{ coin.symbol }} 币名: {{ coin.bcdn }} </h1>
        <p>
          机器人指令: <br/>
          1. 自发币通过机器人指令来操作价格<br/>
          2. 通过自发币当前的价格, 选择符合最大最小价格的条件指令,
          如果多个指令符合要求,这按照优先级从低到高的方式选择<br/>
          3. 选择指令后, 从最大最小波幅之间随机一个随机波幅值, 对自发币进行价格波幅: 老价格 + 随机波幅 = 新价格<br/>
          4. 最大最小数量决定每次波幅机器人购买的数量<br/>
          6. 波幅自发币会生成 自发币的交易记录, 自发币K线数据<br/>
          7. 完成一次波幅后, 从最大最小频率之间随机一个随机波幅值来觉得下一次波幅时间. 1分钟 = 60秒 = 60000毫秒<br/>
          8. 当没有符合条件的指令时, 将会按照频率: 250-750毫秒,波幅: -0.005-0.005U, 数量: 1-5进行波动<br/>
        </p>
      </el-row>
      <!--      <el-form-item label="状态" prop="status">-->
      <!--        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.yes_no"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item>-->
      <!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
      <!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      <!--      </el-form-item>-->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['coin:robot:add']"
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
          v-hasPermi="['coin:robot:edit']"
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
          v-hasPermi="['coin:robot:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleGen"
        >快速生成数据
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="$router.push('/coin/kline?symbol='+coin.symbol+'&bcdn='+coin.bcdn)"
        >K线图
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="robotList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="最小价格" align="center" prop="minPrice"/>
      <el-table-column label="最大价格" align="center" prop="maxPrice"/>
      <el-table-column label="最小频率(毫秒)" align="center" prop="minFrequency"/>
      <el-table-column label="最大频率(毫秒)" align="center" prop="maxFrequency"/>
      <el-table-column label="最小波幅" align="center" prop="minAmplitude"/>
      <el-table-column label="最大波幅" align="center" prop="maxAmplitude"/>
      <el-table-column label="最小数量" align="center" prop="minNum"/>
      <el-table-column label="最大数量" align="center" prop="maxNum"/>
      <el-table-column label="优先级" align="center" prop="priority"/>
      <el-table-column label="状态" align="center" prop="status">
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
            v-hasPermi="['coin:robot:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['coin:robot:remove']"
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

    <!-- 添加或修改机器人对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="最小价格" prop="minPrice">
          <el-input v-model="form.minPrice" placeholder="请输入小于现价"/>
        </el-form-item>
        <el-form-item label="最大价格" prop="maxPrice">
          <el-input v-model="form.maxPrice" placeholder="请输入大于现价"/>
        </el-form-item>
        <el-form-item label="最小频率(毫秒)" prop="minFrequency">
          <el-input v-model="form.minFrequency" placeholder="请输入频率:毫秒"/>
        </el-form-item>
        <el-form-item label="最大频率(毫秒)" prop="maxFrequency">
          <el-input v-model="form.maxFrequency" placeholder="请输入频率:毫秒"/>
        </el-form-item>
        <el-form-item label="最小波幅" prop="minAmplitude">
          <el-input v-model="form.minAmplitude" placeholder="请输入波幅"/>
        </el-form-item>
        <el-form-item label="最大波幅" prop="maxAmplitude">
          <el-input v-model="form.maxAmplitude" placeholder="请输入波幅"/>
        </el-form-item>
        <el-form-item label="最小数量" prop="minNum">
          <el-input v-model="form.minNum" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="最大数量" prop="maxNum">
          <el-input v-model="form.maxNum" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="优先级" prop="priority">
          <el-input v-model="form.priority" placeholder="请输入优先级"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
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


    <!-- 添加或修改机器人对话框 -->
    <el-dialog title="生成数据" :visible.sync="genOpen" width="500px" append-to-body>
      <el-form ref="genForm" :model="genForm" :rules="rules" label-width="80px">
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="genForm.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitGenForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addRobot, delRobot, genData, getRobot, listRobot, updateRobot} from "@/api/coin/robot";

export default {
  name: "Robot",
  dicts: ['yes_no'],
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
      // 机器人表格数据
      robotList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      genOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        csId: null,
        minPrice: null,
        maxPrice: null,
        minFrequency: null,
        maxFrequency: null,
        minAmplitude: null,
        maxAmplitude: null,
        priority: null,
        status: null,
      },
      coin: {
        symbol: null,
        bcdn: null
      },
      // 表单参数
      form: {},
      genForm: {},
      // 表单校验
      rules: {
        minPrice: [{required: true, message: "不能为空", trigger: "blur"}],
        maxPrice: [{required: true, message: "不能为空", trigger: "blur"}],
        minFrequency: [{required: true, message: "不能为空", trigger: "blur"}],
        maxFrequency: [{required: true, message: "不能为空", trigger: "blur"}],
        minAmplitude: [{required: true, message: "不能为空", trigger: "blur"}],
        maxAmplitude: [{required: true, message: "不能为空", trigger: "blur"}],
        minNum: [{required: true, message: "不能为空", trigger: "blur"}],
        maxNum: [{required: true, message: "不能为空", trigger: "blur"}],
        priority: [{required: true, message: "不能为空", trigger: "blur"}],
        status: [{required: true, message: "不能为空", trigger: "blur"}],
        csId: [{required: true, message: "交易对: 自发币ID不能为空", trigger: "blur"}]

      }
    };
  },
  created() {
    this.getList();
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
  methods: {
    /** 查询机器人列表 */
    getList() {
      this.loading = true;
      this.queryParams.csId = this.$route.query.id;
      listRobot(this.queryParams).then(response => {
        this.robotList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.genOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        csId: null,
        minPrice: null,
        maxPrice: null,
        minFrequency: null,
        maxFrequency: null,
        minAmplitude: null,
        maxAmplitude: null,
        minNum: null,
        maxNum: null,
        priority: null,
        status: null,
        createTime: null,
        updateTime: null
      };
      this.genForm = {
        symbol: null,
        startTime: null,
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
      this.title = "添加机器人";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRobot(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改机器人";
      });
    },
    /** 新增按钮操作 */
    handleGen() {
      this.reset();
      this.genOpen = true;
    },
    submitGenForm() {
      this.$refs["genForm"].validate(valid => {
        if (valid) {
          this.genForm.symbol = this.$route.query.symbol;
          genData(this.genForm).then(response => {
            this.$modal.msgSuccess("生成中");
            this.genOpen = false;
            this.getList();
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRobot(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.csId = this.$route.query.id;
            addRobot(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除机器人编号为"' + ids + '"的数据项？').then(function () {
        return delRobot(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('coin/robot/export', {
        ...this.queryParams
      }, `robot_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
