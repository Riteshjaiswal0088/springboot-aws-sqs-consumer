package com.javalearn.aws.sqs.repository;

import com.javalearn.aws.sqs.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}