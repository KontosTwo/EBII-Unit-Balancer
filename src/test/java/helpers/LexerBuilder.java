package helpers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final  class LexerBuilder{
    private final StringBuilder builder;

    public static LexerBuilder builder(){
        return new LexerBuilder(new StringBuilder());
    }

    public LexerBuilder assignment(final String assignment){
        builder.append(assignment);
        return this;
    }

    public String build(){
        return builder.toString();
    }
}