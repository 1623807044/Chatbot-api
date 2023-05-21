package cn.ning.chatbot.api.domain.zsxq.model.res;

import cn.ning.chatbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * @author ning
 * @description
 */
public class RespData {

    private List<Topics> topics;

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }

}
