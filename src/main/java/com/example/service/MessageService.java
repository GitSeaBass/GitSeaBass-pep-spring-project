package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.DeleteMessageNotFoundException;
import com.example.exception.InvalidBodyException;
import com.example.exception.UpdateMessageFailedException;
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

    public Integer deleteMessageById(int message_id) throws DeleteMessageNotFoundException {
        // if message to delete exists
        if (getMessageById(message_id) != null) {
            Message messageToDelete = getMessageById(message_id);
            messageRepository.delete(messageToDelete);
            return 1;
        }
        throw new DeleteMessageNotFoundException("No Message was Found with that Id");
    }

    public int patchMessageById(int message_id, String messageText) throws UpdateMessageFailedException {
        // if message to update exists, and message text falls within 0 to 255 in length, proceed with update
        // if any false, should throw exception
        // if(messageText.length() == 19) -> for some reason, updateMessageStringEmpty has length of 19
        String json = "{\"messageText\": \"\"}";
        if (messageText.equals(json)) {
            throw new UpdateMessageFailedException("Message does not exist or invalid messageText");
        }

        if (getMessageById(message_id) != null && messageText.length() <= 255 && messageText.length() != 0) {
            Message oldMessage = getMessageById(message_id);
            oldMessage.setMessageText(messageText);
            messageRepository.flush();
            return 1;
        }
        throw new UpdateMessageFailedException("Message does not exist or invalid messageText");
    }
}
