package Parser;

import Lexer.SemanticValue;
import Lexer.Token;
import java.util.List;
import static Lexer.SemanticValue.*;

public class Parser {
    public List<Token> tokens;
    int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        return exp();
    }

    Expression exp() {
        return equality();
    }

    Expression equality() {
        Expression exp = readCompare();

        while (find(SemanticValue.EQ, SemanticValue.NOTQE)) {
            Token operation = previousToken();
            Expression rightExp = readCompare();
            exp = new Expression.BinExp(exp, operation, rightExp);
        }

        return exp;
    }

    Expression readCompare() {
        Expression leftExp = readTerm();

        while (find(SemanticValue.GT,  SemanticValue.LT, SemanticValue.LOEQ, SemanticValue.GOEQ)) {
            Token operation = previousToken();
            Expression rightTExp = readCompare();
            leftExp = new Expression.BinExp(leftExp, operation, rightTExp);
        }

        return leftExp;
    }

    Expression readTerm() {
        Expression leftExp = readFactor();

        while (find(SemanticValue.MINUS, SemanticValue.PLUS)) {
            Token operation = previousToken();
            Expression rightExp = readFactor();
            leftExp = new Expression.BinExp(leftExp, operation, rightExp);
        }

        return leftExp;
    }

    Expression readFactor() {
        Expression leftExp = readUnar();

        while(find(SemanticValue.ASTER)) {
            Token operation = previousToken();
            Expression rightExp = readUnar();
            leftExp = new Expression.BinExp(leftExp, operation, rightExp);
        }

        return leftExp;
    }

    Expression readUnar() {
        if (find(SemanticValue.MINUS)) {
            Token operation = previousToken();
            Expression value = readValue();
            return new Expression.UnExp(operation, value);
        }

        return readValue();
    }

    Expression readValue() {
        Expression exp = null;

        if (find(SemanticValue.VAR, SemanticValue.INT)) {
            exp = new Expression.PrimeExp(previousToken());
        }

        if (find(LPAREN)) {
            exp = exp();

        }

        return exp;
    }

    boolean find(SemanticValue... values) {
        for (SemanticValue value : values) {
            if (current().value == value) {
                move();
                return true;
            }
        }

        return false;
    }

    Token previousToken() {
        return tokens.get(position - 1);
    }

    Token current() {
        return (tokens.get(position));
    }

    void move() {
        if (current().value != END) {
            position++;
        }
    }
}
