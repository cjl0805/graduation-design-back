package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.CustomInfo;
import com.back.graduationdesign.entity.User;
import com.back.graduationdesign.service.CustomInfoService;
import com.back.graduationdesign.service.UserService;
import com.back.graduationdesign.utils.JWTUtils;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomInfoService customInfoService;

    @PostMapping("/register")
    public R register(String username,String password){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        //根据用户名查询数据库中是否存在该用户
        User one = userService.getOne(wrapper);
        if (one == null){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("3");
            CustomInfo customInfo = new CustomInfo();
            customInfo.setUsername(username);
            customInfo.setPassword(password);
            customInfoService.save(customInfo);
            boolean save = userService.save(user);
            if (save) return R.success(username);
        }
        else return R.error("该用户名已存在!");
        return R.success(null);
    }

    @GetMapping("/login")
    public R login(String username,String password){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username).eq(User::getPassword,password);
        User one = userService.getOne(wrapper);
        if (one!=null){
            String token = JWTUtils.createToken(username);
            return R.success(token);
        }
        else return R.error("该用户不存在,请先注册");
    }

//    @PostMapping("/logout")
//    public R logout(HttpServletRequest request){
//        String token = request.getHeader("token");
//        Claims claim = JWTUtils.getTokenClaim(token);
//        String username = claim.getSubject();
//        System.out.println(JWTUtils.getExpirationDateFromToken(token));
//        if (username!=null) return R.success(username+"退出成功");
//        else return R.success("退出成功");
//    }

    @PutMapping("update/password")
    public R updatePassword(String password,String username){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        User user = new User();
        user.setPassword(password);
        boolean update = userService.update(user,wrapper);
        LambdaQueryWrapper<CustomInfo> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(CustomInfo::getUsername,username);
        CustomInfo customInfo = new CustomInfo();
        customInfo.setPassword(password);
        boolean update1 = customInfoService.update(customInfo,wrapper1);
        return R.success(update+" "+update1);
    }



}

