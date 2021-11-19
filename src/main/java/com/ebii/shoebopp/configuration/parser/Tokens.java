package com.ebii.shoebopp.configuration.parser;

import org.scijava.parsington.Operator;
import org.scijava.parsington.Token;
import org.scijava.parsington.Variable;

import java.util.List;

public class Tokens {

    public static Operator write(){
        return new Operator(">",1, Operator.Associativity.NONE,0);
    }

    public static Operator multiply(){
        return new Operator("*",2, Operator.Associativity.RIGHT,1);
    }

    public static Operator divide(){
        return new Operator("/",2, Operator.Associativity.RIGHT,2);
    }
    public static Operator add(){
        return new Operator("+",2, Operator.Associativity.RIGHT,3);
    }

    public static Operator subtract(){
        return new Operator("-",2, Operator.Associativity.RIGHT,4);
    }

    public static Operator read(){
        return new Operator("*",1, Operator.Associativity.NONE,5);
    }

    public static Operator tern(){
        return new Operator("?",3, Operator.Associativity.NONE,6);
    }

    public static List<Operator> operators(){
        return List.of(write(), multiply(),divide(),add(),subtract(),read(),tern());
    }

    public static Token variable(String value){
        return new Variable(value);
    }

    public static Number value(double value){
        return value;
    }

    public static String value(String value){
        return value;
    }
}
