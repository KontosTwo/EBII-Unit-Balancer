package com.ebii.shoebopp.configuration.lexer;

import com.ebii.shoebopp.EduBaseListener;
import com.ebii.shoebopp.EduParser;
import com.ebii.shoebopp.configuration.parser.ParserVisitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LexerListener extends EduBaseListener {

    private ParserVisitor parser;


    @Override
    public void enterAssignment(EduParser.AssignmentContext ctx) {
        parser.beginAssignment();
        parser.processVariable(ctx.var().StringLiteral().getText());
    }

    @Override
    public void enterExpression(EduParser.ExpressionContext ctx) {

    }

    @Override
    public void enterTernExpression(EduParser.TernExpressionContext ctx) {
        parser.processTern();
    }


    @Override
    public void enterMultiplicativeExpression(EduParser.MultiplicativeExpressionContext ctx) {
        parser.processMultiply();
    }

    @Override
    public void enterDivisiveExpression(EduParser.DivisiveExpressionContext ctx) {
        parser.processDivide();
    }


    @Override
    public void enterAdditiveExpression(EduParser.AdditiveExpressionContext ctx) {
        parser.processAdd();
    }

    @Override
    public void enterSubtractiveExpression(EduParser.SubtractiveExpressionContext ctx) {
        parser.processSubtract();
    }


    @Override
    public void enterVar(EduParser.VarContext ctx) {
        parser.processValueVariable(ctx.StringLiteral().getText());
    }

    @Override
    public void enterNumber(EduParser.NumberContext ctx) {
        parser.processValueNumber(Double.parseDouble(ctx.DecimalLiteral().getText()));
    }


    @Override
    public void exitAssignment(EduParser.AssignmentContext ctx) {
        parser.endAssignment();
    }
}
