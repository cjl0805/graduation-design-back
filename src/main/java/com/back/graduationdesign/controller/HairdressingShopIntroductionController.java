package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.HairdressingShopIntroduction;
import com.back.graduationdesign.service.HairdressingShopIntroductionService;
import com.back.graduationdesign.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public R update(@RequestBody HairdressingShopIntroduction shopIntroduction){
        boolean b = shopIntroductionService.updateById(shopIntroduction);
        if (b) return R.success(true);
        else return R.error("修改失败！");
    }
}

