package com.ebii.shoebopp.configuration;

import com.ebii.shoebopp.balancer.Stats;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Configuration {
    private final Map<Stats, Action> statFormulae;



    public static Configuration config(){
        return new Configuration(new HashMap<>());
    }

    private static Configuration parseConfiguration(){
        return null;
    }

    private static String read(String absolutePath) throws IOException {
        return Files.readString(Paths.get(absolutePath));
    }
}
