package com.ssii.ip;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mongodb.BasicDBObject;
import com.ssii.Utils.MyJedis;
import com.ssii.Utils.PatternUtils;
import com.ssii.Utils.ProxyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import java.util.List;
import java.util.Map;

public class IPPipeline extends FilePersistentBase implements Pipeline {
    int count =0;
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println(resultItems.getAll());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                List<String> list = PatternUtils.changeString(entry.getValue().toString());
                if(list!=null){
                    for(String str:list){
                        if(ProxyUtils.checkUrl(str.split(" ")[0],Integer.parseInt(str.split(" ")[1]))){
                            MyJedis.insert(str.split(" ")[0]+" "+Integer.parseInt(str.split(" ")[1]));
                        };
                    }

                }
            }
        }
    }
}