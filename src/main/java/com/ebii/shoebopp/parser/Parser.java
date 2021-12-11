package com.ebii.shoebopp.parser;

import com.ebii.shoebopp.EduBaseListener;
import com.ebii.shoebopp.EduParser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Stack;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Parser extends EduBaseListener {

  private final ParserVisitor visitor;
  private final ParserContext context;

  private String currentAssignmentVar;
  private final Stack<Float> currentExpression;
  private final Stack<Float> currentMul;
  private final Stack<Float> currentAdd;
  private final Stack<Float> currentTern;
  private final Stack<Float> currentTernIf;
  private final Stack<Float> currentTernThen;
  private final Stack<Float> currentTernElse;
  private final Stack<Float> currentValue;

  public static Parser create(final ParserVisitor visitor, final ParserContext context) {
    return new Parser(
        visitor,
        context,
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>());
  }

  @Override
  public void exitAssignmentVar(EduParser.AssignmentVarContext ctx) {
    currentAssignmentVar = ctx.StringLiteral().getText();
  }

  @Override
  public void exitAssignment(EduParser.AssignmentContext ctx) {
    final var expressionValue = currentExpression.pop();
    visitor.assign(currentAssignmentVar, expressionValue);
    context.setContext(currentAssignmentVar, expressionValue);
  }

  @Override
  public void exitTernExpression(EduParser.TernExpressionContext ctx) {
    if (currentTernIf.pop() != 0) {
      currentTern.push(currentTernThen.pop());
    } else {
      currentTern.push(currentTernElse.pop());
    }
  }

  @Override
  public void exitTernIf(EduParser.TernIfContext ctx) {
    currentTernIf.push(currentAdd.pop());
  }

  @Override
  public void exitTernThen(EduParser.TernThenContext ctx) {
    currentTernThen.push(currentAdd.pop());
  }

  @Override
  public void exitTernElse(EduParser.TernElseContext ctx) {
    currentTernElse.push(currentAdd.pop());
  }

  @Override
  public void exitMultiplicativeExpression(EduParser.MultiplicativeExpressionContext ctx) {
    if (ctx.getChildCount() == 1) {
      currentMul.push(currentValue.pop());
    } else if (ctx.MUL() != null) {
      currentMul.push(currentMul.pop() * currentValue.pop());
    } else if (ctx.DIV() != null) {
      currentMul.push(currentMul.pop() / currentValue.pop());
    }
  }

  @Override
  public void exitAdditiveExpression(EduParser.AdditiveExpressionContext ctx) {
    if (ctx.getChildCount() == 1) {
      currentAdd.push(currentMul.pop());
    } else if (ctx.ADD() != null) {
      currentAdd.push(currentAdd.pop() + currentMul.pop());
    } else if (ctx.SUB() != null) {
      currentAdd.push(currentAdd.pop() - currentMul.pop());
    }
  }

  @Override
  public void exitExpression(EduParser.ExpressionContext ctx) {
    if (ctx.additiveExpression() != null) {
      currentExpression.push(currentAdd.pop());
    } else {
      currentExpression.push(currentTern.pop());
    }
  }

  @Override
  public void exitValue(EduParser.ValueContext ctx) {
    if (ctx.var() != null) {
      currentValue.push(context.getContext(ctx.var().StringLiteral().getText()));
    } else if (ctx.number() != null) {
      currentValue.push(Float.parseFloat(ctx.number().DecimalLiteral().getText()));
    } else if (ctx.expression() != null) {
      currentValue.push(currentExpression.pop());
    }
  }
}
