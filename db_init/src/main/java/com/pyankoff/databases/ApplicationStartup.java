package com.pyankoff.databases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final SchemaInitializer schemaInitializer;
    private final DataPopulator dataPopulator;

    public ApplicationStartup(SchemaInitializer schemaInitializer, DataPopulator dataPopulator) {
        this.schemaInitializer = schemaInitializer;
        this.dataPopulator = dataPopulator;
    }

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
