package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.Hairstyle;
import com.back.graduationdesign.service.HairstyleService;
import com.back.graduationdesign.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public R getAll(){
        List<Hairstyle> list = hairstyleService.list();
        if (list == null) {
            return null;
        }
        return R.success(list);
    }
}

