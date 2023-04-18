package com.back.graduationdesign.controller;


import com.back.graduationdesign.dto.HairStylistInfo;
import com.back.graduationdesign.dto.HairstylistDto;
import com.back.graduationdesign.entity.CustomInfo;
import com.back.graduationdesign.entity.Hairstyle;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 分页查询
     * @param query
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/get/page/{page}/{size}")
    public R getPage(@RequestParam String query,@PathVariable int page,@PathVariable int size){
        List<HairStylistInfo> hairStylistInfo = hairstylistService.selectHairStylistInfoByPage((page-1)*size, size, query);
        List<HairStylistInfo> list = hairstylistService.selectHairStylistInfoByPage(1, 10000, query);
        Page<HairStylistInfo> hairStylistInfoPage = new Page<>();
        hairStylistInfoPage.setCurrent(page);
        hairStylistInfoPage.setRecords(hairStylistInfo);
        hairStylistInfoPage.setSize(size);
        hairStylistInfoPage.setTotal(list.size());
        return R.success(hairStylistInfoPage);
    }

    /**
     * 查询发型师的擅长发型
     * @param username
     * @return
     */
    @GetMapping("/get/hairstyle")
    public R getHairstyle(String username){
        LambdaQueryWrapper<Hairstylist> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Hairstylist::getUsername,username);
        Hairstylist one = hairstylistService.getOne(queryWrapper);
        String[] split = one.getSkill().split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        return R.success(list);
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

    /**
     * 根据用户名查询发型师信息
     * @param username
     * @return
     */
    @GetMapping("/get")
    public R getByUsername(String username){
        HairStylistInfo hairStylistInfo = hairstylistService.selectHairStylistInfo(username);
        return R.success(hairStylistInfo);
    }

    /**
     * 保存头像
     * @param img
     * @param username
     * @return
     */
    @PostMapping("/save/img")
    public R saveImg(String img,String username){
        Hairstylist hairstylist = new Hairstylist();
        hairstylist.setImg(img);
        LambdaQueryWrapper<Hairstylist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Hairstylist::getUsername,username);
        boolean update = hairstylistService.update(hairstylist, wrapper);
        return R.success(update);
    }


    @PutMapping("/update/info")
    public R updateInfo(@RequestBody Hairstylist hairstylist){
        boolean b = hairstylistService.updateById(hairstylist);
        return R.success(b);
    }
}

