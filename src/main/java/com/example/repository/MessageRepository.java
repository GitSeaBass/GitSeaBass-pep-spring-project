package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    public <S extends Message> S save(S message);
    public List<Message> findAll();
    public Message findByMessageId(int messageId);
    public void delete(Message message);
    public void flush();
    public List<Message> findByPostedBy(int accountId);
}
