package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.exception.InvalidBodyException;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private MessageService messageService;

    @Autowired
    public SocialMediaController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message newMessage) {
        return ResponseEntity.status(200).body(messageService.createNewMessage(newMessage));
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    @ExceptionHandler(InvalidBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleInvalidBodyException(InvalidBodyException ex) { return ex.getMessage();}
}
