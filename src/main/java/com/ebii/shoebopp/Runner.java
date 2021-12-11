package com.ebii.shoebopp;

import com.ebii.shoebopp.parser.Parser;
import com.ebii.shoebopp.standardizer.Entry;
import com.ebii.shoebopp.standardizer.Standardizer;
import com.ebii.shoebopp.standardizer.StandardizerException;
import com.ebii.shoebopp.standardizer.Stats;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Runner {

  private static final String EDU_ENTRY_REGEX = "^type.*recruit_priority_offset\\s*\\d+$";
  private static final Pattern EDU_ENTRY_PATTERN = Pattern.compile(EDU_ENTRY_REGEX);

  public static void main(String[] args) {
    if (args.length < 5) {
      System.err.println(
          "There must be 4 commands supplied to the standardizer: the absolute edu input path, the absolute edu output path, the standardizer path, and the attribute definitions path");
    }

    final var inputPath = args[1];
    final var outputPath = args[2];
    final var standardizerPath = args[3];
    final var attributeDefinitionsPath = args[4];
    try {
      var eduString = (String) null;
      try {
        eduString = read(inputPath);
      } catch (IOException e) {
        throw new StandardizerException("Could not read from the EDU input file");
      }

      var attributeDefinitionsString = (String) null;
      try {
        attributeDefinitionsString = read(attributeDefinitionsPath);
      } catch (IOException e) {
        throw new StandardizerException("Could not read from the Attribute Definitions file");
      }

      var standardizerString = (String) null;
      try {
        standardizerString = read(standardizerPath);
      } catch (IOException e) {
        throw new StandardizerException("Could not read from the Standardizer file");
      }

      final var attributeDefinitions = createAttributeDefinitions(attributeDefinitionsString);
      final var standardizerTree = createParseTree(standardizerString);
      final var eduEntryMatcher = EDU_ENTRY_PATTERN.matcher(eduString);
      final var eduEntryModifier = new StringBuilder(eduString);

      final var standardizer =
          Standardizer.create(standardizerTree, attributeDefinitions, Entry.ENTRIES);

      while (eduEntryMatcher.find()) {
        final var oldEduEntry = eduEntryMatcher.group();
        final var newEduEntry = standardizer.standardizeEduEntry(oldEduEntry);

        final var eduEntryStart = eduEntryMatcher.start();
        final var eduEntryEnd = eduEntryMatcher.end();
        eduEntryModifier.replace(eduEntryStart, eduEntryEnd, newEduEntry);
      }
      final var modifiedEdu = eduEntryModifier.toString();

      try {
        write(outputPath, modifiedEdu);
      } catch (IOException e) {
        throw new StandardizerException("Could not write to the EDU output file");
      }

    } catch (StandardizerException e) {
      System.err.println(String.format("Error while standardizing unit stats: %s", e.getMessage()));
    }
  }

  private static Stats createAttributeDefinitions(final String attributeDefinitionsString) {
    final var walker = new ParseTreeWalker();
    final var attributeDefinitions = Stats.empty();
    walker.walk(
        Parser.create(attributeDefinitions, attributeDefinitions),
        createParseTree(attributeDefinitionsString));
    return attributeDefinitions;
  }

  private static ParseTree createParseTree(String parseString) {
    final var lexer = new EduLexer(CharStreams.fromString(parseString));
    final var tokens = new CommonTokenStream(lexer);
    final var parser = new EduParser(tokens);
    return parser.getContext();
  }

  private static String read(String path) throws IOException {
    return Files.readString(Paths.get(path));
  }

  private static void write(String path, String data) throws IOException {
    Files.write(Paths.get(path), data.getBytes());
  }
}
