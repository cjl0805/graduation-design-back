package com.back.graduationdesign.utils;

import com.back.graduationdesign.service.ITask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {
    // 任务队列
    private BlockingQueue mTaskQueue;
    // 执行器
    private TaskExecutor[] mTaskExecutors;

    // 创建队列时,指定执行器数量,保证你开的多个线程是否需要等待
    public TaskQueue(int size) {
        mTaskQueue = new LinkedBlockingQueue<>();
        mTaskExecutors = new TaskExecutor[size];
    }

    // 开启队列。
    public void start() {
        stop();
        for (int i = 0; i < mTaskExecutors.length; i++) {
            mTaskExecutors[i] = new TaskExecutor(mTaskQueue);
            mTaskExecutors[i].start();
        }
    }

    // 关闭队列。
    public void stop() {
        if (mTaskExecutors != null)
            for (TaskExecutor taskExecutor : mTaskExecutors) {
                if (taskExecutor != null) taskExecutor.quit();
            }
    }

    //添加任务到队列。
    public int add(ITask task) {
        if (mTaskQueue.size()>mTaskExecutors.length){
            return -1;
        }
        if (!mTaskQueue.contains(task)) {
            mTaskQueue.add(task);
        }
        // 返回队列中的任务数
        return mTaskQueue.size();
    }
}
