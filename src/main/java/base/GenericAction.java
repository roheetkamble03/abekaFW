package base;

import constants.CommonConstants;
import constants.DataBaseQueryConstant;
import elementConstants.AbekaHome;
import elementConstants.Login;
import elementConstants.Students;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageObjects.AbekaHomeScreen;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static constants.CommonConstants.MINUS;
import static constants.CommonConstants.PLUS;
import static elementConstants.Enrollments.GRADE_ONE_ACCREDITED;

public abstract class GenericAction extends SelenideExtended{

    @BeforeMethod
    protected void setUp(String browserName, String platform) {
        super.setUp(browserName,platform);
        //Allure report writing will be done letter
    }

    @AfterMethod
    public void tearDown(){
        log("After each method tearing down the test in GenericAction.class");
        super.tearDown();
    }
    public AbekaHomeScreen loginToAbeka(String userId, String password, String userName){
        waitAndCloseSignUpPop();
        click(AbekaHome.login);
        click(AbekaHome.logInCreateAccount);
        type(Login.emailAddress,userId);
        type(Login.password,password);
        click(Login.loginBtn);
        waitForAbekaBGProcessLogoDisappear();
        waitForElementTobeExist(userName);//"text=Hello, RCG");
        return new AbekaHomeScreen();
    }

    public AbekaHomeScreen navigateToAccountGreetingSubMenu(String submenu){
        click(AbekaHome.accountGreeting);
        click(CommonConstants.LINK_TEXT + submenu);
        if(submenu.equalsIgnoreCase(AbekaHome.DASHBOARD))
            switchToLastOrNewWindow();
        waitForPageTobLoaded();
        return new AbekaHomeScreen();
    }

    public AbekaHomeScreen logoutFromAbeka(){
        navigateToAccountGreetingSubMenu(AbekaHome.LOG_OUT);
        waitForElementTobeVisible(AbekaHome.account);
        return new AbekaHomeScreen();
    }

    public void waitAndCloseSignUpPop(){
        waitForElementTobeEnabled(AbekaHome.closeSignup);
        click(AbekaHome.closeSignup);
    }

    public String formatCurrencyToDollar(Double amount){
        Locale usa = new Locale("en","US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(usa);
        return numberFormat.format(amount);
    }

    public double getCalculatedSubTotal(int quantity, double price){
        return quantity*price;
    }

    public ArrayList getAllAvailableBrowserTab(){
        return new ArrayList<>(getDriver().getWindowHandles());
    }

    public String generateStudentBirthDate(String grade){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        localDate = localDate.minusYears(getStudentRangeAge(grade));
        return localDate.format(formatter);
    }

    public String getModifiedDate(String dateTobeModified,int daysTobeChangeBy, int monthTobeChangeBy, int yearTobeChangeBy, String action){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(dateTobeModified, formatter);
        switch (action.toUpperCase()){
            case PLUS:
                localDate = localDate.plusDays(daysTobeChangeBy);
                localDate = localDate.plusMonths(monthTobeChangeBy);
                localDate = localDate.plusYears(yearTobeChangeBy);
                return localDate.format(formatter);
            case MINUS:
                localDate = localDate.minusDays(daysTobeChangeBy);
                localDate = localDate.minusMonths(monthTobeChangeBy);
                localDate = localDate.minusYears(yearTobeChangeBy);
                return localDate.format(formatter);
            default:
                return "";
        }
    }

    public int getStudentRangeAge(String grade){
        switch (grade){
            case GRADE_ONE_ACCREDITED:
                return 6;
            default:
                return 0;
        }
    }

    public String getStudentIdFromDB(String studentName){
       // return executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_GRADE_WITH_SUBJECT).get(0).get(Students.STUDENT_ID_TBL_COL_NAME);
        return "";
    }
}
