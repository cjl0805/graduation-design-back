package com.back.graduationdesign.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.entity.MessageInfo;
import com.back.graduationdesign.service.MessageInfoService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        wrapper.eq(MessageInfo::getUsername,username).orderByDesc(MessageInfo::getDate);
        Page<MessageInfo> infoPage = messageInfoService.page(new Page<MessageInfo>(page, size), wrapper);
        return R.success(infoPage);
    }

    /**
     * 分页显示
     * @param query
     * @param page
     * @param size
     * @return
     */
    @GetMapping("get/page/{page}/{size}")
    public R getPageList(String query, @PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<MessageInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.like(MessageInfo::getId,query).or().like(MessageInfo::getContent,query).orderByDesc(MessageInfo::getDate);
        Page<MessageInfo> infoPage = messageInfoService.page(new Page<>(page, size), wrapper);
        return R.success(infoPage);
    }

    /**
     * 删除
     * @param messageInfo
     * @return
     */
    @DeleteMapping("delete")
    public R delete(@RequestBody MessageInfo messageInfo){
        Date date = messageInfo.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataString = dateFormat.format(date);
        DateTime date1 = DateUtil.parse(dataString);
        String now = DateUtil.now();
        DateTime date2 = DateUtil.parse(now);
        long between = DateUtil.between(date1, date2, DateUnit.DAY);
        System.out.println("between = " + between);
        if (between>30){
            boolean b = messageInfoService.removeById(messageInfo.getId());
            if (b) return R.success(true);
            else return R.error("删除失败！");
        }else {
            return R.error("你只能删除超过一个月的留言！");
        }
    }

    /**
     * 增加
     * @param messageInfo
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody MessageInfo messageInfo){
        messageInfo.setDate(DateUtil.date());
        boolean save = messageInfoService.save(messageInfo);
        if (save) return R.success(true);
        else return R.error("添加失败！");
    }

}

