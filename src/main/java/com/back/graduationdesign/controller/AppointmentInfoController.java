package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.mapper.AppointmentInfoMapper;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

