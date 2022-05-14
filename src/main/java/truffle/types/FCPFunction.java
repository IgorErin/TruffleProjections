package truffle.types;

import com.oracle.truffle.api.RootCallTarget;

public class FCPFunction {
    RootCallTarget callTarget;

    public FCPFunction(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }
}
