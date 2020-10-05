package com.pyankoff.databases;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SchemaInitializer {

    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;

    public SchemaInitializer(JdbcTemplate jdbcTemplate, ResourceLoader resourceLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = resourceLoader;
    }

    public void run() {
        String ddlQuery = resourceLoader.load("ddl/tradefirm_ddl.sql");
        System.out.println(ddlQuery);
        jdbcTemplate.execute(ddlQuery);
    }

}
