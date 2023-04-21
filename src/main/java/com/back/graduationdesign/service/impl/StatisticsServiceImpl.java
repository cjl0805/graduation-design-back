package com.back.graduationdesign.service.impl;

import cn.hutool.core.date.DateUtil;
import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.entity.Performance;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.service.PerformanceService;
import com.back.graduationdesign.service.StatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private HairstylistService hairstylistService;
    @Autowired
    private AppointmentInfoService appointmentInfoService;
    @Autowired
    private PerformanceService performanceService;

    private final static String successStatus="已完成";

    private final static String acceptStatus="已接受";

    @Override
    public HashMap<String, Object> getPerformance(String username) {
        LambdaQueryWrapper<Hairstylist> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Hairstylist::getUsername,username);
        String name = hairstylistService.getOne(queryWrapper).getName();

        HashMap<String, Object> map = new HashMap<>();
        //今日业绩
        String now = DateUtil.now();
        String substring1 = now.substring(0, 10);
        LambdaQueryWrapper<AppointmentInfo> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(AppointmentInfo::getHairstylist,name)
                .eq(AppointmentInfo::getDate,substring1)
                .eq(AppointmentInfo::getStatus,successStatus);
        int nowCount = appointmentInfoService.count(wrapper1);
        map.put("nowCount",nowCount);
        //本月业绩
        String substring2 = now.substring(5, 6);
        LambdaQueryWrapper<AppointmentInfo> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(AppointmentInfo::getHairstylist,name)
                .eq(AppointmentInfo::getStatus,successStatus);

        List<AppointmentInfo> list = appointmentInfoService.list(wrapper2);
        int monthCount = 0;
        for (AppointmentInfo appointmentInfo : list) {
            String month = appointmentInfo.getDate().toString().substring(5, 6);
            if (month.equals(substring2)) {
                monthCount++;
            }
        }
        map.put("monthCount",monthCount);

        //总共业绩
        LambdaQueryWrapper<AppointmentInfo> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(AppointmentInfo::getHairstylist,name)
                .eq(AppointmentInfo::getStatus,successStatus);
        int totalCount = appointmentInfoService.count(wrapper3);
        map.put("totalCount",totalCount);
        insertPerformance(nowCount,monthCount,totalCount,name);
        return map;
    }

    @Override
    public HashMap<String, Object> getOrdersCount(String username) {

        HashMap<String, Object> performance = getPerformance(username);
        int nowCount = Integer.parseInt(performance.get("nowCount").toString());
        int monthCount = Integer.parseInt(performance.get("monthCount").toString());
        int totalCount = Integer.parseInt(performance.get("totalCount").toString());

        LambdaQueryWrapper<Hairstylist> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Hairstylist::getUsername,username);
        String name = hairstylistService.getOne(queryWrapper).getName();

        HashMap<String, Object> map = new HashMap<>();
        //今日业绩
        String now = DateUtil.now();
        String substring1 = now.substring(0, 10);
        LambdaQueryWrapper<AppointmentInfo> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(AppointmentInfo::getHairstylist,name)
                .eq(AppointmentInfo::getDate,substring1)
                .eq(AppointmentInfo::getStatus,acceptStatus);
        nowCount += appointmentInfoService.count(wrapper1);

        map.put("nowCount",nowCount);
        //本月业绩
        String substring2 = now.substring(5, 6);
        LambdaQueryWrapper<AppointmentInfo> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(AppointmentInfo::getHairstylist,name)
                .eq(AppointmentInfo::getStatus,acceptStatus);

        List<AppointmentInfo> list = appointmentInfoService.list(wrapper2);

        for (AppointmentInfo appointmentInfo : list) {
            String month = appointmentInfo.getDate().toString().substring(5, 6);
            if (month.equals(substring2)) {
                monthCount++;
            }
        }
        map.put("monthCount",monthCount);

        //总共业绩
        LambdaQueryWrapper<AppointmentInfo> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(AppointmentInfo::getHairstylist,name)
                .eq(AppointmentInfo::getStatus,acceptStatus);
        totalCount += appointmentInfoService.count(wrapper3);
        map.put("totalCount",totalCount);
        return map;
    }

    /**
     * 插入或更新业绩表
     * @param nowCount
     * @param monthCount
     * @param totalCount
     * @param name
     */
    @Override
    public void insertPerformance(int nowCount,int monthCount,int totalCount,String name){
        LambdaQueryWrapper<Performance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Performance::getName,name);
        Performance one = performanceService.getOne(queryWrapper);
        Performance performance = new Performance();
        performance.setNowCount(nowCount);
        performance.setMonthCount(monthCount);
        performance.setTotalCount(totalCount);
        performance.setName(name);
        if (one==null){
            performanceService.save(performance);
        }else {
            performance.setOtherCount(one.getOtherCount());
            performanceService.update(performance,queryWrapper);
        }

    }

}
