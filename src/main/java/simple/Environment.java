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
        env.put("print", BuiltinFun.printFun);
        env.put(">", BuiltinFun.greaterFun);
        env.put("<", BuiltinFun.lessFun);
        env.put(">=", BuiltinFun.greaterOEFun);
        env.put("<=", BuiltinFun.lessOEFun);
        env.put("=", BuiltinFun.equalFun);
        env.put("println", BuiltinFun.printlnFun);
        env.put("now", BuiltinFun.timeFun);
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
