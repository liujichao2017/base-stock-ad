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
      </el-form-item>
      <el-form-item label="申购数量" prop="nums">
        <el-input
          v-model="queryParams.nums"
          placeholder="请输入申购数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="购买金额" prop="buyAmt">
        <el-input
          v-model="queryParams.buyAmt"
          placeholder="请输入购买金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!--      <el-form-item label="购买方向" prop="direction">-->
      <!--        <el-select v-model="queryParams.direction" placeholder="请选择购买方向" clearable>-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.otc_record_direction"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!-- <el-form-item label="杠杆" prop="lever">
        <el-input
          v-model="queryParams.lever"
          placeholder="请输入杠杆"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.otc_record_status"
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
          v-hasPermi="['stock:otcRecord:add']"
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
          v-hasPermi="['stock:otcRecord:edit']"
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
          v-hasPermi="['stock:otcRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['stock:otcRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table v-loading="loading" :data="otcRecordList" @selection-change="handleSelectionChange"
              :default-expand-all="true">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right" size="small" :inline="true" class="demo-table-expand">
            <el-row>
              <el-col :span="6">
                <el-form-item label="股票名称">
                  <span>{{ props.row.stockName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="股票代码">
                  <span>{{ props.row.stockCode }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="股票GID">
                  <span>{{ props.row.stockGid }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="股票价格">
                  <span>{{ props.row.price }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="申购数量">
                  <span>{{ props.row.nums }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="申购金额">
                  <span>{{ props.row.buyAmt }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="通过数量">
                  <span>{{ props.row.adoptNum }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="通过金额">
                  <span>{{ props.row.adoptAmt }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="买卖方向">
                  <dict-tag :options="dict.type.otc_record_direction" :value="props.row.direction"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="杠杆倍数">
                  <span>{{ props.row.lever }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="状态">
                  <dict-tag :options="dict.type.otc_record_status" :value="props.row.status"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="创建时间">
                  <span>{{ props.row.createTime }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </template>
      </el-table-column>
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
      <!-- <el-table-column label="股票名称" align="center" prop="stockName" /> -->
      <!-- <el-table-column label="股票代码" align="center" prop="stockCode" />
      <el-table-column label="股票GID" align="center" prop="stockGid" /> -->
      <!-- <el-table-column label="价格" align="center" prop="price" /> -->
      <!-- <el-table-column label="类型：备用" align="center" prop="type" /> -->
      <!-- <el-table-column label="申购数量" align="center" prop="nums" />
      <el-table-column label="购买金额" align="center" prop="buyAmt" /> -->
      <!-- <el-table-column label="通过数量" align="center" prop="adoptNum" />
      <el-table-column label="通过金额" align="center" prop="adoptAmt" /> -->
      <!-- <el-table-column label="购买方向" align="center" prop="direction">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.otc_record_direction" :value="scope.row.direction"/>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="杠杆" align="center" prop="lever" /> -->
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.otc_record_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status === '1'"
            icon="el-icon-edit"
            @click="handleExam(scope.row)"
            v-hasPermi="['stock:otcRecord:exam']"
          >审批
          </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:otcRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:otcRecord:remove']"
          >删除</el-button> -->
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

    <!-- 添加或修改otc(大宗)记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="市场" prop="marketId">
          <el-input v-model="form.marketId" placeholder="请输入市场"/>
        </el-form-item>
        <el-form-item label="会员Id" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入会员Id"/>
        </el-form-item>
        <el-form-item label="股票名称" prop="stockName">
          <el-input v-model="form.stockName" placeholder="请输入股票名称"/>
        </el-form-item>
        <el-form-item label="股票代码" prop="stockCode">
          <el-input v-model="form.stockCode" placeholder="请输入股票代码"/>
        </el-form-item>
        <el-form-item label="股票GID" prop="stockGid">
          <el-input v-model="form.stockGid" placeholder="请输入股票GID"/>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="申购数量" prop="nums">
          <el-input v-model="form.nums" placeholder="请输入申购数量"/>
        </el-form-item>
        <el-form-item label="购买金额" prop="buyAmt">
          <el-input v-model="form.buyAmt" placeholder="请输入购买金额"/>
        </el-form-item>
        <el-form-item label="购买方向" prop="direction">
          <el-radio-group v-model="form.direction">
            <el-radio
              v-for="dict in dict.type.otc_record_direction"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="杠杆" prop="lever">
          <el-input v-model="form.lever" placeholder="请输入杠杆"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.otc_record_status"
              :key="dict.value"
              :label="dict.value"
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


    <el-dialog title="申购审批" :visible.sync="open2" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="市场" prop="marketId">
              <MarketSpan :marketId="form.marketId"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会员" prop="memberId">
              <span>{{ form.memberRealName + '(' + form.memberPhone + ')' }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="股票名称" prop="stockName">
              <span>{{ form.stockName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="股票代码" prop="stockCode">
              <span>{{ form.stockCode }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="股票GID" prop="stockGid">
              <span>{{ form.stockGid }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="价格" prop="price">
              <span>{{ form.price }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申购数量" prop="nums">
              <span>{{ form.nums }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购买金额" prop="buyAmt">
              <span>{{ form.buyAmt }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="买卖方向">
              <dict-tag :options="dict.type.otc_record_direction" :value="form.direction"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="杠杆倍数">
              <span>{{ form.lever }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间" prop="createTime">
              <span>{{ form.createTime }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通过数量" prop="adoptNum">
              <el-input v-model="form.adoptNum" placeholder="请输入市场"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.otc_record_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
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
  addOtcRecord,
  delOtcRecord,
  examOtcRecord,
  getOtcRecord,
  listOtcRecord,
  updateOtcRecord
} from "@/api/otc/otcRecord";

export default {
  name: "OtcRecord",
  dicts: ['otc_record_direction', 'otc_record_status'],
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
      // otc(大宗)记录表格数据
      otcRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false, open2: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        marketId: null,
        memberId: null,
        stockName: null,
        stockCode: null,
        stockGid: null,
        price: null,
        type: null,
        nums: null,
        buyAmt: null,
        direction: null,
        lever: null,
        status: null,
        memberPhone: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        marketId: [
          {required: true, message: "市场不能为空", trigger: "blur"}
        ],
        memberId: [
          {required: true, message: "会员Id不能为空", trigger: "blur"}
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
        nums: [
          {required: true, message: "申购数量不能为空", trigger: "blur"}
        ],
        buyAmt: [
          {required: true, message: "购买金额不能为空", trigger: "blur"}
        ],
        direction: [
          {required: true, message: "购买方向不能为空", trigger: "change"}
        ],
        lever: [
          {required: true, message: "杠杆不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        createTime: [
          {required: true, message: "创建时间/申请时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        adoptNum: [
          {required: true, message: "通过数量不能为空", trigger: "blur"}
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
    /** 查询otc(大宗)记录列表 */
    getList() {
      this.loading = true;
      this.queryParams.type = this.$route.query.type;
      listOtcRecord(this.queryParams).then(response => {
        this.otcRecordList = response.rows;
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
        stockName: null,
        stockCode: null,
        stockGid: null,
        price: null,
        type: null,
        nums: null,
        buyAmt: null,
        direction: null,
        lever: null,
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
      this.title = "添加otc(大宗)记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOtcRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改otc(大宗)记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOtcRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOtcRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除otc(大宗)记录编号为"' + ids + '"的数据项？').then(function () {
        return delOtcRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/otcRecord/export', {
        ...this.queryParams
      }, `otcRecord_${new Date().getTime()}.xlsx`)
    },
    //审批
    handleExam(row) {
      this.form = row;
      this.form.adoptNum = row.nums;
      this.open2 = true;
      this.form.status = '2'
    },
    //审批取消
    cancel2() {
      this.open2 = false;
      this.getList();
    },
    //审批提交
    submitForm2() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          examOtcRecord(this.form).then(res => {
            this.$modal.msgSuccess("操作成功");
            this.open2 = false;
            this.getList();
          })
        }
      });
    }
  }
};
</script>
