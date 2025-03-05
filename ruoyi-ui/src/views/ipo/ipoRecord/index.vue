<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="市场" prop="marketId">
        <MarketSelect @input="(val)=>{queryParams.marketId = val}"/>
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
      <el-form-item label="代理" prop="userId">
        <AgentSelect @val="(val)=>queryParams.userId = val"></AgentSelect>
      </el-form-item>
      <!-- <el-form-item label="新股ID" prop="ipoId">
        <el-input
          v-model="queryParams.ipoId"
          placeholder="请输入新股ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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
      <!-- <el-form-item label="申购数量" prop="nums">
        <el-input
          v-model="queryParams.nums"
          placeholder="请输入申购数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ipo_record_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="通知状态" prop="notifyStatus">
        <el-select v-model="queryParams.notifyStatus" placeholder="请选择通知状态" clearable>
          <el-option
            v-for="dict in dict.type.ipo_record_tz"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="中签金额" prop="totalAmt">
        <el-input
          v-model="queryParams.totalAmt"
          placeholder="请输入中签金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已划转金额" prop="transferAmt">
        <el-input
          v-model="queryParams.transferAmt"
          placeholder="请输入已划转金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!--
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['stock:ipoRecord:add']"
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
              v-hasPermi="['stock:ipoRecord:edit']"
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
              v-hasPermi="['stock:ipoRecord:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['stock:ipoRecord:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row> -->

    <el-table v-loading="loading" :data="ipoRecordList" @selection-change="handleSelectionChange"
              :default-expand-all="true">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right" size="small" :inline="true" class="demo-table-expand">
            <el-row>
              <el-col :span="6">
                <el-form-item label="新股名称">
                  <span>{{ props.row.name }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="申购代码">
                  <span>{{ props.row.code }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="发行价格">
                  <span>{{ props.row.price }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="申购数量">
                  <span>{{ props.row.nums }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="中签数量">
                  <span>{{ props.row.winNum }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="已划转金额">
                  <span>{{ props.row.transferAmt }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="状态">
                  <dict-tag :options="dict.type.ipo_record_status" :value="props.row.status"/>
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
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ipo_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="新股名称" align="center" prop="name" />
      <el-table-column label="申购代码" align="center" prop="code" />
      <el-table-column label="发行价格" align="center" prop="price" />
      <el-table-column label="申购数量" align="center" prop="nums" /> -->
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ipo_record_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="通知状态" align="center" prop="notifyStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ipo_record_tz" :value="scope.row.notifyStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="通知内容" align="center" prop="notifyContext" />
      <el-table-column label="中签金额" align="center" prop="totalAmt" /> -->
      <!-- <el-table-column label="已划转金额" align="center" prop="transferAmt" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.transferAmt == 0"
            @click="handleExam(scope.row)"
            v-hasPermi="['stock:ipoRecord:exam']"
          >审批
          </el-button>
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status === '2'"
            icon="el-icon-money"
            @click="handleTransfer(scope.row)"
            v-hasPermi="['stock:ipoRecord:transfer']"
          >划转
          </el-button>
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status === '2'"
            icon="el-icon-down"
            @click="handleTranPos(scope.row)"
            v-hasPermi="['stock:ipoRecord:traPos']"
          >转持仓
          </el-button>
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status === '2'"
            icon="el-icon-delete"
            @click="handleRefund(scope.row)"
            v-hasPermi="['stock:ipoRecord:refund']"
          >退款
          </el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:ipoRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:ipoRecord:remove']"
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

    <!-- 添加或修改IPO(新股)申购记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="市场ID" prop="marketId">
          <el-input v-model="form.marketId" placeholder="请输入市场ID"/>
        </el-form-item>
        <el-form-item label="会员Id" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入会员Id"/>
        </el-form-item>
        <el-form-item label="新股ID" prop="ipoId">
          <el-input v-model="form.ipoId" placeholder="请输入新股ID"/>
        </el-form-item>
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
        <el-form-item label="新股名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入新股名称"/>
        </el-form-item>
        <el-form-item label="申购代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入申购代码"/>
        </el-form-item>
        <el-form-item label="发行价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入发行价格"/>
        </el-form-item>
        <el-form-item label="申购数量" prop="nums">
          <el-input v-model="form.nums" placeholder="请输入申购数量"/>
        </el-form-item>
        <el-form-item label="状态: 1: 待审批, 2: 中签, 3: 未中签" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.ipo_record_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="通知状态: 1: 已通知, 0: 未通知" prop="notifyStatus">
          <el-radio-group v-model="form.notifyStatus">
            <el-radio
              v-for="dict in dict.type.ipo_record_tz"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="通知内容" prop="notifyContext">
          <el-input v-model="form.notifyContext" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="中签金额" prop="totalAmt">
          <el-input v-model="form.totalAmt" placeholder="请输入中签金额"/>
        </el-form-item>
        <el-form-item label="已划转金额" prop="transferAmt">
          <el-input v-model="form.transferAmt" placeholder="请输入已划转金额"/>
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
          <el-col :span="12">
            <el-form-item label="新股名称" prop="stockName">
              <span>{{ form.name }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申购代码" prop="stockCode">
              <span>{{ form.code }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发行价格" prop="price">
              <span>{{ form.price }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申购数量" prop="nums">
              <span>{{ form.nums }}</span>
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
            <el-form-item label="中签数量" prop="winNum">
              <el-input v-model="form.winNum" placeholder="请输入中签数量"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.ipo_record_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item v-if="form.status === '2'" label="中签提示" prop="kindTips">
              <el-input type="textarea" style="width: 400px" rows="3" v-model="form.kindTips"
                        placeholder="Friendly reminder：make sure trading account have sufficient funds to subscribe the IPO allotment, otherwise it will affect your next IPO subscription and the allotment %"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item v-if="form.status === '2'" label="通知日期">
              <el-date-picker
                v-model="form.publishTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择通知日期">
              </el-date-picker>
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
  addIpoRecord,
  delIpoRecord,
  examIpoRecord,
  getIpoRecord,
  listIpoRecord,
  refundIpoRecord,
  transferIpoRecord,
  traPosIpoRecord,
  updateIpoRecord
} from "@/api/ipo/ipoRecord";
import AgentSelect from "@/components/AgentSelect/index.vue";

export default {
  name: "IpoRecord",
  components: {AgentSelect},
  dicts: ['ipo_record_status', 'ipo_record_tz', 'ipo_type'],
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
      // IPO(新股)申购记录表格数据
      ipoRecordList: [],
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
        ipoId: null,
        type: null,
        name: null,
        code: null,
        price: null,
        nums: null,
        status: null,
        notifyStatus: null,
        notifyContext: null,
        totalAmt: null,
        transferAmt: null,
        memberPhone: null
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
        ipoId: [
          {required: true, message: "新股ID不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "类型不能为空", trigger: "change"}
        ],
        name: [
          {required: true, message: "新股名称不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "申购代码不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "发行价格不能为空", trigger: "blur"}
        ],
        nums: [
          {required: true, message: "申购数量不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态: 1: 待审批, 2: 中签, 3: 未中签不能为空", trigger: "change"}
        ],
        notifyStatus: [
          {required: true, message: "通知状态: 1: 已通知, 0: 未通知不能为空", trigger: "change"}
        ],
        notifyContext: [
          {required: true, message: "通知内容不能为空", trigger: "blur"}
        ],
        totalAmt: [
          {required: true, message: "中签金额不能为空", trigger: "blur"}
        ],
        transferAmt: [
          {required: true, message: "已划转金额不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间/申请时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        winNum: [
          {required: true, message: "中签数不能为空", trigger: "blur"}
        ],
      }, open2: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询IPO(新股)申购记录列表 */
    getList() {
      this.loading = true;
      listIpoRecord(this.queryParams).then(response => {
        this.ipoRecordList = response.rows;
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
        ipoId: null,
        type: null,
        name: null,
        code: null,
        price: null,
        nums: null,
        status: null,
        notifyStatus: null,
        notifyContext: null,
        totalAmt: null,
        transferAmt: null,
        createTime: null,
        updateTime: null,
        kindTips: 'Friendly reminder：make sure trading account have sufficient funds to subscribe the IPO allotment, otherwise it will affect your next IPO subscription and the allotment'
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
      this.title = "添加IPO(新股)申购记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIpoRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改IPO(新股)申购记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIpoRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIpoRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除IPO(新股)申购记录编号为"' + ids + '"的数据项？').then(function () {
        return delIpoRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('stock/ipoRecord/export', {
        ...this.queryParams
      }, `ipoRecord_${new Date().getTime()}.xlsx`)
    },
    //审批
    handleExam(row) {
      this.form = row;
      this.form.winNum = row.nums;
      this.form.kindTips = 'Friendly reminder：make sure trading account have sufficient funds to subscribe the IPO allotment, otherwise it will affect your next IPO subscription and the allotment'
      this.open2 = true;
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
          examIpoRecord(this.form).then(res => {
            this.$modal.msgSuccess("操作成功");
            this.open2 = false;
            this.getList();
          })
        }
      });
    },
    //划转
    handleTransfer(row) {
      this.$modal.confirm('是否开始划转？').then(function () {
        return transferIpoRecord(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {
      });
    },
    //转持仓
    handleTranPos(row) {
      this.$modal.confirm('是否转持仓？').then(function () {
        return traPosIpoRecord(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {
      });
    },
    //退款
    handleRefund(row) {
      this.$modal.confirm('是否退款？').then(function () {
        return refundIpoRecord(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {
      });
    }
  }
};
</script>
