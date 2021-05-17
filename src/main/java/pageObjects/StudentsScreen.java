package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.ex.ElementNotFound;
import constants.CommonConstants;
import constants.DataBaseQueryConstant;
import constants.StudentToDoList;
import elementConstants.Dashboard;
import elementConstants.Students;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;

import java.util.*;

import static com.codeborne.selenide.Selenide.back;

public class StudentsScreen extends GenericAction {

    public StudentsScreen validateStudentInformationSection(String studentName){
        if (isElementExists(Students.STUDENT_INFORMATION)) {
            String oldURL;
            List<String> widgetList = Arrays.asList(Students.CALENDAR_SETTINGS,Students.IMAGE,Students.LOGIN_INFORMATION,
                    Students.GRADE_ALERT_SETTING,Students.PROGRESS_REPORTS,Students.ASSESSMENT_PERMISSIONS);
            bringElementIntoView(Dashboard.MY_STUDENTS);
            for(String widget:widgetList){
                try {
                    oldURL = getCurrentURL();
                    click(String.format(Students.widgetLink,widget));
                    switch (widget) {
                        case Students.CALENDAR_SETTINGS:
                            String calendarHeader = String.format(Students.calendarPopupHeader,String.format(Students.CHANGE_CALENDAR_LENGTH,studentName));
                            softAssertions.assertThat(isElementExists(calendarHeader))
                                    .as(calendarHeader +" setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath,Students.calendarPopup));
                            break;
                        case Students.IMAGE:
                            String imageHeader = String.format(Students.imagePopupHeader,String.format(Students.CHANGE_IMAGE,studentName));
                            softAssertions.assertThat(isElementExists(imageHeader))
                                    .as(imageHeader +" setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath,Students.imagePopup));
                            break;
                        case Students.LOGIN_INFORMATION:
                            String loginInfoHeader = String.format(Students.loginInfoPopupHeader,String.format(Students.CHANGE_LOGIN_INFO,studentName));
                            softAssertions.assertThat(isElementExists(loginInfoHeader))
                                    .as(loginInfoHeader +" setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath,Students.loginInfoPopup));
                            break;
                        case Students.GRADE_ALERT_SETTING:
                            String gradeAlertSettingHeader = String.format(Students.gradeAlertHeader,Students.GRADE_ALERT_SETTING_HEADER);
                            softAssertions.assertThat(isElementExists(gradeAlertSettingHeader))
                                    .as(gradeAlertSettingHeader +" setting header is not appeared").isTrue();
                            click(String.format(CommonConstants.closeXpath,Students.gradeAlertSettingPopup));
                            break;
                        case Students.PROGRESS_REPORTS:
                            waitForPageTobLoaded();
                            softAssertions.assertThat(getCurrentURL().indexOf(Students.PROGRESS_REPORTS)>0 && !(getCurrentURL().equals(oldURL)))
                                    .as("Navigated URL is not "+ Students.PROGRESS_REPORTS).isTrue();
                            softAssertions.assertThat(isElementExists(Students.PROGRESS_REPORTS))
                                    .as("Navigated page is not header "+Students.PROGRESS_REPORTS).isTrue();
                            if(!getCurrentURL().equals(oldURL)){
                                back();
                            }
                            break;
                        case Students.ASSESSMENT_PERMISSIONS:
                            waitForPageTobLoaded();
                            softAssertions.assertThat(getCurrentURL().indexOf(Students.ASSESSMENT_PERMISSIONS)>0 && !(getCurrentURL().equals(oldURL)))
                                    .as("Navigated URL is not "+ Students.ASSESSMENT_PERMISSIONS).isTrue();
                            softAssertions.assertThat(isElementExists(Students.ASSESSMENT_PERMISSIONS))
                                    .as("Navigated page is not header "+Students.ASSESSMENT_PERMISSIONS).isTrue();
                            if(!getCurrentURL().equals(oldURL)){
                                back();
                            }
                        default:
                            softAssertions.fail("My Student Information validation not took place");

                    }

                }catch (ElementNotFound e){
                    softAssertions.fail(widget +" widget is not present in My Student info section");
                    continue;
                }
            }
        }else {
            Assert.fail(Students.STUDENT_INFORMATION+" section is not present on Students screen");
        }
        return this;
    }

    public StudentsScreen validateMyToDoListData(ArrayList<StudentToDoList> studentToDoList){
        click(Students.updateAll);
        softAssertions.assertThat(isElementTextEquals(Students.toDoListHeader,Students.MY_TO_DO_LIST))
                .as("My to do list header is not equal to "+ Students.MY_TO_DO_LIST).isTrue();
        //DB validation logic yet to implement
        return this;
    }

    public StudentsScreen validateAccountInfoPage(){
        navigateToAccountInfoWidget(Students.PASSWORD);
        softAssertions.assertThat(isElementExists(Students.changePasswordHeader)).as("Change Password pop-up is not appeared").isTrue();
        softAssertions.assertThat(isElementExists(Students.currentPassword)).as("Current password text box is not present on Change Password pop-up").isTrue();
        softAssertions.assertThat(isElementExists(Students.newPassword)).as("New password text box is not present on Change Password pop-up").isTrue();
        softAssertions.assertThat(isElementTextEquals(Students.forgotPasswordMessage,Students.FORGOT_PASSWORD_MESSAGE))
                .as("Forgot password message of Change Password pop-up is not equal to "+ Students.FORGOT_PASSWORD_MESSAGE).isTrue();
        softAssertions.assertThat(isElementExists(Students.changePasswordSubmitBtn))
                .as("Change Password button is not present on Change Password pop-up.").isTrue();
        click(String.format(CommonConstants.closeXpath,Students.changePasswordPopup));
        return this;
    }

    public StudentsScreen navigateToAccountInfoWidget(String widgetName){
        click(String.format(Students.accountInfoWidgetLink, widgetName));
        waitForPageTobLoaded();
        return this;
    }

    public StudentsScreen validateRequestTranScriptFunctionality(String studentName){
        openTranScriptRequestPopUp();
        if(isElementExists(Students.yourNameTextBox)){
            type(Students.yourNameTextBox,studentName);
        }else {
            softAssertions.fail("Your name text box is not present on Transcript Request pop-up");
        }
        if(isElementExists(Students.relationshipSelectBox)){
            selectByVisibleText(Students.relationshipSelectBox,Students.SELF);
        }else {
            softAssertions.fail("Relationship to Student select box is not present on Transcript Request pop-up");
        }

        if(isElementExists(Students.phoneNumberTextBox)){
            type(Students.phoneNumberTextBox, RandomStringUtils.randomNumeric(10));
        }else {
            softAssertions.fail("Phone number box is not present on Transcript Request pop-up");
        }
        if(isElementExists(Students.sendMyTranscriptToPCCRadio)){
            click(Students.sendMyTranscriptToPCCRadio);
        }else {
            softAssertions.fail("Send my transcript to PCC ratio button is not present on Transcript Request pop-up");
        }
        if(isElementExists(Students.attentionLineTextBox)){
            type(Students.attentionLineTextBox, CommonConstants.AUTOMATION_TEST);
        }else {
            softAssertions.fail("Attention line text box is not present on Transcript Request pop-up");
        }
        if(isElementExists(Students.sendImmediately)){
            click(Students.sendImmediately);
        }else {
            softAssertions.fail("Send transcript immediately with current status. radio button is not present on Transcript Request pop-up");
        }
        click(Students.submitTranScriptBtn);
        implicitWaitInSeconds(10);
        if(isAlertPresent()){
            softAssertions.assertThat(getDriver().switchTo().alert().getText().equals(Students.TRANSCRIPT_ALERT_TEXT))
                    .as("Transcript success alert text is not equal to "+Students.TRANSCRIPT_ALERT_TEXT).isTrue();
            acceptAlert();
            waitForPageTobLoaded();
        }else {
            softAssertions.fail("Transcript success alert is not appeared");
        }
        return this;
    }

    public StudentsScreen validateMyRecentGrades(String studentName){
        if(isElementExists(Students.myRecentGradeSection)) {
            String assignment;
            String grade;
            bringElementIntoView(getElements(Students.myRecentGradeRows).get(getElements(Students.myRecentGradeRows).size()-1));
            int myRecentGradeRowCount = getElements(Students.myRecentGradeRows).size() - 1;
            //add getStudentIdFromDB(studentName) to string.format
            ArrayList<HashMap<String, String>> myGrades = executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_GRADE_WITH_SUBJECT.replaceAll(Students.ROW_COUNT,Integer.toString(myRecentGradeRowCount)));

            for (HashMap<String, String> rowData : myGrades) {
                assignment = rowData.get(Students.ASSESSMENT_TBL_COL_NAME);
                grade = rowData.get(Students.GRADE_TBL_COL_NAME);
                softAssertions.assertThat(isElementExists(String.format(Students.subjectWithGradeRow, assignment, grade)))
                        .as(assignment + ":" + grade + " row is not present in My recent grade section").isTrue();
            }
        }else {
            softAssertions.fail("My recent grade section is not present on UI");
        }

        return this;
    }

    public StudentsScreen openTranScriptRequestPopUp(){
        click(Students.requestTranScriptBtn);
        waitForElementTobeExist(Students.submitTranScriptBtn);
        return this;
    }

    public StudentsScreen verifyLastViewedLessons(){
        if(isElementExists(Students.lastViewedVideoLessonsSection)) {
            //Data need to fetch from DB
//            String assignment;
//            String grade;
//            bringElementIntoView(getElements(Students.myRecentGradeRows).get(getElements(Students.myRecentGradeRows).size()-1));
//            int myRecentGradeRowCount = getElements(Students.myRecentGradeRows).size() - 1;
//            //add getStudentIdFromDB(studentName) to string.format
//            ArrayList<HashMap<String, String>> myGrades = executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_GRADE_WITH_SUBJECT.replaceAll(Students.ROW_COUNT,Integer.toString(myRecentGradeRowCount)));
//
//            for (HashMap<String, String> rowData : myGrades) {
//                assignment = rowData.get(Students.ASSESSMENT_TBL_COL_NAME);
//                grade = rowData.get(Students.GRADE_TBL_COL_NAME);
//                softAssertions.assertThat(isElementExists(String.format(Students.subjectWithGradeRow, assignment, grade)))
//                        .as(assignment + ":" + grade + " row is not present in My recent grade section").isTrue();
//            }
        }else {
            softAssertions.fail("Last viewed video lessons section is not present on UI");
        }

        return this;
    }

    public StudentsScreen navigateToStartWatchingYourLessonsLink(){
        bringChildElementIntoView(getElement(Students.lastViewedVideoLessonsSection),Students.startWatchingYourLessonsLin);
        waitForPageTobLoaded();
        return this;
    }

    public StudentsScreen validateMyLessonsTodaySectionData(){
        if(isElementExists(Students.MY_LESSONS_TODAY)) {
            if (isElementExists(Students.lessonsToday)) {
                //fetch data from DB for validation.
                String myLessonsVideoLink = String.format(Students.myLessonsTodayVideoLink,"Subject Name","Lesson to be read from db");
                //for loop
                bringElementIntoView(myLessonsVideoLink);
                click(myLessonsVideoLink);
                validateVideoWithVideoLibrary();
            } else {
                softAssertions.fail(Students.MY_LESSONS_TODAY+" is not having any video link on UI");
            }
        }else {
            softAssertions.fail(Students.MY_LESSONS_TODAY+" section is not present on UI");
        }
        return this;
    }

    public StudentsScreen validateVideoWithVideoLibrary(){
        //Validating video with lesson number
        return this;
    }

    public StudentsScreen validateAssessmentsAreLocked(){
        return this;
    }

    public StudentsScreen validateWatchedVideoLessonsAreTickedInVideoLibrary(){
        return this;
    }

    public StudentsScreen validateStudentIsAbleToWatchNextDayLessonFromVideoLibrary(){
        return this;
    }
}
