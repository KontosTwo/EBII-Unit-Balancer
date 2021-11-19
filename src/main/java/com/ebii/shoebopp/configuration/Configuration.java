package com.ebii.shoebopp.configuration;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@NoArgsConstructor
public class Configuration {





    private static Configuration parseConfiguration(){
        return null;
    }

    private static String read(String absolutePath) throws IOException {
        return Files.readString(Paths.get(absolutePath));
    }
}
