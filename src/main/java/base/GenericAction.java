package base;

import apiPojo.CreateAccountApiResponsePojo;
import apiPojo.DeleteAccountApiResponse;
import com.google.common.base.CaseFormat;
import constants.*;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import elementConstants.Login;
import io.restassured.common.mapper.TypeRef;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pageObjects.AbekaHomeScreen;
import utility.ExcelUtils;
import utility.ParentAccountDetails;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static constants.ApiServiceConstants.customerNumber;
import static constants.ApiServiceConstants.navService;
import static constants.Calendar.dayMonthSingleDate;
import static constants.Calendar.weekDayMonthDayOfMonth;
import static constants.CommonConstants.*;
import static constants.DataBaseQueryConstant.*;
import static constants.TableColumn.*;
import static elementConstants.Enrollments.*;
import static io.restassured.RestAssured.given;

public abstract class GenericAction extends SelenideExtended{

    @Getter
    public static UserAccountDetails userAccountDetails = UserAccountDetails.builder().build();

    @Getter
    public static HashMap<String, SubjectDetails> studentSubjectDetailsList = new HashMap<>();

    @BeforeMethod
    @Parameters({"browser", "platform"})
    public void setUp(String browserName, String platform) {
        super.setUp(browserName,platform);
        //Allure report writing will be done later
    }

    @AfterMethod
    public void tearDown(){
        log("After each method tearing down the test in GenericAction.class");
        super.tearDown();
    }

    @AfterThrowing
    public void tearDownAfterError(){
        log("After each method tearing down the test in GenericAction.class");
        super.tearDown();
    }

    public List<String> removeBlankDataFromList(List<String> stringList){
        return stringList.stream().filter(e->e.trim().length()!=0).collect(Collectors.toList());
    }

    public List<Integer> removeBlankDataFromIntegerList(List<Integer> stringList){
        return stringList.stream().filter(e->Integer.toString(e).trim().length()!=0).collect(Collectors.toList());
    }

    public AbekaHomeScreen loginToAbeka(String userId, String password, boolean isSignUpPopAppears){
        waitAndCloseSignUpPop(isSignUpPopAppears);
        log("logging in to application");
        log("UserId: "+ userId);
        log("Password: "+ password);
        type(Login.emailAddress,userId);
        type(Login.password,password);
        click(Login.loginBtn, false);
        waitForAbekaBGProcessLogoDisappear();
        //waitForElementTobeExist(userName);//"text=Hello, RCG");
        return new AbekaHomeScreen();
    }

    public AbekaHomeScreen navigateToAccountGreetingSubMenu(String submenu){
        click(getVisibleElement(AbekaHome.accountGreeting));
        if(isElementExists(CommonConstants.LINK_TEXT + submenu, false, elementLoadWait)){
            clickByJavaScript(CommonConstants.LINK_TEXT + submenu);
        }else {
            clickByJavaScript(submenu);
        }

        if(submenu.equalsIgnoreCase(AbekaHome.DASHBOARD))
            switchToLastOrNewWindow();
        waitForPageTobeLoaded();
        return new AbekaHomeScreen();
    }

    public AbekaHomeScreen logoutFromAbeka(){
        navigateToAccountGreetingSubMenu(AbekaHome.LOG_OUT);
        waitForElementTobeVisibleOrMoveAhead(AbekaHome.account);
        return new AbekaHomeScreen();
    }

    public void waitAndCloseSignUpPop(boolean isSignUpPopAppears){
        log("Waiting for sign up popup");
        if(isSignUpPopAppears) {
            waitForElementTobeVisibleOrMoveAhead(AbekaHome.closeSignup, veryLongWait);
            //if (browser.equals(SAFARI)) {
                //clickByJavaScript(getVisibleElement(AbekaHome.closeSignup));
            //} else {
                try {
                    click(getVisibleElement(AbekaHome.closeSignup, veryLongWait));
                } catch (Exception e) {
                    log("Sign up pop up on page load not appeared");
                }
            //}
            click(AbekaHome.login, false);
            click(AbekaHome.logInCreateAccount, false);
        }else {
            navigateToAccountGreetingSubMenu(AbekaHome.login);
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

    public static String generateStudentBirthDate(String grade){
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

    public static int getStudentRangeAge(String grade){
        switch (grade){
            case GRADE_ONE_VIDEO:
                return 6;
            case GRADE_FOUR_ACCREDITED:
                return 10;
            case GRADE_NINE:
                return 15;
            case GRADE_TWELVE:
                return 18;
            default:
                return 0;
        }
    }

    public ParentAccountDetails getParentAccountDetails(int rowNumber, String testDataExcelName){
        Map<Integer,String> dataMap = new DataProviders().getExcelData(PARENT_CREDENTIALS, rowNumber, rowNumber, testDataExcelName).get(0);
        ParentAccountDetails parentDetails = new ParentAccountDetails();
        parentDetails.setParentUserName(dataMap.get(0));
        parentDetails.setParentPassword(dataMap.get(1));
        parentDetails.setParentSignature(dataMap.get(2));
        parentDetails.setParentCustomerNumber(dataMap.get(3));
        parentDetails.setParentName(dataMap.get(2));
        return parentDetails;
    }

    public StudentDetails getStudentAccountDetails(int rowNumber, String testDataExcelName){
        Map<Integer,String> dataMap = new DataProviders().getExcelData(STUDENT_CREDENTIALS, rowNumber, rowNumber, testDataExcelName).get(0);
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setStudentUserId(dataMap.get(0));
        studentDetails.setPassword(dataMap.get(1));
        studentDetails.setFirstName(dataMap.get(2).split("\\s")[0]);
        studentDetails.setLastName((dataMap.get(2).split("\\s").length>1)?dataMap.get(2).split("\\s")[1]:"");
        studentDetails.setStudentFullName(studentDetails.getFirstName()+" "+studentDetails.getLastName());
        return studentDetails;
    }

    /**
     * Setting student account details at global level
     * @param userId user ID to get the details
     * @param isFetchSubscriptionNumber
     */
    public void setStudentAccountDetailsFromDB(String userId, boolean isFetchSubscriptionNumber){
        HashMap<String,String> userLoginDetails =  executeAndGetSelectQueryData(DataBaseQueryConstant.LOGIN_DETAILS_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,userId),SD_DATA_BASE, false).get(0);
        String applicationNumber = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_APPLICATION_NUMBER_AD_DB.replaceAll(STUDENT_ID_DATA, userLoginDetails.get(STUDENT_ID)), AD_DATA_BASE, false).get(0).get(APPLICATION_NUMBER);
        String subscriptionNumber = "";

        if(isFetchSubscriptionNumber) {
            HashMap<String, String> subscriptionAccountDetails = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_SUBSCRIPTION_ACCOUNT_DETAILS_SD_DB
                    .replaceAll(APPLICATION_NUMBER_DATA, applicationNumber), SD_DATA_BASE, false).get(0);
            subscriptionNumber = subscriptionAccountDetails.get(SUBSCRIPTION_NUMBER_PK);
        }

        userAccountDetails = new UserAccountDetails(userId,userLoginDetails.get(LOGIN_ID),userLoginDetails.get(ACCOUNT_NUMBER),
                userLoginDetails.get(STUDENT_ID),userLoginDetails.get(DISPLAY_NAME),subscriptionNumber,applicationNumber);
    }

    public String getStudentIDFromDB(String userId){
        HashMap<String,String> userLoginDetails =  executeAndGetSelectQueryData(DataBaseQueryConstant.LOGIN_DETAILS_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,userId),SD_DATA_BASE, false).get(0);
        return userLoginDetails.get(STUDENT_ID);
    }

    /**
     * Setting student subject details at global level
     * @param studentName student name for get student details
     */
    public void setStudentSubjectDetailsFromDB(String studentName){
        if(getUserAccountDetails() == null){
            setStudentAccountDetailsFromDB(studentName, true);
        }
        for(HashMap<String,String> row: executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_SUBJECT_DETAILS_SD_DB
                .replaceAll(LOGIN_ID_DATA, getUserAccountDetails().getLoginId()).replaceAll(SUBSCRIPTION_NUMBER_DATA, getUserAccountDetails().getSubscriptionNumber()),SD_DATA_BASE, false)){

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

    public ParentAccountDetails createAndGetParentAccountDetails(int rowNumber, boolean isSetDataToExcel, String testDataExcelName){
        ParentAccountDetails parentAccountDetails = new ParentAccountDetails();
        CreateAccountApiResponsePojo response = given().when().multiPart(ApiServiceConstants.request,ApiServiceConstants.createRequestType)
                .multiPart(ApiServiceConstants.key,properties.getProperty(APP_KEY))
                .multiPart(ApiServiceConstants.customerType,"80")
                .multiPart(ApiServiceConstants.name,parentAccountDetails.getParentName())
                .multiPart(ApiServiceConstants.address1,parentAccountDetails.getParentAddressOne())
                .multiPart(ApiServiceConstants.address2,parentAccountDetails.getParentAddressTwo())
                .multiPart(ApiServiceConstants.city,parentAccountDetails.getParentCity())
                .multiPart(ApiServiceConstants.state,parentAccountDetails.getParentState())
                .multiPart(ApiServiceConstants.zip,parentAccountDetails.getParentZipCode())
                .multiPart(ApiServiceConstants.country,parentAccountDetails.getParentCountry())
                .multiPart(ApiServiceConstants.phone,parentAccountDetails.getParentPhoneNumber())
                .header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER + properties.getProperty(API_AUTH_KEY))
                .get(properties.get(CommonConstants.API_END_URL).toString()).getBody().as(new TypeRef<CreateAccountApiResponsePojo>(){});

        parentAccountDetails.setParentUserName("rcg+"+response.getCustomerNumber()+"@pcci.edu");
        parentAccountDetails.setParentPassword("rcg"+response.getCustomerNumber());
        parentAccountDetails.setParentCustomerNumber(response.getCustomerNumber());
        log("Parent account created with following details \n+" +
                "User Id:"+parentAccountDetails.getParentUserName()+"\n" +
                "Password:"+parentAccountDetails.getParentPassword());
        if(isSetDataToExcel) {
            ExcelUtils excelUtils = new ExcelUtils(testDataExcelName);
            excelUtils.setCellData(CommonConstants.PARENT_CREDENTIALS,
                    new String[]{parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword(), parentAccountDetails.getParentName(), parentAccountDetails.getParentCustomerNumber()}, rowNumber, true, testDataExcelName);
        }
        return parentAccountDetails;
    }

    public void setStudentAccountDetailsInTestDataExcel(StudentDetails studentDetails, int rowNumber, String testDataExcelName) {
        ExcelUtils excelUtils = new ExcelUtils(testDataExcelName);
        excelUtils.setCellData(STUDENT_CREDENTIALS,
                new String[]{studentDetails.getStudentUserId(), studentDetails.getPassword(), studentDetails.getFirstName()+" "+studentDetails.getLastName(), studentDetails.getGrade()}, rowNumber, true, testDataExcelName);
    }

    public void deleteParentAccount(String parentCustomerNumber){
        DeleteAccountApiResponse response = given().when().multiPart(ApiServiceConstants.request,ApiServiceConstants.deleteRequestType)
                .multiPart(ApiServiceConstants.key,properties.getProperty(APP_KEY))
                .multiPart(customerNumber,parentCustomerNumber)
                .header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER + properties.getProperty(API_AUTH_KEY))
                .get(properties.get(CommonConstants.API_END_URL).toString()).getBody().as(new TypeRef<DeleteAccountApiResponse>(){});
        response.getMessage();
        if(!response.getResponse().equalsIgnoreCase(CommonConstants.OK)){
            softAssertions.fail("Parent account deletion failed");
        }
        softAssertions.assertAll();
    }

    public void deleteStudentAccountFromSystem(String studentUserId){
        executeDeleteStudentAccountStoredProcedure(DELETE_STUDENT_ACCOUNT_SP_AD_DB, getStudentIDFromDB(studentUserId), AD_DATA_BASE);
    }

    public void removeABAHold(String applicationNumber, ArrayList<HashMap<String,String>> holdCodeList){
        executeRemoveABAHoldStoredProcedure(REMOVE_ABA_HOLD_SP_AD_DB, applicationNumber,holdCodeList, BY_AUTOMATION, AD_DATA_BASE);
    }

    public void markApplicationAsCompleted(String applicationNumber){
        executeMarkApplicationAsCompletedStoredProcedure(MARK_APPLICATION_AS_COMPLETED, applicationNumber, BY_AUTOMATION, AD_DATA_BASE);
    }

    public void updateCourseBeginDateToBackDateAndRemoveHolds(String studentUserName){
        setStudentAccountDetailsFromDB(studentUserName, false);
        removeAccountHoldsIfAny();
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //localDate = localDate.minusMonths(3);
        String pastCourseBeginDate = localDate.format(formatter);
        executeUpdateCourseBeginDateToBackDate(UPDATE_COURSE_BEGIN_DATE_TO_BACK_DATE_SP_AD_DB, getUserAccountDetails().getApplicationNumber(), pastCourseBeginDate, studentUserName, AD_DATA_BASE);
    }

    public void removeAccountHoldsIfAny(){
        executeQuery(SET_PROOF_OF_COMPLETION_NO_AD_DB.replaceAll(APPLICATION_NUMBER_DATA,getUserAccountDetails().getApplicationNumber()),AD_DATA_BASE);
        ArrayList<HashMap<String,String>> holdReasonList;
        while (executeAndGetSelectQueryData(FETCH_HOLD_REASON_LIST_AD_DB.replaceAll(APPLICATION_NUMBER_DATA, getUserAccountDetails().getApplicationNumber()), AD_DATA_BASE, true).size()>0){
            holdReasonList = executeAndGetSelectQueryData(FETCH_HOLD_REASON_LIST_AD_DB.replaceAll(APPLICATION_NUMBER_DATA, getUserAccountDetails().getApplicationNumber()), AD_DATA_BASE, true);
            removeABAHold(getUserAccountDetails().getApplicationNumber(),holdReasonList);
            markApplicationAsCompleted(getUserAccountDetails().getApplicationNumber());
            implicitWaitInSeconds(10);
        }

        ArrayList<HashMap<String,String>> navHoldReasonList = executeAndGetSelectQueryData(FETCH_NAV_HOLD_REASON_LIST_AD_DB
                .replaceAll(ACCOUNT_NUMBER_DATA,getUserAccountDetails().getAccountNumber()).replaceAll(APPLICATION_NUMBER_DATA, getUserAccountDetails().getApplicationNumber()),AD_DATA_BASE, true);
        if(navHoldReasonList.size()>0){
            for(HashMap<String,String> navHoldReason:navHoldReasonList){
                given().when().multiPart(ApiServiceConstants.request,ApiServiceConstants.resolveHold)
                        .multiPart(ApiServiceConstants.key,properties.getProperty(APP_KEY))
                        .multiPart(ApiServiceConstants.cart,navHoldReason.get(HOLD_CODE))
                        .multiPart(ApiServiceConstants.impersonationCode,navService)
                        .header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER + properties.getProperty(API_AUTH_KEY))
                        .get(properties.get(CommonConstants.API_END_URL).toString());
            }
        }
    }
}
