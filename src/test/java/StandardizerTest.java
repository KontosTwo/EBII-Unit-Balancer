import com.ebii.shoebopp.EduLexer;
import com.ebii.shoebopp.EduParser;
import com.ebii.shoebopp.standardizer.Entry;
import com.ebii.shoebopp.standardizer.Standardizer;
import com.ebii.shoebopp.standardizer.Stats;
import helpers.AttributeDefinition;
import helpers.EduEntry;
import helpers.LexerBuilder;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;

public class StandardizerTest {
    private ParseTree standardizerTree;
    private Stats attributeDefinitions;
    private Map<String, Entry> entryReplacer;

    private Standardizer standardizer;

    @Test
    public void standardizeMass() throws Exception{
        setupDefaultStandardizer();

        final var eduEntry = EduEntry.builder()
                .line(eduAttributes("hasLongSword","hasChainmail"))
                .line("soldier                 Argoi_Early, 48, 0, 1.2, 0.25")
                .build().toEntry();

        final var expectedEduEntry = EduEntry.builder()
                .line(eduAttributes("hasLongSword","hasChainmail"))
                .line("soldier                 Argoi_Early, 48, 0, 4.0, 0.25")
                .build().toEntry();

        final var newEduEntry = standardizer.standardizeEduEntry(eduEntry);
        assertThat(newEduEntry,equalTo(expectedEduEntry));
    }

    private void setupDefaultStandardizer(){
        final var lexerBuilder = LexerBuilder.builder()
                .assignment("mass = (hasFalcata * longswordMultiplier) + (hasChainmail * chainmailMultiplier);")
                .assignment("unused = 3;");
        final var attributeDefinitionBuilder = AttributeDefinition.builder()
                .attribute("chainmailMultiplier",4f)
                .attribute("longswordMultiplier",5f);
        setupStandardizer(lexerBuilder,attributeDefinitionBuilder);
    }

    private void setupStandardizer(final LexerBuilder lexerBuilder,final AttributeDefinition.AttributeDefinitionBuilder attributeDefinition){
        setupStandardizerTree(lexerBuilder);
        setupAttributeDefinitionsTree(attributeDefinition);
        standardizer = Standardizer.create(standardizerTree,attributeDefinitions, Entry.ENTRIES);
    }

    private void setupStandardizerTree(final LexerBuilder builder){
        final var lexer = new EduLexer(CharStreams.fromString(builder.build()));
        final var tokens = new CommonTokenStream(lexer);
        final var parser = new EduParser(tokens);
        standardizerTree = spy(parser.configuration());
    }

    private void setupAttributeDefinitionsTree(final AttributeDefinition.AttributeDefinitionBuilder builder){
        attributeDefinitions = builder.build().buildStats();
    }

    private String eduAttributes(String... attributes){
        return "attributes " + String.join(",",attributes);
    }
}
