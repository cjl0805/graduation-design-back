package com.back.graduationdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HairStylistInfo {
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色（1为管理员，2为发型师，3为顾客）
     */
    private String role;

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
}
