package simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import simple.nodes.Node;
import simple.nodes.exps.BooleanNode;
import simple.nodes.exps.IntNode;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;
import simple.nodes.stmts.SimpleFcpBuiltin;
import simple.nodes.stmts.SimpleFcpStatement;

class SimpleFcpParserTest {
    SimpleFcpParser fcpParser = new SimpleFcpParser();

    @Test
    public void defTest() {
        Node defNode = fcpParser.getAstFromFile("src/test/java/defTest.fcp").get(0);
        Node correctDefNode = SimpleFcpStatement.getDefNode(new VarNode("name"), new IntNode(4));

        Assertions.assertEquals(correctDefNode, defNode);
    }

    @Test
    public void ifTest() {
        Node ifNode = fcpParser.getAstFromFile("src/test/java/ifTest.fcp").get(0);
        Node correctIfNode = SimpleFcpStatement.getIfNode(
                SimpleFcpBuiltin.getBinOpNode("<", new IntNode(4), new IntNode(3)),
                new BooleanNode(true),
                new BooleanNode(false)
        );

        Assertions.assertEquals(ifNode, correctIfNode);
    }

    @Test
    public void lambdaTest() {
        Node lambdaNode = fcpParser.getAstFromFile("src/test/java/lambdaTest.fcp").get(0);
        Node correctLambdaNode = SimpleFcpStatement.getLambdaNode(
                new ListNode(new VarNode("x"), new VarNode("y")),
                SimpleFcpBuiltin.getBinOpNode("+", new VarNode("x"), new VarNode("y"))
        );

        Assertions.assertEquals(lambdaNode, correctLambdaNode);
    }

    @Test
    public void plusTest() {
        Node plusNode = fcpParser.getAstFromFile("src/test/java/plusTest.fcp").get(0);
        Node correctPlusNode = SimpleFcpBuiltin.getBinOpNode(
                "+",
                new IntNode(3),
                new IntNode(4)
        );

        Assertions.assertEquals(correctPlusNode, plusNode);
    }

    @Test
    public void printTest() {
        Node defNode = fcpParser.getAstFromFile("src/test/java/printTest.fcp").get(0);
        Node correctDefNode = new ListNode(new VarNode("print"), new IntNode(4));

        Assertions.assertEquals(defNode, correctDefNode);
    }
}