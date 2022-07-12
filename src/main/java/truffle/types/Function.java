package truffle.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class Function {
    public final RootCallTarget callTarget;
    private MaterializedFrame scope;

    public Function(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public void setScope(MaterializedFrame scope) {
        this.scope = scope;
    }

    public MaterializedFrame getScope() {
        return this.scope;
    }
}
