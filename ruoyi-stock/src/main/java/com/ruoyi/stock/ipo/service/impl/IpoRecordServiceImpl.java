package com.ruoyi.stock.ipo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.common.enums.YNEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.common.utils.SessionInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.stock.ipo.domain.Ipo;
import com.ruoyi.stock.ipo.domain.IpoRecord;
import com.ruoyi.stock.ipo.domain.IpoRecordDTO;
import com.ruoyi.stock.ipo.domain.vo.IpoRecordVo;
import com.ruoyi.stock.ipo.mapper.IpoRecordMapper;
import com.ruoyi.stock.ipo.service.IIpoRecordService;
import com.ruoyi.stock.ipo.service.IIpoService;
import com.ruoyi.stock.member.domain.MemberFunds;
import com.ruoyi.stock.member.service.MemberFundsService;
import com.ruoyi.stock.member.vo.FundsOperateVo;
import com.ruoyi.stock.message.domain.MemberMessage;
import com.ruoyi.stock.message.mapper.MemberMessageMapper;
import com.ruoyi.stock.message.service.IMemberMessageService;
import com.ruoyi.stock.stock.domain.MemberPosition;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.service.IMemberPositionService;
import com.ruoyi.stock.stock.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IPO(新股)申购记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Slf4j
@Service
public class IpoRecordServiceImpl implements IIpoRecordService {
    @Autowired
    private IpoRecordMapper ipoRecordMapper;
    @Autowired
    private MemberMessageMapper memberMessageMapper;

    @Autowired
    private IIpoService iIpoService;
    @Autowired
    private IMemberMessageService iMemberMessageService;
    @Autowired
    private IMemberPositionService iMemberPositionService;
    @Autowired
    private IStockService iStockService;
    @Autowired
    private MemberFundsService memberFundsService;

    /**
     * 查询IPO(新股)申购记录
     *
     * @param id IPO(新股)申购记录主键
     * @return IPO(新股)申购记录
     */
    @Override
    public IpoRecord selectIpoRecordById(Long id) {
        return ipoRecordMapper.selectById(id);
    }

    /**
     * 查询IPO(新股)申购记录列表
     *
     * @param ipoRecord IPO(新股)申购记录
     * @return IPO(新股)申购记录
     */
    @Override
    public List<IpoRecordVo> selectIpoRecordList(IpoRecordVo ipoRecord) {
        QueryWrapper<IpoRecord> ew = new QueryWrapper<>();
        ew.eq(LogicUtils.isNotNull(ipoRecord.getMemberId()), "b.member_id", ipoRecord.getMemberId());
        ew.like(LogicUtils.isNotNull(ipoRecord.getMemberPhone()), "m.phone", ipoRecord.getMemberPhone());
        ew.eq(LogicUtils.isNotNull(ipoRecord.getUserId()), "u.user_id", ipoRecord.getUserId());
        ew.like(SessionInfo.isAgent(), "u.tree", "/" + SessionInfo.getUserId() + "/");
        ew.eq(LogicUtils.isNotNull(ipoRecord.getMarketId()), "b.market_id", ipoRecord.getMarketId());
        ew.eq(LogicUtils.isNotNull(ipoRecord.getType()), "b.type", ipoRecord.getType());
        ew.like(LogicUtils.isNotNull(ipoRecord.getName()), "b.name", ipoRecord.getName());
        ew.like(LogicUtils.isNotNull(ipoRecord.getCode()), "b.code", ipoRecord.getCode());
        ew.eq(LogicUtils.isNotNull(ipoRecord.getStatus()), "b.status", ipoRecord.getStatus());
        ew.eq(LogicUtils.isNotNull(ipoRecord.getNotifyStatus()), "b.notify_status", ipoRecord.getNotifyStatus());

        ew.orderByDesc("b.create_time");
        return ipoRecordMapper.selectIpoRecordLiat(ew);
    }

    /**
     * 新增IPO(新股)申购记录
     *
     * @param ipoRecord IPO(新股)申购记录
     * @return 结果
     */
    @Override
    public int insertIpoRecord(IpoRecord ipoRecord) {
        ipoRecord.setCreateTime(DateUtils.getNowDate());
        return ipoRecordMapper.insert(ipoRecord);
    }

    /**
     * 修改IPO(新股)申购记录
     *
     * @param ipoRecord IPO(新股)申购记录
     * @return 结果
     */
    @Override
    public int updateIpoRecord(IpoRecord ipoRecord) {
        ipoRecord.setUpdateTime(DateUtils.getNowDate());
        return ipoRecordMapper.updateById(ipoRecord);
    }

    /**
     * 批量删除IPO(新股)申购记录
     *
     * @param ids 需要删除的IPO(新股)申购记录主键
     * @return 结果
     */
    @Override
    public int deleteIpoRecordByIds(List<Long> ids) {
        QueryWrapper<IpoRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return ipoRecordMapper.delete(ew);
    }

    /**
     * 删除IPO(新股)申购记录信息
     *
     * @param id IPO(新股)申购记录主键
     * @return 结果
     */
    @Override
    public int deleteIpoRecordById(Long id) {
        return ipoRecordMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int examIpoRecord(IpoRecordDTO ipoRecord) {
        // 验证是否待审核记录是否存在
        IpoRecord ipoRecordFromSql = ipoRecordMapper.selectById(ipoRecord.getId());
        LogicUtils.assertNotNull(ipoRecordFromSql, "记录不存在");
        // 审核通过
        if (ipoRecord.getStatus().equals("2")) {
            LogicUtils.assertFalse(ipoRecord.getWinNum() <= 0, "通过数量不能小于等于0");
            LogicUtils.assertFalse(ipoRecord.getNums() < ipoRecord.getWinNum(), "通过数量不能大于申购数量");
            // 如果中签，插入中签消息
            Ipo ipo = iIpoService.selectIpoById(ipoRecordFromSql.getIpoId());

            MemberMessage memberMessage = new MemberMessage();
            memberMessage.setMemberId(ipoRecordFromSql.getMemberId());
            memberMessage.setType("1");
            memberMessage.setTitle("IPO");
            memberMessage.setSource(new BigDecimal("2"));

            Map<String, String> maps = new HashMap<>();
            // 股票名称
            maps.put("stockName", ipo.getName());
            // 股票代码
            maps.put("stockCode", ipo.getCode());
            // 中签数量
            maps.put("winnLotteryNum", ipoRecord.getWinNum().toString());
            // 中签金额
            maps.put("bzj", String.valueOf(new BigDecimal(ipoRecord.getWinNum()).multiply(ipo.getPrice())));
            memberMessage.setContent(JSONUtil.toJsonStr(maps));

            memberMessage.setReadStatus(1);
            memberMessage.setCreateTime(DateUtils.getNowDate());
            memberMessage.setKindTips(StringUtils.isNotBlank(ipoRecord.getKindTips()) ? ipoRecord.getKindTips() :
                    "Friendly reminder：make sure trading account have sufficient funds to subscribe " +
                            "the IPO allotment, otherwise it will affect your next IPO subscription " +
                            "and the allotment");
            memberMessage.setPushTime(ipoRecord.getPublishTime());
            memberMessage.setProductId(ipoRecord.getId());

            // 删除之前的通知消息，后台可能修改了中签数量
            MemberMessage memberMessageF =
                    memberMessageMapper.selectOne(new LambdaQueryWrapper<MemberMessage>()
                            .eq(MemberMessage::getMemberId, ipoRecordFromSql.getMemberId())
                            .eq(MemberMessage::getProductId, ipoRecord.getId()));
            if (null != memberMessageF) {
                iMemberMessageService.deleteMemberMessageById(memberMessage.getId());
            }
            iMemberMessageService.insertMemberMessage(memberMessage);
        }
        // 审核状态不是通过，查询是否有通知消息，有的话删除
        if (!ipoRecord.getStatus().equals("2")) {
            MemberMessage memberMessage =
                    memberMessageMapper.selectOne(new LambdaQueryWrapper<MemberMessage>()
                            .eq(MemberMessage::getMemberId, ipoRecordFromSql.getMemberId())
                            .eq(MemberMessage::getProductId, ipoRecord.getId()));
            if (null != memberMessage) {
                iMemberMessageService.deleteMemberMessageById(memberMessage.getId());
            }
        }

        Long winNum = ipoRecord.getStatus().equals("1") || ipoRecord.getStatus().equals("3") ? 0L :
                ipoRecord.getWinNum();
        ipoRecord.setWinNum(winNum);
        ipoRecord.setUpdateTime(DateUtils.getNowDate());
        IpoRecord update = new IpoRecord();
        BeanUtils.copyProperties(ipoRecord, update);
        return ipoRecordMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult transfer(Long id) {
        IpoRecord ipoRecord = ipoRecordMapper.selectById(id);
        MemberFunds memberFunds = memberFundsService.getFundsRecord(ipoRecord.getMarketId(), ipoRecord.getMemberId());
        //中签总资金
        BigDecimal totalAmt = ipoRecord.getPrice().multiply(BigDecimal.valueOf(ipoRecord.getWinNum()));
        if (totalAmt.compareTo(ipoRecord.getTransferAmt()) == 0) return AjaxResult.error("划转金额已满，可以转持仓");
        //剩余未划转资金
        BigDecimal surAmt = totalAmt.subtract(ipoRecord.getTransferAmt());
        //校验可用资金
        if (memberFunds.getEnableAmt().compareTo(surAmt) < 0) {
            surAmt = memberFunds.getEnableAmt();
        }
        LogicUtils.assertTrue(surAmt.compareTo(BigDecimal.ZERO) > 0, "可用资金不足");

        ipoRecord.setTransferAmt(ipoRecord.getTransferAmt().add(surAmt));
        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.IPO);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.FREEZE);
        fundsOperateVo.setMemberId(ipoRecord.getMemberId());
        fundsOperateVo.setMarketId(ipoRecord.getMarketId());
        fundsOperateVo.setAmt(surAmt);
        fundsOperateVo.setSourceId(ipoRecord.getId());
        fundsOperateVo.setName(ipoRecord.getName());
        fundsOperateVo.setCode(ipoRecord.getCode());

        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_NAME, ipoRecord.getName())
                .set(FundsOperateVo.FundsInfoKey.STOCK_CODE, ipoRecord.getCode())
                .set(FundsOperateVo.FundsInfoKey.SUBSCRIPTION_AMT, surAmt)
                .build();

        memberFundsService.addFreezeAmt(fundsOperateVo);
        ipoRecordMapper.updateById(ipoRecord);
        return AjaxResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult traPos(Long id) {
        IpoRecord ipoRecord = ipoRecordMapper.selectById(id);
        BigDecimal amt = ipoRecord.getPrice().multiply(BigDecimal.valueOf(ipoRecord.getWinNum()));
        if (amt.compareTo(ipoRecord.getTransferAmt()) > 0) return AjaxResult.error("划转未满，无法进行转持仓操作");

        Stock stock = iStockService.selectStock(ipoRecord.getCode());
        if (stock == null) return AjaxResult.error("该股还未上市");

        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.IPO);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.UNFREEZE);
        fundsOperateVo.setMemberId(ipoRecord.getMemberId());
        fundsOperateVo.setMarketId(ipoRecord.getMarketId());
        fundsOperateVo.setAmt(ipoRecord.getTransferAmt());
        fundsOperateVo.setSourceId(ipoRecord.getId());
        fundsOperateVo.setName(ipoRecord.getName());
        fundsOperateVo.setCode(ipoRecord.getCode());
        fundsOperateVo.setVisible(YNEnum.NO.getType());

        memberFundsService.subFreezeAmt(fundsOperateVo);

        //转持仓

        MemberPosition memberPosition = new MemberPosition();
        memberPosition.setMemberId(ipoRecord.getMemberId());
        memberPosition.setStockGid(stock.getGid());
        memberPosition.setBuyOrderTime(DateUtils.getNowDate());
        memberPosition.setLever(1L);
        memberPosition.setNum(ipoRecord.getWinNum());
        memberPosition.setDirection("1");
        memberPosition.setBuyOrderPrice(ipoRecord.getPrice());
        memberPosition.setSource(FundsSourceEnum.IPO.getType());
        int i = iMemberPositionService.insertMemberPosition(memberPosition);
        if (i == 1) {
            ipoRecordMapper.deleteById(id);
        }
        return AjaxResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult refund(Long id) {
        IpoRecord ipoRecord = ipoRecordMapper.selectById(id);
        BigDecimal amt = ipoRecord.getTransferAmt();
        if (amt.compareTo(BigDecimal.ZERO) <= 0) return AjaxResult.error("还未划转无法进行退款");

        FundsOperateVo fundsOperateVo = new FundsOperateVo();
        fundsOperateVo.setSource(FundsSourceEnum.IPO);
        fundsOperateVo.setOperateType(FundsOperateTypeEnum.ROLLBACK);
        fundsOperateVo.setMemberId(ipoRecord.getMemberId());
        fundsOperateVo.setMarketId(ipoRecord.getMarketId());
        fundsOperateVo.setAmt(amt);
        fundsOperateVo.setSourceId(ipoRecord.getId());
        fundsOperateVo.setName(ipoRecord.getName());
        fundsOperateVo.setCode(ipoRecord.getCode());
        fundsOperateVo.set(FundsOperateVo.FundsInfoKey.STOCK_NAME, ipoRecord.getName())
                .set(FundsOperateVo.FundsInfoKey.STOCK_CODE, ipoRecord.getCode())
                .set(FundsOperateVo.FundsInfoKey.ROLLBACK_AMT, amt)
                .build();
        memberFundsService.subFreezeAmt(fundsOperateVo);

        ipoRecord.setTransferAmt(BigDecimal.ZERO);
        ipoRecordMapper.updateById(ipoRecord);
        return AjaxResult.success();
    }
}
