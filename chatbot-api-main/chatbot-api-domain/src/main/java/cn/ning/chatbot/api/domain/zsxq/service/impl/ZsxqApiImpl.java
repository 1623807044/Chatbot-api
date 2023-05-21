package cn.ning.chatbot.api.domain.zsxq.service.impl;

import cn.ning.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.alibaba.fastjson.JSON;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import cn.ning.chatbot.api.domain.zsxq.model.req.AnswerReq;
import cn.ning.chatbot.api.domain.zsxq.model.req.ReqData;
import cn.ning.chatbot.api.domain.zsxq.model.res.AnswerRes;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;



/**
 * @author ning
 * @description 启动入口
 */

@Service
public class ZsxqApiImpl implements cn.ning.chatbot.api.domain.zsxq.ZsxqApi {


    private Logger logger =LoggerFactory.getLogger(ZsxqApiImpl.class);
    //获取问题
    @Override

    public UnAnsweredQuestionsAggregates NotAnswereds(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //拼接请求
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");
        //设置cookie
        get.addHeader("cookie", cookie);
        //设置内容格式
        get.addHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse response = httpClient.execute(get);
        //获取到响应信息。判断是否为200。
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        }else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }

    }
    @Override
    public boolean Answers(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");
        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type", "application/json;charset=utf8");
        //告诉是从浏览器获取的
        post.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

        //封装应答信息
        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        //将应答类转换为json数据
        String paramJson = JSONObject.toJSONString(answerReq);
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }

    }

}
