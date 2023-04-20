package com.back.graduationdesign.controller;


import com.back.graduationdesign.entity.Notice;
import com.back.graduationdesign.service.NoticeService;
import com.back.graduationdesign.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2023-04-20
 */
@CrossOrigin
@RestController
@RequestMapping("/graduation/design/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页展示
     * @param username
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/get/{page}/{size}")
    public R getPage(String username, @PathVariable int page, @PathVariable int size){
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getUser,username);
        Page<Notice> noticePage = noticeService.page(new Page<>(page, size), queryWrapper);
        return R.success(noticePage);
    }

}

