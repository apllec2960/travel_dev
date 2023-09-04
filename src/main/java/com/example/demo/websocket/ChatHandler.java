package com.example.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChatHandler extends TextWebSocketHandler {
    Logger logger = LoggerFactory.getLogger(ChatHandler.class);
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.debug("--------------------ChatHandler handleTextMessage START--------------------");
        String payload = message.getPayload();
        logger.debug("payload : " + payload);
        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }

        logger.debug("--------------------ChatHandler handleTextMessage END--------------------");
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("--------------------ChatHandler afterConnectionEstablished START--------------------");

        list.add(session);

        logger.debug(session + " 클라이언트 접속");

        logger.debug("--------------------ChatHandler afterConnectionEstablished END--------------------");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("--------------------ChatHandler afterConnectionClosed END--------------------");
        logger.debug(session + " 클라이언트 접속 해제");
        list.remove(session);
        logger.debug("--------------------ChatHandler afterConnectionClosed END--------------------");
    }

}
