package truffle.nodes.builtin;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class PrintNode extends UnNode {
    @Specialization
    protected Object printObject(Object value) {
        System.out.println("Printed in truffle lang: " + value);
        return value;
    }
}
