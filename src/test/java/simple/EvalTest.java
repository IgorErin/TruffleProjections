package simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.nodes.Node;

import java.util.List;

public class EvalTest {
    SimpleFcpParser fcpParser = new SimpleFcpParser();
    Environment env;

    @BeforeEach
    void initEnv() {
        env = new Environment(null);
    }

    /*@Test
    public void defTest() {
        Node defNode = fcpParser.getAstFromFile("src/test/java/defTest.fcp").get(0);

        Assertions.assertEquals((long) 4, defNode.eval(env));
        Assertions.assertEquals((long) 4, env.getValue("name"));
    }

    @Test
    public void ifTest() {
        Node ifNode = fcpParser.getAstFromFile("src/test/java/ifTest.fcp").get(0);

        Assertions.assertEquals(false, ifNode.eval(env));
    }

    @Test
    public void fibTest() {
        List<Node> nodes =  fcpParser.getAstFromFile("src/test/java/fibTest.fcp");
        Object result = null;

        for (Node node : nodes) {
            result = node.eval(env);
        }


        Assertions.assertEquals((Long) result, 10946);
    }

    @Test
    public void plusTest() {
        Node node = fcpParser.getAstFromFile("src/test/java/plusTest.fcp").get(0);

        Assertions.assertEquals((long) 7, node.eval(env));
    }

    @Test
    public void minusTest() {
        Node node = fcpParser.getAstFromFile("src/test/java/minusTest.fcp").get(0);

        Assertions.assertEquals((long) -7, node.eval(env));
    }

    @Test
    public void falseBooleanDefTest() {
        List<Node> nodes = fcpParser.getAstFromFile("src/test/java/falseBooleanDefTest.fcp");
        nodes.get(0).eval(env);

        Assertions.assertEquals( false, nodes.get(1).eval(env));
    }

    @Test
    public void trueBooleanDefTest() {
        List<Node> nodes = fcpParser.getAstFromFile("src/test/java/trueBooleanDefTest.fcp");
        nodes.get(0).eval(env);

        Assertions.assertEquals( true, nodes.get(1).eval(env));
    }*/
}
