package edu.hw2;

public class Task4 {
    public static CallingInfo getCallingInfo() {
        CallingInfo callingInfo;
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            StackTraceElement caller = e.getStackTrace()[1];
            callingInfo = new CallingInfo(caller.getClassName(), caller.getMethodName());
        }

        return callingInfo;
    }

    private Task4() {
    }

    public record CallingInfo(String className, String methodName) {
    }
}
