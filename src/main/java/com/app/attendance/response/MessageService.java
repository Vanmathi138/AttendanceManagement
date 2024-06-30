package com.app.attendance.response;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class MessageService {
    private final MessageSource messageSource;

    @Autowired
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String messageResponse(String key) {
        return messageSource.getMessage(key, null, Locale.ENGLISH);
    }

    public String messageResponse(String key, String[] params) {
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }
}


