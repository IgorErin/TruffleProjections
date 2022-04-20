import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

abstract public class myNode extends Node {
    abstract static class Exp extends myNode {
        abstract int execute(int[] args);
    }

    static class Add extends Exp {
        @Child Exp right;
        @Child Exp left;

        public Add(Exp right, Exp left) {
            this.right = right;
            this.left = left;
        }

        @Override
        int execute(int[] args) {
            return right.execute(args) + left.execute(args);
        }
    }

    static class Arg extends Exp {
        int index;

        public Arg(int index) {
            this.index = index;
        }

        @Override
        int execute(int[] args) {
            return args[index];
        }
    }

    static class Fun extends RootNode {
        @Child Exp body;

        public Fun(Exp body) {
            super(null);
            this.body = body;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return body.execute((int[]) frame.getArguments()[0]);
        }
    }

    public static void main(String[] args) {
        Fun sample = new Fun(new Add(new Add(new Add(new Arg(2), new Arg(0)), new Arg(1)), new Arg(2)));
        CallTarget target = Truffle.getRuntime().createCallTarget(sample);


        long start = System.nanoTime();
        target.call(new int[] {1, 2, 3});
        long elapsedTime = System.nanoTime() - start;

        System.out.println(elapsedTime);

        for (int i = 0; i < 1000; i++) {
            target.call(new int[] {1, 2, 3});
        }

        start = System.nanoTime();
        target.call(new int[] {1, 2, 3});
        elapsedTime = System.nanoTime() - start;

        System.out.println(elapsedTime);
    }
}



