package com.ebii.shoebopp;

import com.ebii.shoebopp.standardizer.Standardizer;
import com.ebii.shoebopp.standardizer.StandardizerException;

public class Runner {
    public static void main(String[] args){
        if(args.length < 5){
            System.err.println("There must be 4 commands supplied to the standardizer: the absolute edu input path, the absolute edu output path, the standardizer path, and the attribute definitions path" );
        }

        final var inputPath = args[1];
        final var outputPath = args[2];
        final var standardizerPath = args[3];
        final var attributeDefinitionsPath = args[4];
        try{
            Standardizer.create(inputPath,outputPath,standardizerPath,attributeDefinitionsPath).standardizeEdu();
        }catch(StandardizerException e){
            System.err.println(String.format("Error while standardizing unit stats: %s",e.getMessage()));
        }
    }
}
