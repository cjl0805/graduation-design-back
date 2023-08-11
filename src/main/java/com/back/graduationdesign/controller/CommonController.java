package com.back.graduationdesign.controller;

import com.back.graduationdesign.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@CrossOrigin
@RestController
@RequestMapping("/graduation/design/common")
public class CommonController {

//    @Resource
//    private RabbitTemplate rabbitTemplate;


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

//    /**
//     * 消息队列
//     * @param value
//     * @return
//     */
//    @GetMapping("/rabbitMq")
//    public R send(String value){
//        String messageId = String.valueOf(UUID.randomUUID());
//        String messageData = "message: " + value;
//        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Map<String, Object> map = new HashMap<>();
//        map.put("messageId", messageId);
//        map.put("messageData", messageData);
//        map.put("createTime", createTime);
//        rabbitTemplate.convertAndSend("test",map);
//        return R.success(value);
//    }
//
//    @GetMapping("/file")
//    public R ExcelTest(@RequestPart MultipartFile file) throws IOException {
//        File file1 = new File("C:\\Users\\ABC\\Desktop\\yyhome\\测试.xlsx");
//        file.transferTo(file1);
//        ExcelReader reader = ExcelUtil.getReader(file1);
//        List<JSONObject> read = reader.readAll(JSONObject.class);
//        System.out.println("read = " + read);
//        return R.success(null);
//    }


}
