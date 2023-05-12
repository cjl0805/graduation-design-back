package com.back.graduationdesign.service.impl;

import com.back.graduationdesign.service.ITask;
import org.springframework.stereotype.Service;

@Service
public class MyTask implements ITask {
    //要执行的代码逻辑
    @Override
    public void run() {
        for (int i=0;i<10000;i++){
            i++;
        }
        System.out.println("MyTask");
    }
}
