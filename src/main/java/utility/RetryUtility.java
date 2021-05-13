package utility;
import base.GenericAction;
import com.sun.net.httpserver.Authenticator;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryUtility extends GenericAction implements IRetryAnalyzer {
        private int retryCount = 0;
        private int maxRetryCount = 0;

        public boolean retry(ITestResult result) {

            if (retryCount < maxRetryCount) {
                retryCount++;
                return true;
            }
            return false;
        }
}
