package simple;

import simple.nodes.Node;

import java.util.List;

public class SimpleFcpMain {
    static public void main(String[] args) {
        SimpleFcpParser newParser = new SimpleFcpParser();

        List<Node> nodeList = newParser.getAst("src/test/java/defTest.fcp");
        Environment env = new Environment();

        try {
            for (Node node : nodeList) {
                node.eval(env);
            }
        } catch (Exception e) {
            System.out.println("Eval exception: " + e.getMessage());
        }
    }


}
