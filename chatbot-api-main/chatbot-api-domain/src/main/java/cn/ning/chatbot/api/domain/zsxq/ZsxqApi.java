package cn.ning.chatbot.api.domain.zsxq;

import cn.ning.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import java.io.IOException;

/**
 * @author ning
 * @description 知识星球 API 接口
 */
public interface ZsxqApi {

    UnAnsweredQuestionsAggregates NotAnswereds(String groupId,String cookie)throws IOException;

    boolean Answers(String groupld, String cookie, String topicId,String text, boolean silenced) throws IOException;

}