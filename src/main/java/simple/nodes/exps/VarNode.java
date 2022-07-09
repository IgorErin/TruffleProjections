package simple.nodes.exps;

import simple.Environment;
import simple.nodes.Node;

import java.util.Objects;

public record VarNode(String name) implements Node {
    @Override
    public Object eval(Environment env) {
        return env.getValue(name);
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof VarNode) {
            return Objects.equals(this.name, ((VarNode) another).name);
        }

        return false;
    }
}
