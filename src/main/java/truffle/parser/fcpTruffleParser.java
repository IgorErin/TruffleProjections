package truffle.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import simple.nodes.Node;
import simple.nodes.exps.BooleanNode;
import simple.nodes.exps.IntNode;
import simple.nodes.exps.ListNode;
import simple.nodes.exps.VarNode;

import java.util.LinkedList;
import java.util.Stack;

public class fcpTruffleParser extends fcpBaseListener {
    private final Stack<LinkedList<Node>> nodeListStack = new Stack<LinkedList<Node>>();

    public Node getRootListNode() throws RuntimeException {
        if (nodeListStack.size() == 1) {
            return getLastListNode();
        }

        throw new RuntimeException("Parse exception, not complete root list or empty list");
    }

    private Node getLastListNode() {
        if (nodeListStack.size() >= 1) {
            return new ListNode(nodeListStack.pop());
        }

        throw new RuntimeException("Parse exception, empty list queue");
    }

    private void addNodeToCurrentList(Node newNode) {
        if (nodeListStack.size() > 0) {
            LinkedList<Node> currentList = nodeListStack.pop();

            currentList.add(newNode);
            nodeListStack.push(currentList);
        }
    }

    //@Override public void enterProgram(fcpParser.ProgramContext ctx) { System.out.println(ctx.list()); }

    //@Override public void exitProgram(fcpParser.ProgramContext ctx) { }

    @Override public void enterList(fcpParser.ListContext ctx) {
        nodeListStack.push(new LinkedList<Node>());
    }

    @Override public void exitList(fcpParser.ListContext ctx) {
        if (nodeListStack.size() > 1) {
            addNodeToCurrentList(getLastListNode());
        }
    }

    @Override public void enterLiteral(fcpParser.LiteralContext ctx) {
        if (ctx.INT() != null) {
            int intNumber = Integer.parseInt(ctx.INT().getText());
            addNodeToCurrentList(new IntNode(intNumber));
        } else if (ctx.SYMBOL() != null) {
            addNodeToCurrentList(new VarNode(ctx.SYMBOL().getText()));
        } else if (ctx.BOOLEAN() != null) {
            addNodeToCurrentList(new BooleanNode(ctx.BOOLEAN().getText()));
        } else {
            throw new RuntimeException("Unknown lexeme in parse process");
        }
    }
    @Override public void exitLiteral(fcpParser.LiteralContext ctx) { }

    //@Override public void enterEveryRule(ParserRuleContext ctx) { }

    //@Override public void exitEveryRule(ParserRuleContext ctx) { }

    //@Override public void visitTerminal(TerminalNode node) { }

    @Override public void visitErrorNode(ErrorNode node) { }

    public static void main(String[] args) {
        try {
            CodePointCharStream input = CharStreams.fromString("(define fibonacci\n" +
                    "  (lambda (n)\n" +
                    "    (define iter\n" +
                    "      (lambda (i n1 n2)\n" +
                    "        (if (= i 0)\n" +
                    "            n2\n" +
                    "            (iter (- i 1)\n" +
                    "                  n2\n" +
                    "                  (+ n1 n2)))))\n" +
                    "    (iter n 0 1)))");

            fcpLexer lexer = new fcpLexer(input);
            fcpParser parser = new fcpParser(new CommonTokenStream(lexer));

            fcpTruffleParser truffleParser = new fcpTruffleParser();
            parser.addParseListener(truffleParser);
            parser.program();

            //System.out.println("result: " + truffleParser.getRootNode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
