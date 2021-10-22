package elementConstants;

import java.util.Random;

public @interface Enrollments {
    /**
     * Element text
     */
    String GRADE_ONE_ACCREDITED = "Grade 1 - Accredited";
    String GRADE_ONE_VIDEO = "Grade 1 Video";
    String GRADE_FOUR_VIDEO = "Grade 4 Video";
    String GRADE_NINE_VIDEO = "Grade 9 Video";
    String GRADE_TWELVE_VIDEO = "Grade 12 Video";
    String GRADE_FOUR_ACCREDITED = "Grade 4 - Accredited";
    String GRADE_NINE = "Grade 9";
    String GRADE_TWELVE = "Grade 12";
    String NEW = "New";
    String SAVED = "Saved";
    String HELD = "Held";
    String ACTIVE = "Active";
    String SELECT_OR = "Select or";
    String ADD_A_STUDENT = "Add a Student";
    String SUFFIX_JR = "Jr.";
    String SUFFIX_SR = "Sr.";
    String SUFFIX_I = "I";
    String SUFFIX_II = "II";
    String SUFFIX_III = "III";
    String SUFFIX_IV = "IV";
    String SUFFIX_V = "V";
    String SUFFIX_VI = "VI";
    String MALE = "Male";
    String FEMALE = "Female";
    String GENDER = new String[]{MALE, FEMALE}[new Random().nextInt(1)];
    String USER_ALREADY_EXISTS_ERR_MSG = "Username already exists, please enter a different username.";
    String MANUSCRIPT = "Manuscript";
    String ACCREDITED_PROGRAM = "Accredited Program";
    String CURSIVE = "Cursive";
    String STREAMING = "Streaming";
    String DVD = "DVD";
    String EXISTING_PARENT = "Confirm Existing Parent/Guardian";
    String NEW_PARENT = "Add New Parent/Guardian";
    String BEGIN_DATE_BEFORE_ERROR = "Begin date cannot be before %s.";
    String BEGIN_DATE_PAST_ERROR = "Begin date cannot be past %s.";
    String BEGIN_DATE_IN_WRONG_FORMAT = "Please provide the begin date in MM/DD/YYYY format.";
    String BEGIN_DATE_REQUIRED = "Begin date is required";
    String ALL_SET = "You’re all set!";
    String GRADE_ONE = "Grade One";
    String HOME_SCHOOLING = "Homeschooling";
    String DIGITAL_ASSESSMENTS = "Digital Assessments";
    String DIGITAL = "Digital";

    /**
     * Element xpath
     */
    String sectionChildLink = "xpath=//h2[normalize-space(text())='%s']";
    String sectionChildOpenLink = "xpath=//h2[normalize-space(text())='%s']/following-sibling::div/descendant::a[@id='lkbApp' and normalize-space(text())='%s']";
    String addNewStudent = "id=btnAddNewStudent";
    String studentPageHeader = String.format("xpath=//h1[normalize-space(text())='%s']/span[normalize-space(text())='%s']",SELECT_OR,ADD_A_STUDENT);
    String formFirstNameInputBox = "id=txtFirstName";
    String formMiddleNameInputBox = "id=txtMiddleName";
    String formLastNameInputBox = "id=txtLastName";
    String formSuffixSelectBox = "id=drpSuffix";
    String formGenderSelectBox = "id=drpGender";
    String formBirthDateInputBox = "id=txtBirthDate";
    String formUserNameInputBox = "id=txtNewUserName";
    String formPasswordInputBox = "id=txtNewPassword";
    String CREATE = "id=btnCreateStudent";
    String userNameAlreadyExists = String.format("xpath=//span[@id='csvNewUserName' and normalize-space(text())='%s']",USER_ALREADY_EXISTS_ERR_MSG);
    String nextButton = "id=btnNext";
    String parentSelectBox = "id=ddlUnassignedGuardians";
    String relationSelectBox = "id=ddlRelation";
    String confirmButton = "id=btnAssignGuardian";
    String enrollmentOptionRadioBtn = "xpath=//span[normalize-space(text())='%s']/preceding-sibling::span/descendant::input[@type='radio']";
    String beginDateInputBox = "id=txtBeginDate";
    String beginDateLbl = "id=lblMinBeginDate";
    String maxDateLbl = "id=lblMaxBeginDate";
    String agreementSignatureInputBox = "id=txtSignature";
    String existingGuardianRemove = "id=lbtnRemove";
    String repeatNo = "id=rbnRepeatNo";
    String enrolledAnotherProgramNo = "id=rbtnAnotherNo";
    String firstSemesterNo = "id=rbtnFirstSemesterOfNextGradeNo";
    String mailThisFormLaterChkBox = "id=ckbMailIn";
    String diplomaFromAbekaNo = "id=rbnDiplomaNo";
    String mailFormLater = "id=ckbMailIn";
    String RADIO_NO = "xpath=//input[@type='radio' and contains(@id,'No')]";
    String guardianTitle = "id=ddlGuardianTitle";
    String guardianFirstName = "id=txtGuardianFirstName";
    String guardianLastName = "id=txtGuardianLastName";
    String guardianRelation = "id=ddlNewRelation";
    String addNewParentDoneBtn = "id=btnCreateGuardian";
    String pleaseWait = "text=Please wait...";
    String orderProcessingMessage = "Please wait while we process your order…";
    String orderProcessingLoaderText = "id=loaderText";
    String applicationNumber = "id=lblAppNo";
    String applicationStatusCompleted = "xpath=//span[@id='lblAppStatus' and normalize-space(text())='Completed']";
}
