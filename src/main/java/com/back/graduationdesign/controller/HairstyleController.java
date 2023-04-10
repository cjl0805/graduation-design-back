package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.Hairstyle;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.service.HairstyleService;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.LambdaConversionException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-01
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/hairstyle")
public class HairstyleController {

    @Autowired
    private HairstyleService hairstyleService;

    @Autowired
    private HairstylistService hairstylistService;

    @GetMapping("/list")
    public R getAll(){
        List<Hairstyle> list = hairstyleService.list();
        if (list == null) {
            return null;
        }
        return R.success(list);
    }

    /**
     * 根据发型师推荐发型
     * @param hairstylist
     * @return
     */
    @GetMapping("/recommend")
    public R getRecommend(String hairstylist){
        LambdaQueryWrapper<Hairstylist> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Hairstylist::getName,hairstylist);
        Hairstylist one = hairstylistService.getOne(queryWrapper);
        String[] split = one.getSkill().split(",");
        List<Hairstyle> hairstyles = new ArrayList<>();
        for (String s : split) {
            LambdaQueryWrapper<Hairstyle> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Hairstyle::getHairstyle,s);
            Hairstyle hairstyle = hairstyleService.getOne(wrapper);
            hairstyles.add(hairstyle);
        }
        return R.success(hairstyles);
    }
}

