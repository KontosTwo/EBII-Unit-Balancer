package com.ebii.shoebopp.standardizer;

import com.ebii.shoebopp.parser.ParserContext;
import com.ebii.shoebopp.parser.ParserException;
import com.ebii.shoebopp.parser.ParserVisitor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Stats implements ParserVisitor, ParserContext {
    @Getter
    private final Map<String, Float> values;

    public static Stats empty(){
        return new Stats(new HashMap<>());
    }

    public static Stats withValues(final Map<String, Float> context){
        return new Stats(context);
    }

    public void add(String stat, float value){values.put(stat,value);}

    public void addAll(Stats other){
        this.values.putAll(other.values);
    }

    @Override
    public void assign(String stat, float value) {
        values.put(stat,value);
    }

    @Override
    public float getContext(String stat) {
        if(values.get(stat) != null){
            return values.get(stat);
        }else{
            throw new ParserException(String.format("'%s' has not been defined yet",stat));
        }
    }

    @Override
    public void setContext(String stat, float value) {
        values.put(stat, value);
    }
}
