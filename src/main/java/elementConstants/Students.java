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
    String QUIZ = "Quiz";
    String TEST = "Test";
    String EXAM = "Exam";
    String MISC_NON_GRADED = "MISC_NON_GRADED";
    String SIX_MONTHS_ASSESSMENT = "Accelerated (6 months)";
    String NINE_MONTHS_ASSESSMENT = "Standard (9 months)";
    String TWELVE_MONTHS_ASSESSMENT = "Extended (12 months)";
    String YES = "Yes";
    String No = "No";
    String LESSON_LOCKED = "Lesson Locked";
    String GRADE_TEXT_BOX_VALIDATOR_MESSAGE = "The highlighted fields below are required. Please be sure to enter a grade between 0 and 100.";
    String AVERAGE = "Average";
    String MULTIPLE_CHOICE = "MULTIPLE CHOICE";
    String TRUE_FALSE = "TRUE/FALSE";


    /**
     * Element xpath
     */
    String calendarPopupHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='calendarModal']";
    String calendarPopup = "calendarModal";
    String imagePopupHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='photoModal']";
    String imagePopup = "photoModal";
    String loginInfoPopupHeader = "xpath=//div[@id='passwordModal']/descendant::*[@class='modal-title themeColor']";
    String loginInfoPopup = "passwordModal";
    String gradeAlertHeader = "xpath=//h4[normalize-space(text())=\"%s\"]/ancestor::div[@id='thresholdModal']";
    String gradeAlertSettingPopup = "thresholdModal";
    String widgetLink = "xpath=//div[@id='%s']/descendant::a";
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
    String videoLibraryVideoLink = "xpath=//div[@id='lessonContents']//descendant::li[@data-lesson='%s' and @data-sub='%s']";
    String nextToNextVideoLink = "xpath=(//li[@id='VideoLibrary']/descendant::li[contains(normalize-space(@onclick),'Lesson_Clicked') and not(@class ='lessonComplete') and @data-teacher and position()])[1]";
    String lessonLockedCloseButton = "xpath=//div[@id='lessonIncomplete']/descendant::*[normalize-space(text())='%s']/parent::div/following-sibling::div/descendant::p[normalize-space(text())='%s']/parent::div/parent::div/following-sibling::div/descendant::button[normalize-space(text())='%s']";
    String videoPlayer = "id=player";
    String playingVideoTitle = "xpath=//div[@id='player']/descendant::span[@id='lblTitle' and normalize-space(text())='%s']";
    String assessmentLocked = "xpath=//span[@id='lblSubject' and normalize-space(text())='%s']/parent::h4/following-sibling::p/descendant::a[@id='lbtnAssessment' and normalize-space(text())='%s']/preceding-sibling::span[@id='lblLockedIcon' and @data-original-title]";
    String assessmentUnlocked = "xpath=//span[@id='lblSubject' and normalize-space(text())='%s']/parent::h4/following-sibling::p/descendant::a[@id='lbtnAssessment' and normalize-space(text())='%s' and @href and @class='assessmentBtn']";
    String projectReportSubjectDropDown = "id=subjectsDropDown";
    String gradingPeriod = "xpath=//div[@class='gradingPeriods']/a[normalize-space(text())='%s']";
    String sectionHeader = "//div[@id='%s']/descendant::*[normalize-space(text())='%s']";
    String gradeTableHeader = "xpath=//div[@id='%s']/descendant::*[normalize-space(text())='%s']/following-sibling::table[@class='GradeTable']";
    String gradeTextBox = "xpath=//div[@id='%s']/descendant::*[normalize-space(text())='%s']/following-sibling::table[@class='GradeTable']/descendant::span[normalize-space(text())='%s']/parent::td/following-sibling::td/descendant::span[normalize-space(text())='%s']/parent::td/following-sibling::td/input[not(@type='hidden')]";
    String gradeHiddenTextBox = "xpath=//div[@id='%s']/descendant::*[normalize-space(text())='%s']/following-sibling::table[@class='GradeTable']/descendant::span[normalize-space(text())='%s']/parent::td/following-sibling::td/descendant::span[normalize-space(text())='%s']/parent::td/following-sibling::td/input[@type='hidden']";
    String gradeAverageXpath = "xpath=//div[@id='%s']/descendant::*[normalize-space(text())='%s']/following-sibling::table[@class='GradeTable']/descendant::span[normalize-space(text())='%s']/parent::td/following-sibling::td/span";
    String progressReportSignatureTextBox = "id=txtSignature";
    String submit = "id=lbtnSubmit";
    String calendarAssessmentModificationRadioBtn = "xpath=//div[@id='calendarModal']/descendant::label[normalize-space(text())='%s']/preceding-sibling::div/input[@type='radio']";
    String updateCalendarBtn = "id=btnSubmitCalendarLength";
    String saveCalendarPermission = "id=btnSaveCalendarPermission";
    String Y = "Y";
    String N = "N";
    String restartVideo = "id=restartVideo";
    //String myToDoListLesson = "xpath=//div[@id='updatePanel']/descendant::*[normalize-space(text())='%s']/parent::div/descendant::span[normalize-space(text())='%s']/preceding-sibling::a/span[normalize-space(text())='%s']";
    String myToDoListLesson = "xpath=//div[@id='updatePanel']/descendant::*[normalize-space(text())='%s']/parent::div/descendant::span[normalize-space(text())='%s']/ancestor::div[normalize-space(@class)='dailyLesson']/descendant::*[normalize-space(text())='%s']";
    String signature = "id=txtSignature";
    String signPledgeBtn = "id=lbtnSignPledge";
    String linkitBeginBtn = "id=startButton";
    String linkitQuestionPanel = "id=questionItemPanel";
    String linkitTotalQuestions = "xpath=//div[@class='currentQuestion']";
    String linkitQuestionType = "xpath=//div[@class='sectionInstructor']";
    String linkitMultipleChoiceFirstAnswer = "xpath=//choiceinteraction/descendant::div[@class='answer']/descendant::span";
    String linkitTrueFalseFirstAnswer = "xpath=//choiceinteraction/descendant::div[@class='answer']/descendant::span";
    String linkitNextQuestionBtn = "id=nextQuestionButton";
    String linkitSubmitAnswer = "id=acceptCompleteTestButton";
    String linkitStartAnotherSession = "id=takeAnotherTestButton";
}
