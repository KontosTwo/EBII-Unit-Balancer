package helpers;

import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public class EduEntry {
    @Singular
    private final List<String> lines;

    public String toEntry(){
        return String.join(System.lineSeparator(),lines);
    }
}
