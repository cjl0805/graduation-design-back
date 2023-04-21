package com.back.graduationdesign.service;

import java.util.HashMap;

public interface StatisticsService {
    HashMap<String,Object> getPerformance(String username);

    HashMap<String,Object> getOrdersCount(String username);

     void insertPerformance(int nowCount,int monthCount,int totalCount,String name);
}
