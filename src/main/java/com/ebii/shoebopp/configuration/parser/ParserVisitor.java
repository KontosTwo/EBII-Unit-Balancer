package com.ebii.shoebopp.configuration.parser;


import org.scijava.parsington.SyntaxTree;

import java.util.List;

public interface ParserVisitor {
    void beginAssignment();
    void endAssignment();
    void processVariable(String variable);
    void processValueNumber(double value);
    void processValueVariable(String value);
    void processMultiply();
    void processDivide();
    void processAdd();
    void processSubtract();
    void processTern();

    List<SyntaxTree> getStatFormulae();
}
