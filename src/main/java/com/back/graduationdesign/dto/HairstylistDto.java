package com.back.graduationdesign.dto;

import lombok.Data;

import java.util.List;

@Data
public class HairstylistDto {
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
    private List<String> skill;

    /**
     * 简介
     */
    private String description;

}
