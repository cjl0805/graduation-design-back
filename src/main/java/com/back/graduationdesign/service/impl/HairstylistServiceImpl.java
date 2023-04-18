package com.back.graduationdesign.service.impl;

import com.back.graduationdesign.dto.HairStylistInfo;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.mapper.HairstylistMapper;
import com.back.graduationdesign.service.HairstylistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 发型师 服务实现类
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Service
public class HairstylistServiceImpl extends ServiceImpl<HairstylistMapper, Hairstylist> implements HairstylistService {

    @Autowired
    private HairstylistMapper hairstylistMapper;

    @Override
    public HairStylistInfo selectHairStylistInfo(String username) {
        return hairstylistMapper.selectHairStylistInfo(username);
    }

    @Override
    public List<HairStylistInfo> selectHairStylistInfoByPage(int page, int size, String query) {
        return hairstylistMapper.selectHairStylistInfoByPage(page, size, query);
    }


}
