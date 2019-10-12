package com.pyankoff.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SchemaInitializer {

    private JdbcTemplate jdbcTemplate;

    private ResourceLoader resourceLoader;

    public void run() throws IOException {
        String ddlQuery = resourceLoader.load("ddl/tradefirm_ddl.sql");
        System.out.println(ddlQuery);
        jdbcTemplate.execute(ddlQuery);
    }


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
