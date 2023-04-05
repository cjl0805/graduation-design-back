package com.back.graduationdesign.mapper;

import com.back.graduationdesign.entity.AppointmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2023-03-30
 */
@Mapper
@Repository
public interface AppointmentInfoMapper extends BaseMapper<AppointmentInfo> {

}
