package com.javalearn.aws.sqs.dto;

import com.javalearn.aws.sqs.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private String name;
    private Double salary;

    public static Message toEntity(MessageDto messageDto){
        return Message.builder()
        		.name(messageDto.getName())
        		.salary(messageDto.getSalary())
        		.build();

    }

}
