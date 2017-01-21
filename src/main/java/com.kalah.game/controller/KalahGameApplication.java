package com.kalah.game.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/**
 * Application runner
 * @author oalizada
 */
@SpringBootApplication
@ComponentScan("com.kalah.game")
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class KalahGameApplication {
    public static void main(String[] args) throws Exception {

        SpringApplication.run(KalahGameApplication.class, args);
    }


}
