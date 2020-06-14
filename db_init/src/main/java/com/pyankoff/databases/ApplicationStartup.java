package com.pyankoff.databases;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SchemaInitializer schemaInitializer;
    @Autowired
    private DataPopulator dataPopulator;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            schemaInitializer.run();
            dataPopulator.run();
        }catch (Throwable e){
            e.printStackTrace();
            SpringApplication.exit(event.getApplicationContext(), () -> -1 );
        }
    }
}
