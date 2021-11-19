// Generated from com/ebii/shoebopp/Edu.g4 by ANTLR 4.7.1
package com.ebii.shoebopp;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EduParser}.
 */
public interface EduListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EduParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(EduParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(EduParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#configuration}.
	 * @param ctx the parse tree
	 */
	void enterConfiguration(EduParser.ConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#configuration}.
	 * @param ctx the parse tree
	 */
	void exitConfiguration(EduParser.ConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(EduParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(EduParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(EduParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(EduParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(EduParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(EduParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(EduParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(EduParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#ternExpression}.
	 * @param ctx the parse tree
	 */
	void enterTernExpression(EduParser.TernExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#ternExpression}.
	 * @param ctx the parse tree
	 */
	void exitTernExpression(EduParser.TernExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#subtractiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractiveExpression(EduParser.SubtractiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#subtractiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractiveExpression(EduParser.SubtractiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(EduParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(EduParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#divisiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterDivisiveExpression(EduParser.DivisiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#divisiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitDivisiveExpression(EduParser.DivisiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(EduParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(EduParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EduParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(EduParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link EduParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(EduParser.ValueContext ctx);
}