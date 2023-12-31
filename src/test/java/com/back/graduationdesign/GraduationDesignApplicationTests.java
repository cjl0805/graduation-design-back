package com.back.graduationdesign;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.back.graduationdesign.service.impl.MyTask;
import com.back.graduationdesign.utils.TaskQueue;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class GraduationDesignApplicationTests {


    @Test
    void generator() {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("E:\\IDEA workplace\\graduation-design-back" + "/src/main/java");

        gc.setAuthor("cjl");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖

        //UserServie
        gc.setServiceName("%sService");	//去掉Service接口的首字母I

        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/graduation-design?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("cjl0805");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //下面两行生成包路径就是com.ws.eduservice
        pc.setModuleName("graduationdesign"); //模块名
        pc.setParent("com.back");

        //包下面的controller,service,mapper,entity  com.atguigu.eduservice.controlle//service//mapper//entity
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);


        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("performance");

        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);


        // 6、执行
        mpg.execute();

    }

    @Test
    public void formatTest(){
        double d=4.0E-4;
        DecimalFormat format = new DecimalFormat("##.####");
        String s = format.format(d);
        System.out.println("s = " + s);
    }

    @Test
    public void timeTest(){
        Date nowDate = new Date();
        System.out.println("nowDate = " + nowDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = format.format(nowDate);
        System.out.println("endDate = " + endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DATE,-7);
        String startDate = format.format(calendar.getTime());
        System.out.println("startDate = " + startDate);
    }

    @Test
    public void UUIDTest(){
        String now = DateUtil.now();
        System.out.println("now = " + now);
    }

    @Test
    public void StringTest(){
        String s = "OHAS-cjl-20230418103424";
        String x = "OHAS-aka145-20230418103424";
        s.lastIndexOf("-");
        x.lastIndexOf("-");
        String s1 = s.substring(5, s.lastIndexOf("-"));
        System.out.println("s1 = " + s1);
        String x1 = x.substring(5, x.lastIndexOf("-"));
        System.out.println("x1 = " + x1);
    }

    @Test
    public void DateUtilTest(){
        DateTime date = DateUtil.date();
        System.out.println("date = " + date);
        String substring1 = date.toString().substring(0, 10);
        System.out.println("substring1 = " + substring1);
        String substring2 = date.toString().substring(11, 19);
        System.out.println("substring2 = " + substring2);
    }

    @Test
    public void taskTest(){
        TaskQueue taskQueue = new TaskQueue(5);
        for (int i=0;i<10;i++){
            int add = taskQueue.add(new MyTask());
            System.out.println("add = " + add);
        }
        taskQueue.start();
    }

    @Test
    public void queueTest(){
        Queue<Map<String,Object>> queue = new LinkedList<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",1);
        hashMap.put("name","name");
        queue.offer(hashMap);
    }
    @Test
    public void treeSetTest(){
        // 创建一个包含Map.Entry对象的TreeSet集合
        Set<Map.Entry<String, Integer>> set = new TreeSet<>(Map.Entry.comparingByValue());

        // 向set集合中添加元素
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 3);
        map.put("B", 5);
        map.put("C", 2);
        map.put("D", 7);
        set.addAll(map.entrySet());

        // 遍历输出set集合中的元素
        for (Map.Entry<String, Integer> entry : set) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    @Test
    public void readExcelTest() throws IOException {
        String fileName = "C:\\Users\\ABC\\Desktop\\yyhome\\上传\\生物项目\\生物项目(1).xlsx";
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            //字节流转为字符流
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
