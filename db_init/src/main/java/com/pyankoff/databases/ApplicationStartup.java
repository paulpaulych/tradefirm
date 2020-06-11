package com.pyankoff.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SchemaInitializer schemaInitializer;
    @Autowired
    private DataPopulator dataPopulator;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        try {
            Thread.currentThread().sleep(1000);
            System.out.println("Select action:");
            System.out.println("1. create schema");
            System.out.println("2. populate data");
            System.out.println("3. 1 and 2");
            Scanner scanner = new Scanner(System.in);
            int action = scanner.nextInt();

            if (action == 1) {
                schemaInitializer.run();
            } else if (action == 2) {
                dataPopulator.run();
            } else if(action == 3){
                schemaInitializer.run();
                dataPopulator.run();
            } else {
                System.out.println("Wrong choise");
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
