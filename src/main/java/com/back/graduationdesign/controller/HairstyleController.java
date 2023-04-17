package com.back.graduationdesign.controller;


import com.back.graduationdesign.dto.HairStyleChecked;
import com.back.graduationdesign.entity.Hairstyle;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.service.HairstyleService;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.LambdaConversionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("list/hairstyle")
    public R getHairstyle(){
        List<Hairstyle> list = hairstyleService.list();
        List<String> stringList = new ArrayList<>();
        for (Hairstyle hairstyle : list) {
            stringList.add(hairstyle.getHairstyle());
        }
        return R.success(stringList);
    }

    /**
     * 查询带check状态的发型列表
     * @param username
     * @return
     */
    @GetMapping("/list/checked")
    public R listChecked(String username){
        LambdaQueryWrapper<Hairstylist> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Hairstylist::getUsername,username);
        Hairstylist one = hairstylistService.getOne(queryWrapper);
        String[] split = one.getSkill().split(",");
        List<Hairstyle> hairstyleList = hairstyleService.list();
        List<HairStyleChecked> list = hairstyleList.stream().map(item->{
            HairStyleChecked hairStyleChecked = new HairStyleChecked();
            BeanUtils.copyProperties(item,hairStyleChecked);
            for (String s : split) {
                if (s.equals(item.getHairstyle())){
                    hairStyleChecked.setChecked(true);
                    break;
                }else {
                    hairStyleChecked.setChecked(false);
                }
            }
            return hairStyleChecked;
        }).collect(Collectors.toList());
        return R.success(list);
    }
}

