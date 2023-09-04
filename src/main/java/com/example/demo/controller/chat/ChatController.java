package com.example.demo.controller.chat;

import com.example.demo.websocket.ChatHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class ChatController {

    Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    @RequestMapping("/chat")
    public String chatGET(){

        logger.debug("@ChatController, chat GET()");

        return "chat";
    }
}