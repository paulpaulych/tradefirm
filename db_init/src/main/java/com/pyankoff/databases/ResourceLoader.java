package com.pyankoff.databases;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ResourceLoader {

    public String load(String fname) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        this.getClass().getClassLoader().getResourceAsStream(fname)));
        String line;
        StringBuilder  stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            if(stringBuilder.toString() != null){
                return stringBuilder.toString();
            }
            throw new RuntimeException("cannot read file " + fname);
        } finally {
            reader.close();
        }
    }
}
