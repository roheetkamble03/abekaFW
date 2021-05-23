package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.ex.ElementNotFound;
import constants.CommonConstants;
import constants.DataBaseQueryConstant;
import constants.TableColumn;
import elementConstants.Dashboard;
import elementConstants.Students;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;

import java.util.*;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;
import static constants.CommonConstants.AD_DATA_BASE;
import static constants.TableColumn.*;
import static elementConstants.Students.LESSON_LOCKED;
import static elementConstants.Students.YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON;

public class StudentsScreen extends GenericAction {

    HashMap<String, Boolean> videoViewStatusVideoLibrary = new HashMap<>();

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

    public StudentsScreen navigateToProgressReportSection(){
        click(bringElementIntoView(String.format(Students.widgetLink,Students.PROGRESS_REPORTS)));
        waitForPageTobLoaded();
        return this;
    }

    public StudentsScreen validateMyToDoListData(){
        if (isElementExists(Students.toDoListHeader)) {
            click(Students.updateAll);
            softAssertions.assertThat(isElementTextEquals(Students.toDoListHeader,Students.MY_TO_DO_LIST))
                    .as("My to do list header is not equal to "+ Students.MY_TO_DO_LIST).isTrue();

            String toDoListName;
            String studentID = userAccountDetails.get(TableColumn.STUDENT_ID_DATA);
            String accountNumber = userAccountDetails.get(TableColumn.ACCOUNT_NUMBER_DATA);;
            String startDate = "";
            String endDate = "";
            ArrayList<HashMap<String,String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_CALENDER_EVENTS_AD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA,studentID).replaceAll(TableColumn.ACCOUNT_NUMBER_DATA,accountNumber)
                    .replaceAll(TableColumn.START_DATE_DATA,startDate).replaceAll(TableColumn.END_DATE_DATA,endDate), AD_DATA_BASE);
            for(HashMap<String,String> row:myLessonsToday) {
                toDoListName = "read data from row";
                softAssertions.assertThat(isElementExists(String.format(Students.MY_TO_DO_LIST, toDoListName)))
                            .as(toDoListName + " task is not present in My to do list").isTrue();

            }
        }else {
            softAssertions.fail("My to do list section is not present on UI");
        }
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
            String studentID = "427725";//fetch from DB
            bringElementIntoView(getElements(Students.myRecentGradeRows).get(getElements(Students.myRecentGradeRows).size()-1));
            int myRecentGradeRowCount = getElements(Students.myRecentGradeRows).size() - 1;
            //add getStudentIdFromDB(studentName) to string.format
            ArrayList<HashMap<String, String>> myGrades = executeAndGetSelectQueryData(DataBaseQueryConstant.STUDENT_GRADE_WITH_SUBJECT_AD_DB
                    .replaceAll(TableColumn.ROW_COUNT_DATA,Integer.toString(myRecentGradeRowCount)
                    .replaceAll(TableColumn.STUDENT_ID_DATA,studentID)), AD_DATA_BASE);

            for (HashMap<String, String> rowData : myGrades) {
                assignment = rowData.get(TableColumn.ASSESSMENT);
                grade = rowData.get(TableColumn.GRADE);
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
//                assignment = rowData.get(Students.ASSESSMENT);
//                grade = rowData.get(Students.GRADE);
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
        click(getChildElement(getElement(Students.lastViewedVideoLessonsSection),Students.startWatchingYourLessonsLin));
        waitForPageTobLoaded();
        new DashboardScreen().waitAndCloseWidgetTourPopup();
        return this;
    }

    public StudentsScreen validateDigitalAssessmentsAreLockedOrNot(boolean isAssessmentLocked){
            String subject;
            String lesson;
            boolean isLocked;
            String studentID = userAccountDetails.get(TableColumn.STUDENT_ID);;
            ArrayList<HashMap<String,String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.ASSESSMENT_TEST_QUIZZES_AD_DB
                    .replaceAll(TableColumn.STUDENT_ID_DATA,studentID), AD_DATA_BASE);
            refresh();
            waitForPageTobLoaded();
            for(HashMap<String,String> row:myLessonsToday) {
                subject = row.get(SHORT_DESCRIPTION);
                lesson = row.get(LONG_DESCRIPTION);
                isLocked = false;//need to fetch from DB;
                if(isAssessmentLocked) {
                    softAssertions.assertThat(isElementExists(String.format(Students.assessmentLocked, subject,lesson)))
                            .as(subject + "-"+lesson+" subject is not locked on assessment page").isTrue();
                }else {
                    softAssertions.assertThat(isElementExists(String.format(Students.assessmentUnlocked, subject,lesson)))
                            .as("["+subject +"-"+lesson+ "] is not available for assessment, though lesson is completed").isTrue();
                }
            }
        return this;
    }

    /**
     * Here we are validating only video list, not clicking on video link
     * @return
     */
    public StudentsScreen validateMyLessonsTodaySectionData(){
        if(isElementExists(Students.MY_LESSONS_TODAY)) {
            if (isElementExists(Students.lessonsToday)) {
                String studentID = userAccountDetails.get(TableColumn.STUDENT_ID);
                String myLessonsVideoLink;
                String subject;
                String lesson;
                ArrayList<HashMap<String,String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                        .replaceAll(TableColumn.STUDENT_ID_DATA,studentID), CommonConstants.SD_DATA_BASE);
                for(HashMap<String,String> row:myLessonsToday) {
                    subject = row.get(SHORT_DESCRIPTION);
                    lesson = row.get(LONG_DESCRIPTION);
                    myLessonsVideoLink = String.format(Students.myLessonsTodayVideoLink, subject, lesson);
                    softAssertions.assertThat(isElementExists(myLessonsVideoLink))
                            .as(lesson +" of "+subject+" subject is not present in My lessons today section").isTrue();
                }
            } else {
                softAssertions.fail(Students.MY_LESSONS_TODAY+" is not having any video link on UI");
            }
        }else {
            softAssertions.fail(Students.MY_LESSONS_TODAY+" section is not present on UI");
        }
        return this;
    }

    /**
     * We are validating My lessons today videos by clicking on link and after changing status from DB it should shown completed in video library
     * Also, validating the My today's lesson and Video library having same video.
     * @return
     */
    public StudentsScreen watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary() {
        if (isElementExists(Students.MY_LESSONS_TODAY)) {
            if (isElementExists(Students.lessonsToday)) {
                String myLessonsVideoLink;
                String subject;
                String lesson;
                String subjectIDNumber;
                String subscriptionItemNumber;
                String studentID = userAccountDetails.get(STUDENT_ID);
                ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                        .replaceAll(TableColumn.STUDENT_ID_DATA,studentID), CommonConstants.SD_DATA_BASE);
                //for loop
                for (HashMap<String, String> row : myLessonsToday) {
                    subject = row.get(SHORT_DESCRIPTION);
                    lesson = row.get(LONG_DESCRIPTION);
                    subjectIDNumber = "fetch Video library data from DB";
                    subscriptionItemNumber = "fetch Video library data from DB";
                    myLessonsVideoLink = String.format(Students.myLessonsTodayVideoLink, subject, lesson);

                    //pending
                    softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subjectIDNumber, subscriptionItemNumber, lesson.split("\\s")[1], subject))
                            .as(lesson + " lesson of " + subject + " subject is already viewed and completed according Video library status").isFalse();

                    bringElementIntoView(myLessonsVideoLink);
                    click(myLessonsVideoLink);
                    softAssertions.assertThat(isChildElementExists(getElement(Students.videoPlayer),String.format(Students.playingVideoTitle,subject+" - "+lesson)))
                            .as("Running video is not similar to clicked link").isTrue();

                    //logic needs to implement to complete video from back end
                    refresh();
                    waitForPageTobLoaded();
                    softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subjectIDNumber, subscriptionItemNumber, lesson.split("\\s")[1], subject))
                            .as(lesson + " lesson of " + subject + " subject's video status is not updated to viewed/completed in Video library section").isTrue();
                }
            }else{
                    softAssertions.fail(Students.MY_LESSONS_TODAY + " is not having any video link on UI");
                }
            } else {
                softAssertions.fail(Students.MY_LESSONS_TODAY + " section is not present on UI");
            }
            return this;
    }

    public StudentsScreen validateVideoLibraryVideoStatusWithDataBase(){
        String subject;
        String lesson;
        String subjectIDNumber;
        String subscriptionItemNumber;
        String studentID = userAccountDetails.get(STUDENT_ID);

        // Query needed to fetch video status of video library
        ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.VIDEO_LIBRARY_VIDEOS_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,studentID), CommonConstants.SD_DATA_BASE);
        for (HashMap<String, String> row : myLessonsToday) {
            subject = row.get(SHORT_DESCRIPTION);
            lesson = row.get(LONG_DESCRIPTION);
            subjectIDNumber = "fetch Video library data from DB";
            subscriptionItemNumber = "fetch Video library data from DB";
            //pending
            softAssertions.assertThat(getIsLessonCompletedStatusFromVideoLibrary(subjectIDNumber, subscriptionItemNumber, lesson.split("\\s")[1], subject))
                    .as(lesson + " lesson of " + subject + " subject's video status is not showing as completed").isTrue();
        }
        return this;
    }


    public StudentsScreen validateIsLessonVideoLinkIsPresentInVideoLibrary(String subject, String lesson, String subjectIDNumber, String subscriptionItemNumber){
        selectValueFromDropDown(subject,lesson);
        tempXpath = String.format(Students.videoLibraryVideoLink,subjectIDNumber,subscriptionItemNumber,lesson);
        if(isChildElementExists(getElement(Students.videoLibrarySection),tempXpath)){
            bringElementIntoView(getChildElement(getElement(Students.videoLibrarySection),tempXpath));
            videoViewStatusVideoLibrary.put(subject+":"+lesson,
                    isLessonCompleted(getClassAttributeValue(getChildElement(getElement(Students.videoLibrarySection),tempXpath))));
        }else {
            softAssertions.fail("Video link is not present in Video Library section for Subject:"+subject+" and Lesson:"+lesson+
                    "\n xpath ="+getChildElement(getElement(Students.videoLibrarySection),tempXpath).toString());
        }
        return this;
    }

    public boolean getIsLessonCompletedStatusFromVideoLibrary(String subjectIDNumber, String subscriptionItemNumber, String lesson, String subject){
        selectValueFromDropDown(Students.videoLibrarySubjectDropDown,subject);
        tempXpath = String.format(Students.videoLibraryVideoLink,subjectIDNumber,subscriptionItemNumber,lesson);
        bringElementIntoView(getChildElement(getElement(Students.videoLibrarySection),tempXpath));
        //here we need to add previous video marked as completed with the help of video library data from DB
        return isLessonCompleted(getClassAttributeValue(getChildElement(getElement(Students.videoLibrarySection),tempXpath)));
    }

    public StudentsScreen validateAssessmentsAreLocked(){
        return this;
    }

    public StudentsScreen validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary(){
        String subject;
        String studentId = userAccountDetails.get(STUDENT_ID);
        ArrayList<HashMap<String, String>> myLessonsToday = executeAndGetSelectQueryData(DataBaseQueryConstant.MY_LESSONS_TODAY_SD_DB
                .replaceAll(TableColumn.STUDENT_ID_DATA,studentId), CommonConstants.SD_DATA_BASE);

        for (HashMap<String, String> row : myLessonsToday) {
            subject = row.get(SHORT_DESCRIPTION);
            selectValueFromDropDown(Students.videoLibrarySubjectDropDown, subject);
            click(bringElementIntoView(getElement(Students.videoLibrarySection)));
            tempXpath = String.format(Students.videoLibraryVideoLink, LESSON_LOCKED,YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON,CommonConstants.CLOSE);
            if (isElementExists(tempXpath)) {
                click(tempXpath);
            } else {
                softAssertions.fail(Students.YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON + " message popup is not appeared.");
            }
        }
        return this;
    }

    public boolean isLessonCompleted(String classValue){
        return classValue.equalsIgnoreCase(CommonConstants.LESSON_COMPLETED_CLASS_VALUE);
    }
}
