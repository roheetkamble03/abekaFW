package base;

import com.google.common.base.CaseFormat;
import constants.CommonConstants;
import constants.TableColumn;
import constants.DataBaseQueryConstant;
import elementConstants.AbekaHome;
import elementConstants.Login;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pageObjects.AbekaHomeScreen;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static constants.Calendar.weekDayMonthDayOfMonth;
import static constants.CommonConstants.*;
import static elementConstants.Enrollments.GRADE_ONE_ACCREDITED;

public abstract class GenericAction extends SelenideExtended{

    @Getter@Setter
    public static HashMap<String,String> userAccountDetails = new HashMap<>();

    @Parameters({"browser", "platform"})
    @BeforeMethod
    protected void setUp(String browserName, String platform) {
        super.setUp(browserName,platform);
        //Allure report writing will be done later
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

    public void setStudentAccountDetails(String studentName){
        HashMap<String,String> userLoginDetails =  executeAndGetSelectQueryData(DataBaseQueryConstant.LOGIN_DETAILS_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,studentName),SD_DATA_BASE).get(0);
        userAccountDetails.put(TableColumn.LOGIN_ID,userLoginDetails.get(TableColumn.LOGIN_ID));
        userAccountDetails.put(TableColumn.STUDENT_ID_DATA,userLoginDetails.get(TableColumn.STUDENT_ID_DATA));
        userAccountDetails.put(TableColumn.ACCOUNT_NUMBER_DATA,userLoginDetails.get(TableColumn.ACCOUNT_NUMBER_DATA));
    }

    /**
     * Will group by table data on the basis of one column
     * @param groupByColumn table data will be grouped on the basis of this column
     * @param resultSetMapList data to be processed
     * @param dataColumnOrMergedColumnData If we pass multiple column, data will be merged of all columns
     * @param delimiter merged data will be separated by this
     * @return
     */
    public Map<String, ArrayList<String>> getGroupedDataAccordingToColumn(String groupByColumn, ArrayList<HashMap<String, String>> resultSetMapList, ArrayList<String> dataColumnOrMergedColumnData, String delimiter) {
        Map<String, ArrayList<String>> groupByMap = new TreeMap<>();
        ArrayList<String> columnDataList = new ArrayList<>();
        List<String> columnReadDataList = new ArrayList<>();
        String oldColumnData;

        for(int i=0;i<resultSetMapList.size();i++){
            oldColumnData = resultSetMapList.get(i).get(groupByColumn);
            for(String column:dataColumnOrMergedColumnData) {
                try {
                    columnReadDataList.add(resultSetMapList.get(i).get(column).trim());
                }catch (NullPointerException e){
                    columnReadDataList.add("");
                }
            }
            columnDataList.add(StringUtils.join(columnReadDataList,delimiter));
            columnReadDataList = new ArrayList<>();
            if(i+1 != resultSetMapList.size()){
                if (!oldColumnData.equals(resultSetMapList.get(i+1).get(groupByColumn))) {
                    groupByMap.put(oldColumnData, columnDataList);
                    columnDataList = new ArrayList<>();
                }
            }else {
                groupByMap.put(oldColumnData, columnDataList);
            }
        }
        return groupByMap;
    }

    public String getFormattedDate(String date, String actualDateFormat, String expectedDateFormat){
        DateTimeFormatter actualFormat = DateTimeFormatter.ofPattern(actualDateFormat);
        LocalDate localDate = LocalDate.parse(date,actualFormat);

        switch (expectedDateFormat){
            case weekDayMonthDayOfMonth:
                String weekDay = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,localDate.getDayOfWeek().toString());
                String month = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,localDate.getMonth().name());
                String dayOfMonth = Integer.toString(localDate.getDayOfMonth());
                return weekDay+", "+month+" "+dayOfMonth;
            default:
                DateTimeFormatter expectedFormat = DateTimeFormatter.ofPattern(expectedDateFormat);
                return localDate.format(expectedFormat);
        }
    }
}
