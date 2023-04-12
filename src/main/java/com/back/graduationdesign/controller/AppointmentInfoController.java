package com.back.graduationdesign.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.back.graduationdesign.dto.Result;
import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.mapper.AppointmentInfoMapper;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-30
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/appointmentInfo")
public class AppointmentInfoController {

    @Autowired
    private AppointmentInfoService appointmentInfoService;

    @GetMapping("/get/{page}/{size}")
    public R getAppointmentInfo(String username, @PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<AppointmentInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(AppointmentInfo::getUsername,username);
        Page<AppointmentInfo> infoPage = appointmentInfoService.page(new Page<AppointmentInfo>(page, size), wrapper);
        return R.success(infoPage);
    }

    /**
     * 查看预约信息
     * @param map
     * @return
     * @throws ParseException
     */
    @PostMapping("/list")
    public R getAll(@RequestBody Map<String,Object> map) throws ParseException {
        int compare = appointmentInfoService.dateCompare(map);
        if (compare<0){
            return R.error("选择时间不能小于当前时间，请重新选择");
        }
        List<AppointmentInfo> list = appointmentInfoService.makeAnAppointment(map);
        return R.success(new Result(list.size(),list));
    }

    /**
     * 保存预约信息
     * @param map
     * @return
     * @throws ParseException
     */
    @PostMapping("/save")
    public R save(@RequestBody Map<String,Object> map) throws ParseException {
        AppointmentInfo appointmentInfo = new AppointmentInfo();
        String appointmentId = appointmentInfoService.generateAppointmentId(map);
        String date = appointmentInfoService.dateHandler(map.get("date").toString()).substring(0, 10);

        //首先判断用户是否已经预约该时间段

        //保存预约信息到数据库
        appointmentInfo.setAppointmentId(appointmentId);
        appointmentInfo.setDate(DateUtil.parse(date));
        appointmentInfo.setDateNow(DateUtil.date());
        appointmentInfo.setHairstyle(map.get("hairstyle").toString());
        appointmentInfo.setHairstylist(map.get("stylist").toString());
        appointmentInfo.setUsername(map.get("username").toString());
        appointmentInfo.setTime(DateUtil.parse(map.get("time").toString()));
        appointmentInfo.setStatus("未支付");

        boolean save = appointmentInfoService.save(appointmentInfo);
        if (save) return R.success(true);
        return R.error("预约失败！");
    }

}

