package truffle.types;

import com.oracle.truffle.api.CallTarget;

public class FCPFunction{
    public CallTarget callTarget;

    public FCPFunction(CallTarget callTarget) {
        this.callTarget = callTarget;
    }
}
