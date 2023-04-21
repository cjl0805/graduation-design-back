package com.back.graduationdesign.controller;

import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.entity.Performance;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.service.PerformanceService;
import com.back.graduationdesign.service.StatisticsService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/graduation/design/statistics")
public class PerformanceController {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private HairstylistService hairstylistService;
    @Autowired
    private PerformanceService performanceService;


    /**
     * 业绩查询
     * @param username
     * @return
     */
    @GetMapping("/get/performance")
    public R getPerformance(String username){
        HashMap<String, Object> map = statisticsService.getPerformance(username);

        return R.success(map);
    }

    /**
     * 接单次数查询
     * @param username
     * @return
     */
    @GetMapping("/get/ordersCount")
    public R getOrdersCount(String username){
        HashMap<String, Object> map = statisticsService.getOrdersCount(username);
        return R.success(map);
    }

    /**
     * 查询所有发型师的业绩
     * @param page
     * @param size
     * @param query
     * @return
     */
    @GetMapping("list/{page}/{size}")
    public R getPage(@PathVariable int page,@PathVariable int size,String query){
        List<Hairstylist> list = hairstylistService.list();
        for (Hairstylist hairstylist : list) {
            String username = hairstylist.getUsername();
            HashMap<String, Object> hashMap = statisticsService.getPerformance(username);
            int nowCount = Integer.parseInt(hashMap.get("nowCount").toString());
            int monthCount = Integer.parseInt(hashMap.get("monthCount").toString());
            int totalCount = Integer.parseInt(hashMap.get("totalCount").toString());
            statisticsService.insertPerformance(nowCount,monthCount,totalCount,hairstylist.getName());
        }
        LambdaQueryWrapper<Performance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Performance::getName,query);
        Page<Performance> performancePage = performanceService.page(new Page<>(page, size),queryWrapper);
        return R.success(performancePage);
    }

    /**
     * 手动增加业绩
     * @param performance
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody Performance performance){
        performance.setOtherCount(performance.getOtherCount()+1);
        boolean b = performanceService.updateById(performance);
        if (b) return R.success(true);
        else return R.error("增加失败!");
    }

}
