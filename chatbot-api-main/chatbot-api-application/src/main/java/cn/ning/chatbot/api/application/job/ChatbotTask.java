package cn.ning.chatbot.api.application.job;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.ning.chatbot.api.domain.zsxq.ZsxqApi;
import cn.ning.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.ning.chatbot.api.domain.zsxq.model.vo.Topics;
import cn.ning.chatbot.api.domain.ai.OpentoAI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author ning
 * @description 任务执行流程调度
 */

@Configuration
@EnableScheduling
public class ChatbotTask implements Runnable {
    //引入日志
    private Logger logger = LoggerFactory.getLogger(ChatbotTask.class);
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Resource
    private ZsxqApi zsxqApi;
    @Resource
    private OpentoAI openAI;


    @Scheduled(cron = "0/5 * * * * ? ")
    @Override
    public void run() {
        //使得获取问题频率不规律,避免风控
        try {
            if (new Random().nextBoolean()) {
                logger.info("{} 随机时间获取...");
                return;
            }
            //获取问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.NotAnswereds(groupId, cookie);
            logger.info("检索问题结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
                logger.info("本次检索未查询到待会答问题");
                return;
            }
            //ChatGPT回答
            Topics topic = topics.get(topics.size() - 1);

            String answer = openAI.doChatGPT(openAiKey, topic.getQuestion().getText().trim());
            //回复问题
            boolean status = zsxqApi.Answers(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info(" 编号：{} 问题：{} 回答：{} 状态：{}", topic.getTopic_id(), topic.getQuestion().getText(), answer, status);
        } catch (Exception e) {
            logger.error("{} 自动回答问题异常", e);
        }
    }

}
