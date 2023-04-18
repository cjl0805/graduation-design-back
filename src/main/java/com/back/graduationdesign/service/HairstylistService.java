package com.back.graduationdesign.service;

import com.back.graduationdesign.dto.HairStylistInfo;
import com.back.graduationdesign.entity.Hairstylist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 发型师 服务类
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
public interface HairstylistService extends IService<Hairstylist> {
    HairStylistInfo selectHairStylistInfo(String username);
    List<HairStylistInfo> selectHairStylistInfoByPage(int page, int size, String query);
}
