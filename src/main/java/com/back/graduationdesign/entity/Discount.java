package com.back.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 折扣
 * </p>
 *
 * @author cjl
 * @since 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 折扣
     */
    private Integer discount;

    /**
     * 发型
     */
    private String hairstyle;


}
