package com.ebii.shoebopp.parser;

import com.ebii.shoebopp.EduBaseListener;
import com.ebii.shoebopp.EduParser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Parser extends EduBaseListener {

    private final ParserVisitor visitor;
    private final ParserContext context;

    private String currentAssignmentVar;
    private float currentExpression;
    private float currentMul;
    private float currentAdd;
    private float currentTern;
    private float currentTernIf;
    private float currentTernThen;
    private float currentTernElse;
    private float currentValue;

    public static Parser create(final ParserVisitor visitor, final ParserContext context){
        return new Parser(visitor, context);
    }

    @Override
    public void exitAssignmentVar(EduParser.AssignmentVarContext ctx) {
        currentAssignmentVar = ctx.StringLiteral().getText();
    }

    @Override
    public void exitAssignment(EduParser.AssignmentContext ctx) {
        visitor.assign(currentAssignmentVar, currentExpression);
        context.setContext(currentAssignmentVar, currentExpression);
    }

    @Override
    public void exitTernExpression(EduParser.TernExpressionContext ctx){
        if(currentTernIf != 0){
            currentTern = currentTernThen;
        }else{
            currentTern = currentTernElse;
        }
    }

    @Override
    public void exitTernIf(EduParser.TernIfContext ctx){
        currentTernIf = currentAdd;
    }

    @Override
    public void exitTernThen(EduParser.TernThenContext ctx){
        currentTernThen = currentAdd;
    }

    @Override
    public void exitTernElse(EduParser.TernElseContext ctx){
        currentTernElse = currentAdd;
    }


    @Override
    public void exitMultiplicativeExpression(EduParser.MultiplicativeExpressionContext ctx){
        if(ctx.getChildCount() == 1){
            currentMul = currentValue;
        }else if(ctx.getChild(1).getText().equals("MUL")){
            currentMul = currentMul * currentValue;
        }else{
            currentMul = currentMul / currentValue;
        }
    }

    @Override
    public void exitAdditiveExpression(EduParser.AdditiveExpressionContext ctx){
        if(ctx.getChildCount() == 1){
            currentAdd = currentMul;
        }else if(ctx.getChild(1).getText().equals("ADD")){
            currentAdd = currentAdd + currentMul;
        }else{
            currentAdd = currentAdd - currentMul;
        }
    }

    @Override
    public void exitExpression(EduParser.ExpressionContext ctx) {
        if(ctx.additiveExpression() != null){
            currentExpression = currentAdd;
        }else{
            currentExpression = currentTern;
        }
    }

    @Override
    public void exitValue(EduParser.ValueContext ctx) {
        if(ctx.var() != null){
            currentValue = context.getContext(ctx.var().StringLiteral().getText());
        }else if(ctx.number() != null){
            currentValue = Float.parseFloat(ctx.number().DecimalLiteral().getText());
        }else{
            currentValue = currentExpression;
        }
    }
}
