package edu.hw2;

public class Task4 {
    public static CallingInfo getCallingInfo() {
        CallingInfo callingInfo;
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            StackTraceElement caller = stackTrace[1];
            String className = caller.getClassName();
            String methodName = caller.getMethodName();
            callingInfo = new CallingInfo(className, methodName);
        }

        return callingInfo;
    }

    private Task4() {
    }

    public record CallingInfo(String className, String methodName) {
    }
}
