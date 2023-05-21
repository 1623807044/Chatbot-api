package cn.ning.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author ning
 * @description ChatGPT open ai
 */

public interface OpentoAI {


    public String doChatGPT(String openAiKey, String question) throws IOException;

}
