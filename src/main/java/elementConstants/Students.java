package elementConstants;

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
    String TRANSCRIPT_ALER_TEXT = "Transcript Request submitted and being processed";

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
}
