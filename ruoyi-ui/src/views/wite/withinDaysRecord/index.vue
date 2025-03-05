<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场" prop="marketId">
        <MarketSelect @input="(val)=>{queryParams.marketId = val}"/>
      </el-form-item>
      <el-form-item label="代理" prop="userId">
        <AgentSelect @val="(val)=>queryParams.userId = val"></AgentSelect>
      </el-form-item>
      <el-form-item label="会员Id">
        <el-input
          v-model="queryParams.memberId"
          placeholder="请输入会员Id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员手机号">
        <el-input
          v-model="queryParams.memberPhone"
          placeholder="请输入会员手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.rn_jy_status"
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

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['stock:withinDaysRecord:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:withinDaysRecord:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:withinDaysRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['stock:withinDaysRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table v-loading="loading" :data="withinDaysRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="市场" align="center" prop="marketId">
        <template slot-scope="scope">
          <MarketSpan :marketId="scope.row.marketId"/>
        </template>
      </el-table-column>
      <el-table-column label="实盘" align="center" prop="accountType">
        <template slot-scope="scope">
          <span v-if="scope.row.accountType === '1'">模拟</span>
          <span v-if="scope.row.accountType === '0'">实盘</span>
        </template>
      </el-table-column>
      <el-table-column label="会员ID" align="center" prop="memberId" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column label="会员名称" align="center" prop="memberRealName"/>
      <el-table-column label="会员手机" align="center" prop="memberPhone"/>
      <el-table-column label="代理" align="center" prop="userId">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}/{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请金额" align="center" prop="amt"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.rn_jy_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status === 1"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:withinDaysRecord:edit']"
          >审批
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            v-if="scope.row.status === 2"
            @click="handleCreate(scope.row)"
            v-hasPermi="['stock:withinDaysRecord:create']"
          >日内建仓
          </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:withinDaysRecord:remove']"
          >删除
          </el-button> -->
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

    <!-- 添加或修改用户日内交易记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="市场" prop="marketId">
          <MarketSpan :marketId="form.marketId"/>
        </el-form-item>
        <el-form-item label="会员ID" prop="memberId">
          <span>{{ form.memberId }}</span>
        </el-form-item>
        <el-form-item label="申请金额" prop="amt">
          <span>{{ form.amt }}</span>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.rn_jy_sp_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="日内建仓" :visible.sync="open2" width="900px" append-to-body>
      <el-form ref="form2" :model="form2" :rules="rules2" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="选择股票" prop="stockGid">
              <el-input v-model="form2.stockGid" size="small" placeholder="请输入股票CODE或股票GID">
                <el-button slot="append" icon="el-icon-search" @click="searchStock()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form :model="stock" size="small" :inline="true" label-width="68px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="名称">
                <span>{{ stock.name }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="代码">
                <span>{{ stock.code }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="现价">
                <span>{{ stock.last }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="涨跌">
                <span>{{ stock.chg }}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div style="height: 10px;"></div>
        <el-divider content-position="center">下单信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="买入时间" prop="buyOrderTime">
              <el-date-picker
                v-model="form2.buyOrderTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择买入时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="买入价格" prop="buyOrderPrice">
              <el-input v-model="form2.buyOrderPrice" size="small" style="width: 220px;" placeholder="请输入买入价格"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="杠杆倍数" prop="lever">
              <el-input v-model="form2.lever" size="small" style="width: 220px;" placeholder="请输入杠杆倍数"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="买入方向">
              <el-radio-group v-model="form2.direction">
                <el-radio
                  v-for="dict in dict.type.stock_direction"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="买入股数" prop="num">
              <el-input v-model="form2.num" size="small" style="width: 220px;" placeholder="请输入杠杆倍数">
                <el-button slot="append">股</el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addWithinDaysRecord,
  createWithinDaysRecord,
  delWithinDaysRecord,
  getWithinDaysRecord,
  listWithinDaysRecord,
  updateWithinDaysRecord
} from "@/api/wite/withinDaysRecord";
import {getLastStock} from "@/api/stock/stock";

export default {
  name: "WithinDaysRecord",
  dicts: ['rn_jy_status', 'rn_jy_sp_status', 'stock_direction'],
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
      // 用户日内交易记录表格数据
      withinDaysRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        marketId: null,
        memberId: null,
        amt: null,
        status: null,
        memberPhone: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        marketId: [
          {required: true, message: "市场ID不能为空", trigger: "blur"}
        ],
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
        ],
        amt: [
          {required: true, message: "申请金额不能为空", trigger: "blur"}
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
      },
      open2: false,
      form2: {},
      member: {},
      stock: {},
      rules2: {
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
        ],
        stockGid: [
          {required: true, message: "股票GID不能为空", trigger: "blur"}
        ],
        buyOrderTime: [
          {required: true, message: "购买时间不能为空", trigger: "blur"}
        ],
        buyOrderPrice: [
          {required: true, message: "买入价格不能为空", trigger: "blur"}
        ],
        num: [
          {required: true, message: "买入数量不能为空", trigger: "blur"}
        ],
        lever: [
          {required: true, message: "杠杆不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户日内交易记录列表 */
    getList() {
      this.loading = true;
      listWithinDaysRecord(this.queryParams).then(response => {
        this.withinDaysRecordList = response.rows;
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
        memberId: null,
        amt: null,
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
      this.title = "添加用户日内交易记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWithinDaysRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户日内交易记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWithinDaysRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWithinDaysRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户日内交易记录编号为"' + ids + '"的数据项？').then(function () {
        return delWithinDaysRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/withinDaysRecord/export', {
        ...this.queryParams
      }, `withinDaysRecord_${new Date().getTime()}.xlsx`)
    },
    //日内建仓
    handleCreate(row) {
      this.form2 = {
        memberId: null,
        phone: null,
        stockGid: null,
        mode: 1,
        buyOrderTime: null,
        lever: 1,
        num: null,
        buyOrderPrice: null,
        direction: '1',
        witeId: row.id
      },
        this.stock = {};
      this.open2 = true;
    },
    //搜索股票
    searchStock() {
      getLastStock(this.form2.stockGid).then(res => {
        this.stock = res.data
      })
    },
    //取消日内建仓
    cancel2() {
      this.open2 = false;
    },
    //提交日内建仓
    submitForm2() {
      this.$refs["form2"].validate(valid => {
        if (valid) {
          createWithinDaysRecord(this.form2).then(res => {
            this.$modal.msgSuccess("建仓成功");
            this.open2 = false;
          })
        }
      });
    }
  }
};
</script>
<style lang="css" scoped>
.btn {
  text-align: center;
  margin: 10px 0 60px 0;
}

.el-divider {
  margin: 20px 0 50px 0;
  font-size: 20em;
}

.el-divider__text {
  position: absolute;
  background-color: #FFFFFF;
  padding: 0 20px;
  font-weight: 500;
  color: #303133;
  font-size: 18px;
}
</style>
