package com.back.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 发型师
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Hairstylist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 头像
     */
    private String img;

    /**
     * 擅长技术
     */
    private String skill;

    /**
     * 简介
     */
    private String description;

    /**
     * 用户名
     */
    private String username;

}
