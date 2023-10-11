package edu.hw2;

import static edu.hw2.Task1.Expression;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExpressionConstantTest {
    @ParameterizedTest
    @ValueSource(doubles = {10.0, 2.25, -12.3, 0.0, Double.MAX_VALUE, Double.MIN_VALUE})
    void evaluate_objectOfExpressionConstantClassCreated_ReturnExpectedValue(double value) {
        Expression expr = new Expression.Constant(value);
        double actualAnswer = expr.evaluate();
        assertThat(actualAnswer).isEqualTo(value);
    }
}

class ExpressionNegateTest {
    private static final class ExpressionNegateArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Expression.Constant(25.5), -25.5),
                Arguments.of(new Expression.Constant(0.0), 0.0),
                Arguments.of(new Expression.Constant(-100.7), 100.7)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ExpressionNegateArgumentsProvider.class)
    void evaluate_ExpressionNegateObjectCreated_ReturnExpectedAnswer(Expression expr, double expectedAnswer) {
        Expression negateExpression = new Expression.Negate(expr);
        double actualAnswer = negateExpression.evaluate();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}

class ExpressionExponentTest {
    private static final class ExpressionExponentArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Expression.Constant(10.0), new Expression.Constant(2), 100.0),
                Arguments.of(new Expression.Constant(2.0), new Expression.Constant(6.0), 64.0),
                Arguments.of(new Expression.Constant(2.0), new Expression.Constant(0.0), 1.0),
                Arguments.of(new Expression.Constant(2.0), new Expression.Constant(-2.0), 0.25)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ExpressionExponentArgumentsProvider.class)
    void evaluate_ExpressionAdditionObjectCreated_ReturnExpectedAnswer(
        Expression base,
        Expression exponent,
        double expectedAnswer
    ) {
        Expression exponentExpression = new Expression.Exponent(base, exponent);
        double actualAnswer = exponentExpression.evaluate();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}

class ExpressionAdditionTest {
    private static final class ExpressionAdditionArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Expression.Constant(10.0), new Expression.Constant(15.5), 25.5),
                Arguments.of(new Expression.Constant(75.2), new Expression.Constant(-15.1), 60.1),
                Arguments.of(new Expression.Constant(100.0), new Expression.Constant(0.0), 100.0),
                Arguments.of(new Expression.Constant(-100.0), new Expression.Constant(-12.725), -112.725)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ExpressionAdditionArgumentsProvider.class)
    void evaluate_ExpressionAdditionObjectCreated_ReturnExpectedAnswer(
        Expression expr1,
        Expression expr2,
        double expectedAnswer
    ) {
        Expression additionExpression = new Expression.Addition(expr1, expr2);
        double actualAnswer = additionExpression.evaluate();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}

class ExpressionMultiplicationTest {
    private static final class ExpressionMultiplicationArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Expression.Constant(10.0), new Expression.Constant(10.1), 101.0),
                Arguments.of(new Expression.Constant(2.0), new Expression.Constant(-0.5), -1.0),
                Arguments.of(new Expression.Constant(121.15), new Expression.Constant(0.0), 0.0),
                Arguments.of(new Expression.Constant(-100.0), new Expression.Constant(-12.725), 1272.5)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ExpressionMultiplicationArgumentsProvider.class)
    void evaluate_ExpressionMultiplicationObjectCreated_ReturnExpectedAnswer(
        Expression expr1,
        Expression expr2,
        double expectedAnswer
    ) {
        Expression multiplicationExpression = new Expression.Multiplication(expr1, expr2);
        double actualAnswer = multiplicationExpression.evaluate();
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }
}
