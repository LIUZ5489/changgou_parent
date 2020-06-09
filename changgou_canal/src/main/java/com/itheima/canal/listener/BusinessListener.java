package com.itheima.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.itheima.canal.config.RabbitMQConfig;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CanalEventListener //声名当前类是一个canal监听类
public class BusinessListener {

    private static Logger logger = LoggerFactory.getLogger(BusinessListener.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ListenPoint(schema = "changgou_business", table = "tb_ad")
    public void adUpdate(CanalEntry.EntryType entryType, CanalEntry.RowData rowData) {
        logger.info("广告表数据发生改变");

        //改变之后的数据
        Map afmap = new HashMap();
        List<CanalEntry.Column> afl = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afl) {
            if ("position".equals(column.getName())) {
                String value = column.getValue();
                System.out.println("发送最新数据到mq:"+value);
                logger.info("发送最新的数据到" + RabbitMQConfig.AD_UPDATE_QUEUE + "队列" + "消息为： " + value);
                rabbitTemplate.convertAndSend("", RabbitMQConfig.AD_UPDATE_QUEUE, value);
            }
        }
    }
}
