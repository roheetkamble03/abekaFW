package base;

import com.google.common.base.CaseFormat;
import constants.*;
import elementConstants.AbekaHome;
import elementConstants.Login;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pageObjects.AbekaHomeScreen;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static constants.Calendar.dayMonthSingleDate;
import static constants.Calendar.weekDayMonthDayOfMonth;
import static constants.CommonConstants.*;
import static constants.TableColumn.*;
import static elementConstants.Enrollments.GRADE_ONE_ACCREDITED;
import static org.openqa.selenium.remote.BrowserType.SAFARI;

public abstract class GenericAction extends SelenideExtended{

    @Getter
    public static UserAccountDetails userAccountDetails = null;

    @Getter
    public static HashMap<String, SubjectDetails> studentSubjectDetailsList = new HashMap<>();

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
        click(getVisibleElement(AbekaHome.accountGreeting));
        click(getVisibleElement(CommonConstants.LINK_TEXT + submenu));
        if(submenu.equalsIgnoreCase(AbekaHome.DASHBOARD))
            switchToLastOrNewWindow();
        waitForPageTobeLoaded();
        return new AbekaHomeScreen();
    }

    public AbekaHomeScreen logoutFromAbeka(){
        navigateToAccountGreetingSubMenu(AbekaHome.LOG_OUT);
        waitForElementTobeVisible(AbekaHome.account);
        return new AbekaHomeScreen();
    }

    public void waitAndCloseSignUpPop(){
        log("Waiting for sign up popup");
        waitForElementTobeVisible(AbekaHome.closeSignup);
        if(browser.equals(SAFARI)){
            clickByJavaScript(getVisibleElement(AbekaHome.closeSignup));
        }else {
            click(getVisibleElement(AbekaHome.closeSignup));
        }

    }

    public String formatCurrencyToDollar(Double amount){
        Locale usa = new Locale("en","US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(usa);
        return numberFormat.format(amount);
    }

    public double getCalculatedSubTotal(int quantity, double price){
        return quantity*price;
    }

    public ArrayList<String> getAllAvailableBrowserTab(){
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

    /**
     * Setting student account details at global level
     * @param userId user ID to get the details
     */
    public void setStudentAccountDetailsFromDB(String userId){
        HashMap<String,String> userLoginDetails =  executeAndGetSelectQueryData(DataBaseQueryConstant.LOGIN_DETAILS_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,userId),SD_DATA_BASE).get(0);

        userAccountDetails = new UserAccountDetails(userId,userLoginDetails.get(LOGIN_ID),userLoginDetails.get(ACCOUNT_NUMBER),
                userLoginDetails.get(STUDENT_ID),userLoginDetails.get(DISPLAY_NAME),executeAndGetSelectQueryData(DataBaseQueryConstant.GET_SUBSCRIPTION_NUMBER_SD_DB
                .replaceAll(LOGIN_ID_DATA,userLoginDetails.get(LOGIN_ID)),SD_DATA_BASE).get(0).get(SUBSCRIPTION_NUMBER));
    }

    /**
     * Setting student subject details at global level
     * @param studentName student name for get student details
     */
    public void getStudentSubjectDetailsFromDB(String studentName){
        if(getUserAccountDetails() == null){
            setStudentAccountDetailsFromDB(studentName);
        }
        for(HashMap<String,String> row: executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_SUBJECT_DETAILS_SD_DB
                .replaceAll(LOGIN_ID_DATA, getUserAccountDetails().getLoginId()).replaceAll(SUBSCRIPTION_NUMBER_DATA, getUserAccountDetails().getSubscriptionNumber()),SD_DATA_BASE)){

            studentSubjectDetailsList.put(row.get(SUBJECT_NAME).trim(),new SubjectDetails(row.get(SUBJECT_NAME).trim(),row.get(TableColumn.SUBJECT_ID).trim(),
                    row.get(TableColumn.SUBSCRIPTION_ITEMS).trim(),row.get(SUBSCRIPTION_NUMBER).trim()));
        }
    }

    /**
     * Will group by table data on the basis of one column
     * @param groupByColumn table data will be grouped on the basis of this column
     * @param resultSetMapList data to be processed
     * @param dataColumnOrMergedColumnData If we pass multiple column, data will be merged of all columns
     * @param delimiter merged data will be separated by this
     * @return returning grouped by data in the form of Map<String, ArrayList<String>>
     */
    public Map<String, ArrayList<String>> getGroupedByDataAccordingToColumn(String groupByColumn, ArrayList<HashMap<String, String>> resultSetMapList, String[] dataColumnOrMergedColumnData, String delimiter) {
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

    private String getGroupByColumnData(HashMap<String, String> resultSetMap, String[] groupByColumns, String joiner){
        ArrayList<String> columnDataList = new ArrayList<>();
        for(String column:groupByColumns){
            try{
                columnDataList.add(resultSetMap.get(column).trim());
            }catch (Throwable t){
                columnDataList.add("");
            }
        }
        return StringUtils.join(columnDataList,joiner);
    }

    /**
     * Returns the data list with custom key and value
     * @param resultSetMapList Result set from DB
     * @param dataColumnsToBeFetched Column whose data we want fetch
     * @param customKeyColumns List of column whose data should be used as a key
     * @param customKeyJoiner custom key column data joiner
     * @return returning the custom key with column list in the form of Map<String, ArrayList<String>>
     */
    public Map<String, ArrayList<String>> getCustomKeyAndColumnDataList(ArrayList<HashMap<String, String>> resultSetMapList, String[] customKeyColumns,
                                                                   String customKeyJoiner, String[] dataColumnsToBeFetched, String dataColumnJoiner ){
        Map<String, ArrayList<String>> groupByMap = new TreeMap<>();
        ArrayList<String> columnDataList = new ArrayList<>();
        List<String> columnReadDataList = new ArrayList<>();
        String oldColumnData;

        for(int i=0;i<resultSetMapList.size();i++){
            oldColumnData = getGroupByColumnData(resultSetMapList.get(i), customKeyColumns, customKeyJoiner);
            for(String column:dataColumnsToBeFetched) {
                try {
                    columnReadDataList.add(resultSetMapList.get(i).get(column).trim());
                }catch (NullPointerException e){
                    columnReadDataList.add("");
                }
            }
            columnDataList.add(StringUtils.join(columnReadDataList,dataColumnJoiner));
            columnReadDataList = new ArrayList<>();
            if(i+1 != resultSetMapList.size()){
                if (!oldColumnData.equals(getGroupByColumnData(resultSetMapList.get(i+1), customKeyColumns, customKeyJoiner))) {
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

        String weekDay;
        String month;
        String dayOfMonth;
        DateTimeFormatter actualFormat = DateTimeFormatter.ofPattern(actualDateFormat);
        LocalDate localDate = LocalDate.parse(date,actualFormat);

        switch (expectedDateFormat){
            case weekDayMonthDayOfMonth:
                weekDay = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,localDate.getDayOfWeek().toString());
                month = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,localDate.getMonth().name());
                dayOfMonth = Integer.toString(localDate.getDayOfMonth());
                return weekDay+", "+month+" "+dayOfMonth;
            case dayMonthSingleDate:
                //Returns day in Thu, Apr 8
                weekDay = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,localDate.getDayOfWeek().toString());
                month = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,localDate.getMonth().name());
                dayOfMonth = Integer.toString(localDate.getDayOfMonth());
                return weekDay.substring(0,3)+", "+month.subSequence(0,3)+" "+dayOfMonth;
            default:
                DateTimeFormatter expectedFormat = DateTimeFormatter.ofPattern(expectedDateFormat);
                return localDate.format(expectedFormat);
        }
    }

    public void validatedURLContent(String urlContent){
        softAssertions.assertThat(getCurrentURL().contains(urlContent))
                .as("Navigated page's current URL is not having mentioned URL content.\nCurrent URL:"+getCurrentURL()
                        +"\n Expected content:"+urlContent).isTrue();
    }

    public boolean isURLContainsGivenText(String text){
        return getCurrentURL().indexOf(text)>0;
    }
}
