package utility;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Captures the currently executing test method so it can be accessed by the test,
 * e.g. to retrieve the test method's name. This class is thread-safe.
 *
 * <p>Register this class as a
 * <a href="http://testng.org/doc/documentation-main.html#testng-listeners">TestNG
 * listener</a>, then access the method and result from test code with the static
 * {@link #getTestMethod} and {@link #getTestResult} methods.
 *
 * <p>Annotating a test class with {@code @Listeners(TestMethodCapture.class)} is the
 * suggested way to enable capturing if your test's correctness will depend on this
 * listener being enabled.
 */
public class TestMethodCapture implements IInvokedMethodListener {
    private static ThreadLocal<ITestNGMethod> currentMethods = new ThreadLocal<>();
    private static ThreadLocal<ITestResult> currentResults = new ThreadLocal<>();

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        currentMethods.set(method.getTestMethod());
        currentResults.set(testResult);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        currentMethods.remove();
        currentResults.remove();
    }

    public static ITestNGMethod getTestMethod() {
        return checkNotNull(currentMethods.get(),
                "Did you forget to register the %s listener?", TestMethodCapture.class.getName());
    }

    /**
     * Parameters passed from a data provider are accessible in the test result.
     */
    public static ITestResult getTestResult() {
        return checkNotNull(currentResults.get(),
                "Did you forget to register the %s listener?", TestMethodCapture.class.getName());
    }
}

