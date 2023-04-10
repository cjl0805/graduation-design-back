package com.back.graduationdesign.controller;


import com.back.graduationdesign.dto.HairstylistDto;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 发型师 前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/hairstylist")
public class HairstylistController {

    @Autowired
    private HairstylistService hairstylistService;

    @GetMapping("/list")
    public R getAll(){
        List<Hairstylist> list = hairstylistService.list();
        //利用stream流遍历
        List<HairstylistDto> hairstylistDtoList = list.stream().map(item->{
            HairstylistDto hairstylistDto = new HairstylistDto();
            BeanUtils.copyProperties(item,hairstylistDto,"skill");
            String[] split = item.getSkill().split(",");
            ArrayList<String> skills = new ArrayList<>();
            for (String s : split) {
                skills.add(s);
            }
            hairstylistDto.setSkill(skills);
            return hairstylistDto;
        }).collect(Collectors.toList());
        return R.success(hairstylistDtoList);
    }

    /**
     * 根据发型推荐发型师
     * @param hairstyle
     * @return
     */
    @GetMapping("/recommend")
    public R getRecommend(String hairstyle){
        List<Hairstylist> hairstylist = hairstylistService.list();
        List<Hairstylist> list = new ArrayList<>();
        for (Hairstylist item : hairstylist) {
            String[] split = item.getSkill().split(",");
            for (String s : split) {
                if (s.equals(hairstyle)){
                    list.add(item);
                }
            }
        }
        return R.success(list);
    }

}

