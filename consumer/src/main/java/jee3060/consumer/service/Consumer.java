package jee3060.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jee3060.consumer.model.Hobby;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Consumer {
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener
    public void receive(String jsonHobby){
        log.info("message recived" + jsonHobby);

        try {
            Hobby hobby = objectMapper.readValue(jsonHobby, Hobby.class);
            log.info(hobby.getHobbyCategory());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
