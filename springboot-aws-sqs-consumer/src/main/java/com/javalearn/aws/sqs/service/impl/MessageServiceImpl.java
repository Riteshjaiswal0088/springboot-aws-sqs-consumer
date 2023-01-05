package com.javalearn.aws.sqs.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalearn.aws.sqs.dto.MessageDto;
import com.javalearn.aws.sqs.entity.Message;
import com.javalearn.aws.sqs.repository.MessageRepository;
import com.javalearn.aws.sqs.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    @SqsListener("javalearn-queue")
    public void receiveMessage(String message) throws JsonProcessingException {
        log.info("Message: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDto messageDto = objectMapper.readValue(message, MessageDto.class);
        Message entity = MessageDto.toEntity(messageDto);
        Message save = messageRepository.save(entity);
        log.info("saved data to db: {}",save);
    }
}
