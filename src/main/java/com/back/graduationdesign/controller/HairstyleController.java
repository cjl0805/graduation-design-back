package com.back.graduationdesign.controller;


import com.back.graduationdesign.dto.HairStyleChecked;
import com.back.graduationdesign.entity.Hairstyle;
import com.back.graduationdesign.entity.Hairstylist;
import com.back.graduationdesign.service.HairstyleService;
import com.back.graduationdesign.service.HairstylistService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 分页显示
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/get/{page}/{size}")
    public R getPage(@RequestParam String query,@PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<Hairstyle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Hairstyle::getId,query).or().like(Hairstyle::getHairstyle,query);
        Page<Hairstyle> hairstylePage = hairstyleService.page(new Page<>(page, size),queryWrapper);
        return R.success(hairstylePage);
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

    /**
     * 增加
     * @param hairstyle
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody Hairstyle hairstyle){
        boolean save = hairstyleService.save(hairstyle);
        if (save) return R.success(true);
        else return R.error("添加失败！");
    }

    /**
     * 删除
     * @param hairstyle
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Hairstyle hairstyle){

        //同时删除发型师的发型信息
        LambdaQueryWrapper<Hairstylist> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询出包含当前发型的发型师信息
        queryWrapper.like(Hairstylist::getSkill,hairstyle.getHairstyle());
        List<Hairstylist> list = hairstylistService.list(queryWrapper);
        if (!list.isEmpty()){
            //利用stream流处理发型师信息，将带有当前发型的信息删除
            List<Hairstylist> hairstylists = list.stream().map(item->{
                Hairstylist hairstylist = new Hairstylist();
                BeanUtils.copyProperties(item,hairstylist,"skill");
                String skill ="";
                String[] split = item.getSkill().split(",");
                for (int i=0;i<split.length-1;i++) {
                    if (!split[i].equals(hairstyle.getHairstyle())){
                        skill+=split[i]+",";
                    }
                }
                StringBuilder s = new StringBuilder(skill);
                //替换最后一个,为空
                String value = String.valueOf(s.replace(skill.length() - 1, skill.length(), ""));
                hairstylist.setSkill(value);
                return hairstylist;
            }).collect(Collectors.toList());
            boolean b1 = hairstylistService.updateBatchById(hairstylists);
        }
        boolean b = hairstyleService.removeById(hairstyle.getId());
        if (b) return R.success(true);
        else return R.error("删除失败！");
    }

    /**
     * 修改
     * @param hairstyle
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody Hairstyle hairstyle){
        boolean b = hairstyleService.updateById(hairstyle);
        if (b) return R.success(true);
        else return R.error("修改失败！");
    }
}

