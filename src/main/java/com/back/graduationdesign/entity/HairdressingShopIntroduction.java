package com.back.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 美发店简介
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HairdressingShopIntroduction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 美发店名称
     */
    private String name;

    /**
     * 营业时间
     */
    private String businessHours;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 简介
     */
    private String description;


}
