package edu.hw2;

public class Task1 {
    private Task1() {
    }

    public sealed interface Expression {
        double evaluate();

        record Constant(double value) implements Expression {
            public double evaluate() {
                return value;
            }
        }

        record Negate(Expression constant) implements Expression {
            public double evaluate() {
                return -1.0 * constant.evaluate();
            }
        }

        record Exponent(Expression base, Expression exponent) implements Expression {
            public double evaluate() {
                return Math.pow(base.evaluate(), exponent.evaluate());
            }
        }

        record Addition(Expression a, Expression b) implements Expression {
            public double evaluate() {
                return a.evaluate() + b.evaluate();
            }
        }

        record Multiplication(Expression a, Expression b) implements Expression {
            public double evaluate() {
                return a.evaluate() * b.evaluate();
            }
        }
    }
}


