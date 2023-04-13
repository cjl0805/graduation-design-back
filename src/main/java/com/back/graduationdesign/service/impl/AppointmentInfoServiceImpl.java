package com.back.graduationdesign.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.mapper.AppointmentInfoMapper;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjl
 * @since 2023-03-30
 */
@Service
public class AppointmentInfoServiceImpl extends ServiceImpl<AppointmentInfoMapper, AppointmentInfo> implements AppointmentInfoService {

    @Autowired
    private AppointmentInfoMapper appointmentInfoMapper;

    /**
     * 查看预约情况
     * @param map
     * @return
     * @throws ParseException
     */
    @Override
    public List<AppointmentInfo> makeAnAppointment(Map<String, Object> map) throws ParseException {
        String stylist = map.get("stylist").toString();
        String date = dateHandler(map.get("date").toString());
        System.out.println("date = " + date);
        String time = map.get("time").toString();
        LambdaQueryWrapper<AppointmentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppointmentInfo::getHairstylist,stylist)
                .eq(AppointmentInfo::getDate,date)
                .eq(AppointmentInfo::getTime,time);
        List<AppointmentInfo> list = appointmentInfoMapper.selectList(wrapper);
        return list;
    }


    /**
     * 处理日期
     * @param date
     * @return
     */
    @Override
    public String dateHandler(String date) throws ParseException {
        date = date.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = format.parse(date);
        System.out.println(d);
        String sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        System.out.println(sdf);
        return sdf;
    }

    /**
     * 比较两个字符串类型的时间
     * @param map
     * @return
     * @throws ParseException
     */
    @Override
    public int dateCompare(Map<String, Object> map) throws ParseException {
        String now = DateUtil.now();
        System.out.println("now = " + now);
        String date = dateHandler(map.get("date").toString()).substring(0,10);
        String time = map.get("time").toString();
        String dateTime = date+" "+time+":00";
        System.out.println("dateTime = " + dateTime);
        return dateTime.compareTo(now);
    }

    /**
     * 生成预约单号
     * @param map
     * @return
     */
    @Override
    public String generateAppointmentId(Map<String, Object> map) throws ParseException {
        String appointmentId ="OHAS-"+map.get("username")+"-";
        String now = DateUtil.now();
        String replace = now.replace("-", "").replace(" ", "").replace(":", "");
        appointmentId+=replace;
        return appointmentId;
    }

    /**
     * 获取某个时间前后几分钟的时间
     * @param time 某个时间
     * @param offset 偏差几分钟（负值代表前，正值代表后）
     * @return
     */
    public String getDateOffset(String time,int offset){
        Date date = DateUtil.parse(time);
        Date newDate = DateUtil.offset(date, DateField.MINUTE, offset);
        String startTime = DateUtil.format(newDate, "yyyy-MM-dd HH:mm:ss");
        return startTime;
    }


}
