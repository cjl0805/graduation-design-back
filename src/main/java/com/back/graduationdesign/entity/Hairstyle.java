package com.back.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjl
 * @since 2023-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Hairstyle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    /**
     * 发型名称
     */
    private String hairstyle;

    /**
     * 发型图片
     */
    private String image;


}
