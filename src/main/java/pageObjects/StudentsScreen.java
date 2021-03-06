package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import constants.Calendar;
import constants.CommonConstants;
import constants.DataBaseQueryConstant;
import constants.TableColumn;
import dataProvider.DataProviders;
import elementConstants.*;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;
import static constants.CommonConstants.EVENT_ID;
import static constants.CommonConstants.*;
import static constants.TableColumn.*;
import static elementConstants.Enrollments.CURSIVE;
import static elementConstants.Students.*;

public class StudentsScreen extends GenericAction {

    HashMap<String, Boolean> videoViewStatusVideoLibrary = new HashMap<>();
    public String uploadWordFilePath = implementPath(System.getProperty("user.dir") + "\\src\\main\\resources\\testData\\AutomationTest.docx");
    private static ArrayList<String[]> submittedAssessmentList = new ArrayList<String[]>();

    public StudentsScreen validateStudentInformationSection(String studentName) {
        if (isElementExists(Students.STUDENT_INFORMATION, false)) {
            String oldURL;
            List<String> widgetList = Arrays.asList(CALENDAR_SETTINGS, IMAGE, LOGIN_INFORMATION,
                    PROGRESS_REPORTS, ASSESSMENT_PERMISSIONS);
            bringElementIntoView(Dashboard.MY_STUDENTS);
            for (String widget : widgetList) {
                try {
                    oldURL = getCurrentURL();
                    click(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(widget)), false);
                    switch (widget) {
                        case Students.CALENDAR_SETTINGS:
                            String calendarHeader = String.format(Students.calendarPopupHeader, String.format(Students.CHANGE_CALENDAR_LENGTH, studentName));
                            softAssertions.assertThat(isElementExists(calendarHeader, false))
                                    .as(calendarHeader + " setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.calendarPopup), false);
                            break;
                        case Students.IMAGE:
                            String imageHeader = String.format(Students.imagePopupHeader, String.format(Students.CHANGE_IMAGE, studentName));
                            softAssertions.assertThat(isElementExists(imageHeader, false))
                                    .as(imageHeader + " setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.imagePopup), false);
                            break;
                        case Students.LOGIN_INFORMATION:
                            softAssertions.assertThat(getElementText(Students.loginInfoPopupHeader).trim().equals(String.format(Students.CHANGE_LOGIN_INFO, studentName)))
                                    .as(String.format(Students.CHANGE_LOGIN_INFO, studentName) + " setting header is not appeared identifier:\n" + Students.loginInfoPopupHeader).isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.loginInfoPopup), false);
                            break;
                        case Students.GRADE_ALERT_SETTING:
                            String gradeAlertSettingHeader = String.format(Students.gradeAlertHeader, Students.GRADE_ALERT_SETTING_HEADER);
                            softAssertions.assertThat(isElementExists(gradeAlertSettingHeader, false))
                                    .as(gradeAlertSettingHeader + " setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.gradeAlertSettingPopup), false);
                            break;
                        case Students.PROGRESS_REPORTS:
                            waitForPageTobeLoaded();
                            softAssertions.assertThat(isURLContainsGivenText(Students.PROGRESS_REPORTS.replaceAll("\\s", "")) && !(getCurrentURL().equals(oldURL)))
                                    .as("Navigated URL is not related to " + Students.PROGRESS_REPORTS + "\n current URL:" + getCurrentURL() + "\n Old URL:" + oldURL).isTrue();
                            softAssertions.assertThat(isElementExists(Students.PROGRESS_REPORTS, false))
                                    .as("Navigated page is not header " + Students.PROGRESS_REPORTS).isTrue();
                            if (!getCurrentURL().equals(oldURL)) {
                                back();
                            }
                            break;
                        case Students.ASSESSMENT_PERMISSIONS:
                            waitForPageTobeLoaded();
                            softAssertions.assertThat(getCurrentURL().indexOf(Students.ASSESSMENT_PERMISSIONS.replaceAll("\\s", "")) > 0 && !(getCurrentURL().equals(oldURL)))
                                    .as("Navigated URL is not related to " + Students.ASSESSMENT_PERMISSIONS + "\n current URL:" + getCurrentURL() + "\n Old URL:" + oldURL).isTrue();
                            softAssertions.assertThat(isElementExists(Students.ASSESSMENT_PERMISSIONS, false))
                                    .as("Navigated page is not header " + Students.ASSESSMENT_PERMISSIONS).isTrue();
                            if (!getCurrentURL().equals(oldURL)) {
                                back();
                            }
                            break;
                        default:
                            softAssertions.fail("My Student Information validation not took place for :" + widget);

                    }

                } catch (ElementNotFound e) {
                    softAssertions.fail(widget + " widget is not present in My Student info section");
                    continue;
                }
            }
        } else {
            Assert.fail(Students.STUDENT_INFORMATION + " section is not present on Students screen");
        }
        return this;
    }

    public StudentsScreen navigateToProgressReportWidget(String studentName) {
        click(bringElementIntoView(String.format(progressReportsStudentLink,studentName)));
        waitForPageTobeLoaded();
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(PROGRESS_REPORTS))));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen navigateToCalendarSettingsWidget() {
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(CALENDAR_SETTINGS))));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen navigateToImageWidget() {
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(IMAGE))));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen navigateToLoginInformationWidget() {
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(LOGIN_INFORMATION))));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen navigateToGradeAlertSettingWidget() {
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(GRADE_ALERT_SETTING))));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen navigateToProgressReportsWidget() {
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(PROGRESS_REPORTS))));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen navigateToAssessmentPermissionWidget() {
        click(bringElementIntoView(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(ASSESSMENT_PERMISSIONS))));
        waitForPageTobeLoaded();
        return this;
    }

    @SneakyThrows
    private String getStudentInformationWidgetLinkId(String widget) {
        switch (widget) {
            case CALENDAR_SETTINGS:
                return "pnlCalendarLength";
            case IMAGE:
                return "pnlImage";
            case LOGIN_INFORMATION:
                return "pnlLoginInfo";
            case GRADE_ALERT_SETTING:
                return "pnlAssessmentThreshold";
            case PROGRESS_REPORTS:
                return "pnlProgressReports";
            case ASSESSMENT_PERMISSIONS:
                return "pnlAssessmentPermissions";
            default:
                throw new Exception("Wrong widget name passed");
        }
    }

    @Step("Changing the student assessment details")
    public StudentsScreen changeStudentAssessmentDetails(String months, String permissionToMakeCalendarChanges) {
        click(String.format(Students.calendarAssessmentModificationRadioBtn, months), false);
        bringElementIntoView(Students.updateCalendarBtn);
        click(Students.updateCalendarBtn, false);
        waitForPageTobeLoaded();
        if (permissionToMakeCalendarChanges.length() > 0) {
            navigateToCalendarSettingsWidget();
            click(bringElementIntoView(String.format(calendarAssessmentModificationRadioBtn, permissionToMakeCalendarChanges)));
            click(Students.saveCalendarPermission, false);
            waitForPageTobeLoaded();
        }
        return this;
    }

    @Step("validating the student assessment details")
    public StudentsScreen validateStudentAssessmentDetails(String selectedMonth, String selectedPermission) {
        softAssertions.assertThat(isSelected(String.format(Students.calendarAssessmentModificationRadioBtn, selectedMonth)))
                .as(selectedMonth + " is not selected on Student's assessment(Calendar settings) page").isTrue();
        softAssertions.assertThat(isSelected(String.format(Students.calendarAssessmentModificationRadioBtn, selectedPermission)))
                .as(selectedPermission + " is not selected on Student's assessment(Calendar settings) page").isTrue();
        click(String.format(CommonConstants.closeXpath, Students.calendarPopup), false);
        return this;
    }

    public StudentsScreen enterStudentGrades(boolean isSubmissionForAllSubject, boolean isSubmitAllGradingPeriod, String signature) {
        //for loop according to db data
        String subject;
        String form;
        String school;
        String version;
        String boxLetter;
        String tableName;
        String reportTableSectionType;
        String reportTableSectionName;
        List<String> gradingPeriodList;
        int submittedReportCount = 0;
        String applicationNumber = getUserAccountDetails().getApplicationNumber();
        String accountNumber = getUserAccountDetails().getAccountNumber();
        ArrayList<HashMap<String, String>> subjectList = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_PROGRESS_REPORT_SUBJECT_LIST_AD_DB
                .replaceAll(APPLICATION_NUMBER_DATA, applicationNumber).replaceAll(ACCOUNT_NUMBER_DATA, accountNumber), CommonConstants.AD_DATA_BASE, false);
        ArrayList<HashMap<String, String>> progressReportTableSectionList;
        ArrayList<HashMap<String, String>> progressReportSectionTableList;
        ArrayList<HashMap<String, String>> progressReportTableRows;
        for(HashMap<String, String> subjectListRow:subjectList){
            subject = subjectListRow.get(SUBJECT_NAME);
            gradingPeriodList = getGradingPeriod(subjectListRow);
            switchToLastOrNewWindow();
            selectSubjectOnProgressReportPage(subject);
            for(String gradingPeriod:gradingPeriodList){
                form = subjectListRow.get(FORM).concat(gradingPeriod);
                school = subjectListRow.get(SCHOOL);
                version = subjectListRow.get(VERSION);

                progressReportTableSectionList =  executeAndGetSelectQueryData(DataBaseQueryConstant.GET_PROGRESS_REPORT_TABLE_SECTION_LIST_AD_DB
                        .replaceAll(FORM_DATA, form).replaceAll(SCHOOL_DATA, school).replaceAll(VERSION_DATA,version), CommonConstants.AD_DATA_BASE, false);
                chooseGradingPeriod(gradingPeriod);
                if(!isElementExists(Students.submit, false)){
                    continue;
                }
                for(HashMap<String, String> tableSectionDetailsRow:progressReportTableSectionList){
                    reportTableSectionType = tableSectionDetailsRow.get(REPORT_TABLE_SECTION_TYPE);
                    reportTableSectionName = tableSectionDetailsRow.get(REPORT_TABLE_SECTION_NAME);
                    progressReportSectionTableList =  executeAndGetSelectQueryData(DataBaseQueryConstant.GET_PROGRESS_REPORT_SECTION_TABLE_LIST_AD_DB
                            .replaceAll(APPLICATION_NUMBER_DATA, applicationNumber).replaceAll(FORM_DATA, form).replaceAll(VERSION_DATA,version).
                                    replaceAll(REPORT_TABLE_SECTION_TYPE_DATA,reportTableSectionType).replaceAll(SCHOOL_DATA,school), CommonConstants.AD_DATA_BASE, false);
                    for(HashMap<String, String> sectionTableDetails:progressReportSectionTableList){
                        boxLetter = sectionTableDetails.get(TXH_BOXLTR);
                        tableName = sectionTableDetails.get(TABLE_NAME);
                        progressReportTableRows =  getProgressReportTableRows(form, school, boxLetter, version, applicationNumber);
                        fillGradesToSection(reportTableSectionName,tableName, progressReportTableRows, false);
                    }
                }
                waitForPageTobeLoaded();
                if(!isSubmitAllGradingPeriod){
                    break;
                }
            }
            fillBookReport();
            signProgressReport(signature);
            //submitProgressReport();
            if(!isSubmissionForAllSubject){
                break;
            }
        }
        return this;
    }

    private void fillBookReport() {
        List<SelenideElement> bookReportsElements = getElements(Students.bookReports);
        int i = 1;
        for(SelenideElement bookReportParent:bookReportsElements){
            type(bringChildElementIntoView(bookReportParent,String.format(Students.bookReportTextBox,i,AUTHOR)),"Automation author");
            type(bringChildElementIntoView(bookReportParent,String.format(Students.bookReportTextBox,i, TITLE)),"Automation title");
            type(bringChildElementIntoView(bookReportParent,String.format(Students.bookReportTextBox,i, PAGES_READ)),Integer.toString(new Random().nextInt(100)));
            i++;
        }
    }

    private ArrayList<HashMap<String, String>> getProgressReportTableRows(String form, String school, String boxLetter, String version, String applicationNumber) {
        ArrayList<HashMap<String, String>> progressReportTableRowsList = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_PROGRESS_REPORT_TABLE_ROWS_AD_DB
                .replaceAll(FORM_DATA, form).replaceAll(SCHOOL_DATA, school).replaceAll(TXH_BOXLTR_DATA,boxLetter).
                        replaceAll(VERSION_DATA,version).replaceAll(APPLICATION_NUMBER_DATA,applicationNumber), CommonConstants.AD_DATA_BASE, false);
        ArrayList<HashMap<String, String>> progressReportTableRowsTemp = new ArrayList<>();
        progressReportTableRowsTemp.addAll(progressReportTableRowsList);

        for(HashMap<String, String> progressReportTableRow:progressReportTableRowsList){
            if(progressReportTableRow.get(ITEM_TYPE).matches("X|C|M")){
                progressReportTableRowsTemp.addAll(getAndAddParentPRViewSuiteItemGrade(form, progressReportTableRow.get(ITEM_NUMBER), school, boxLetter, version, applicationNumber));
            }
        }
        return progressReportTableRowsTemp;
    }

    private Collection<? extends HashMap<String, String>> getAndAddParentPRViewSuiteItemGrade(String form, String itemNumber, String school, String boxLetter, String version, String applicationNumber) {
        return executeAndGetSelectQueryData(DataBaseQueryConstant.GET_PROGRESS_REPORT_ADDITIONAL_TABLE_PARENT_ROWS
                .replaceAll(FORM_DATA, form).replaceAll(ITEM_NUMBER_DATA, itemNumber).replaceAll(SCHOOL_DATA,school).
                        replaceAll(TXH_BOXLTR_DATA,boxLetter).replaceAll(VERSION_DATA,version).replaceAll(APPLICATION_NUMBER_DATA,applicationNumber), CommonConstants.AD_DATA_BASE, false);
    }

    private List<String> getGradingPeriod(HashMap<String, String> subjectListRow) {
        ArrayList<String> gradingPeriodList = new ArrayList<>();
        gradingPeriodList.add(subjectListRow.get(GRD_PRD1));
        gradingPeriodList.add(subjectListRow.get(GRD_PRD2));
        gradingPeriodList.add(subjectListRow.get(GRD_PRD3));
        gradingPeriodList.add(subjectListRow.get(GRD_PRD4));
        return gradingPeriodList.stream().filter(e->e!=null).collect(Collectors.toList());
    }

    public StudentsScreen signProgressReport(String signature) {
        type(bringElementIntoView(Students.progressReportSignatureTextBox), signature);
        return this;
    }

    public StudentsScreen submitProgressReport() {
        click(bringElementIntoView(Students.submit));
        waitForPageTobeLoaded();
        return this;
    }

    private void fillGradesToSection(String reportTableSectionName, String tableName, ArrayList<HashMap<String, String>> tableRows, boolean validateMoreThan100Marks) {
        SelenideElement gradeTableParentElement;
        boolean isInputTextBoxShouldPresent = false;
            ArrayList<Float> givenGradeList = new ArrayList<>();
            float gradeTobeGiven = 0;
            boolean isInputBoxPresent;
            if (bringElementIntoView(String.format(sectionHeader, getSectionID(reportTableSectionName), reportTableSectionName)) != null) {
                    gradeTableParentElement = bringElementIntoView(String.format(Students.gradeTableHeader, getSectionID(reportTableSectionName), tableName));
                    if (gradeTableParentElement != null) {
                        for (HashMap<String, String> row : tableRows) {
                            //isInputTextBoxShouldPresent = false;//need to fetch from db
                            //if (isInputTextBoxShouldPresent) {
                                isInputBoxPresent = Boolean.parseBoolean(row.get(SHOW_GRADE_TEXT_BOX));
                                if(isInputBoxPresent) {
                                    gradeTobeGiven = new Random().nextInt(101);
                                    givenGradeList.add(gradeTobeGiven);
                                }
                                addGrade(row, reportTableSectionName, tableName, validateMoreThan100Marks, isInputBoxPresent, gradeTobeGiven);

                               // validateGradeInputBox(row, reportTableSectionName, tableName);
                        }
                        if (givenGradeList.size() > 0) {
                            validateAverage(givenGradeList, reportTableSectionName, tableName);
                        }
                    } else {
                        softAssertions.fail(tableName + " is not present in given sectionMap["+reportTableSectionName+"] of Progress Report page");
                    }
            } else {
                softAssertions.fail(reportTableSectionName + ": sectionMap is not present on Progress Report page");
            }
        }

    private void validateAverage(ArrayList<Float> givenGradeList, String sectionName, String gradeTableName) {
        float totalGrade = 0;
        for (float grade : givenGradeList) {
            totalGrade = totalGrade + grade;
        }
        tempXpath = String.format(Students.gradeAverageXpath, getSectionID(sectionName), gradeTableName, Students.AVERAGE);
        String expectedAverage = String.format("%.1f",(Float.parseFloat(String.valueOf(totalGrade)) / (100 * givenGradeList.size())) * 100);
        //implicitWaitInSeconds(3);
        softAssertions.assertThat(getElementText(tempXpath).equals(expectedAverage))
                .as(sectionName + "->" + gradeTableName + "'s average is not matching with table average. \nexpected average:"
                        + expectedAverage + "actual average:" + getElementText(tempXpath)).isTrue();
    }

    private StudentsScreen addGrade(HashMap<String,String> tableRow, String sectionName, String gradeTableName, boolean validateMoreThan100Marks, boolean isInputBoxPresent, float gradeTobeGiven) {
        String lesson = tableRow.get(MY_LESSONS_TODAY_LESSON_LIST);
        String description = tableRow.get(ITEM_DESCRIPTION);
        String grade = (tableRow.get(ITEM_GRADE) != null)?tableRow.get(ITEM_GRADE).trim():tableRow.get(ITEM_GRADE);
        boolean elementExists;
        tempXpath = (isInputBoxPresent)?String.format(gradeTextBox, getSectionID(sectionName), gradeTableName, lesson,
                description):(grade.equals("777.7"))?String.format(pendingGrade, getSectionID(sectionName), gradeTableName, lesson,
                description):String.format(hiddenGradeTextBox, getSectionID(sectionName), gradeTableName, lesson,
                description);

        elementExists = isElementExists(tempXpath, false);
        if (elementExists && isInputBoxPresent) {
            if (validateMoreThan100Marks) {
                type(tempXpath, "101");
                pressTab(getElement(tempXpath));
                softAssertions.assertThat(isElementExists(Students.GRADE_TEXT_BOX_VALIDATOR_MESSAGE, false))
                        .as(GRADE_TEXT_BOX_VALIDATOR_MESSAGE + " grade text box validator message is not appeared, though we enter text 101").isTrue();
            }
            type(tempXpath, Float.toString(gradeTobeGiven));
            pressTab(tempXpath);
        } else if(grade.equals("777.7")){
            softAssertions.assertThat(elementExists).as("Progress report with \n Section name:"+
                    sectionName+"\n GradeTableName:"+gradeTableName+"\nLesson:"+lesson+"\n Description:"+description+"is not present with status pending").isTrue();
        }else if (sectionName.equals(MISC_NON_GRADED)){
            if(gradeTableName.equals(COMPOSITIONS)){
                completeComposition(sectionName, gradeTableName, lesson, description);
            }
        } else{
            softAssertions.assertThat((tableRow.get(ITEM_TYPE).matches("X|C|M|S")||tableRow.get(TEST_ID).equals("0"))?isElementExists(description, false):elementExists).as("Progress report with \n Section name:"+
                    sectionName+"\n GradeTableName:"+gradeTableName+"\nLesson:"+lesson+"\n Description:"+description+"is not present with "+((isInputBoxPresent)?"[GradeInputTextBox]":"[--] \nxpath:"+tempXpath)).isTrue();
        }
        return this;
    }

    private void completeComposition(String sectionName, String gradeTableName, String lesson, String description) {
        tempXpath = String.format(compositionSubmitLink, getSectionID(sectionName), gradeTableName, lesson,
                description);
        click(tempXpath, false);
        waitForPageTobeLoaded();
        click(submitOnline, false);
        type(fileUpload,uploadWordFilePath);
        submitProgressReport();
        waitForElementTobeVisibleOrMoveAhead(BACK_TO_SERVICE_REPORT);
        click(BACK_TO_SERVICE_REPORT, false);
    }

    private StudentsScreen validateGradeInputBox(HashMap<String,String> tableRow, String sectionName, String gradeTableName) {
        String lesson = tableRow.get(MY_LESSONS_TODAY_LESSON_LIST);
        String description = tableRow.get(DESCRIPTION);
        tempXpath = String.format(gradeHiddenTextBox, getSectionID(sectionName), gradeTableName, lesson,
                description);
        softAssertions.assertThat(isElementExists(tempXpath, false)).as("Grade input text box is not present for following details\nSectionName:" + sectionName + "\nTableName:" + gradeTableName +
                "\nLesson:" + lesson + "\nDescription:" + description + "\n xpath:" + tempXpath).isTrue();
        return this;
    }

    @SneakyThrows
    private String getSectionID(String sectionName) {
        switch (sectionName) {
            case QUIZ:
                return "divQ";
            case TEST:
                return "divT";
            case EXAM:
                return "divE";
            case MISC_NON_GRADED:
                return "divX";
            case REVIEW:
                return "divV";
            default:
                throw new Exception("["+sectionName+"] Section name is other that :" + QUIZ + "," + TEST + "," + EXAM + "," + MISC_NON_GRADED);

        }
    }

    public StudentsScreen chooseGradingPeriod(String gradingPeriod) {
        click(bringElementIntoView(String.format(Students.gradingPeriod, gradingPeriod)));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen selectSubjectOnProgressReportPage(String subject) {
        selectValueFromDropDownVideo(Students.projectReportSubjectDropDown, subject);
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen validateMyToDoListData() {
        if (isElementExists(Students.toDoListHeader, false)) {
            String studentID = getUserAccountDetails().getStudentId();//getuserAccountDetails.get(TableColumn.STUDENT_ID);
            String subject;
            String startDate;
            String lesson;

            click(Students.updateAll, false);
            softAssertions.assertThat(isElementTextEquals(Students.toDoListHeader, Students.MY_TO_DO_LIST))
                    .as("My to do list header is not equal to " + Students.MY_TO_DO_LIST).isTrue();

            ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_LIST_LESSONS_SD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), SD_DATA_BASE, false);
            ArrayList<HashMap<String, String>> myAssignmentsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_DO_LIST_ASSIGNMENTS_AD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE, false);
            for (HashMap<String, String> lessonsMap : myLessonsToday) {
                subject = lessonsMap.get(SHORT_DESCRIPTION);
                lesson = lessonsMap.get(LONG_DESCRIPTION);
                startDate = getFormattedDate(lessonsMap.get(START_DATE), Calendar.YYYY_MM_DD_HH_MM_SS, Calendar.dayMonthSingleDate);

                tempXpath = String.format(myToDoListLesson, subject, startDate, lesson);
                softAssertions.assertThat(isElementExists(tempXpath, false))
                        .as(subject + ":" + lesson + ":" + startDate + " is not present in My to do list. \n identifier:" + tempXpath).isTrue();

            }
            for (HashMap<String, String> assignmentsMap : myAssignmentsToday) {
                subject = assignmentsMap.get(SHORT_DESCRIPTION);
                lesson = assignmentsMap.get(LONG_DESCRIPTION);
                startDate = getFormattedDate(assignmentsMap.get(START_DATE), Calendar.YYYY_MM_DD_HH_MM_SS, Calendar.dayMonthSingleDate);

                tempXpath = String.format(myToDoListLesson, subject, startDate, lesson);
                softAssertions.assertThat(isElementExists(tempXpath, false))
                        .as(subject + ":" + lesson + ":" + startDate + " is not present in My to do list. \n identifier:" + tempXpath).isTrue();
            }
        } else {
            softAssertions.fail("My to do list section is not present on UI");
        }
        return this;
    }

    public StudentsScreen validateAccountInfoPage() {
        navigateToAccountInfoWidget(Students.PASSWORD);
        softAssertions.assertThat(isElementExists(Students.changePasswordHeader, false)).as("Change Password pop-up is not appeared").isTrue();
        softAssertions.assertThat(isElementExists(Students.currentPassword, false)).as("Current password text box is not present on Change Password pop-up").isTrue();
        softAssertions.assertThat(isElementExists(Students.newPassword, false)).as("New password text box is not present on Change Password pop-up").isTrue();
        softAssertions.assertThat(isElementTextEquals(Students.forgotPasswordMessage, Students.FORGOT_PASSWORD_MESSAGE))
                .as("Forgot password message of Change Password pop-up is not equal to " + Students.FORGOT_PASSWORD_MESSAGE).isTrue();
        softAssertions.assertThat(isElementExists(Students.changePasswordSubmitBtn, false))
                .as("Change Password button is not present on Change Password pop-up.").isTrue();
        click(String.format(CommonConstants.closeXpath, Students.changePasswordPopup), false);
        return this;
    }

    public StudentsScreen navigateToAccountInfoWidget(String widgetName) {
        click(String.format(Students.accountInfoWidgetLink, widgetName), false);
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen validateRequestTranScriptFunctionality(String studentName) {
        openTranScriptRequestPopUp();
        if (isElementExists(Students.yourNameTextBox, false)) {
            type(Students.yourNameTextBox, studentName);
        } else {
            softAssertions.fail("Your name text box is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.relationshipSelectBox, false)) {
            selectByVisibleText(Students.relationshipSelectBox, Students.SELF);
        } else {
            softAssertions.fail("Relationship to Student select box is not present on Transcript Request pop-up");
        }

        if (isElementExists(Students.phoneNumberTextBox, false)) {
            type(Students.phoneNumberTextBox, RandomStringUtils.randomNumeric(10));
        } else {
            softAssertions.fail("Phone number box is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.sendMyTranscriptToPCCRadio, false)) {
            click(Students.sendMyTranscriptToPCCRadio, false);
        } else {
            softAssertions.fail("Send my transcript to PCC ratio button is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.attentionLineTextBox, false)) {
            type(Students.attentionLineTextBox, CommonConstants.AUTOMATION_TEST);
        } else {
            softAssertions.fail("Attention line text box is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.sendImmediately, false)) {
            click(Students.sendImmediately, false);
        } else {
            softAssertions.fail("Send transcript immediately with current status. radio button is not present on Transcript Request pop-up");
        }
        clickWithoutPageLoadWait(Students.submitTranScriptBtn);
        implicitWaitInSeconds(10);
        if (isAlertPresent()) {
            softAssertions.assertThat(getDriver().switchTo().alert().getText().equals(Students.TRANSCRIPT_ALERT_TEXT))
                    .as("Transcript success alert text is not equal to " + Students.TRANSCRIPT_ALERT_TEXT).isTrue();
            acceptAlert();
            waitForPageTobeLoaded();
        } else {
            softAssertions.fail("Transcript success alert is not appeared");
        }
        return this;
    }

    public StudentsScreen validateMyRecentGrades(String studentName) {
        if (isElementExists(Students.myRecentGradeSection, false)) {
            String assignment;
            String grade;
            String studentID = getUserAccountDetails().getStudentId();//getUserAccountDetails().get(STUDENT_ID);//fetch from DB
            bringElementIntoView(getElements(Students.myRecentGradeRows).get(getElements(Students.myRecentGradeRows).size() - 1));
            int myRecentGradeRowCount = getElements(Students.myRecentGradeRows).size() - 1;
            //add getStudentIdFromDB(studentName) to string.format
            ArrayList<HashMap<String, String>> myGrades = executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_GRADE_WITH_SUBJECT_AD_DB
                    .replaceAll(TableColumn.ROW_COUNT_DATA, Integer.toString(myRecentGradeRowCount)).replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE, true);

            for (HashMap<String, String> rowData : myGrades) {
                assignment = rowData.get(TableColumn.ASSESSMENT).trim();
                grade = rowData.get(TableColumn.GRADE).trim();
                softAssertions.assertThat(isElementExists(String.format(Students.subjectWithGradeRow, assignment, grade), false))
                        .as(assignment + ":" + grade + " row is not present in My recent grade section").isTrue();
            }
        } else {
            softAssertions.fail("My recent grade section is not present on UI");
        }

        return this;
    }

    public StudentsScreen openTranScriptRequestPopUp() {
        click(Students.requestTranScriptBtn, false);
        waitForElementTobeExist(Students.submitTranScriptBtn);
        return this;
    }

    public StudentsScreen verifyLastViewedLessons() {
        boolean isValidationDone = false;
        if (isElementExists(Students.lastViewedVideoLessonsSection, false)) {
            String loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);
            ArrayList<HashMap<String, String>> lastViewedLessonsDataMapList = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_LAST_VIEWED_LESSON_DATA_SD_DB.replaceAll(LOGIN_ID_DATA, loginId), SD_DATA_BASE, true);
            for (HashMap<String, String> lastViewedLesson : lastViewedLessonsDataMapList) {

            }
        } else {
            softAssertions.fail("Last viewed lesson section is not present on UI");
        }
        if (!isValidationDone) {
            log("Last viewed lesson table is not having any data.");
        }

        return this;
    }

    public StudentsScreen navigateToStartWatchingYourLessonsLink() {
        bringChildElementIntoView(getElement(Students.lastViewedVideoLessonsSection), Students.startWatchingYourLessonsLin);
        click(getChildElement(getElement(Students.lastViewedVideoLessonsSection), Students.startWatchingYourLessonsLin));
        waitForPageTobeLoaded();
        new DashboardScreen().waitAndCloseWidgetTourPopup();
        return this;
    }

    public StudentsScreen validateCursiveWritingVideo(){
        softAssertions.assertThat(isElementExists(seatworkExplanationCursive, true)).as(seatworkExplanationCursive + " lesson is not present on UI").isTrue();
        softAssertions.assertThat(isElementExists(seatworkExplanationCursive, true)).as(cursiveWriting + " lesson is not present on UI").isTrue();
        selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, WRITING_1);
        click(lessonOne, false);
        softAssertions.assertThat(isElementExists(cursiveWritingVideo,false)).as("Cursive writing video is not played").isTrue();
        return this;
    }

    public StudentsScreen skipPracticeTest(){
        click(getElement(takeMyPracticeTest));
        waitForPageTobeLoaded();
        waitForElementTobeExist(Students.linkitBeginBtn, veryLongWait);
        click(getVisibleElement(Students.linkitBeginBtn));
        waitForPageTobeLoaded();
        waitForElementTobeVisibleOrMoveAhead(Students.linkitQuestionPanel, veryLongWait);
        click(getElement(closeButton));
        click(linkitstopTestButton, false);
        click(linkitYesSubmit, false);
        click(linkitStartAnotherSession, false);
        return this;
    }

    public StudentsScreen validateDigitalAssessmentsAreLockedOrNot(boolean isValidateDataFromExcel, String testDataExcelName) {
        String subject;
        String lesson;
        boolean isLocked;
        String studentID = getUserAccountDetails().getStudentId();
        List<DigitalAssessmentsTestData> fromExcelDigitalAssessmentsTestDataArrayList = new ArrayList<>();
        List<DigitalAssessmentsTestData> fromDataBaseDigitalAssessmentsTestDataArrayList = new ArrayList<>();
        if(isValidateDataFromExcel){
            fromExcelDigitalAssessmentsTestDataArrayList = getDigitalAssessmentTestDataFromExcel(testDataExcelName);
        } else {
            ArrayList<HashMap<String, String>> myAssessmentToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_DO_LIST_ASSIGNMENTS_AD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE, false);
            myAssessmentToday.stream().forEach(e->fromDataBaseDigitalAssessmentsTestDataArrayList.add(DigitalAssessmentsTestData.builder()
                    .subject(e.get(SUBJECT)).quiz(e.get(MY_LESSONS_TODAY_LESSON_LIST)).isLocked(getIsLocked(e.get(LOCKED), Students.Y)).build()));
            refresh();
        }
            //userAccountDetails.get(TableColumn.STUDENT_ID);;
            waitForPageTobeLoaded();
            for (DigitalAssessmentsTestData digitalAssessmentsTestData : (isValidateDataFromExcel)? fromExcelDigitalAssessmentsTestDataArrayList :fromDataBaseDigitalAssessmentsTestDataArrayList) {
                subject = digitalAssessmentsTestData.getSubject();
                lesson = digitalAssessmentsTestData.getQuiz();
                isLocked = digitalAssessmentsTestData.isLocked();//need to fetch from DB;
                if (isLocked) {
                    softAssertions.assertThat(isElementExists(String.format(Students.assessmentLocked, subject, lesson), false))
                            .as(subject + "-" + lesson + " subject is not locked on assessment page").isTrue();
                } else {
                    softAssertions.assertThat(isElementExists(String.format(Students.assessmentUnlocked, subject, lesson), false))
                            .as("[" + subject + "-" + lesson + "] is not available for assessment or Locked, though lesson is completed").isTrue();
                }
            }

        return this;
    }

    private boolean getIsLocked(String dataColumnText, String criteria) {
        try {
            return (dataColumnText.equals(criteria)) ? true : false;
        }catch (NullPointerException e){
            return false;
        }
    }

    private boolean isAllAssignmentsLocked(ArrayList<HashMap<String, String>> assessmentList) {
        return !assessmentList.stream().anyMatch(e->e.get(LOCK_ASSIGNMENT) == null);
    }

    /**
     * Here we are validating only video list, not clicking on video link
     *
     * @return
     * @param isValidationWithExcel
     * @param videoListSheetName
     * @param fromDataRowNumber
     * @param toDataRowNumber
     */
    public StudentsScreen validateMyLessonsTodaySectionData(boolean isValidationWithExcel, String videoListSheetName, int fromDataRowNumber, int toDataRowNumber, String testDataExcelName) {
        //getExcelDataSet()
        if (isElementExists(Students.MY_LESSONS_TODAY, false)) {
            if (isElementExists(Students.lessonsToday, false)) {
                String studentID = getUserAccountDetails().getStudentId();//userAccountDetails.get(TableColumn.STUDENT_ID);
                String myLessonsVideoLink;
                String subject;
                String lesson;
                int counter = 0;
                if(isValidationWithExcel){
                    VideoListTestData videoListTestData = getVideoListTestDataFroExcel(videoListSheetName, fromDataRowNumber, toDataRowNumber, testDataExcelName);
                    for(String subjectName: videoListTestData.getMyLessonsTodaySubjectList()){
                        lesson = videoListTestData.getMyLessonsTodayLessonList().get(counter);
                        myLessonsVideoLink = String.format(Students.myLessonsTodayVideoLink, subjectName, lesson);
                        softAssertions.assertThat(isElementExists(myLessonsVideoLink, false))
                                .as(lesson + " of " + subjectName + " subject is not present in My lessons today section").isTrue();
                        counter++;
                    }
                }else {
                    ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                            .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), CommonConstants.SD_DATA_BASE, false);
                    for (HashMap<String, String> row : myLessonsToday) {
                        subject = row.get(SHORT_DESCRIPTION);
                        lesson = row.get(LONG_DESCRIPTION);
                        myLessonsVideoLink = String.format(Students.myLessonsTodayVideoLink, subject, lesson);
                        softAssertions.assertThat(isElementExists(myLessonsVideoLink, false))
                                .as(lesson + " of " + subject + " subject is not present in My lessons today section").isTrue();
                    }
                }
                //Change data of excel as per grade 9 and fetch it
            } else {
                softAssertions.fail(Students.MY_LESSONS_TODAY + " is not having any video link on UI");
            }
        } else {
            softAssertions.fail(Students.MY_LESSONS_TODAY + " section is not present on UI");
        }
        return this;
    }

    /**
     * We are validating My lessons today videos by clicking on link and after changing status from DB it should show completed in video library
     * Also, validating the My today's lesson and Video library having same video.
     *
     * @return
     * @param isValidationWithExcelData
     * @param videoListSheetName
     */
    public StudentsScreen watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary(boolean isValidationWithExcelData, String videoListSheetName, String testDataExcelName) {
        if (isElementExists(Students.MY_LESSONS_TODAY, false)) {
            if (isElementExists(Students.lessonsToday, false)) {
                String myLessonsVideoLink;
                String subject;
                String lesson;
                String longDescription;
                String subscriptionNumber;
                String subscriptionItem;
                String loginId;
                String segmentId;
                boolean completed;
                boolean isVideoAlreadyViewedInVideoLibrary = false;
                int counter = 0;
                String studentID = getUserAccountDetails().getStudentId();//userAccountDetails.get(STUDENT_ID);
                VideoListTestData videoListTestData = getVideoListTestDataFroExcel(videoListSheetName, 0, 0, testDataExcelName);

                if(isValidationWithExcelData){
                    for(String subjectName: videoListTestData.getVideoLibraryDropdownSubjectList()) {
                        subject = subjectName;
                        longDescription = videoListTestData.getVideoLibraryDropdownLongDescriptionList().get(counter);
                        lesson = videoListTestData.getTodayLessonOfVideoLibrary().get(counter);
                        segmentId = videoListTestData.getSegmentId().get(counter);
                        subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
                        subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();
                        loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);

                        isVideoAlreadyViewedInVideoLibrary = getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true);
                        if (isVideoAlreadyViewedInVideoLibrary) {
                            softAssertions.fail("According Video library status [" + lesson + " lesson of " + subject + " subject] is already viewed and completed.");
                        }
                        selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subject);
                        playLessonVideo(subject, longDescription);
                        tempXpath = String.format(Students.playingVideoTitle, subject + " - " + longDescription);
                        softAssertions.assertThat(isElementExists(tempXpath, false))
                                .as(subject + " - " + lesson + ": Running video is not similar to clicked link. \n identifier:" + tempXpath).isTrue();

                        markVideoLessonAsCompleted(subscriptionNumber, subscriptionItem, loginId, segmentId, getUserAccountDetails().getUserId());//getUserAccountDetails().get(USER_ID));
                        refresh();
                        waitForPageTobeLoaded();

                        selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subject);
                        softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true))
                                .as(lesson + " lesson of " + subject + " subject's video status is not updated to viewed/completed in Video library section").isTrue();
                        counter++;
                    }
                }else {
                    ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                            .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), CommonConstants.SD_DATA_BASE, false);

                    for (HashMap<String, String> row : myLessonsToday) {
                        subject = row.get(SHORT_DESCRIPTION);
                        longDescription = row.get(LONG_DESCRIPTION);
                        lesson = row.get(LESSON_NUMBER);
                        completed = (row.get(COMPLETED).equals(Y)) ? true : false;
                        segmentId = row.get(SEGMENT_ID_FK);
                        subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
                        subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();
                        loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);

                        selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subject);
                        isVideoAlreadyViewedInVideoLibrary = getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true);
                        if (isVideoAlreadyViewedInVideoLibrary && !completed) {
                            softAssertions.fail("According Video library status [" + lesson + " lesson of " + subject + " subject] is already viewed and completed.");
                        }

                        if (!isVideoAlreadyViewedInVideoLibrary) {
                            playLessonVideo(subject, longDescription);

                            tempXpath = String.format(Students.playingVideoTitle, subject + " - " + longDescription);
                            softAssertions.assertThat(isElementExists(tempXpath, false))
                                    .as(subject + " - " + lesson + ": Running video is not similar to clicked link. \n identifier:" + tempXpath).isTrue();

                            markVideoLessonAsCompleted(subscriptionNumber, subscriptionItem, loginId, segmentId, getUserAccountDetails().getUserId());//getUserAccountDetails().get(USER_ID));
                            refresh();
                            waitForPageTobeLoaded();

                            selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subject);
                            softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true))
                                    .as(lesson + " lesson of " + subject + " subject's video status is not updated to viewed/completed in Video library section").isTrue();
                        } else {
                            log(subject + ":" + lesson + " is already viewed on My lessons today.");
                        }
                    }
                }
            } else {
                softAssertions.fail(Students.MY_LESSONS_TODAY + " is not having any video link on UI");
            }
        } else {
            softAssertions.fail(Students.MY_LESSONS_TODAY + " section is not present on UI");
        }
        return this;
    }

    private void playLessonVideo(String subject, String longDescription) {
        int retry = 0;
        tempXpath = String.format(Students.myLessonsTodayVideoLink, subject, longDescription);

        while (!isElementExists(String.format(Students.playingVideoTitle, subject + " - " + longDescription), false) && retry < 3) {
            bringElementIntoView(tempXpath);
            //implicitWaitInSeconds(2);
            clickByJavaScript(tempXpath);
            if (isElementExists(Students.restartVideo, false)) {
                click(restartVideo, false);
            }
            retry++;
        }
    }

    public void markVideoLessonAsCompleted(String subscriptionNumber, String subscriptionItem, String loginId, String segmentId, String userId) {
        executeSetVideoCompletedStoredProcedure(DataBaseQueryConstant.MARK_VIDEO_LESSON_AS_COMPLETED_SD_DB, subscriptionNumber, subscriptionItem, loginId,
                segmentId, userId, SD_DATA_BASE);
    }

    public void markAllVideoLessonsAsCompletedForRespectiveStudent(String loginId){
        log("Marking all lessons as completed");
        executeSetAllVideoCompletedStoredProcedure(DataBaseQueryConstant.SET_ALL_VIDEO_COMPLETED_SP_SD_DB,loginId,SD_DATA_BASE);
    }

    public void markAssessmentRelatedAllLessonVideosAsCompleted(String loginId,String subjectId, String endLesson){
        log("Mark all assessment related videos as completed");
        try{
            processSpecificAssessmentToUnlockedStoredProcedure(DataBaseQueryConstant.MARK_ASSESSMENT_RELATED_VIDEO_COMPLETED_SP_SD_DB,loginId,subjectId,endLesson,SD_DATA_BASE);
        }catch (Exception e){
            softAssertions.fail("Stored procedure failed for:\n"+DataBaseQueryConstant.MARK_ASSESSMENT_RELATED_VIDEO_COMPLETED_SP_SD_DB +"\n LoginId:"+loginId +"\n SubjectId:"+subjectId+"\n EndLesson:"+endLesson
            +"\n"+e);
        }
    }

    public void clearAssessmentProcessMarkVideoLessonsAsNotViewed(String loginId,String subjectId, String startLesson, String endLesson){
        log("Mark all assessment related videos as completed");
        try{
            clearProcessOfAssessmentMarkVideoAsNotViewedStoredProcedure(DataBaseQueryConstant.CLEAR_ASSESSMENT_PROCESS_MARK_VIDEO_NOT_VIEWED_SP_SD_DB,loginId,subjectId,startLesson,endLesson,SD_DATA_BASE);
        }catch (Exception e){
            softAssertions.fail("Stored procedure failed for:\n"+DataBaseQueryConstant.MARK_ASSESSMENT_RELATED_VIDEO_COMPLETED_SP_SD_DB +"\n LoginId:"+loginId +"\n SubjectId:"+subjectId+"\n StartLesson"+startLesson+"\n EndLesson:"+endLesson
                    +"\n"+e);
        }
    }

    //Should be comment out
    public StudentsScreen validateVideoLibraryVideoStatusWithDataBase(boolean isValidateWithExcel) {
        if(!isValidateWithExcel) {
            String subject;
            String subjectID;
            String lesson;
            String subscriptionNumber;
            String subscriptionItem;
            String loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);
            String studentID = getUserAccountDetails().getStudentId();//getUserAccountDetails().get(STUDENT_ID);
            boolean isLessonCompleted;

            ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), CommonConstants.SD_DATA_BASE, false);
            ArrayList<String> validatedSubject = new ArrayList<>();
            for (HashMap<String, String> studentLessons : myLessonsToday) {
                subject = studentLessons.get(SHORT_DESCRIPTION);
                if (!validatedSubject.contains(subject)) {
                    subjectID = getStudentSubjectDetailsList().get(subject).getSubjectId();
                    subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
                    subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();

                    ArrayList<HashMap<String, String>> videoLibraryVideos = executeAndGetSelectQueryData(DataBaseQueryConstant.VIDEO_LIBRARY_VIDEOS_SD_DB
                            .replaceAll(LOGIN_ID_DATA, loginId).replaceAll(SUBSCRIPTION_NUMBER_DATA, subscriptionNumber).replaceAll(SUBJECT_ID_DATA, subjectID), CommonConstants.SD_DATA_BASE, false);

                    selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subject);
                    for (HashMap<String, String> video : videoLibraryVideos) {
                        isLessonCompleted = (video.get(COMPLETED).equals(Y)) ? true : false;
                        lesson = video.get(LESSON_NUMBER);
                        try {
                            if (isLessonCompleted) {
                                softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true))
                                        .as(subject + "'s lesson number [" + lesson + "] is not completed in video library.").isTrue();
                            } else {
                                softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true))
                                        .as(subject + "'s lesson number [" + lesson + "] is not locked in video library.").isFalse();
                            }

                        } catch (Throwable e) {
                            softAssertions.fail("Video link not found for Subject:" + subject + "\n Lesson:" + lesson);
                        }
                    }
                    validatedSubject.add(subject);
                }
            }
        }

        return this;
    }


    public StudentsScreen validateIsLessonVideoLinkIsPresentInVideoLibrary(String subject, String lesson, String subjectIDNumber, String subscriptionItemNumber) {
        selectValueFromDropDownVideo(subject, lesson);
        tempXpath = String.format(Students.videoLibraryVideoLinkWithSubId, subjectIDNumber, subscriptionItemNumber, lesson);
        if (isChildElementExists(getElement(Students.videoLibrarySection), tempXpath)) {
            bringElementIntoView(getChildElement(getElement(Students.videoLibrarySection), tempXpath));
            videoViewStatusVideoLibrary.put(subject + ":" + lesson,
                    isLessonCompleted(getClassAttributeValue(getChildElement(getElement(Students.videoLibrarySection), tempXpath))));
        } else {
            softAssertions.fail("Video link is not present in Video Library section for Subject:" + subject + " and Lesson:" + lesson +
                    "\n xpath =" + getChildElement(getElement(Students.videoLibrarySection), tempXpath).toString());
        }
        return this;
    }

    public boolean getIsLessonCompletedStatusFromVideoLibrary(String subscriptionItemNumber, String lesson, boolean isBringLinkIntoView) {
        tempXpath = String.format(Students.videoLibraryVideoLinkWithSubId, lesson, subscriptionItemNumber);
        if (isBringLinkIntoView) {
            bringElementIntoView(getElement(tempXpath));
        }
        return isLessonCompleted(getClassAttributeValue(getElement(tempXpath)));
    }

    public StudentsScreen validateAssessmentsAreLocked() {
        return this;
    }

    public StudentsScreen validateVideoListAvailableOnVideoLibrary(String testDataExcelName){
        VideoListTestData videoListTestData = getVideoListTestDataFroExcel(CommonConstants.GRADE_WISE_VIDEO_LIST, 0, 0, testDataExcelName);
        validateMyLessonsTodayVideoList(removeBlankDataFromList(videoListTestData.getMyLessonsTodaySubjectList()), videoListTestData.getMyLessonsTodayLessonList());
        validateVideoLibraryDropDownList(removeBlankDataFromList(videoListTestData.getVideoLibraryDropdownSubjectList()));
        return this;
    }

    private VideoListTestData getVideoListTestDataFroExcel(String sheetName, int fromDataRowNumber, int toDataRowNumber, String testDataExcelName) {
        Map<String, ArrayList<String>> excelDataHashTable = new DataProviders().getExcelDataInHashTable(sheetName, fromDataRowNumber, toDataRowNumber, testDataExcelName);
        VideoListTestData videoListTestData = VideoListTestData.builder()
                .myLessonsTodaySubjectList(removeBlankDataFromList(excelDataHashTable.get(MY_LESSONS_TODAY_SUBJECT_LIST)))
                .myLessonsTodayLessonList(removeBlankDataFromList(excelDataHashTable.get(MY_LESSONS_TODAY_LESSON_LIST)))
                .videoLibraryDropdownSubjectList(removeBlankDataFromList(excelDataHashTable.get(VIDEO_LIBRARY_DROPDOWN_SUBJECT_LIST)))
                .videoLibraryDropdownLongDescriptionList(removeBlankDataFromList(excelDataHashTable.get(VIDEO_LIBRARY_DROPDOWN_LONG_DESCRIPTION_LIST)))
                .segmentId(excelDataHashTable.get(SEGMENT_ID))
                .todayLessonOfVideoLibrary(removeBlankDataFromList(excelDataHashTable.get(TODAY_LESSON_OF_VIDEO_LIBRARY)))
                .nextDayLessonOfVideoLibrary(removeBlankDataFromList(excelDataHashTable.get(NEXT_DAY_LESSON_OF_VIDEO_LIBRARY))).build();
        return videoListTestData;
    }

    public ProgressReportEventPreviewTestData getProgressReportPreviewEventDataFroExcel(String sheetName, int fromDataRowNumber, int toDataRowNumber, String testDataExcelName) {
        Map<String, ArrayList<String>> excelDataHashTable = new DataProviders().getExcelDataInHashTable(sheetName, fromDataRowNumber, toDataRowNumber, testDataExcelName);
        ProgressReportEventPreviewTestData progressReportEventPreviewTestData = ProgressReportEventPreviewTestData.builder()
                .dayCount(removeBlankDataFromIntegerList(excelDataHashTable.get(DAY_COUNT).stream().map(Integer::parseInt).collect(Collectors.toList())))
                .eventID(removeBlankDataFromList(excelDataHashTable.get(EVENT_ID)))
                .previewTitle(removeBlankDataFromList(excelDataHashTable.get(PREVIEW_TITLE)))
                .popupTitle(removeBlankDataFromList(excelDataHashTable.get(POPUP_TITLE)))
                .popupType(excelDataHashTable.get(POPUP_TYPE))
                .previewDescription(removeBlankDataFromList(excelDataHashTable.get(PREVIEW_DESCRIPTION))).build();
        return progressReportEventPreviewTestData;
    }

    private void validateVideoLibraryDropDownList(List<String> videosInDropdown) {
        click(videoLibrarySubjectDropDown, false);
        for(String subjectTitle: videosInDropdown){
            softAssertions.assertThat(isElementExists(String.format(videoNameInLibraryDropDown,subjectTitle.trim()),true))
                    .as(subjectTitle.trim()+" video is not present in video library dropdown list").isTrue();
        }
    }

    private void validateMyLessonsTodayVideoList(List<String> subjectNameList, List<String> lessonList) {
        int i = 0;
        for(String subjectName: subjectNameList){
            softAssertions.assertThat(isElementExists(String.format(subjectAndLessonNameOnVideoPage,subjectName.trim(), lessonList.get(i).trim()),true))
                    .as(subjectName.trim()+" subject with "+ lessonList.get(i).trim() +" is not present on in My Lessons today list").isTrue();
            i++;
        }
    }

    public StudentsScreen validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary(boolean isValidationWithExcelData, String testDataExcelName) {
        String subject;
        String lesson;
        String subscriptionItem;
        String subscriptionNumber;
        String subjectID;
        int futureVideoIndex;
        boolean isValidationDone = false;
        String studentId = getUserAccountDetails().getStudentId();//userAccountDetails.get(STUDENT_ID);
        String loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);
        int counter = 0;
        if(isValidationWithExcelData){
            VideoListTestData videoListTestData = getVideoListTestDataFroExcel(GRADE_NINE_VIDEO_LIST, 0, 0, testDataExcelName);
            for(String subjectName: videoListTestData.getVideoLibraryDropdownSubjectList()){
                selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subjectName);
                click(bringElementIntoView(getElement(String.format(Students.videoLibraryVideoLink, videoListTestData.getNextDayLessonOfVideoLibrary().get(counter)))));
                tempXpath = String.format(Students.lessonLockedCloseButton, LESSON_LOCKED, YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON, CLOSE);
                if (isElementExists(tempXpath, false)) {
                    click(tempXpath, false);
                } else {
                    softAssertions.fail(Students.YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON + " message popup is not appeared.");
                }
                isValidationDone = true;
                counter++;
            }
        }else {
            ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentId), CommonConstants.SD_DATA_BASE, false);

            for (HashMap<String, String> row : myLessonsToday) {
                subject = row.get(SHORT_DESCRIPTION);
                subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();
                subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
                subjectID = getStudentSubjectDetailsList().get(subject).getSubjectId();

                ArrayList<HashMap<String, String>> videoLibraryVideos = executeAndGetSelectQueryData(DataBaseQueryConstant.VIDEO_LIBRARY_VIDEOS_SD_DB
                        .replaceAll(LOGIN_ID_DATA, loginId).replaceAll(SUBSCRIPTION_NUMBER_DATA, subscriptionNumber).replaceAll(SUBJECT_ID_DATA, subjectID), CommonConstants.SD_DATA_BASE, false);
                futureVideoIndex = isSubjectHavingFutureLessons(videoLibraryVideos);
                if (futureVideoIndex != 0 && futureVideoIndex != -1) {
                    lesson = videoLibraryVideos.get(futureVideoIndex).get(LESSON_NUMBER);
                    selectValueFromDropDownVideo(Students.videoLibrarySubjectDropDown, subject);
                    click(bringElementIntoView(getElement(String.format(Students.videoLibraryVideoLinkWithSubId, lesson, subscriptionItem))));
                    tempXpath = String.format(Students.lessonLockedCloseButton, LESSON_LOCKED, YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON, CLOSE);
                    if (isElementExists(tempXpath, false)) {
                        click(tempXpath, false);
                    } else {
                        softAssertions.fail(Students.YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON + " message popup is not appeared.");
                    }
                    isValidationDone = true;
                } else {
                    log(subject + ": All videos are viewed, don't have future videos to validate, student Should Not Able To Watch Next Day Lesson FromVideo Library pop up.");
                }
            }
        }
            if (!isValidationDone) {
                softAssertions.fail("Student is not having any future videos to validate student Should Not Able To Watch Next Day Lesson FromVideo Library");
            }
        return this;
    }

    private int isSubjectHavingFutureLessons(ArrayList<HashMap<String, String>> videoLibraryVideos) {
        int futureVideoIndex = 0;
        int futureVideoCounter = 0;
        for (HashMap<String, String> row : videoLibraryVideos) {
            if (row.get(COMPLETED).equals(N)) {
                futureVideoCounter++;
                if (futureVideoCounter == 2) {
                    return futureVideoIndex;
                }
            }
            futureVideoIndex++;
        }
        return -1;
    }

    public boolean isLessonCompleted(String classValue) {
        return classValue.equalsIgnoreCase(CommonConstants.LESSON_COMPLETED_CLASS_VALUE);
    }

    public StudentsScreen markAllVideosAsNotViewed() {
        String studentID = getUserAccountDetails().getStudentId();
        String subjectId;
        String endLesson;

        ArrayList<HashMap<String, String>> myLessonsAssessmentToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_DO_LIST_ASSIGNMENTS_AD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE, false);
        for (HashMap<String, String> myAssessment : myLessonsAssessmentToday) {
            subjectId = myAssessment.get(SUBJECT_ID_FK);
            endLesson = myAssessment.get(LESSON_NUMBER);
//            clearAssessmentProcessMarkVideoLessonsAsNotViewed(getUserAccountDetails().getLoginId(), subjectId, "1", endLesson);
//            clearAssessmentProcessMarkVideoLessonsAsNotViewed(getUserAccountDetails().getLoginId(), subjectId, "1", endLesson);
            clearAssessmentProcessMarkVideoLessonsAsNotViewed(getUserAccountDetails().getLoginId(), subjectId, "1", "500");
            //clearAssessmentProcessMarkVideoLessonsAsNotViewed(getUserAccountDetails().getLoginId(), subjectId, "1", "500");
        }
        return this;
    }

    public StudentsScreen answerAndSubmitDigitalAssessment(boolean isAnswerAndSubmitAllAssessment) {
        String subject;
        String assignmentName;
        String studentID = getUserAccountDetails().getStudentId();
        boolean isLocked;
        String userName;
        boolean isValidationDone = false;
        String subjectId;
        String endLesson;
        ArrayList<HashMap<String, String>> myLessonsAssessmentToday = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_DIGITAL_ONLY_ASSESSMENT_DETAILS_AD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE, false);
        for (HashMap<String, String> myAssessment : myLessonsAssessmentToday) {
            subject = myAssessment.get(SUBJECT).trim();
            assignmentName = myAssessment.get(LONG_DESCRIPTION).trim();
            isLocked = getIsLocked(myAssessment.get(LOCKED), Students.Y);
            subjectId = myAssessment.get(SUBJECT_ID_FK);
            endLesson = myAssessment.get(LESSON_NUMBER);
            userName = getUserAccountDetails().getUserName().trim();

            if(isAllAssignmentsLocked(myLessonsAssessmentToday)){
                markAssessmentRelatedAllLessonVideosAsCompleted(getUserAccountDetails().getLoginId(),subjectId,endLesson);
            }
            if (!isLocked) {
                try {
                    bringElementIntoView(String.format(Students.assessmentUnlocked, subject, assignmentName));
                }catch (Exception e){
                    softAssertions.fail("ASSESSMENT_LOCK column value is not in sync with UI");
                }
                click(String.format(Students.assessmentUnlocked, subject, assignmentName), false);
                if(isElementDisplayed(Students.signature)) {
                    type(Students.signature, userName);
                    click(Students.signPledgeBtn, false);
                    clickIfExists(Students.signPledgeBtn);
                }
                waitForPageTobeLoaded();
                waitForElementTobeExist(Students.linkitBeginBtn, veryLongWait);
                click(getVisibleElement(Students.linkitBeginBtn));
                waitForPageTobeLoaded();
                waitForElementTobeVisibleOrMoveAhead(Students.linkitQuestionPanel, veryLongWait);
                answerQuestions(false);
                submitQuestion();
                goToAnotherSession();
                submittedAssessmentList.add(new String[]{subject,assignmentName});
                isValidationDone = true;
            }
            if (!isAnswerAndSubmitAllAssessment && isValidationDone) {
                break;
            }
        }
        if(!isValidationDone){
            softAssertions.fail("All assessments are locked, Validation is not completed for any assessment.");
        }
        return this;
    }

    private void goToAnotherSession() {
        click(Students.linkitStartAnotherSession, false);
        waitForPageTobeLoaded();
    }

    private void submitQuestion() {
        click(getVisibleElement(Students.linkitSubmitAnswer));
        waitForPageTobeLoaded();
    }

    @SneakyThrows
    private void answerQuestions(boolean isCloseButtonAppear) {
        String questionType;
        waitForElementTobeVisibleOrMoveAhead(Students.linkitTotalQuestions, veryLongWait);

        int totalQuestions = 0;
        boolean isLastQuestion = false;
        try{
            totalQuestions = Integer.parseInt(getElementText(Students.linkitTotalQuestions, veryLongWait).split("of")[1].trim());
        }catch (ArrayIndexOutOfBoundsException e) {
            implicitWaitInSeconds(5);
            totalQuestions = Integer.parseInt(getElementText(Students.linkitTotalQuestions, veryLongWait).split("of")[1].trim());
        }

        while(Integer.parseInt(getElementText(Students.linkitTotalQuestions, veryLongWait).split("of")[0].trim()) <= totalQuestions){
            isLastQuestion = (Integer.parseInt(getElementText(Students.linkitTotalQuestions, veryLongWait).split("of")[0].trim()) == totalQuestions)?true:false;
            questionType = getElementText(Students.linkitQuestionType, veryLongWait).split(":")[0].trim();
            switch (questionType.toUpperCase()){
                case MULTIPLE_CHOICE:
                    click(getVisibleElement(Students.linkitMultipleChoiceFirstAnswer));
                    break;
                case TRUE_FALSE:
                    click(getVisibleElement(Students.linkitMultipleChoiceFirstAnswer));
                    break;
                case DICTATION:
                    type(getVisibleElement(Students.linkitDictationTextBox),"Answered by automation test");
                    click(linkitNextQuestionBtn, false);
                    break;
                case SPELLING:
                    type(getVisibleElement(Students.linkitDictationTextBox),"Answered by automation test for spelling");
                    click(linkitNextQuestionBtn, false);
                    break;
                case VOCABULARY:
                    clickByJavaScript(Students.linkitVocabDropDown);
                    click(linkitNextQuestionBtn, false);
                    break;
                case SHORT_ANSWER:
                    type(getVisibleElement(Students.linkitDictationTextBox),"Short answer");
                    click(linkitNextQuestionBtn, false);
                    break;
                case CHOOSE_CORRECT_ANSWER:
                    click(getVisibleElement(linkitChooseCorrectAnswerFirst));
                    break;
                case MEMORIZATION:
                    getDriver().switchTo().frame(getVisibleElement(linkitFrame));
                    type(getVisibleElement(linkitFrameBody),"Answered by automation");
                    switchToDefaultFrame(getDriver());
                    click(linkitNextQuestionBtn, false);
                    break;
                default:
                    if(isElementDisplayed(linkitTextArea)){
                        type(getVisibleElement(linkitTextArea),"Answered by automation");
                        click(linkitNextQuestionBtn, false);
                        break;
                    }else {
                        click(linkitNextQuestionBtn, false);
                        if(isCloseButtonAppear){
                            click(closeButton, true);
                        }else {
                            click(getVisibleElement(linkitSkipQuestionYes));
                        }
                    }
            }
            if(isCloseButtonAppear){
                click(closeButton, true);
            }
            implicitWaitInSeconds(5);
            if(isLastQuestion)break;
        }
    }

    public StudentsScreen navigateToPreviewVideoStudentScreen(){
        click(studentPreviewVideoLink, false);
        return this;
    }

    public void isVideoLibraryPageOpened() {
        softAssertions.assertThat(isElementExists(videoLibrarySubjectDropDown, false)).as("Video library page is not loaded successfully").isTrue();
    }

    public List<DigitalAssessmentsTestData> getDigitalAssessmentTestDataFromExcel(String testDataExcelName) {
        ArrayList<Map<Integer,String>> digitalAssessmentList = new DataProviders().getExcelData(DIGITAL_ASSESSMENT_LIST, 0, 0, testDataExcelName);
        List<DigitalAssessmentsTestData> digitalAssessmentsTestDataList = new ArrayList<>();
                digitalAssessmentList.stream()
                .forEach(e->digitalAssessmentsTestDataList.add(DigitalAssessmentsTestData.builder().subject(e.get(0)).quiz(e.get(1)).segmentId(e.get(2)).isLocked(Boolean.parseBoolean(e.get(3))).build()));
        return digitalAssessmentsTestDataList;
    }

    public List<DigitalAssessmentsTestData> getGradeOneVideoListTestDataFromExcel(String testDataExcelName) {
        ArrayList<Map<Integer,String>> digitalAssessmentList = new DataProviders().getExcelData(GRADE_ONE_VIDEO_LIST, 0, 0, testDataExcelName);
        List<DigitalAssessmentsTestData> videoListTestDataList = new ArrayList<>();
        digitalAssessmentList.stream()
                .forEach(e->videoListTestDataList.add(DigitalAssessmentsTestData.builder().subject(e.get(0)).quiz(e.get(1)).segmentId(e.get(2)).isLocked(Boolean.parseBoolean(e.get(3))).build()));
        return videoListTestDataList;
    }

    public void validateSubjectListInSubjectProgressSection(String testDataExcelName) {
        VideoListTestData videoListTestData = getVideoListTestDataFroExcel(GRADE_ONE_VIDEO_LIST, 0, 0, testDataExcelName);
        bringElementIntoView(subjectProgressSection);
        for(String subject: videoListTestData.getMyLessonsTodaySubjectList()){
            softAssertions.assertThat(isElementExists(subject, true)).as(subject+" not found on Screen").isTrue();
        }
    }

    public void switchPenmanShipFromBackEnd(String type){
        int penKey = (type.equals(CURSIVE)?2:1);
        executeQuery(DataBaseQueryConstant.UPDATE_PENMANSHIP_TYPE_BACKEND_AD_DB.replaceAll(PEN_KEY,
                Integer.toString(penKey)).replaceAll(APPLICATION_NUMBER_DATA, getUserAccountDetails().getApplicationNumber()), AD_DATA_BASE);
    }
}

