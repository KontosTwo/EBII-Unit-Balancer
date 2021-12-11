package com.ebii.shoebopp.standardizer;

import com.ebii.shoebopp.parser.Parser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Standardizer {

  private final ParseTree standardizer;
  private final Stats attributeDefinitions;
  private final Map<String, Entry> entryReplacer;

  private static final float EXISTS = 1f;

  private static final String EDU_ENTRY_ATTRIBUTES_REGEX =
      "\\s*attributes\\s+.+(,\\s*\\w+\\s*)*\\s*";
  private static final Pattern EDU_ENTRY_ATTRIBUTES_PATTERN =
      Pattern.compile(EDU_ENTRY_ATTRIBUTES_REGEX);

  public static Standardizer create(
      final ParseTree standardizer,
      final Stats attributeDefinitions,
      Map<String, Entry> entryReplacer) {
    return new Standardizer(standardizer, attributeDefinitions, entryReplacer);
  }

  public String standardizeEduEntry(final String eduEntry) throws StandardizerException {
    final var stats = Stats.empty();
    final var attributesFromEdu = getAttributesFromEdu(eduEntry);
    stats.addAll(attributesFromEdu);
    stats.addAll(attributeDefinitions);

    final var processor = new ParseTreeWalker();
    processor.walk(Parser.create(stats, stats), standardizer);

    String modifiedEduEntry = eduEntry;
    for (final var stat : stats.getValues().entrySet()) {
      final var statName = stat.getKey();
      final var statValue = stat.getValue();
      if (entryReplacer.containsKey(statName)) {
        final var replacer = entryReplacer.get(statName);
        final var replacementPattern = replacer.getReplacementPattern();
        final var replacementMatcher =
            Pattern.compile(replacementPattern).matcher(modifiedEduEntry);

        final var modifiedEduEntryBuffer = new StringBuffer();
        if (replacementMatcher.find()) {
          replacementMatcher.appendReplacement(
              modifiedEduEntryBuffer,
              replacementMatcher
                  .group(0)
                  .replaceFirst(
                      Pattern.quote(replacementMatcher.group(1)), String.valueOf(statValue)));
          replacementMatcher.appendTail(modifiedEduEntryBuffer);
          modifiedEduEntry = modifiedEduEntryBuffer.toString();
        } else {
          throw new StandardizerException(
              String.format(
                  "This replacement string did not match anything: %s%s",
                  System.lineSeparator(), replacementPattern));
        }
      }
    }

    return modifiedEduEntry;
  }

  private static Stats getAttributesFromEdu(final String eduEntry) throws StandardizerException {
    final var attributesMatcher = EDU_ENTRY_ATTRIBUTES_PATTERN.matcher(eduEntry);
    if (attributesMatcher.find()) {
      final var attributesLine = attributesMatcher.group().replace("attributes", "");
      final var attributeMatcher = Pattern.compile("\\w+").matcher(attributesLine);
      final var attributes = allMatches(attributeMatcher);
      final var attributesValues =
          attributes.stream().collect(Collectors.toMap(Function.identity(), s -> EXISTS));
      return Stats.withValues(attributesValues);
    } else {
      throw new StandardizerException(
          String.format(
              "This EDU entry does not have an attribute line: %s%s",
              System.lineSeparator(), eduEntry));
    }
  }

  private static List<String> allMatches(final Matcher m) {
    var matches = new ArrayList<String>();
    while (m.find()) {
      matches.add(m.group());
    }
    return matches;
  }
}
