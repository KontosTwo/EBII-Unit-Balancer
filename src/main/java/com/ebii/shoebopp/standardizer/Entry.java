package com.ebii.shoebopp.standardizer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ebii.shoebopp.standardizer.Regex.DECIMAL_REGEX;

@RequiredArgsConstructor
public enum Entry {
  MASS("mass", String.format("\\s*soldier\\s*\\w+,\\s*\\d+,\\s*\\d+,\\s*(%s)", DECIMAL_REGEX)),
//    PRIMARY_ATTACK("primaryAttack"),
//    SECONDARY_ATTACK("secondaryAttack"),
//    ARMOR("armor"),
//    DEFENSE_SKILL("defenseSkill"),
//    SHIELD("shield"),
;

  @Getter(AccessLevel.PRIVATE)
  private final String statName;

  @Getter private final String replacementPattern;

  public static final Map<String, Entry> ENTRIES;

  static {
    ENTRIES =
        Arrays.stream(Entry.values())
            .collect(Collectors.toMap(Entry::getStatName, Function.identity()));
  }
}
