package simple;

import simple.nodes.stmts.BuiltinFun;

import java.util.HashMap;

public class Environment {
    Environment paren;
    private final HashMap<String, Object> env = new HashMap<String, Object>();

    public Environment() {
        this(null);
    }

    public Environment(Environment paren) {
        this.paren = paren;
        env.put("+", BuiltinFun.plusFun);
        env.put("-", BuiltinFun.minusFun);
    }

    public Object getValue(String name) {
        Object value = env.get(name);

        if (value != null) {
            return value;
        }

        if (paren != null) {
            return paren.getValue(name);
        }

        throw new RuntimeException("no mapped values for " + name);
    }

    public void putValue(String name, Object value) {
        env.put(name, value);
    }
}
