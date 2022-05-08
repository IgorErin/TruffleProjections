package truffle.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class Label {
    RootCallTarget callTarget;
    MaterializedFrame lexicalScope; // from example

    public Label(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public MaterializedFrame getLexicalScope() {
        return lexicalScope;
    }

    public void setLexicalScope(MaterializedFrame frame) {
        this.lexicalScope = frame;
    }
}
