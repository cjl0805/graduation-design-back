package com.back.graduationdesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjl
 * @since 2023-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppointmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预约号
     */
    private String appointmentId;

    /**
     * 顾客用户名
     */
    private String username;

    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateBegin;

    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateEnd;

    /**
     * 预约发型
     */
    private String hairstyle;

    /**
     * 预约发型师
     */
    private String hairstylist;

    /**
     * 预约状态
     */
    private String status;


}
