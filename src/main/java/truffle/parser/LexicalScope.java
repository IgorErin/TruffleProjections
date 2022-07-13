package truffle.parser;

import java.util.HashMap;
import java.util.Map;

public class LexicalScope {
    private final Map<String, Integer> scope = new HashMap<String, Integer>();
    private LexicalScope outer;

    public LexicalScope(LexicalScope outer) {
        this.outer = outer;
    }

    Integer find(String name) {
        final Integer result = scope.get(name);

        if (result != null) {
            return result;
        } else if (outer != null) {
            return outer.find(name);
        } else {
            return null;
        }
    }

    Integer add(String name, int slot) {
        return scope.put(name, slot);
    }
}
