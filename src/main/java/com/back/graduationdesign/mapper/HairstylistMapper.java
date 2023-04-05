package com.back.graduationdesign.mapper;

import com.back.graduationdesign.entity.Hairstylist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 发型师 Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Mapper
public interface HairstylistMapper extends BaseMapper<Hairstylist> {

}
