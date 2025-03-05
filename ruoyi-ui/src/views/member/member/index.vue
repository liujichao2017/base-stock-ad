<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户ID" prop="userId">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入客户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="代理" prop="userId">
        <AgentSelect @val="(val)=>queryParams.userId = val"></AgentSelect>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实盘" prop="accountType">
        <el-select v-model="queryParams.accountType" placeholder="请选择实盘" clearable>
          <el-option
            v-for="dict in dict.type.kh_zh_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="实名状态" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择实名状态" clearable>
          <el-option
            v-for="dict in dict.type.kh_zh_sm_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="允许登录" prop="isLogin">
        <el-select v-model="queryParams.isLogin" placeholder="请选择允许登录" clearable>
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
          v-hasPermi="['member:member:add']"
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
          v-hasPermi="['member:member:edit']"
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
          v-hasPermi="['member:member:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="sendSms"
          v-hasPermi="['member:member:edit']"
        >发短信
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['member:member:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="memberList"
              :default-expand-all="true"
              @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-row v-for="fund in props.row.funds" :key="fund.marketId">
              <el-col :span="3">
                <br/>
              </el-col>
              <el-col :span="3">
                <el-form-item label="国家">
                  <el-tag>{{ fund.marketName }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="可用余额">
                  <el-tag>{{ fund.enableAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="盈利金额">
                  <el-tag>{{ fund.profitAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="占用金额">
                  <el-tag>{{ fund.occupancyAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item label="冻结金额">
                  <el-tag>{{ fund.freezeAmt }}</el-tag>
                </el-form-item>
              </el-col>
              <el-col v-if="fund.experienceAmt" :span="3">
                <el-form-item label="体验金">
                  <el-tag>{{ fund.experienceAmt }}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="实盘" align="center" prop="accountType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.kh_zh_type" :value="scope.row.accountType"/>
        </template>
      </el-table-column>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="代理" align="center" prop="userId">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}/{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" prop="phone"/>
      <el-table-column label="真实姓名" align="center" prop="realName"/>
      <el-table-column label="实盘" align="center" prop="accountType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.kh_zh_type" :value="scope.row.accountType"/>
        </template>
      </el-table-column>
      <el-table-column label="实名状态" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.kh_zh_sm_status" :value="scope.row.isActive"/>
        </template>
      </el-table-column>
      <el-table-column label="视频认证" align="center" prop="videoActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.videoActive"/>
        </template>
      </el-table-column>
      <el-table-column label="允许登录" align="center" prop="isLogin">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isLogin"/>
        </template>
      </el-table-column>
      <el-table-column label="股票交易" align="center" prop="isStock">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isStock"/>
        </template>
      </el-table-column>
      <el-table-column label="杠杆交易" align="center" prop="isLever">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isLever"/>
        </template>
      </el-table-column>
      <el-table-column label="贷款额度" align="center" prop="loanAmt"/>
      <el-table-column label="操作" align="center" width="200px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['member:member:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateAmt(scope.row)"
            v-hasPermi="['member:member:updateAmt']"
          >资金
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateBankCard(scope.row)"
            v-hasPermi="['member:member:bankCard']"
          >银行卡
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="sendSms(scope.row)"
            v-hasPermi="['member:member:edit']"
          >发送短信
          </el-button>
          <el-button
            v-if="scope.row.isActive !== '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="updateRealName(scope.row)"
            v-hasPermi="['member:member:realName']"
          >实名
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['member:member:remove']"
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

    <!-- 添加或修改会员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="代理" prop="userId">
          <AgentSelect ref="AgentSelect1" @val="(val)=>form.userId = val" :agentId="form.userId"></AgentSelect>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input :disabled="update" v-model="form.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="提现密码" prop="withPwd">
          <el-input v-model="form.withPwd" placeholder="请输入提现密码"/>
        </el-form-item>
        <el-form-item v-if="update" label="真实姓名" prop="realName">
          <el-input disabled v-model="form.realName" placeholder="请输入真实姓名"/>
        </el-form-item>
        <el-form-item label="实盘" prop="accountType">
          <el-select v-model="form.accountType" placeholder="请选择实盘">
            <el-option
              v-for="dict in dict.type.kh_zh_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="视频认证" prop="videoActive">
          <el-select v-model="form.videoActive" placeholder="请选择视频认证">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="允许登录" prop="isLogin">
          <el-select v-model="form.isLogin" placeholder="请选择允许登录">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="股票交易" prop="isStock">
          <el-select v-model="form.isStock" placeholder="请选择股票交易">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="杠杆交易" prop="isLever">
          <el-select v-model="form.isLever" placeholder="请选择杠杆交易">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :disabled="form.isLever === '0'" label="杠杆权限" prop="code">
          <el-select
            v-model="levelItem"
            multiple
            placeholder="请选择杠杆交易">
            <el-option
              v-for="item in levelList"
              :key="item.id"
              :label="item.level"
              :value="item.level">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="贷款额度" prop="loanAmt">
          <el-input v-model="form.loanAmt" placeholder="请输入贷款额度"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="资金修改" :visible.sync="fundsOpen" width="500px" append-to-body>
      <el-form ref="fundsForm" :model="fundsForm" :rules="rules" label-width="80px">
        <el-form-item label="市场" prop="marketId">
          <MarketSelect ref="marketSelect" @input="(val)=>{fundsForm.marketId = val}"/>
        </el-form-item>
        <el-form-item label="资金" prop="amt">
          <el-input v-model="fundsForm.amt" placeholder="资金"/>
        </el-form-item>
        <el-form-item label="操作类型" prop="type">
          <el-select v-model="fundsForm.type" placeholder="操作类型">
            <el-option label="上分" value="1"></el-option>
            <el-option label="下分" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFundsForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="实名认证" :visible.sync="realNameOpen" width="800px" append-to-body>
      <el-form label-width="80px">
        <el-row>
          <el-form-item label="证件">
            <img style="width: 200px" :src="imagesUrl + form.img1Key" alt="暂无">
          </el-form-item>
          <el-form-item v-if="form.img2Key" label="证件">
            <img style="width: 200px" :src="imagesUrl + form.img2Key" alt="暂无">
          </el-form-item>
          <el-form-item v-if="form.img3Key" label="证件">
            <img style="width: 200px" :src="imagesUrl + form.img3Key" alt="暂无">
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="真实姓名">
            <el-tag>{{ form.realName }}</el-tag>
          </el-form-item>
          <el-form-item label="证件号">
            <el-tag>{{ form.idCard }}</el-tag>
          </el-form-item>
        </el-row>
      </el-form>
      <div v-if="form.isActive === '3'" slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitRealName('1')">通过</el-button>
        <el-button type="primary" @click="submitRealName('2')">拒绝</el-button>
      </div>
    </el-dialog>


    <el-dialog title="修改银行卡" :visible.sync="bankOpen" width="500px" append-to-body>
      <el-form ref="bank" :model="bank" label-width="80px">
        <el-form-item label="账户名称">
          <el-input v-model="bank.bank3" placeholder="账户名称"/>
        </el-form-item>
        <el-form-item label="银行名称">
          <el-input v-model="bank.bank1" placeholder="银行名称"/>
        </el-form-item>
        <el-form-item label="银行账号">
          <el-input v-model="bank.bank2" placeholder="银行账号"/>
        </el-form-item>
        <!--        <el-form-item label="开户人名称" prop="bank4">-->
        <!--          <el-input v-model="bank.bank4" placeholder="开户人名称"/>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitBankForm">提交</el-button>
        <el-button type="warning" @click="submitBankCardReset">重置</el-button>
      </div>
    </el-dialog>


    <el-dialog title="发送短信" :visible.sync="smsOpen" width="500px" append-to-body>
      <el-form ref="sms" :model="sms" label-width="100px">
        <el-form-item v-if="!sms.memberId" label="手机号">
          <el-select v-model="sms.smsId" placeholder="区号">
            <el-option
              v-for="item in smsList"
              :key="item.id"
              :label="item.areaCode"
              :value="item.id"
            ></el-option>
          </el-select>
          <el-input v-model="sms.phone" placeholder="手机号"/>
        </el-form-item>
        <el-form-item label="不实际发送">
          <el-select v-model="sms.imitation" placeholder="不实际发送">
            <el-option
              v-for="dict in dict.type.yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="sms.imitation === '0'" label="短信模版">
          <el-select v-model="sms.type" placeholder="短信模版">
            <el-option
              v-for="dict in dict.type.sms_temple_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitSmsForm">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addMember,
  delMember,
  getMember,
  listMember,
  sendSms,
  updateMember,
  updateMemberAmt,
  updateMemberBankCard,
  updateMemberBankCardReset,
  updateMemberRealName
} from "@/api/member/member";
import {listStocklevel} from "@/api/stocklevel/stocklevel";
import {listConfig as listSms} from "@/api/sms/config";
import MarketSelect from "@/components/marketSelect/index.vue";
import AgentSelect from "@/components/AgentSelect/index.vue";

export default {
  name: "Member",
  dicts: ['kh_zh_type', 'kh_zh_sm_status', 'yes_no', 'sms_temple_type'],
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
      // 会员表格数据
      memberList: [],
      // 弹出层标题
      title: "",
      update: false,
      // 是否显示弹出层
      open: false,
      bankOpen: false,
      realNameOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        userName: null,
        userId: null,
        phone: null,
        password: null,
        withPwd: null,
        realName: null,
        avatarImg: null,
        accountType: null,
        idCard: null,
        isActive: null,
        activeMsg: null,
        img1Key: null,
        img2Key: null,
        img3Key: null,
        isLogin: null,
        isStock: null,
        isLever: null,
        loanAmt: null,
      },
      // 资金修改
      fundsOpen: false,
      fundsForm: {},
      // 表单参数
      form: {},
      bank: {},
      // 杠杆
      levelItem: [],
      levelList: [],
      smsList: [],
      smsOpen: false,
      sms: {},
      // 表单校验
      rules: {
        userId: [
          {required: true, message: "用户ID不能为空", trigger: "blur"}
        ],
        phone: [
          {required: true, message: "手机号不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间/注册时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        marketId: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        amt: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        bank1: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        bank2: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        bank3: [
          {required: true, message: "不能为空", trigger: "blur"}
        ],
        bank4: [
          {required: true, message: "不能为空", trigger: "blur"}
        ]
      }
    };
  },
  components: {
    AgentSelect,
    MarketSelect
  },
  created() {
    this.getList();
    this.queryLevelList()
    this.getListSms()
  },
  methods: {
    getListSms() {
      listSms({}).then(res => {
        this.smsList = res.rows;
      });
    },
    queryLevelList() {
      listStocklevel({status: '1'}).then(res => {
        this.levelList = res.rows;
      });
    },
    /** 查询会员列表 */
    getList() {
      this.loading = true;
      listMember(this.queryParams).then(response => {
        this.memberList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.smsOpen = false;
      this.fundsOpen = false;
      this.bankOpen = false;
      this.realNameOpen = false;
      this.reset();
      this.resetFunds();
      this.resetBank();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        phone: null,
        password: null,
        withPwd: null,
        realName: null,
        avatarImg: null,
        accountType: null,
        idCard: null,
        isActive: null,
        videoActive: null,
        activeMsg: null,
        img1Key: null,
        img2Key: null,
        img3Key: null,
        isLogin: null,
        isStock: null,
        isLever: null,
        loanAmt: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    resetFunds() {
      this.fundsForm = {
        marketId: null,
        amt: null,
        type: null,
        memberId: null,
      };
      this.resetForm("fundsForm");
    },
    resetBank() {
      this.bank = {
        marketId: null,
        bank1: null,
        bank2: null,
        bank3: null,
        bank4: null,
        bank5: null,
        bank6: null,
        bank7: null,
        bank8: null,
      };
      this.resetForm("bank");
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
      this.update = false;
      this.title = "添加会员";
      this.$refs.AgentSelect1.reset()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMember(id).then(response => {
        this.form = response.data;
        this.levelItem = this.form.levelItem ? JSON.parse(this.form.levelItem) : [];
        this.open = true;
        this.update = true;
        this.title = "修改会员";
      });
    },
    /** 实名 */
    updateRealName(row) {
      this.reset();
      const id = row.id
      getMember(id).then(response => {
        this.form = response.data;
        this.realNameOpen = true;
        this.title = "修改会员";
      });
    },
    /** 修改资金 */
    updateAmt(row) {
      this.resetFunds();
      this.fundsOpen = true;
      this.fundsForm.memberId = row.id;
    },
    /** 修改银行卡 */
    updateBankCard(row) {
      console.log(JSON.stringify(row))
      this.resetBank();
      this.bankOpen = true;
      this.bank = row.bank ? row.bank : {};
      this.bank.memberId = row.id
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.levelItem = JSON.stringify(this.levelItem);
          if (this.form.id != null) {
            updateMember(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMember(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
          this.$refs.AgentSelect1.reset()
        }
      });
    },
    submitFundsForm() {
      this.$refs["fundsForm"].validate(valid => {
        if (valid) {
          updateMemberAmt(this.fundsForm).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.fundsOpen = false;
            this.getList();
            this.$refs.marketSelect.reset();
          });
        }
      });
    },
    sendSms(row) {

      this.sms = {
        memberId: row ? row.id : null,
        imitation: '1',
        type: '1',
        smsId: null,
        phone: null,
      }
      this.smsOpen = true;
      this.bank = row.bank ? row.bank : {};
      this.bank.memberId = row.id
    },
    submitSmsForm() {
      this.$refs["sms"].validate(valid => {
        if (valid) {
          this.sms.phone = this.sms.phone ? this.smsList.filter(s => s.id === this.sms.smsId)[0].areaCode + this.sms.phone : null;
          sendSms(this.sms).then(response => {
            this.smsOpen = false;
            this.$alert(response.data, '短信验证码', {
              confirmButtonText: '确定',
            });
          });
        }
      });
    },
    submitBankForm() {
      this.$refs["bank"].validate(valid => {
        if (valid) {
          updateMemberBankCard(this.bank).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.bankOpen = false;
            this.getList();
          });
        }
      });
    },
    submitBankCardReset() {
      if (!this.bank.id) {
        this.$message({
          type: 'warning',
          message: '用户未填写银行卡'
        });
      }
      this.$confirm('将删除用户填写的银行卡信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateMemberBankCardReset({id: this.bank.id}).then(response => {
          this.$modal.msgSuccess("重置成功");
          this.bankOpen = false;
          this.getList();
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消'
        });
      });
      this.bankOpen = false;
    },
    submitRealName(isActive) {
      if (isActive === '2') {
        this.$prompt('请输入拒绝原因', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(({value}) => {
          let opt = {
            id: this.form.id,
            isActive: '2',
            activeMsg: value,
          };
          updateMemberRealName(opt).then(response => {
            this.$modal.msgSuccess("审批成功");
            this.realNameOpen = false;
            this.getList();
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消'
          });
        });
      }
      if (isActive === '1') {
        let opt = {
          id: this.form.id,
          isActive: '1'
        };
        updateMemberRealName(opt).then(response => {
          this.$modal.msgSuccess("审批成功");
          this.realNameOpen = false;
          this.getList();
        });
      }

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除会员编号为"' + ids + '"的数据项？').then(function () {
        return delMember(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('member/member/export', {
        ...this.queryParams
      }, `member_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
