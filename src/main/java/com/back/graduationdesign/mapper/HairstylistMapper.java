package com.back.graduationdesign.mapper;

import com.back.graduationdesign.dto.HairStylistInfo;
import com.back.graduationdesign.entity.Hairstylist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 发型师 Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Mapper
@Repository
public interface HairstylistMapper extends BaseMapper<Hairstylist> {

    @Select("SELECT hairstylist.*,user.password,user.role FROM hairstylist,user WHERE hairstylist.username=user.username AND hairstylist.username=#{username}")
    HairStylistInfo selectHairStylistInfo(String username);

    @Select("SELECT hairstylist.*,user.password,user.role FROM hairstylist,user WHERE hairstylist.username=user.username and (hairstylist.id like '%${query}%' or name like '%${query}%') limit #{page},#{size}")
    List<HairStylistInfo> selectHairStylistInfoByPage(int page, int size, String query);

}
