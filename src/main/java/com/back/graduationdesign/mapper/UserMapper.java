package com.back.graduationdesign.mapper;

import com.back.graduationdesign.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
