package cn.ning.chatbot.api.domain.ai.service.impl;

import cn.ning.chatbot.api.domain.ai.OpentoAI;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author ning
 * @description 实现远程ChatGPT的请求调用
 */

@Service
public class OpenAIImpl implements OpentoAI {

    @Override
    public String doChatGPT(String openAiKey, String question) throws IOException {

//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        // 创建Post请求
//        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
//        post.addHeader("Content-Type", "application/json");
//        post.addHeader("Authorization", "Bearer " + openAiKey);
//        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + question + "\", \"temperature\": 0, \"max_tokens\": 1024}";
//
//        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
//        post.setEntity(stringEntity);
//        //发起请求调用得到响应结果
//        CloseableHttpResponse response = httpClient.execute(post);
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            String jsonStr = EntityUtils.toString(response.getEntity());
//            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
//            StringBuilder answers = new StringBuilder();
//            List<Choices> choices = aiAnswer.getChoices();
//            for (Choices choice : choices) {
//                answers.append(choice.getText());
//            }
//            return answers.toString();
//        } else {
      //测试
        return "{\n" +
                    "  \"req_data\": {\n" +
                    "    \"text\": \"自己去百度！\\n\",\n" +
                    "    \"image_ids\": [],\n" +
                    "    \"silenced\": false\n" +
                    "  }\n" +
                    "}";
        //    throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
//       }
    }

}
