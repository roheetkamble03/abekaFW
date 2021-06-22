package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import constants.Calendar;
import constants.CommonConstants;
import constants.DataBaseQueryConstant;
import constants.GradeTable;
import constants.TableColumn;
import elementConstants.Dashboard;
import elementConstants.Students;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import java.util.*;
import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;
import static constants.CommonConstants.*;
import static constants.TableColumn.*;
import static elementConstants.Students.*;

public class StudentsScreen extends GenericAction {

    HashMap<String, Boolean> videoViewStatusVideoLibrary = new HashMap<>();
    private static ArrayList<String[]> submittedAssessmentList = new ArrayList<String[]>();

    public StudentsScreen validateStudentInformationSection(String studentName) {
        if (isElementExists(Students.STUDENT_INFORMATION)) {
            String oldURL;
            List<String> widgetList = Arrays.asList(CALENDAR_SETTINGS, IMAGE, LOGIN_INFORMATION,
                    PROGRESS_REPORTS, ASSESSMENT_PERMISSIONS);
            bringElementIntoView(Dashboard.MY_STUDENTS);
            for (String widget : widgetList) {
                try {
                    oldURL = getCurrentURL();
                    click(String.format(Students.widgetLink, getStudentInformationWidgetLinkId(widget)));
                    switch (widget) {
                        case Students.CALENDAR_SETTINGS:
                            String calendarHeader = String.format(Students.calendarPopupHeader, String.format(Students.CHANGE_CALENDAR_LENGTH, studentName));
                            softAssertions.assertThat(isElementExists(calendarHeader))
                                    .as(calendarHeader + " setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.calendarPopup));
                            break;
                        case Students.IMAGE:
                            String imageHeader = String.format(Students.imagePopupHeader, String.format(Students.CHANGE_IMAGE, studentName));
                            softAssertions.assertThat(isElementExists(imageHeader))
                                    .as(imageHeader + " setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.imagePopup));
                            break;
                        case Students.LOGIN_INFORMATION:
                            softAssertions.assertThat(getElementText(Students.loginInfoPopupHeader).trim().equals(String.format(Students.CHANGE_LOGIN_INFO, studentName)))
                                    .as(String.format(Students.CHANGE_LOGIN_INFO, studentName) + " setting header is not appeared identifier:\n" + Students.loginInfoPopupHeader).isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.loginInfoPopup));
                            break;
                        case Students.GRADE_ALERT_SETTING:
                            String gradeAlertSettingHeader = String.format(Students.gradeAlertHeader, Students.GRADE_ALERT_SETTING_HEADER);
                            softAssertions.assertThat(isElementExists(gradeAlertSettingHeader))
                                    .as(gradeAlertSettingHeader + " setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath, Students.gradeAlertSettingPopup));
                            break;
                        case Students.PROGRESS_REPORTS:
                            waitForPageTobeLoaded();
                            softAssertions.assertThat(isURLContainsGivenText(Students.PROGRESS_REPORTS.replaceAll("\\s", "")) && !(getCurrentURL().equals(oldURL)))
                                    .as("Navigated URL is not related to " + Students.PROGRESS_REPORTS + "\n current URL:" + getCurrentURL() + "\n Old URL:" + oldURL).isTrue();
                            softAssertions.assertThat(isElementExists(Students.PROGRESS_REPORTS))
                                    .as("Navigated page is not header " + Students.PROGRESS_REPORTS).isTrue();
                            if (!getCurrentURL().equals(oldURL)) {
                                back();
                            }
                            break;
                        case Students.ASSESSMENT_PERMISSIONS:
                            waitForPageTobeLoaded();
                            softAssertions.assertThat(getCurrentURL().indexOf(Students.ASSESSMENT_PERMISSIONS.replaceAll("\\s", "")) > 0 && !(getCurrentURL().equals(oldURL)))
                                    .as("Navigated URL is not related to " + Students.ASSESSMENT_PERMISSIONS + "\n current URL:" + getCurrentURL() + "\n Old URL:" + oldURL).isTrue();
                            softAssertions.assertThat(isElementExists(Students.ASSESSMENT_PERMISSIONS))
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

    public StudentsScreen navigateToProgressReportWidget() {
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
        click(String.format(Students.calendarAssessmentModificationRadioBtn, months));
        click(Students.updateCalendarBtn);
        waitForPageTobeLoaded();
        if (permissionToMakeCalendarChanges.length() > 0) {
            navigateToCalendarSettingsWidget();
            click(bringElementIntoView(String.format(calendarAssessmentModificationRadioBtn, permissionToMakeCalendarChanges)));
            click(Students.saveCalendarPermission);
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
        click(String.format(CommonConstants.closeXpath, Students.calendarPopup));
        return this;
    }

    public StudentsScreen enterStudentGrades() {
        //for loop according to db data
        String subject = "need to fetch from db";
        String gradingPeriod = "need to fetch from db";
        ArrayList<Map<String, ArrayList<Map<String, ArrayList<GradeTable>>>>> studentGradeTableDetailList = new ArrayList<>();// need to fetch from db
        selectSubjectOnProgressReportPage(subject);
        chooseGradingPeriod(gradingPeriod);
        fillGradesToSection(studentGradeTableDetailList, false);
        waitForPageTobeLoaded();
        return this;
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

    private void fillGradesToSection(ArrayList<Map<String, ArrayList<Map<String, ArrayList<GradeTable>>>>> studentGradeTableDetailList, boolean validateMoreThan100Marks) {
        int sectionGetKeyCounter = 0;
        int gradeTableNameCounter = 0;
        SelenideElement gradeTableParentElement;
        String sectionName;
        String gradeTableName;
        ArrayList<GradeTable> lessonList;
        String lesson;
        String description;
        boolean isInputTextBoxShouldPresent = false;
        for (Map<String, ArrayList<Map<String, ArrayList<GradeTable>>>> sectionMap : studentGradeTableDetailList) {
            ArrayList<Integer> givenGradeList = new ArrayList<>();
            int gradeTobeGiven;
            sectionName = sectionMap.keySet().toArray()[sectionGetKeyCounter].toString();
            if (bringElementIntoView(String.format(sectionHeader, getSectionID(sectionName), sectionName)) != null) {
                for (Map<String, ArrayList<GradeTable>> gradeTableList : sectionMap.get(sectionName)) {
                    gradeTableName = gradeTableList.keySet().toArray()[gradeTableNameCounter].toString();
                    gradeTableParentElement = bringElementIntoView(String.format(Students.gradeTableHeader, getSectionID(sectionName), gradeTableName));
                    lessonList = gradeTableList.get(gradeTableName);
                    if (gradeTableParentElement != null) {
                        for (GradeTable gradeTableLessonDetails : lessonList) {
                            isInputTextBoxShouldPresent = false;//need to fetch from db
                            if (isInputTextBoxShouldPresent) {
                                gradeTobeGiven = new Random().nextInt(101);
                                givenGradeList.add(gradeTobeGiven);
                                addGrade(gradeTableLessonDetails, sectionName, gradeTableName, validateMoreThan100Marks, gradeTobeGiven);
                            } else {
                                validateGradeInputBox(gradeTableLessonDetails, sectionName, gradeTableName);
                            }
                        }
                        if (givenGradeList.size() > 0) {
                            validateAverage(givenGradeList, sectionName, gradeTableName);
                        }
                    } else {
                        softAssertions.fail(gradeTableName + " is not present in given sectionMap[" + sectionMap +
                                "] of Progress Report page");
                    }
                    gradeTableNameCounter++;
                }
                sectionGetKeyCounter++;
            } else {
                softAssertions.fail(sectionName + ": sectionMap is not present on Progress Report page");
                continue;
            }
        }
    }

    private void validateAverage(ArrayList<Integer> givenGradeList, String sectionName, String gradeTableName) {
        int totalGrade = 0;
        for (Integer grade : givenGradeList) {
            totalGrade = totalGrade + grade;
        }
        tempXpath = String.format(Students.gradeAverageXpath, getSectionID(sectionName), gradeTableName, Students.AVERAGE);
        int expectedAverage = (totalGrade / (100 * givenGradeList.size())) * 100;
        softAssertions.assertThat(getElementText(tempXpath).equals(Integer.toString(expectedAverage)))
                .as(sectionName + "->" + gradeTableName + "'s average is not matching with table average. \nexpected average:"
                        + expectedAverage + "actual average:" + getElementText(tempXpath)).isTrue();
    }

    private StudentsScreen addGrade(GradeTable gradeTableLessonDetails, String sectionName, String gradeTableName, boolean validateMoreThan100Marks, int gradeTobeGiven) {
        String lesson = gradeTableLessonDetails.getLesson();
        String description = gradeTableLessonDetails.getDescription();
        tempXpath = String.format(gradeTextBox, getSectionID(sectionName), gradeTableName, lesson,
                description);
        if (isElementExists(tempXpath)) {
            if (validateMoreThan100Marks) {
                type(tempXpath, "101");
                pressTab(getElement(tempXpath));
                softAssertions.assertThat(isElementExists(Students.GRADE_TEXT_BOX_VALIDATOR_MESSAGE))
                        .as(GRADE_TEXT_BOX_VALIDATOR_MESSAGE + " grade text box validator message is not appeared, though we enter text 101").isTrue();
            }
            type(tempXpath, Integer.toString(gradeTobeGiven));
            pressTab(tempXpath);
        } else {
            softAssertions.fail("Grade input text box is not present for following details\nSectionName:" + sectionName + "\nTableName:" + gradeTableName +
                    "\nLesson:" + lesson + "\nDescription:" + description + "\n xpath:" + tempXpath);
        }
        return this;
    }

    private StudentsScreen validateGradeInputBox(GradeTable gradeTableLessonDetails, String sectionName, String gradeTableName) {
        String lesson = gradeTableLessonDetails.getLesson();
        String description = gradeTableLessonDetails.getDescription();
        tempXpath = String.format(gradeHiddenTextBox, getSectionID(sectionName), gradeTableName, lesson,
                description);
        softAssertions.assertThat(isElementExists(tempXpath)).as("Grade input text box is not present for following details\nSectionName:" + sectionName + "\nTableName:" + gradeTableName +
                "\nLesson:" + lesson + "\nDescription:" + description + "\n xpath:" + tempXpath).isTrue();
        return this;
    }

    @SneakyThrows
    private String getSectionID(String sectionName) {
        switch (sectionName.toUpperCase()) {
            case QUIZ:
                return "divQ";
            case TEST:
                return "divT";
            case EXAM:
                return "divE";
            case MISC_NON_GRADED:
                return "divX";
            default:
                throw new Exception("Section name is other that :" + QUIZ + "," + TEST + "," + EXAM + "," + MISC_NON_GRADED);

        }
    }

    public StudentsScreen chooseGradingPeriod(String gradingPeriod) {
        click(bringElementIntoView(String.format(Students.gradingPeriod, gradingPeriod)));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen selectSubjectOnProgressReportPage(String subject) {
        selectValueFromDropDownVideoLibrary(Students.projectReportSubjectDropDown, subject);
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen validateMyToDoListData() {
        if (isElementExists(Students.toDoListHeader)) {
            String studentID = getUserAccountDetails().getStudentId();//getuserAccountDetails.get(TableColumn.STUDENT_ID);
            String subject;
            String startDate;
            String lesson;

            click(Students.updateAll);
            softAssertions.assertThat(isElementTextEquals(Students.toDoListHeader, Students.MY_TO_DO_LIST))
                    .as("My to do list header is not equal to " + Students.MY_TO_DO_LIST).isTrue();

            ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_LIST_LESSONS_SD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), SD_DATA_BASE);
            ArrayList<HashMap<String, String>> myAssignmentsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_TO_DO_LIST_ASSIGNMENTS_AD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE);
            for (HashMap<String, String> lessonsMap : myLessonsToday) {
                subject = lessonsMap.get(SHORT_DESCRIPTION);
                lesson = lessonsMap.get(LONG_DESCRIPTION);
                startDate = getFormattedDate(lessonsMap.get(START_DATE), Calendar.YYYY_MM_DD_HH_MM_SS, Calendar.dayMonthSingleDate);

                tempXpath = String.format(myToDoListLesson, subject, startDate, lesson);
                softAssertions.assertThat(isElementExists(tempXpath))
                        .as(subject + ":" + lesson + ":" + startDate + " is not present in My to do list. \n identifier:" + tempXpath).isTrue();

            }
            for (HashMap<String, String> assignmentsMap : myAssignmentsToday) {
                subject = assignmentsMap.get(SHORT_DESCRIPTION);
                lesson = assignmentsMap.get(LONG_DESCRIPTION);
                startDate = getFormattedDate(assignmentsMap.get(START_DATE), Calendar.YYYY_MM_DD_HH_MM_SS, Calendar.dayMonthSingleDate);

                tempXpath = String.format(myToDoListLesson, subject, startDate, lesson);
                softAssertions.assertThat(isElementExists(tempXpath))
                        .as(subject + ":" + lesson + ":" + startDate + " is not present in My to do list. \n identifier:" + tempXpath).isTrue();
            }
        } else {
            softAssertions.fail("My to do list section is not present on UI");
        }
        return this;
    }

    public StudentsScreen validateAccountInfoPage() {
        navigateToAccountInfoWidget(Students.PASSWORD);
        softAssertions.assertThat(isElementExists(Students.changePasswordHeader)).as("Change Password pop-up is not appeared").isTrue();
        softAssertions.assertThat(isElementExists(Students.currentPassword)).as("Current password text box is not present on Change Password pop-up").isTrue();
        softAssertions.assertThat(isElementExists(Students.newPassword)).as("New password text box is not present on Change Password pop-up").isTrue();
        softAssertions.assertThat(isElementTextEquals(Students.forgotPasswordMessage, Students.FORGOT_PASSWORD_MESSAGE))
                .as("Forgot password message of Change Password pop-up is not equal to " + Students.FORGOT_PASSWORD_MESSAGE).isTrue();
        softAssertions.assertThat(isElementExists(Students.changePasswordSubmitBtn))
                .as("Change Password button is not present on Change Password pop-up.").isTrue();
        click(String.format(CommonConstants.closeXpath, Students.changePasswordPopup));
        return this;
    }

    public StudentsScreen navigateToAccountInfoWidget(String widgetName) {
        click(String.format(Students.accountInfoWidgetLink, widgetName));
        waitForPageTobeLoaded();
        return this;
    }

    public StudentsScreen validateRequestTranScriptFunctionality(String studentName) {
        openTranScriptRequestPopUp();
        if (isElementExists(Students.yourNameTextBox)) {
            type(Students.yourNameTextBox, studentName);
        } else {
            softAssertions.fail("Your name text box is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.relationshipSelectBox)) {
            selectByVisibleText(Students.relationshipSelectBox, Students.SELF);
        } else {
            softAssertions.fail("Relationship to Student select box is not present on Transcript Request pop-up");
        }

        if (isElementExists(Students.phoneNumberTextBox)) {
            type(Students.phoneNumberTextBox, RandomStringUtils.randomNumeric(10));
        } else {
            softAssertions.fail("Phone number box is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.sendMyTranscriptToPCCRadio)) {
            click(Students.sendMyTranscriptToPCCRadio);
        } else {
            softAssertions.fail("Send my transcript to PCC ratio button is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.attentionLineTextBox)) {
            type(Students.attentionLineTextBox, CommonConstants.AUTOMATION_TEST);
        } else {
            softAssertions.fail("Attention line text box is not present on Transcript Request pop-up");
        }
        if (isElementExists(Students.sendImmediately)) {
            click(Students.sendImmediately);
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
        if (isElementExists(Students.myRecentGradeSection)) {
            String assignment;
            String grade;
            String studentID = getUserAccountDetails().getStudentId();//getUserAccountDetails().get(STUDENT_ID);//fetch from DB
            bringElementIntoView(getElements(Students.myRecentGradeRows).get(getElements(Students.myRecentGradeRows).size() - 1));
            int myRecentGradeRowCount = getElements(Students.myRecentGradeRows).size() - 1;
            //add getStudentIdFromDB(studentName) to string.format
            ArrayList<HashMap<String, String>> myGrades = executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_GRADE_WITH_SUBJECT_AD_DB
                    .replaceAll(TableColumn.ROW_COUNT_DATA, Integer.toString(myRecentGradeRowCount)).replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE);

            for (HashMap<String, String> rowData : myGrades) {
                assignment = rowData.get(TableColumn.ASSESSMENT);
                grade = rowData.get(TableColumn.GRADE);
                softAssertions.assertThat(isElementExists(String.format(Students.subjectWithGradeRow, assignment, grade)))
                        .as(assignment + ":" + grade + " row is not present in My recent grade section").isTrue();
            }
        } else {
            softAssertions.fail("My recent grade section is not present on UI");
        }

        return this;
    }

    public StudentsScreen openTranScriptRequestPopUp() {
        click(Students.requestTranScriptBtn);
        waitForElementTobeExist(Students.submitTranScriptBtn);
        return this;
    }

    public StudentsScreen verifyLastViewedLessons() {
        boolean isValidationDone = false;
        if (isElementExists(Students.lastViewedVideoLessonsSection)) {
            String loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);
            ArrayList<HashMap<String, String>> lastViewedLessonsDataMapList = executeAndGetSelectQueryData(DataBaseQueryConstant.GET_LAST_VIEWED_LESSON_DATA_SD_DB.replaceAll(LOGIN_ID_DATA, loginId), SD_DATA_BASE);
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

    public StudentsScreen validateDigitalAssessmentsAreLockedOrNot() {
        String subject;
        String lesson;
        boolean isLocked;
        String studentID = getUserAccountDetails().getStudentId();//userAccountDetails.get(TableColumn.STUDENT_ID);;
        ArrayList<HashMap<String, String>> myAssessmentToday = executeAndGetSelectQueryData(DataBaseQueryConstant.DIGITAL_ONLY_ASSESSMENT_DETAILS_AD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE);
        refresh();
        waitForPageTobeLoaded();
        for (HashMap<String, String> row : myAssessmentToday) {
            subject = row.get(SUBJECT);
            lesson = row.get(LESSON);
            isLocked = getIsLocked(row.get(LOCKED), Students.Y);//need to fetch from DB;
            if (isLocked) {
                softAssertions.assertThat(isElementExists(String.format(Students.assessmentLocked, subject, lesson)))
                        .as(subject + "-" + lesson + " subject is not locked on assessment page").isTrue();
            } else {
                softAssertions.assertThat(isElementExists(String.format(Students.assessmentUnlocked, subject, lesson)))
                        .as("[" + subject + "-" + lesson + "] is not available for assessment or Locked, though lesson is completed").isTrue();
            }
        }
        return this;
    }

    private boolean getIsLocked(String dataColumnText, String criteria) {
        return (dataColumnText.equals(criteria)) ? true : false;
    }

    /**
     * Here we are validating only video list, not clicking on video link
     *
     * @return
     */
    public StudentsScreen validateMyLessonsTodaySectionData() {
        if (isElementExists(Students.MY_LESSONS_TODAY)) {
            if (isElementExists(Students.lessonsToday)) {
                String studentID = getUserAccountDetails().getStudentId();//userAccountDetails.get(TableColumn.STUDENT_ID);
                String myLessonsVideoLink;
                String subject;
                String lesson;
                ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                        .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), CommonConstants.SD_DATA_BASE);
                for (HashMap<String, String> row : myLessonsToday) {
                    subject = row.get(SHORT_DESCRIPTION);
                    lesson = row.get(LONG_DESCRIPTION);
                    myLessonsVideoLink = String.format(Students.myLessonsTodayVideoLink, subject, lesson);
                    softAssertions.assertThat(isElementExists(myLessonsVideoLink))
                            .as(lesson + " of " + subject + " subject is not present in My lessons today section").isTrue();
                }
            } else {
                softAssertions.fail(Students.MY_LESSONS_TODAY + " is not having any video link on UI");
            }
        } else {
            softAssertions.fail(Students.MY_LESSONS_TODAY + " section is not present on UI");
        }
        return this;
    }

    /**
     * We are validating My lessons today videos by clicking on link and after changing status from DB it should shown completed in video library
     * Also, validating the My today's lesson and Video library having same video.
     *
     * @return
     */
    public StudentsScreen watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary() {
        if (isElementExists(Students.MY_LESSONS_TODAY)) {
            if (isElementExists(Students.lessonsToday)) {
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
                String studentID = getUserAccountDetails().getStudentId();//userAccountDetails.get(STUDENT_ID);
                ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                        .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), CommonConstants.SD_DATA_BASE);

                for (HashMap<String, String> row : myLessonsToday) {
                    subject = row.get(SHORT_DESCRIPTION);
                    longDescription = row.get(LONG_DESCRIPTION);
                    lesson = row.get(LESSON_NUMBER);
                    completed = (row.get(COMPLETED).equals(Y)) ? true : false;
                    segmentId = row.get(SEGMENT_ID_FK);
                    subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
                    subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();
                    loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);

                    selectValueFromDropDownVideoLibrary(Students.videoLibrarySubjectDropDown, subject);
                    isVideoAlreadyViewedInVideoLibrary = getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true);
                    if (isVideoAlreadyViewedInVideoLibrary && !completed) {
                        softAssertions.fail("According Video library status [" + lesson + " lesson of " + subject + " subject] is already viewed and completed.");
                    }

                    if (!isVideoAlreadyViewedInVideoLibrary) {
                        playLessonVideo(subject, longDescription);

                        tempXpath = String.format(Students.playingVideoTitle, subject + " - " + longDescription);
                        softAssertions.assertThat(isElementExists(tempXpath))
                                .as(subject + " - " + lesson + ": Running video is not similar to clicked link. \n identifier:" + tempXpath).isTrue();

                        markVideoLessonAsCompleted(subscriptionNumber, subscriptionItem, loginId, segmentId, getUserAccountDetails().getUserId());//getUserAccountDetails().get(USER_ID));
                        refresh();
                        waitForPageTobeLoaded();

                        selectValueFromDropDownVideoLibrary(Students.videoLibrarySubjectDropDown, subject);
                        softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subscriptionItem, lesson, true))
                                .as(lesson + " lesson of " + subject + " subject's video status is not updated to viewed/completed in Video library section").isTrue();
                    } else {
                        log(subject + ":" + lesson + " is already viewed on My lessons today.");
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

        while (!isElementExists(String.format(Students.playingVideoTitle, subject + " - " + longDescription)) && retry < 3) {
            bringElementIntoView(tempXpath);
            implicitWaitInSeconds(2);
            clickByJavaScript(tempXpath);
            if (isElementExists(Students.restartVideo)) {
                click(restartVideo);
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

    public StudentsScreen validateVideoLibraryVideoStatusWithDataBase() {
        String subject;
        String subjectID;
        String lesson;
        String subscriptionNumber;
        String subscriptionItem;
        String loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);
        String studentID = getUserAccountDetails().getStudentId();//getUserAccountDetails().get(STUDENT_ID);
        boolean isLessonCompleted;

        ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), CommonConstants.SD_DATA_BASE);
        ArrayList<String> validatedSubject = new ArrayList<>();
        for (HashMap<String, String> studentLessons : myLessonsToday) {
            subject = studentLessons.get(SHORT_DESCRIPTION);
            if (!validatedSubject.contains(subject)) {
                subjectID = getStudentSubjectDetailsList().get(subject).getSubjectId();
                subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
                subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();

                ArrayList<HashMap<String, String>> videoLibraryVideos = executeAndGetSelectQueryData(DataBaseQueryConstant.VIDEO_LIBRARY_VIDEOS_SD_DB
                        .replaceAll(LOGIN_ID_DATA, loginId).replaceAll(SUBSCRIPTION_NUMBER_DATA, subscriptionNumber).replaceAll(SUBJECT_ID_DATA, subjectID), CommonConstants.SD_DATA_BASE);

                selectValueFromDropDownVideoLibrary(Students.videoLibrarySubjectDropDown, subject);
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

        return this;
    }


    public StudentsScreen validateIsLessonVideoLinkIsPresentInVideoLibrary(String subject, String lesson, String subjectIDNumber, String subscriptionItemNumber) {
        selectValueFromDropDownVideoLibrary(subject, lesson);
        tempXpath = String.format(Students.videoLibraryVideoLink, subjectIDNumber, subscriptionItemNumber, lesson);
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
        tempXpath = String.format(Students.videoLibraryVideoLink, lesson, subscriptionItemNumber);
        if (isBringLinkIntoView) {
            bringElementIntoView(getElement(tempXpath));
        }
        return isLessonCompleted(getClassAttributeValue(getElement(tempXpath)));
    }

    public StudentsScreen validateAssessmentsAreLocked() {
        return this;
    }

    public StudentsScreen validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary() {
        String subject;
        String lesson;
        String subscriptionItem;
        String subscriptionNumber;
        String subjectID;
        int futureVideoIndex;
        boolean isValidationDone = false;
        String studentId = getUserAccountDetails().getStudentId();//userAccountDetails.get(STUDENT_ID);
        String loginId = getUserAccountDetails().getLoginId();//getUserAccountDetails().get(LOGIN_ID);
        ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA, studentId), CommonConstants.SD_DATA_BASE);

        for (HashMap<String, String> row : myLessonsToday) {
            subject = row.get(SHORT_DESCRIPTION);
            subscriptionItem = getStudentSubjectDetailsList().get(subject).getSubscriptionItems();
            subscriptionNumber = getStudentSubjectDetailsList().get(subject).getSubscriptionNumber();
            subjectID = getStudentSubjectDetailsList().get(subject).getSubjectId();

            ArrayList<HashMap<String, String>> videoLibraryVideos = executeAndGetSelectQueryData(DataBaseQueryConstant.VIDEO_LIBRARY_VIDEOS_SD_DB
                    .replaceAll(LOGIN_ID_DATA, loginId).replaceAll(SUBSCRIPTION_NUMBER_DATA, subscriptionNumber).replaceAll(SUBJECT_ID_DATA, subjectID), CommonConstants.SD_DATA_BASE);
            futureVideoIndex = isSubjectHavingFutureLessons(videoLibraryVideos);
            if (futureVideoIndex != 0) {
                lesson = videoLibraryVideos.get(futureVideoIndex).get(LESSON_NUMBER);
                selectValueFromDropDownVideoLibrary(Students.videoLibrarySubjectDropDown, subject);
                click(bringElementIntoView(getElement(String.format(Students.videoLibraryVideoLink, lesson, subscriptionItem))));
                tempXpath = String.format(Students.lessonLockedCloseButton, LESSON_LOCKED, YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON, CLOSE);
                if (isElementExists(tempXpath)) {
                    click(tempXpath);
                } else {
                    softAssertions.fail(Students.YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON + " message popup is not appeared.");
                }
                isValidationDone = true;
            } else {
                log(subject + ": Is not having any future videos to validate student Should Not Able To Watch Next Day Lesson FromVideo Library");
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
        return futureVideoIndex;
    }

    public boolean isLessonCompleted(String classValue) {
        return classValue.equalsIgnoreCase(CommonConstants.LESSON_COMPLETED_CLASS_VALUE);
    }

    public StudentsScreen answerAndSubmitDigitalAssessment(boolean isAnswerAndSubmitAllAssessment) {
        String subject;
        String lesson;
        String studentID = getUserAccountDetails().getStudentId();
        boolean isLocked;
        String userName;
        boolean isValidationDone = false;
        markAllVideoLessonsAsCompletedForRespectiveStudent(getUserAccountDetails().getLoginId());
        ArrayList<HashMap<String, String>> myLessonsAssessmentToday = executeAndGetSelectQueryData(DataBaseQueryConstant.DIGITAL_ONLY_ASSESSMENT_DETAILS_AD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA, studentID), AD_DATA_BASE);
        for (HashMap<String, String> myAssessment : myLessonsAssessmentToday) {
            subject = myAssessment.get(SUBJECT).trim();
            lesson = myAssessment.get(LESSON).trim();
            isLocked = getIsLocked(myAssessment.get(LOCKED), Students.Y);
            userName = getUserAccountDetails().getUserName().trim();
            if (!isLocked) {
                bringElementIntoView(String.format(Students.assessmentUnlocked, subject, lesson));
                click(String.format(Students.assessmentUnlocked, subject, lesson));
                if(isElementDisplayed(Students.signature)) {
                    type(Students.signature, userName);
                    click(Students.signPledgeBtn);
                    clickIfExists(Students.signPledgeBtn);
                }
                waitForPageTobeLoaded();
                waitForElementTobeExist(Students.linkitBeginBtn, veryLongWait);
                click(getVisibleElement(Students.linkitBeginBtn));
                waitForPageTobeLoaded();
                waitForElementTobeVisible(Students.linkitQuestionPanel, veryLongWait);
                answerQuestions();
                submitQuestion();
                goToAnotherSession();
                submittedAssessmentList.add(new String[]{subject,lesson});
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
        click(Students.linkitStartAnotherSession);
        waitForPageTobeLoaded();
    }

    private void submitQuestion() {
        click(getVisibleElement(Students.linkitSubmitAnswer));
        waitForPageTobeLoaded();
    }

    @SneakyThrows
    private void answerQuestions() {
        String questionType;
        waitForElementTobeVisible(Students.linkitTotalQuestions, veryLongWait);

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
                    click(linkitNextQuestionBtn);
                    break;
                case SPELLING:
                    type(getVisibleElement(Students.linkitDictationTextBox),"Answered by automation test for spelling");
                    click(linkitNextQuestionBtn);
                    break;
                case VOCABULARY:
                    clickByJavaScript(Students.linkitVocabDropDown);
                    click(linkitNextQuestionBtn);
                    break;
                case SHORT_ANSWER:
                    type(getVisibleElement(Students.linkitDictationTextBox),"Short answer");
                    click(linkitNextQuestionBtn);
                    break;
                case CHOOSE_CORRECT_ANSWER:
                    click(getVisibleElement(linkitChooseCorrectAnswerFirst));
                    break;
                case MEMORIZATION:
                    getDriver().switchTo().frame(getVisibleElement(linkitFrame));
                    type(getVisibleElement(linkitFrameBody),"Answered by automation");
                    switchToDefaultFrame(getDriver());
                    click(linkitNextQuestionBtn);
                    break;
                default:
                    if(isElementDisplayed(linkitTextArea)){
                        type(getVisibleElement(linkitTextArea),"Answered by automation");
                        click(linkitNextQuestionBtn);
                        break;
                    }else {
                        click(linkitNextQuestionBtn);
                        click(getVisibleElement(linkitSkipQuestionYes));
                    }
            }
            implicitWaitInSeconds(5);
            if(isLastQuestion)break;
        }
    }
}

