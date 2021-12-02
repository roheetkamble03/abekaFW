package abekaUISuite;

import base.GenericAction;
import constants.DataProviderName;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import org.testng.annotations.Test;
import utility.RetryUtility;

public class DeleteParentStudentDataSuite extends GenericAction {

    @DataRowNumber(fromDataRowNumber = "2")
    @Test(testName = "deleteParentTestData", dataProvider = DataProviderName.PARENT_CREDENTIALS,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void deleteParentTestData(String userId, String password, String signature, String customerNumber){
        deleteParentAccount(customerNumber);
    }

    @DataRowNumber(fromDataRowNumber = "2")
    @Test(testName = "deleteStudentTestData", dataProvider = DataProviderName.STUDENT_CREDENTIALS,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = "deleteParentTestData")
    public void deleteStudentTestData(String userId, String password, String signature, String customerNumber, String cartNumber){
        deleteStudentAccountFromSystem(userId);
    }
}
