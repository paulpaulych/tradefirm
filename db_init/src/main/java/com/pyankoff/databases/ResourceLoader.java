package com.pyankoff.databases;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class ResourceLoader {

    public String load(String fname){
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(fname);
        assert resource != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
