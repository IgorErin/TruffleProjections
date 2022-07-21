package simple;

import simple.nodes.Node;

import java.util.List;

public class SimpleFcpMain {
    static public void main(String[] args) {
        SimpleFcpParser newParser = new SimpleFcpParser();

        List<Node> nodeList = newParser.getAstFromFile("src/main/program.fcp");
        Environment env = new Environment();

        for (int i = 0; i < 20; i++) {
            try {
                for (Node node : nodeList) {
                    node.eval(env);
                }
            } catch (Exception e) {
                System.out.println("Eval exception: " + e.getMessage());
            }
        }
    }
}
