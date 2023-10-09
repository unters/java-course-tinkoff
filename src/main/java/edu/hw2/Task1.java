package edu.hw2;

public class Task1 {
    public sealed interface Expression {
        double evaluate();

        public record Constant(double value) implements Expression {
            public double evaluate() {
                return value;
            }
        }

        public record Negate(Expression constant) implements Expression {
            public double evaluate() {
                return -1.0 * constant.evaluate();
            }
        }

        public record Exponent(Expression base, Expression exponent) implements Expression {
            public double evaluate() {
                return Math.pow(base.evaluate(), exponent.evaluate());
            }
        }

        public record Addition(Expression a, Expression b) implements Expression {
            public double evaluate() {
                return a.evaluate() + b.evaluate();
            }
        }

        public record Multiplication(Expression a, Expression b) implements Expression {
            public double evaluate() {
                return a.evaluate() * b.evaluate();
            }
        }
    }

    private Task1() {
    }
}


