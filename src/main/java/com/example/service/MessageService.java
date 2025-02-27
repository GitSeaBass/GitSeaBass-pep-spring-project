package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidBodyException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createNewMessage(Message message) throws InvalidBodyException {
        // fails if message_text blank, over 255 length, and posted by real user
        if (message.getMessageText().length() == 0 || message.getMessageText().length() >= 255) {
            throw new InvalidBodyException("Message is Invalid");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id) {
        return messageRepository.findByMessageId(message_id);
    }
}
