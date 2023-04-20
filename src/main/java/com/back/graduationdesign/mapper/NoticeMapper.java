package com.back.graduationdesign.mapper;

import com.back.graduationdesign.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2023-04-20
 */
@Mapper
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {

}
