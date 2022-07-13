package truffle.parser;

import java.util.HashMap;
import java.util.Map;

public class LexicalScope {
    public final Map<String, Integer> locals = new HashMap<String, Integer>();
    private final LexicalScope outer;

    public LexicalScope(LexicalScope outer) {
        this.outer = outer;
    }

    public Integer find(String name) {
        final Integer result = locals.get(name);

        if (result != null) {
            return result;
        } else if (outer != null) {
            return outer.find(name);
        } else {
            return null;
        }
    }
}
