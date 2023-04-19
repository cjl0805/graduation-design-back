package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.CustomInfo;
import com.back.graduationdesign.entity.User;
import com.back.graduationdesign.service.CustomInfoService;
import com.back.graduationdesign.service.UserService;
import com.back.graduationdesign.utils.R;
import com.back.graduationdesign.utils.RegexUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-20
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/customInfo")
public class CustomInfoController {

    @Autowired
    private CustomInfoService customInfoService;

    @Autowired
    private UserService userService;

    /**
     * 获取顾客信息
     * @param username
     * @return
     */
    @GetMapping("/get")
    public R getCustomInfo(String username){
        LambdaQueryWrapper<CustomInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomInfo::getUsername,username);
        CustomInfo customInfo = customInfoService.getOne(wrapper);
        return R.success(customInfo);
    }

    /**
     * 分页显示
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/get/{page}/{size}")
    public R getPage(String query,@PathVariable int page,@PathVariable int size){
        LambdaQueryWrapper<CustomInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(CustomInfo::getId,query).or().like(CustomInfo::getName,query).or().like(CustomInfo::getUsername,query);
        Page<CustomInfo> customInfoPage = customInfoService.page(new Page<>(page, size),queryWrapper);
        return R.success(customInfoPage);
    }

    /**
     * 删除
     * @param customInfo
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody CustomInfo customInfo){
        boolean b = customInfoService.removeById(customInfo.getId());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,customInfo.getUsername());
        userService.remove(queryWrapper);
        if (b) return R.success(true);
        else return R.error("删除失败");
    }

    /**
     * 保存用户头像
     * @param img
     * @param username
     * @return
     */
    @PostMapping("save/img")
    public R saveImg(String img,String username){
        CustomInfo customInfo = new CustomInfo();
        customInfo.setImg(img);
        LambdaQueryWrapper<CustomInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomInfo::getUsername,username);
        boolean update = customInfoService.update(customInfo, wrapper);
        return R.success(update);
    }

    /**
     * 修改顾客个人信息
     * @param customInfo
     * @return
     */
    @PutMapping("update/info")
    public R updateInfo(@RequestBody CustomInfo customInfo){
        if (!RegexUtils.checkPhone(customInfo.getPhone())) {
            return R.error("手机号格式有误!");
        }
        if (!RegexUtils.checkIDCard(customInfo.getIdentityCard())){
            return R.error("身份证格式有误!");
        }
        LambdaQueryWrapper<CustomInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomInfo::getUsername,customInfo.getUsername());
        CustomInfo info = new CustomInfo();
        info.setName(customInfo.getName());
        info.setPhone(customInfo.getPhone());
        info.setIdentityCard(customInfo.getIdentityCard());
        boolean update = customInfoService.update(info, wrapper);
        return R.success(update);
    }

}

