package com.ebii.shoebopp.configuration.parser;

public interface ParserContext {
    float valueOf(String stat);
    void set(String stat, float value);
}
