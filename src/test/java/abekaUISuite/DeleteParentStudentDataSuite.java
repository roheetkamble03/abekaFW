package abekaUISuite;

import base.GenericAction;
import constants.DataProviderName;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import utility.RetryUtility;

public class DeleteParentStudentDataSuite extends GenericAction {

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteParentTestDataGradeOne", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_ONE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void deleteParentTestDataGradeOne(String userId, String password, String signature, String customerNumber){
        deleteParentAccount(customerNumber);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteStudentTestDataGradeOne", dataProvider = DataProviderName.STUDENT_CREDENTIALS_GRADE_ONE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = "deleteParentTestDataGradeOne")
    public void deleteStudentTestDataGradeOne(String userId, String password, String signature, String customerNumber, String cartNumber){
        deleteStudentAccountFromSystem(userId);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteParentTestDataGradeFour", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_FOUR,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void deleteParentTestDataGradeFour(String userId, String password, String signature, String customerNumber){
        deleteParentAccount(customerNumber);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteStudentTestDataGradeFour", dataProvider = DataProviderName.STUDENT_CREDENTIALS_GRADE_FOUR,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = "deleteParentTestDataGradeFour")
    public void deleteStudentTestDataGradeFour(String userId, String password, String signature, String customerNumber, String cartNumber){
        deleteStudentAccountFromSystem(userId);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteParentTestDataGradeNine", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_NINE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void deleteParentTestDataGradeNine(String userId, String password, String signature, String customerNumber){
        deleteParentAccount(customerNumber);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteStudentTestDataGradeNine", dataProvider = DataProviderName.STUDENT_CREDENTIALS_GRADE_NINE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = "deleteParentTestDataGradeNine")
    public void deleteStudentTestDataGradeNine(String userId, String password, String signature, String customerNumber, String cartNumber){
        deleteStudentAccountFromSystem(userId);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteParentTestDataGradeTwelve", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_TWELVE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void deleteParentTestDataGradeTwelve(String userId, String password, String signature, String customerNumber){
        deleteParentAccount(customerNumber);
    }

    @DataRowNumber(fromDataRowNumber = "1")
    //@AfterSuite(alwaysRun = true)
    @Test(testName = "deleteStudentTestDataGradeTwelve", dataProvider = DataProviderName.STUDENT_CREDENTIALS_GRADE_TWELVE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = "deleteParentTestDataGradeTwelve")
    public void deleteStudentTestDataGradeTwelve(String userId, String password, String signature, String customerNumber, String cartNumber){
        deleteStudentAccountFromSystem(userId);
    }
}
