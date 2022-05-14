package truffle.parser;

import interpreter.nodes.expressions.Expression;
import lexer.Token;
import lexer.Values;
import truffle.nodes.SymbolNode;

import java.util.List;

public class Parser {
    public List<Token> listOfTokens;
    private int position;

    public Parser(List<Token> listOfTokens) {
        this.listOfTokens = listOfTokens;
        this.position = 0;
    }


    /*private fun readExp(): Expression {
        return equality()
    }

    private fun equality(): Expression {
        var exp: Expression = readCompare()
        while (find(EQ, NOTQE)) {
            val operation = previousToken()
            val rightExp: Expression = readCompare()
            exp = BinExp(exp, operation, rightExp)
        }

        return exp
    }

    private fun readCompare(): Expression {
        var leftExp: Expression = readTerm()

        while (find(GT, LT, LOEQ, GOEQ)) {
            val operation = previousToken()
            val rightTExp: Expression = readCompare()
            leftExp = BinExp(leftExp, operation, rightTExp)
        }

        return leftExp
    }

    private fun readTerm(): Expression {
        var leftExp: Expression = readFactor()

        while (find(MINUS, PLUS)) {
            val operation = previousToken()
            val rightExp: Expression = readFactor()
            leftExp = BinExp(leftExp, operation, rightExp)
        }

        return leftExp
    }

    private fun readFactor(): Expression {
        var leftExp: Expression = readUnar()

        while (find(ASTER)) {
            val operation = previousToken()
            val rightExp: Expression = readUnar()
            leftExp = BinExp(leftExp, operation, rightExp)
        }

        return leftExp
    }

    private fun readUnar(): Expression {
        if (find(MINUS)) {
            val operation = previousToken()
            val value: Expression = readValue()

            return UnExp(operation, value)
        }

        return readValue()
    }

    private fun readValue(): PrimeExp {
        var exp: PrimeExp? = null
        val token = current()

        when {
            find(VAR) -> {
                exp = VarNode(frameDescriptor.findFrameSlot(token.name) ?: throw ParserException("not init var: ${token.name}", token.line) )
            }
            find(INT) -> {
                exp = ValNode(token.name.toInt())
            }
        }

        return exp ?: throw ParserException(current().name, current().line)
    }


    private Expression readValues() {
        Expression exp = null;
        Token token = current();

        switch(true) {
            case find(Values.VAR) -> exp =
        }
    }*/

    private boolean find(Values... values) {
        for (Values value : values) {
            if (current().getValue() == value) {
                move();

                return true;
            }
        }

        return false;
    }

    private Token previousToken() {
        return listOfTokens.get(position - 1);
    }

    private Token current() {
        return this.listOfTokens.get(position);
    }

    private void move() {
        if (current().getValue() != Values.END)
        position++;
    }
}
