package cn.ning.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.Test;

import java.io.IOException;
//测试
public class ApiTest {
    @Test
    //获取提问详细内容
    public void UserAcquisitionProblem() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/48884541814248/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie", "abtest_env=product; zsxqsessionid=4212839ce212b8fb2b172c43d0542140; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22184215552258112%22%2C%22first_id%22%3A%2218835bc4d13370-04cd9293a1535e-7b515474-1327104-18835bc4d14f4c%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg4MzViYzRkMTMzNzAtMDRjZDkyOTNhMTUzNWUtN2I1MTU0NzQtMTMyNzEwNC0xODgzNWJjNGQxNGY0YyIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjE4NDIxNTU1MjI1ODExMiJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22184215552258112%22%7D%2C%22%24device_id%22%3A%2218835bc4d13370-04cd9293a1535e-7b515474-1327104-18835bc4d14f4c%22%7D; zsxq_access_token=22B8BD95-7170-BF71-E03B-80073AEDC0CC_E687E1F62F62F9FD");
        get.addHeader("Content-Type", "application/json;charset=UTF-8") ;

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String string = EntityUtils.toString(response.getEntity());
            System.out.println(string);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }
    //
    @Test
    //回答问题
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/412244188882228/answer");
        post.addHeader("cookie", "abtest_env=product; zsxqsessionid=4212839ce212b8fb2b172c43d0542140; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22184215552258112%22%2C%22first_id%22%3A%2218835bc4d13370-04cd9293a1535e-7b515474-1327104-18835bc4d14f4c%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg4MzViYzRkMTMzNzAtMDRjZDkyOTNhMTUzNWUtN2I1MTU0NzQtMTMyNzEwNC0xODgzNWJjNGQxNGY0YyIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjE4NDIxNTU1MjI1ODExMiJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22184215552258112%22%7D%2C%22%24device_id%22%3A%2218835bc4d13370-04cd9293a1535e-7b515474-1327104-18835bc4d14f4c%22%7D; zsxq_access_token=22B8BD95-7170-BF71-E03B-80073AEDC0CC_E687E1F62F62F9FD");
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        String json = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"好\\n\",\n" +
                "    \"image_ids\": []\n" +
                "  }\n" +
                "}";
        StringEntity stringEntity = new StringEntity(json, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String string = EntityUtils.toString(response.getEntity());
            System.out.println(string);
        } else {
            System.out.println(response.getStatusLine().getStatusCode()+"");
        }
    }

    @Test
    void ChatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api,openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String paramson = "{\"model\": \"text-davinci-003\",\"prompt\": \"java未来100年的前景怎么样\",\"temperature\":0,\"maxtokens\": 1024)";
        StringEntity stringEntity = new StringEntity(paramson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            String string = EntityUtils.toString(response.getEntity());
            System.out.println(string);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
