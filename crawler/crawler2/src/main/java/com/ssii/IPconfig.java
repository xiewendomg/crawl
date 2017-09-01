package com.ssii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/9/1.
 */
public class IPconfig {
        protected static Logger log = LoggerFactory.getLogger(IPconfig.class);
        public static String REDIS_IP;
        public static int REDIS_PORT;
        public static String REDIS_PASSWORD;
        public static int MAX_ACTIVE;
        public static int MAX_IDLE;
        public static long MAX_WAIT;
        public static boolean TEST_ON_BORROW;
        public static boolean TEST_ON_RETURN;

        static {
            init();
        }
        public static void init() {
            try {
                String fullFile = "H:/kj/crawler/crawler2/src/main/lesources/ip.properties";

                File file = new File(fullFile);
                if(file.exists()){
                    log.info("loading redis config from ip.properties.......");
                    InputStream in = new FileInputStream(fullFile);
                    Properties p=new Properties();
                    p.load(in);
                    System.out.print(p.getProperty("redis.ip"));
                    REDIS_IP = p.getProperty("redis.ip");
                    REDIS_PORT = Integer.parseInt(p.getProperty("redis.port"));
                    REDIS_PASSWORD = p.getProperty("redis.password");
                    MAX_ACTIVE = Integer.parseInt(p.getProperty("redis.pool.maxActive"));
                    MAX_IDLE = Integer.parseInt(p.getProperty("redis.pool.maxIdle"));
                    MAX_WAIT = Integer.parseInt(p.getProperty("redis.pool.maxWait"));
                    TEST_ON_BORROW = Boolean.parseBoolean(p.getProperty("redis.pool.testOnBorrow"));
                    TEST_ON_RETURN = Boolean.parseBoolean(p.getProperty("redis.pool.testOnReturn"));
                    log.info("redis config load Completed。");
                    System.out.print("redis config load Completed。");
                    in.close();
                    in=null;
                }else{
                    log.error("redis.properties is not found!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

