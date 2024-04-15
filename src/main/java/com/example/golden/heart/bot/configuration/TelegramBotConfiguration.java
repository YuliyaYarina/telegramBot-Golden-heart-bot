package com.example.golden.heart.bot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    String token;

    @Value("${bot.name}")
    String botName;

//    @Value("${bot.owner}")
//    Long ownerId;

//    @Bean
//    public TelegramBot telegramBot() {
//        TelegramBot bot = new TelegramBot(token);
//        bot.execute(new DeleteMyCommands());
//        return bot;
//    }

}
