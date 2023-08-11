package com.back.graduationdesign.controller;

import com.alibaba.fastjson.JSONObject;
import com.back.graduationdesign.entity.CustomInfo;
import com.back.graduationdesign.utils.R;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.concurrent.*;

@RestController
@RequestMapping
public class TargetPredictionController {

    // 创建一个有界队列，用于存储等待访问的用户请求
    private final BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(1);
    // 创建一个线程池执行器，用于控制线程的执行
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2, 3, 0L, TimeUnit.MILLISECONDS, taskQueue);

    @PostMapping("/targetPrediction")
    public R targetPrediction() {
        int size = taskQueue.size();
        int poolSize = threadPoolExecutor.getPoolSize();
        if (size >= 1 && poolSize >= threadPoolExecutor.getMaximumPoolSize()){
            return R.success("前方队列任务已满，请耐心等待");
        }
        Future<String> future = threadPoolExecutor.submit(this::performTargetPrediction);
        try {
            String result = future.get();
            return R.success(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return R.error("内部服务器异常，请稍后");
        }
    }

    // 靶点预测逻辑
    private String performTargetPrediction() {
        // 这里执行你的靶点预测逻辑
        // ...

        // 模拟预测过程的延迟
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "靶点预测结果：";
    }

//    @GetMapping("/webClient")
//    public void webClientTest(){
//        WebClient webClient = WebClient.create();
//        String url = "http://localhost:8090/targetPrediction";
//        Mono<R> response = webClient.get().uri(url).retrieve().bodyToMono(R.class);
//        response.subscribe(r -> {
//            System.out.println("r = " + r);
//        }, Throwable::printStackTrace);
//    }

    /**
     * spring 5 新特性，使用WebClient来进行HTTP服务之间的通信(可以实现异步调用)
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("smi","C([C@@H](C(=O)O)N)S");
        String url = "http://121.41.49.22:43576/YHYYNLP/SMITOINCHIKEY/";
        Mono<R> response = webClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(jsonObject))
                .retrieve()
                .bodyToMono(R.class);
        //阻塞方式获取响应结果
        R block = response.block();
        System.out.println("block = " + block);
//        //非阻塞方式获取响应结果
//        response.subscribe(r -> System.out.println("r = " + r), Throwable::printStackTrace);
//        Thread.sleep(1000);
        System.out.println("============程序结束============");


    }
}
