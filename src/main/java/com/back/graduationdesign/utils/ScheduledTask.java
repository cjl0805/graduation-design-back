package com.back.graduationdesign.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.entity.Notice;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.back.graduationdesign.service.NoticeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private AppointmentInfoService appointmentInfoService;
    @Autowired
    private NoticeService noticeService;

    /**
     * 定时任务，每天8点到22点 判断当前订单在当前整点时是否已被接受，若没有接受则自动拒绝
     */
    @Scheduled(cron = "0 0 8-22 * * ?")
    public void updateAppointmentStatus(){
        DateTime date = DateUtil.date();
        String substring1 = date.toString().substring(0, 10);
        String substring2 = date.toString().substring(11, 19);
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentInfo::getDate,substring1).eq(AppointmentInfo::getTime,substring2);
        List<AppointmentInfo> list = appointmentInfoService.list(queryWrapper);
        if (list!=null){
            for (AppointmentInfo info : list) {
                if (!info.getStatus().equals("已接受")){
                    info.setStatus("已拒绝");
                    appointmentInfoService.updateById(info);
                    Notice notice = new Notice();
                    notice.setUser(info.getUsername());
                    notice.setDatetime(DateUtil.date());
                    notice.setContent("预约单号为 "+info.getAppointmentId()+" 的订单状态修改为已拒绝");
                    noticeService.save(notice);
                }
            }
        }

    }
}
