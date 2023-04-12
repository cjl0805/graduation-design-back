package com.back.graduationdesign.service;

import com.back.graduationdesign.entity.AppointmentInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjl
 * @since 2023-03-30
 */
public interface AppointmentInfoService extends IService<AppointmentInfo> {
    List<AppointmentInfo> makeAnAppointment(Map<String,Object> map) throws ParseException;

    String dateHandler(String date) throws ParseException;

    int dateCompare(Map<String,Object> map) throws ParseException;

    String generateAppointmentId(Map<String,Object> map) throws ParseException;
}
