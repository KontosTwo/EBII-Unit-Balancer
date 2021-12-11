package helpers;

import com.ebii.shoebopp.standardizer.Stats;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

@Builder
public class AttributeDefinition {

    @Singular
    private final Map<String, Float> attributes;

    public Stats buildStats(){
        return Stats.withValues(attributes);
    }
}
