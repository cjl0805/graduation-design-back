package com.back.graduationdesign.controller;

import com.back.graduationdesign.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/graduation/design/common")
public class CommonController {

    @PostMapping("/upload")
    public R upload(@RequestPart MultipartFile file) throws Exception {
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        String url = "E:\\graduation-design\\img\\";
        File dir = new File(url);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file1 = new File(url + originalFilename);
        if (!file1.exists()){
            file.transferTo(file1);
        }
        return R.success("http://localhost:8090/picture/"+originalFilename);
    }
}
