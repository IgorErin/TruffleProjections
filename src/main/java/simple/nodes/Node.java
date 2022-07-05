package simple.nodes;

import simple.Environment;

import java.util.Iterator;

public interface Node {
    public Object eval(Environment env);
}
