package truffle.nodes.builtin;

public abstract class PrintNode extends UnNode {
    protected Object printObject(Object value) {
        System.out.println("Printed in truffle lang: " + value);
        return value;
    }
}
