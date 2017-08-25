package com.ssii;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSON;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class SinaBlogPipeline extends FilePersistentBase implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SinaBlogPipeline() {
        this.setPath("/data/webmagic");
    }

    public SinaBlogPipeline(String path) {
        this.setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        System.out.println(path);
        System.out.println("=====================================================");
        System.out.println(resultItems.getRequest().getUrl());
        System.out.println("=====================================================");
        System.out.println(resultItems.getAll());

        try {
            PrintWriter e = new PrintWriter(new FileWriter(this.getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".json")));
            e.write(JSON.toJSONString(resultItems.getAll()));
            e.close();
        } catch (IOException var5) {
            this.logger.warn("write file error", var5);
        }

    }
}
