package elementConstants;

import com.codeborne.selenide.SelenideElement;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.STXstringImpl;

public @interface Students {
    /**
     * Text
     */
    String STUDENT_INFORMATION = "Student Information";
    String CALENDAR_SETTINGS = "Calendar Settings";
    String IMAGE = "Image";
    String LOGIN_INFORMATION = "Login Information";
    String GRADE_ALERT_SETTING = "Grade Alert Setting";
    String PROGRESS_REPORTS = "Progress Reports";
    String ASSESSMENT_PERMISSIONS = "Assessment Permissions";
    String CHANGE_CALENDAR_LENGTH = "Change %s's Calendar Length";
    String CHANGE_IMAGE = "Change %s's Image";
    String CHANGE_LOGIN_INFO = "Change %s's Login Information";
    String GRADE_ALERT_SETTING_HEADER = "Grade Alert Settings";
    String MY_TO_DO_LIST = "My To–Do List";
    String PASSWORD = "Password";
    String CHANGE_PASSWORD = "Change Password";
    String FORGOT_PASSWORD_MESSAGE = "Forgot your password? Ask your parent to reset it for you.";
    String SELF = "Self";
    String TRANSCRIPT_ALERT_TEXT = "Transcript Request submitted and being processed";
    String MY_LESSONS_TODAY = "My Lessons Today";
    String YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON = "You have not completed the previous lesson.";


    /**
     * Element xpath
     */
    String calendarPopupHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='calendarModal']";
    String calendarPopup = "calendarModal";
    String imagePopupHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='photoModal']";
    String imagePopup = "photoModal";
    String loginInfoPopupHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='passwordModal']";
    String loginInfoPopup = "passwordModal";
    String gradeAlertHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='thresholdModal']";
    String gradeAlertSettingPopup = "thresholdModal";
    String widgetLink = "xpath=//text()[normalize-space()='%s']";
    String updateAll = "id=updateButton";
    String toDoListHeader = "id=lblToDoList";
    String accountInfoWidgetLink = "xpath=//*[@id='Account']/descendant::a/descendant::text()[normalize-space()='%s']/parent::a";
    String changePasswordPopup = "passwordModal";
    String changePasswordHeader = String.format("xpath=//div[@id='passwordModal']/descendant::h4[normalize-space(text())='%s']",CHANGE_PASSWORD);
    String currentPassword = "id=txtOldPassword";
    String newPassword = "id=txtNewPassword";
    String forgotPasswordMessage = "id=forgotPassword";
    String changePasswordSubmitBtn = "id=btnChangePassword";
    String requestTranScriptBtn = "id=hlTranscript";
    String yourNameTextBox = "id=txtRequestedBy";
    String relationshipSelectBox = "id=ddlRelationship";
    String phoneNumberTextBox = "id=txtPhone";
    String sendMyTranscriptToPCCRadio = "id=rbtnPCC";
    String attentionLineTextBox = "id=txtAttention";
    String sendImmediately = "id=rbtnSendImmediately";
    String submitTranScriptBtn = "id=btnSubmitTranscript";
    String myRecentGradeRows = "xpath=//table[@id='gdvRecentGrades']/descendant::tr";
    String subjectWithGradeRow = "xpath=//table[@id='gdvRecentGrades']/descendant::tr/descendant::td[normalize-space(text())='%s']/following-sibling::td/descendant::span[@id='lblGrade' and normalize-space(text())='%s']";
    String myRecentGradeSection = "id=RecentGrades";
    String lastViewedVideoLessonsSection = "id=VODVideos";
    String startWatchingYourLessonsLin = "xpath=./descendant::a[@href='/Video2/streaming/']";
    String lessonsToday = "id=dvLessonsToday";
    String myLessonsTodayVideoLink = "xpath=//ul[@id='ulDailyLessons']/descendant::h3[normalize-space(text())='%s']/following-sibling::span[@id='linkVideo' and normalize-space(text())='%s']";
    String videoLibrarySection = "id=VideoLibrary";
    String videoLibrarySubjectDropDown = "id=subjectsDropDown";
    String videoLibraryVideoLink = "xpath=./descendant::li[@onclick and @data-evalue='%s' and @data-sub='%s' and @data-lesson='%s']";
    String nextToNextVideoLink = "xpath=./descendant::li[@class='lessonComplete'][last()]/following-sibling::li[position()=2]";
    String lessonLockedCloseButton = String.format("xpath=./descendant::div[@id='lessonIncomplete']/descendant::p[normalize-space(text())='%s']/parent::div/parent::div/following-sibling::div/descendant::button",YOU_HAVE_NOT_COMPLETED_THE_PREVIOUS_LESSON);
    String videoPlayer = "id=player";
    String playingVideoTitle = "xpath=./descendant::span[@id='lblTitle' and normalize-space(text())='%s']";
    String ASSESSMENT_TEST_LOCKED = "xpath=//span[@id='lblSubject' and normalize-space(text())='%s']/parent::h4/following-sibling::p/span[@id='lblLockedIcon' and @data-original-title]";
}
