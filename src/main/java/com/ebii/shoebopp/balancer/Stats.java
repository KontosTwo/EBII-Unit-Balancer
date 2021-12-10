package com.ebii.shoebopp.balancer;

import com.ebii.shoebopp.configuration.parser.ParserContext;
import com.ebii.shoebopp.configuration.parser.ParserException;
import com.ebii.shoebopp.configuration.parser.ParserVisitor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Stats implements ParserVisitor, ParserContext {
    private final Map<String, Float> context;
    @Getter
    private final Map<String, Float> values;

    public static Stats empty(){
        return new Stats(new HashMap<>(),new HashMap<>());
    }

    public static Stats withContext(final Map<String, Float> context){
        return new Stats(context, new HashMap<>());
    }

    @Override
    public void assign(String stat, float value) {
        values.put(stat,value);
    }

    @Override
    public float valueOf(String stat) {
        if(context.get(stat) != null){
            return context.get(stat);
        }else if(values.get(stat) != null){
            return values.get(context);
        }else{
            throw new ParserException(String.format("'%s' has not been defined yet",stat));
        }
    }

    @Override
    public void set(String stat, float value) {
        values.put(stat, value);
    }
}
