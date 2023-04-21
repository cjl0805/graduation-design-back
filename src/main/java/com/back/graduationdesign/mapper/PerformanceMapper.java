package com.back.graduationdesign.mapper;

import com.back.graduationdesign.entity.Performance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2023-04-21
 */
@Mapper
@Repository
public interface PerformanceMapper extends BaseMapper<Performance> {

}
