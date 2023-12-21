package edu.hw11;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    private static final String MESSAGE = "Hello, ByteBuddy!";

    private static final Class<?> myClass = new ByteBuddy()
        .subclass(Object.class)
        .method(ElementMatchers.named("toString"))
        .intercept(FixedValue.value(MESSAGE))
        .make()
        .load(Task1Test.class.getClassLoader())
        .getLoaded();

    @Test
    @SneakyThrows
    void toString_MethodCalled_ReturnsExpectedMessage() {
        // given
        Object object = myClass.getDeclaredConstructor().newInstance();

        // when
        String actualAnswer = object.toString();

        // then
        assertThat(actualAnswer).isEqualTo(MESSAGE);
    }
}
