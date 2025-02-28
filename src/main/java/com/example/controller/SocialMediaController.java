package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ConflictingUsernameException;
import com.example.exception.DeleteMessageNotFoundException;
import com.example.exception.InvalidBodyException;
import com.example.exception.UnauthorizedLoginException;
import com.example.exception.UpdateMessageFailedException;
import com.example.service.AccountService;
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
    private AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
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

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(message_id));
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int message_id, @RequestBody String messageText) {
        return ResponseEntity.status(200).body(messageService.patchMessageById(message_id, messageText));
    }
 
    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return ResponseEntity.status(200).body(accountService.postAccount(account));
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        return ResponseEntity.status(200).body(accountService.login(account));
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int account_id) {
        return ResponseEntity.status(200).body(messageService.getMessagesByAccountId(account_id));
    }


    @ExceptionHandler(InvalidBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleInvalidBodyException(InvalidBodyException ex) { return ex.getMessage();}

    @ExceptionHandler(DeleteMessageNotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public void handleDeleteMessageNotFoundException(DeleteMessageNotFoundException ex) { }

    @ExceptionHandler(UpdateMessageFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleUpdateMessageFailedException(UpdateMessageFailedException ex) {return ex.getMessage();}

    @ExceptionHandler(ConflictingUsernameException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleConflictingUsernameException(ConflictingUsernameException ex) { }

    @ExceptionHandler(UnauthorizedLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public void handleUnauthorizedLoginException(UnauthorizedLoginException ex) { }
}
