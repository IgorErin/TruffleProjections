package simple.nodes;

import simple.Environment;

public interface Node {
    Object eval(Environment env);
}
