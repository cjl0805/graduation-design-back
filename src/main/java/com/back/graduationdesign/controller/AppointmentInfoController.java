package com.back.graduationdesign.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.back.graduationdesign.dto.Result;
import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.entity.MessageInfo;
import com.back.graduationdesign.entity.Notice;
import com.back.graduationdesign.mapper.AppointmentInfoMapper;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.back.graduationdesign.service.NoticeService;
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

    @Autowired
    private NoticeService noticeService;


    @GetMapping("/get/{page}/{size}")
    public R getAppointmentInfo(String username, @PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<AppointmentInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(AppointmentInfo::getUsername,username).orderByDesc(AppointmentInfo::getDate).orderByDesc(AppointmentInfo::getTime);
        Page<AppointmentInfo> infoPage = appointmentInfoService.page(new Page<AppointmentInfo>(page, size), wrapper);
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
    public R getPage(String query, @PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<AppointmentInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(AppointmentInfo::getUsername,query)
                .or().like(AppointmentInfo::getId,query)
                .or().like(AppointmentInfo::getHairstylist,query)
                .or().like(AppointmentInfo::getHairstyle,query)
                .orderByDesc(AppointmentInfo::getDate)
                .orderByDesc(AppointmentInfo::getTime);
        Page<AppointmentInfo> infoPage = appointmentInfoService.page(new Page<AppointmentInfo>(page, size), wrapper);
        return R.success(infoPage);
    }


    /**
     * 查询该用户的预约信息
     * @param username
     * @return
     */
    @GetMapping("get/byUsername")
    public R getByUsername(String username){
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentInfo::getUsername,username);
        List<AppointmentInfo> list = appointmentInfoService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 查询发型师的预约信息
     * @param hairstylist
     * @return
     */
    @GetMapping("get/byHairstylist/{page}/{size}")
    public R getByHairstylist(String hairstylist,@PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentInfo::getHairstylist,hairstylist)
                .orderByDesc(AppointmentInfo::getDate)
                .orderByDesc(AppointmentInfo::getTime);
        Page<AppointmentInfo> infoPage = appointmentInfoService.page(new Page<AppointmentInfo>(page, size), queryWrapper);
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

        String time = map.get("time").toString();
        String username = map.get("username").toString();
        String hairstyle = map.get("hairstyle").toString();
        String stylist = map.get("stylist").toString();

        AppointmentInfo appointmentInfo = new AppointmentInfo();
        String appointmentId = appointmentInfoService.generateAppointmentId(map);
        String date = appointmentInfoService.dateHandler(map.get("date").toString()).substring(0, 10);

        //首先判断用户是否已经预约该时间段
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentInfo::getUsername,username)
                .eq(AppointmentInfo::getDate,date)
                .eq(AppointmentInfo::getTime,time);
        AppointmentInfo one = appointmentInfoService.getOne(queryWrapper);
        if (one!=null){
            return R.error("您已预约过该时段，不可重复预约！");
        }

        //保存预约信息到数据库
        appointmentInfo.setAppointmentId(appointmentId);
        appointmentInfo.setDate(DateUtil.parse(date));
        appointmentInfo.setDateNow(DateUtil.date());
        appointmentInfo.setHairstyle(hairstyle);
        appointmentInfo.setHairstylist(stylist);
        appointmentInfo.setUsername(username);
        appointmentInfo.setTime(DateUtil.parse(time));
        appointmentInfo.setStatus("未支付");

        //保存通知信息到数据库
        Notice notice = new Notice();
        notice.setAuthor("在线美发预约系统");
        notice.setUser(username);
        notice.setDatetime(DateUtil.date());
        notice.setContent("您有一笔未支付的预约订单，预约单号为 "+appointmentId+" ，可前往 个人中心-我的预约 中去支付");
        noticeService.save(notice);

        boolean save = appointmentInfoService.save(appointmentInfo);
        if (save) return R.success(appointmentId);
        return R.error("预约失败！");
    }

    /**
     * 修改预约状态
     * @param appointmentId
     * @return
     */
    @PutMapping("/update/status")
    public R updateStatus(String appointmentId,String status){
        AppointmentInfo appointmentInfo = new AppointmentInfo();
        appointmentInfo.setStatus(status);
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentInfo::getAppointmentId,appointmentId);
        boolean update = appointmentInfoService.update(appointmentInfo, queryWrapper);
        if (update) {
            Notice notice = new Notice();
            notice.setDatetime(DateUtil.date());
            notice.setContent("预约单号为 "+appointmentId+" 的订单状态修改为"+status);
            notice.setUser(appointmentId.substring(5,appointmentId.lastIndexOf("-")));
            noticeService.save(notice);
            return R.success(true);
        }
        else return R.error("状态更改失败");
    }

    /**
     * 取消预约
     * @param appointmentInfo
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody AppointmentInfo appointmentInfo){
        //判断预约时间是否小于当前时间，若小于则不可取消
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
        String datetime = format1.format(appointmentInfo.getDate()) + " " + format2.format(appointmentInfo.getTime());
        System.out.println("datetime = " + datetime);
        String now = DateUtil.now();
        if(datetime.compareTo(now)<0){
            return R.error("改预约已过取消时间，不可取消！");
        }
        boolean remove = appointmentInfoService.removeById(appointmentInfo.getId());
        if (remove) {
            Notice notice = new Notice();
            notice.setDatetime(DateUtil.date());
            notice.setContent("预约单号为 "+appointmentInfo.getAppointmentId()+" 的订单被取消");
            notice.setUser(appointmentInfo.getUsername());
            noticeService.save(notice);
            return R.success(true);
        }
        else return R.error("取消失败！");
    }

}

