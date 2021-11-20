package com.ebii.shoebopp.configuration.parser;

public interface Token {
    void process(ParserContext context);
    int precedence();
    int arity();
}
