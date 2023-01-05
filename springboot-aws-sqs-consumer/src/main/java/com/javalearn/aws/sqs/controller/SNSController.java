package com.javalearn.aws.sqs.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SNSController {

    public static final String TOPIC_ARN = "arn:aws:sns:ap-south-1:748701809506:learnjava-topic";

    private final AmazonSNSClient amazonSNSClient;

    @GetMapping("/subscribe/{phone}")
    public String subscribe(@PathVariable(value = "phone") String phoneNumber) {
        SubscribeRequest subscribeRequest =
                new SubscribeRequest(TOPIC_ARN,"sms", phoneNumber);

        amazonSNSClient.subscribe(subscribeRequest);
        return "PhoneNumber Subscription successful";
    }

    @GetMapping("/publish{message}")
    public String sendMessage(@PathVariable(value = "message") String message) {

        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("mySenderID")// the sender ID shown on the device
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50")// set the max price to 0.50 USD
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional")// set the type to Transactional
                .withDataType("String"));

        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setMessage(message);
        publishRequest.setTopicArn(TOPIC_ARN);
        publishRequest.setMessageAttributes( smsAttributes);
        amazonSNSClient.publish(publishRequest);
        return "publish message successfully";
    }
}