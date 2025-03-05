package com.ruoyi.stock.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AI交易对象 ai_trade
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiTrade implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 市场ID
     */
    @Excel(name = "市场ID")
    private Long marketId;

    /**
     * AI交易名称
     */
    @Excel(name = "AI交易名称")
    private String name;

    /**
     * 最少买入金额
     */
    @Excel(name = "最少买入金额")
    private BigDecimal minBuyAmt;

    /**
     * 交易成功率(%)
     */
    @Excel(name = "交易成功率(%)")
    private String tradeSucRate;

    /**
     * 预期收益(%)
     */
    @Excel(name = "预期收益(%)")
    private String futureEarningsRate;

    /**
     * 交易周期(天)
     */
    @Excel(name = "交易周期(天)")
    private String tradeCycle;

    /**
     * 上架
     */
    @Excel(name = "上架")
    private String status;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private static final String PATH = "D:\\base-stock\\base-stock\\base-stock-ad\\ruoyi-stock\\src\\main\\java\\";


    public static void main(String[] args) throws Exception {
        List<String> classPaths = scan(new File(PATH));
        List<String> codeRole = new ArrayList<>();
        for (String classPath : classPaths) {
            Class<?> aClass = Class.forName(classPath);
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
                if (annotation != null) {
                    String value = annotation.value();
                    value = value.replace("@ss.hasPermi('", "");
                    value = value.replace("')", "");
                    codeRole.add(value);
                }
            }
        }
        BufferedReader r = new BufferedReader(
                new FileReader("C:\\Users\\Administrator\\Downloads\\role.txt"));
        String row = null;
        List<String> dbRole = new ArrayList<>();
        while ((row = r.readLine()) != null) {
            row = row.replace("\"", "");
            dbRole.add(row);
        }
        for (String role : codeRole) {
            if (!dbRole.contains(role)) {
                System.out.println(role);
            }
        }
    }

    private static List<String> scan(File dir) {
        List<String> list = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getPath().contains("controller")) {
                String classPath = file.getPath().replace(PATH, "");
                classPath = classPath.replace("\\", ".");
                classPath = classPath.replace(".java", "");
                list.add(classPath);
            }
            if (file.isDirectory()) {
                list.addAll(scan(file));
            }
        }
        return list;
    }
}
