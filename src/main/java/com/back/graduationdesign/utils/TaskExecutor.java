package com.back.graduationdesign.utils;

import com.back.graduationdesign.service.ITask;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class TaskExecutor extends Thread{
    //任务队列，里面是要执行的任务
    private BlockingQueue<ITask> queue;
    //任务队列是否在执行任务
    private boolean isRunning = true;

    public TaskExecutor(BlockingQueue<ITask> queue){
        this.queue = queue;
    }
    // 退出。
    public void quit() {
        isRunning = false;
        interrupt();
    }
    @Override
    public void run() {
        while (isRunning) { // 如果是执行状态就待着。
            ITask iTask;
            try {
                iTask = queue.take(); // 下一个任务，没有就等着。
            } catch (InterruptedException e) {
                if (!isRunning) {
                    // 发生错误中断代码
                    interrupt();
                    break;
                }
                // 发生意外了，不是退出状态，那么窗口继续等待。
                continue;
            }

            // 执行任务
            iTask.run();
        }
    }
}
