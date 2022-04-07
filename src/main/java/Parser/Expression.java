package Parser;
import Lexer.Token;

public abstract class Expression {
    abstract <R> R accept(Visitor<R> visitor);

    public static class BinExp extends Expression {
        public BinExp(Expression left, Token operator, Expression right) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }

        Expression left;
        Token operator;
        Expression right;


        @Override
        <K> K accept(Visitor<K> visitor) {
            return visitor.visitBinExp(this);
        }
    }

    public static class UnExp extends Expression {
        public UnExp(Token operator, Expression right) {
            this.right = right;
            this.operator = operator;
        }

        Token operator;
        Expression right;

        @Override
        <K> K accept(Visitor<K> visitor) {
            return visitor.visitUnExp(this);
        }
    }

    public static class PrimeExp extends Expression{
        public PrimeExp(Token var) {
            this.name = var.name;
        }

        String name;
        int value;

        @Override
        <K> K accept(Visitor<K> visitor) {
            return visitor.visitPrimeExp(this);
        }
    }

    interface Visitor<K> {
        K visitBinExp(BinExp exp);

        K visitUnExp(UnExp exp);

        K visitPrimeExp(PrimeExp exp);
    }
}
