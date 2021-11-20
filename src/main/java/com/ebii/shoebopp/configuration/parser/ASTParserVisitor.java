package com.ebii.shoebopp.configuration.parser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.scijava.parsington.SyntaxTree;
import org.unlaxer.Parsed;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class ASTParserVisitor implements ParserVisitor{

    private List<SyntaxTree> allStatFormulae;

    private List<Token> currentTokens;

    @Override
    public void beginAssignment() {
        currentTokens.clear();
    }

    @Override
    public void endAssignment() {
        allStatFormulae.add(new SyntaxTree(currentTokens));
    }

    @Override
    public void processVariable(String variable) {
        currentTokens.add(Tokens.write());
        currentTokens.add(Tokens.value(variable));
    }

    @Override
    public void processValueNumber(double value) {
        currentTokens.add(Tokens.value(value));
    }

    @Override
    public void processValueVariable(String value) {
        currentTokens.add(Tokens.read());
        currentTokens.add(Tokens.value(value));
    }

    @Override
    public void processMultiply() {
        currentTokens.add(Tokens.multiply());
    }

    @Override
    public void processDivide() {
        currentTokens.add(Tokens.divide());

    }

    @Override
    public void processAdd() {
        currentTokens.add(Tokens.add());
    }

    @Override
    public void processSubtract() {
        currentTokens.add(Tokens.subtract());
    }

    @Override
    public void processTern() {
        currentTokens.add(Tokens.tern());
    }

    @Override
    public List<SyntaxTree> getStatFormulae() {
        return allStatFormulae;
    }

    public static ParserVisitor create(){
        new Parsed()
        return new ASTParserVisitor(new ArrayList<>(),new LinkedList<>());
    }
}
