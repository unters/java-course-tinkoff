package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
@SuppressWarnings("MultipleStringLiterals")
public class ReflectionBenchmark {
    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Student, String> lambdaFunction;

    @SuppressWarnings({"UncommentedMain", "MagicNumber"})
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.minutes(2))
            .build();

        new Runner(options).run();
    }

    @Setup
    public void setup() throws Throwable {
        student = new Student("Artem", "Gromakov");

        /* Method setup.  */
        method = Student.class.getDeclaredMethod("name");

        /* Method handle setup.  */
        methodHandle = MethodHandles.lookup().findGetter(Student.class, "name", String.class);

        /* Lambda metafactory setup. */
        CallSite callSite = LambdaMetafactory.metafactory(
            MethodHandles.lookup(),
            "apply",
            MethodType.methodType(Function.class),
            MethodType.methodType(Object.class, Object.class),
            MethodHandles.lookup().findVirtual(Student.class, "name", MethodType.methodType(String.class)),
            MethodType.methodType(String.class, Student.class)
        );
        lambdaFunction = (Function<Student, String>) callSite.getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflectionAccess(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        Object name = method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandlesAccess(Blackhole bh) throws Throwable {
        Object name = methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetaFactoryAccess(Blackhole bh) {
        String name = lambdaFunction.apply(student);
        bh.consume(name);
    }

    private record Student(String name, String surname) {
    }
}
