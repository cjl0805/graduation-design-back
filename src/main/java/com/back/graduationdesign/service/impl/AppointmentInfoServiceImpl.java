package com.back.graduationdesign.service.impl;

import com.back.graduationdesign.entity.AppointmentInfo;
import com.back.graduationdesign.mapper.AppointmentInfoMapper;
import com.back.graduationdesign.service.AppointmentInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
