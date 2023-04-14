package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.Discount;
import com.back.graduationdesign.service.DiscountService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 折扣 前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-04-13
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    /**
     * 查询所有折扣信息
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List<Discount> list = discountService.list();
        return R.success(list);
    }

    /**
     * 根据发型查询折扣
     * @param hairstyle
     * @return
     */
    @GetMapping("/get/by/hairstyle")
    public R getByHairstyle(String hairstyle){
        LambdaQueryWrapper<Discount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Discount::getHairstyle,hairstyle);
        Discount one = discountService.getOne(queryWrapper);
        if (one==null){
            return R.success(10);
        }else {
            Integer discount = one.getDiscount();
            return R.success(discount);
        }
    }

    /**
     * 折扣分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/get/{page}/{size}")
    public R getPage(@PathVariable int page, @PathVariable int size){
        Page<Discount> discountPage = discountService.page(new Page<>(page, size));
        return R.success(discountPage);
    }

}

