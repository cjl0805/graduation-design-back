package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.entity.MessageInfo;
import com.back.graduationdesign.service.MessageInfoService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-31
 */
@RestController
@RequestMapping("/graduation/design/messageInfo")
public class MessageInfoController {

    @Autowired
    private MessageInfoService messageInfoService;

    @GetMapping("/get/{page}/{size}")
    public R getPage(String username, @PathVariable int page, @PathVariable int size){
        LambdaQueryWrapper<MessageInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(MessageInfo::getUsername,username);
        Page<MessageInfo> infoPage = messageInfoService.page(new Page<MessageInfo>(page, size), wrapper);
        return R.success(infoPage);
    }


}

