package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.Discount;
import com.back.graduationdesign.service.DiscountService;
import com.back.graduationdesign.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 折扣 前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-04-13
 */
@RestController
@RequestMapping("/graduation/design/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    /**
     * 查询所有折扣信息
     * @return
     */
    public R list(){
        List<Discount> list = discountService.list();
        return R.success(list);
    }

}

