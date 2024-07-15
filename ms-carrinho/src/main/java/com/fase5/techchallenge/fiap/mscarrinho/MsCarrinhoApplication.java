package com.fase5.techchallenge.fiap.mscarrinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCarrinhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCarrinhoApplication.class, args);
    }

}