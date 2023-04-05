package com.back.graduationdesign.service.impl;

import com.back.graduationdesign.entity.User;
import com.back.graduationdesign.mapper.UserMapper;
import com.back.graduationdesign.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjl
 * @since 2023-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
