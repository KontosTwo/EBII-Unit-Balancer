package com.ebii.shoebopp.configuration;

import com.ebii.shoebopp.balancer.stat.Stat;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@NoArgsConstructor
public class Configuration {
    private Map<Stat, Action> statFormulae;




    private static Configuration parseConfiguration(){
        return null;
    }

    private static String read(String absolutePath) throws IOException {
        return Files.readString(Paths.get(absolutePath));
    }
}
