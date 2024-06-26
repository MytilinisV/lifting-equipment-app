package com.example.liftingequipmentmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LiftingEquipmentMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiftingEquipmentMainApplication.class, args);
    }

}
