package com.ebii.shoebopp.standardizer;

import com.ebii.shoebopp.EduLexer;
import com.ebii.shoebopp.EduParser;
import com.ebii.shoebopp.parser.Parser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Standardizer {

    private final String standardizerPath;
    private final String attributeDefinitionsPath;
    private final String eduPath;
    private final String outputPath;

    private static final float NONEXISTENT = 0f;
    private static final float EXISTS = 1f;

    private static final String EDU_ENTRY_REGEX = "^type.*recruit_priority_offset\\s*\\d+$";
    private static final Pattern EDU_ENTRY_PATTERN= Pattern.compile(EDU_ENTRY_REGEX);
    private static final String EDU_ENTRY_ATTRIBUTES_REGEX = "^attributes\\s*(\\w+)?(\\s*,\\s*(\\w+))*$";
    private static final Pattern EDU_ENTRY_ATTRIBUTES_PATTERN = Pattern.compile(EDU_ENTRY_ATTRIBUTES_REGEX);

    public static Standardizer create(final  String eduPath, final String outputPath, final String standardizerPath,final  String attributeDefinitionsPath){
        return new Standardizer(standardizerPath, attributeDefinitionsPath, eduPath, outputPath);
    }


    public void standardizeEdu() throws StandardizerException{
        var eduString = (String)null;
        try{
            eduString = read(eduPath);
        } catch (IOException e){
            throw new StandardizerException("Could not read from the EDU input file");
        }

        var attributeDefinitionsString = (String)null;
        try{
            attributeDefinitionsString = read(attributeDefinitionsPath);
        } catch (IOException e){
            throw new StandardizerException("Could not read from the Attribute Definitions file");
        }

        var standardizerString = (String)null;
        try{
            standardizerString = read(standardizerPath);
        } catch (IOException e){
            throw new StandardizerException("Could not read from the Standardizer file");
        }


        final var attributeDefinitions = createAttributeDefinitions(attributeDefinitionsString);
        final var standardizer = createParseTree(standardizerString);
        final var eduEntryMatcher = EDU_ENTRY_PATTERN.matcher(eduString);
        final var eduEntryModifier = new StringBuilder(eduString);
        while(eduEntryMatcher.find()){
            final var oldEduEntry = eduEntryMatcher.group();
            final var newEduEntry = standardizeEduEntry(oldEduEntry, standardizer,attributeDefinitions);

            final var eduEntryStart = eduEntryMatcher.start();
            final var eduEntryEnd = eduEntryMatcher.end();
            eduEntryModifier.replace(eduEntryStart,eduEntryEnd,newEduEntry);
        }
        final var modifiedEdu = eduEntryModifier.toString();

        try{
            write(outputPath, modifiedEdu);
        }catch (IOException e){
            throw new StandardizerException("Could not write to the EDU output file");
        }
    }

    private static String standardizeEduEntry(final String eduEntry, final ParseTree standardizer, final Stats attributeDefinitions) throws StandardizerException {
        final var stats = Stats.empty();
        final var attributesFromEdu = getAttributesFromEdu(eduEntry);
        stats.addAll(attributesFromEdu);
        stats.addAll(attributeDefinitions);

        final var processor = new ParseTreeWalker();
        processor.walk(Parser.create(stats, stats), standardizer);

        String modifiedEduEntry = eduEntry;
        for(final var stat : stats.getValues().entrySet()){
            final var statName = stat.getKey();
            final var statValue = stat.getValue();
            final var replacer = Entry.ENTRIES.get(statName);
            modifiedEduEntry = replacer.replace(modifiedEduEntry, statValue);
        };

        return modifiedEduEntry;
    }

    private static Stats getAttributesFromEdu(final String eduEntry) throws StandardizerException{
        final var attributesMatcher = EDU_ENTRY_ATTRIBUTES_PATTERN.matcher(eduEntry);
        if(attributesMatcher.find()){
            final var attributesLine = attributesMatcher.group();
            final var attributeMatcher = Pattern.compile("\\w+").matcher(attributesLine);
            final var attributes = allMatches(attributeMatcher);
            final var attributesValues =  attributes.stream().collect(Collectors.toMap(Function.identity(), s -> EXISTS));
            return Stats.withValues(attributesValues);
        }else{
            throw new StandardizerException(String.format("This EDU entry does not have an attribute line: %s", eduEntry));
        }
    }

    private static Stats createAttributeDefinitions(final String attributeDefinitionsString){
        final var walker = new ParseTreeWalker();
        final var attributeDefinitions = Stats.empty();
        walker.walk(Parser.create(attributeDefinitions,attributeDefinitions), createParseTree(attributeDefinitionsString));
        return attributeDefinitions;
    }

    private static String read(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    private static void write(String path, String data) throws IOException{
        Files.write(Paths.get(path),data.getBytes());
    }

    private static ParseTree createParseTree(String parseString){
        final var lexer = new EduLexer(CharStreams.fromString(parseString));
        final var tokens = new CommonTokenStream(lexer);
        final var parser = new EduParser(tokens);
        return parser.getContext();
    }

    private static List<String> allMatches(final Matcher m){
        var matches = new ArrayList<String>();
        while(m.find()){
            matches.add(m.group());
        }
        return matches;
    }
}
