package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.HairdressingShopIntroduction;
import com.back.graduationdesign.service.HairdressingShopIntroductionService;
import com.back.graduationdesign.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 美发店简介 前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/hairdressing-shop-introduction")
public class HairdressingShopIntroductionController {

    @Autowired
    private HairdressingShopIntroductionService shopIntroductionService;

    @GetMapping("/get")
    public R get(){
        return R.success(shopIntroductionService.list());
    }
}

