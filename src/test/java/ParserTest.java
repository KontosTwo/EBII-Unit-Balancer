import com.ebii.shoebopp.EduLexer;
import com.ebii.shoebopp.EduParser;
import com.ebii.shoebopp.parser.Parser;
import com.ebii.shoebopp.parser.ParserContext;
import com.ebii.shoebopp.parser.ParserVisitor;
import com.ebii.shoebopp.standardizer.Stats;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;


public final class ParserTest {

    private ParseTree parseTree;

    private ParserContext context;
    private ParserVisitor visitor;


    @Test
    public void testIntegerAssign() throws Exception{
        final var lexerString =
            LexerBuilder.create()
                .assignment("test = 1;")
                .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("test",1f);
    }

    @Test
    public void testDecimalAssign() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("test = 1.2;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("test",1.2f);
    }

    @Test
    public void testDecimalAssignLeadingZero() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("test = 0.2;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("test",0.2f);
    }

    @Test
    public void testDecimalAssignNoLeadingZero() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("test = .2;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("test",0.2f);
    }

    @Test
    public void testMultipleAssign() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = 3;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",3f);
    }

    @Test
    public void testReadVariable() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = testA;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",1f);
    }

    @Test
    public void testReadAndOperateVariable() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = testA + 2;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",3f);
    }

    @Test
    public void testReadAndOperateTwoVariable() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = testA + testB;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",3f);
    }

    @Test
    public void testMultAddPemdas() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = testA + testB * 4;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",9f);
    }

    @Test
    public void testDivSubPemdas() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = testA - testB / 4;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",.5f);
    }

    @Test
    public void testMultDivLeftRight() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = testA * testB / 4;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",.5f);
    }

    @Test
    public void testDivMultLeftRight() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = testA / testB * 4;")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",2f);
    }

    @Test
    public void testParen() throws Exception{
        final var lexerString =
                LexerBuilder.create()
                        .assignment("testA = 1;")
                        .assignment("testB = 2;")
                        .assignment("testC = testA / (testB * 4);")
                        .build();
        setupParser(lexerString);

        executeParser();

        assertAssigned("testA",1f);
        assertAssigned("testB",2f);
        assertAssigned("testC",.125f);
    }

    private void setupParser(String lexerString) throws Exception{
        final var lexer = new EduLexer(CharStreams.fromString(lexerString));
        final var tokens = new CommonTokenStream(lexer);
        final var parser = new EduParser(tokens);
        parseTree = parser.configuration();

        final var stats = Stats.empty();

        context = spy(stats);
        visitor = spy(stats);
    }

    private void executeParser(){
        new ParseTreeWalker().walk(Parser.create(visitor,context),parseTree);
    }

    private void assertAssigned(String variable, float value){
        verify(visitor,times(1)).assign(variable, value);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private final static class LexerBuilder{
        private final StringBuilder builder;

        public static LexerBuilder create(){
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
}
