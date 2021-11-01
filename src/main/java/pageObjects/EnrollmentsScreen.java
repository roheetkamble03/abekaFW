package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import constants.CommonConstants;
import constants.EnrollmentOptions;
import constants.StudentDetails;
import elementConstants.Enrollments;
import lombok.SneakyThrows;
import utility.ExcelUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static elementConstants.Enrollments.*;
import static elementConstants.Students.previousGrade;
import static elementConstants.Students.previousProgram;

public class EnrollmentsScreen extends GenericAction {


    public EnrollmentsScreen validateEnrollmentPageSection() {
        ArrayList<String> sectionList = new ArrayList<>(Arrays.asList(Enrollments.NEW,Enrollments.SAVED,Enrollments.HELD,Enrollments.ACTIVE));
        for(String section:sectionList){
            softAssertions.assertThat(isElementExists(String.format(Enrollments.sectionChildLink,section), false))
                    .as(section+" is not appeared on Enrollment page").isTrue();
        }
        return this;
    }

    public EnrollmentsScreen openSectionLink(String sectionName, String linkText){
        click(bringElementIntoView(getElements(String.format(Enrollments.sectionChildOpenLink,sectionName,linkText)).get(0)));
        waitForPageTobeLoaded();
        return this;
    }

   public EnrollmentsScreen validateStudentPageHeader(){
        softAssertions.assertThat(isElementDisplayed(Enrollments.studentPageHeader))
                .as(Enrollments.studentPageHeader+" page header is not appeared on Enrollment student page").isTrue();
        return this;
   }

    public EnrollmentsScreen goToAddNewStudentPage(){
        click(bringElementIntoView(Enrollments.addNewStudent));
        return this;
    }

    public EnrollmentsScreen fillAndSubmitNewStudentDetails(StudentDetails studentDetails, boolean isValidateUserNameAlreadyExistMessage){
        type(Enrollments.formFirstNameInputBox,studentDetails.getFirstName());
        type(Enrollments.formMiddleNameInputBox,studentDetails.getMiddleName());
        type(Enrollments.formLastNameInputBox,studentDetails.getLastName());
        selectByVisibleText(Enrollments.formSuffixSelectBox,studentDetails.getSuffix());
        selectByVisibleText(Enrollments.formGenderSelectBox,studentDetails.getGender());
        type(Enrollments.formBirthDateInputBox,studentDetails.getBirthDate());
        if(isValidateUserNameAlreadyExistMessage) {
            validateUserNameAlreadyExistsMessage();
        }
        type(Enrollments.formUserNameInputBox,studentDetails.getStudentUserId());
        click(Enrollments.formPasswordInputBox);
        implicitWaitInSeconds(3);
        type(Enrollments.formPasswordInputBox,studentDetails.getPassword());
        click(Enrollments.CREATE);
        waitForPageTobeLoaded();
        log("Student account created with following details:\n" +
                "UserName:"+studentDetails.getStudentUserId()+"\n" +
                "Password:"+studentDetails.getPassword());
        return this;
    }

    public EnrollmentsScreen fillAvailableRecommendedCourses(String grade){
        switch (grade){
            case GRADE_TWELVE:
                selectByVisibleText(GraduationPetition.generalSubjectDropdown,GraduationPetition.KEYBOARDING);
                selectByVisibleText(String.format(GraduationPetition.practicalSubjectDropdown,1),GraduationPetition.HOME_SEWING);
                waitForElementTobeExist(String.format(GraduationPetition.practicalSubjectDropdown,5));
                selectByVisibleText(String.format(GraduationPetition.practicalSubjectDropdown,5),GraduationPetition.DOCUMENT_PROCESSING);
                break;
        }
        List<SelenideElement> elementList = getElements(String.format(Enrollments.enrollmentOptionRadioBtn, Enrollments.DIGITAL));
        for(SelenideElement element: elementList){
            clickByJavaScript(element);
        }
        return this;
    }

    public void validateUserNameAlreadyExistsMessage(){
        type(Enrollments.formUserNameInputBox,"test");
        click(Enrollments.formPasswordInputBox);
        isElementDisplayed(Enrollments.userNameAlreadyExists);
    }

    public EnrollmentsScreen clickOnNextButton(){
        bringElementIntoView(Enrollments.nextButton);
        click(Enrollments.nextButton);
        waitForPageTobeLoaded();
        return this;
    }

    public EnrollmentsScreen validateCourseBeginDateFormat(){
        int year = LocalDate.now().getYear();
        log("Validating date 12/01/0001");
        type(Enrollments.beginDateInputBox,"12/01/0001");
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(BEGIN_DATE_IN_WRONG_FORMAT, false))
                .as(BEGIN_DATE_IN_WRONG_FORMAT+" error is not appeared for date 12/01/0001").isTrue();

        log("Validating date 13/01/"+year);
        type(Enrollments.beginDateInputBox,"13/01/"+year);
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(BEGIN_DATE_IN_WRONG_FORMAT, false))
                .as(BEGIN_DATE_IN_WRONG_FORMAT+" error is not appeared for date 13/01/0001").isTrue();

        int lastDayOfTheMonth = LocalDate.now().lengthOfMonth()+1;
        log("Validating date 12/"+lastDayOfTheMonth+1+"/"+year);
        type(Enrollments.beginDateInputBox,"12/"+lastDayOfTheMonth+"/"+year);
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(BEGIN_DATE_IN_WRONG_FORMAT, false))
                .as(BEGIN_DATE_IN_WRONG_FORMAT+" error is not appeared for date 12/"+lastDayOfTheMonth+"/"+year).isTrue();

        log("Validating date abcd");
        type(Enrollments.beginDateInputBox,"abcd");
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(BEGIN_DATE_IN_WRONG_FORMAT, false))
                .as(BEGIN_DATE_IN_WRONG_FORMAT+" error is not appeared for date abcd").isTrue();

        log("Validating date aa/bb/cccc");
        type(Enrollments.beginDateInputBox,"aa/bb/cccc");
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(BEGIN_DATE_IN_WRONG_FORMAT, false))
                .as(BEGIN_DATE_IN_WRONG_FORMAT+" error is not appeared for date aa/bb/cccc").isTrue();
        return this;
    }

    public EnrollmentsScreen validateBeginDate(){
        String beginDate = getElementText(Enrollments.beginDateLbl);
        String maxDate = getElementText(Enrollments.maxDateLbl);
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(String.format(Enrollments.BEGIN_DATE_REQUIRED,beginDate), false))
                .as(String.format(Enrollments.BEGIN_DATE_BEFORE_ERROR,beginDate)+" error is not appeared").isTrue();

        type(Enrollments.beginDateInputBox,getModifiedDate(beginDate,0,0,1, CommonConstants.MINUS));
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(String.format(Enrollments.BEGIN_DATE_BEFORE_ERROR,beginDate), false))
                .as(String.format(Enrollments.BEGIN_DATE_BEFORE_ERROR,beginDate)+" error is not appeared").isTrue();

        type(Enrollments.beginDateInputBox,getModifiedDate(maxDate,0,0,1, CommonConstants.PLUS));
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(String.format(Enrollments.BEGIN_DATE_PAST_ERROR,maxDate), false))
                .as(String.format(Enrollments.BEGIN_DATE_PAST_ERROR,maxDate)+" error is not appeared").isTrue();

        return this;
    }

    public EnrollmentsScreen addBeginDate(){
        String beginDate = getElementText(Enrollments.beginDateLbl);
        type(Enrollments.beginDateInputBox,getModifiedDate(beginDate,1,0,0, CommonConstants.PLUS));
        return this;
    }

    public EnrollmentsScreen removeExistingGuardianIfExists(){
        if(isElementExists(Enrollments.existingGuardianRemove, false)) {
            click(Enrollments.existingGuardianRemove);
        }
        return this;
    }

    @SneakyThrows
    public EnrollmentsScreen fillEnrollmentOptionsDetails(StudentDetails studentDetails, EnrollmentOptions enrollmentOptions){
        switch (studentDetails.getGrade()){
            case GRADE_ONE_ACCREDITED:
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getPenmanship())));
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
                bringElementIntoView(enrollmentOptions.getGuardians());
                click(enrollmentOptions.getGuardians());
                implicitWaitInSeconds(3);
                if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
                    confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
                }else {
                    addNewParent(enrollmentOptions.getParentName());
                }
                break;
            case (Enrollments.GRADE_FOUR_ACCREDITED):
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
                bringElementIntoView(enrollmentOptions.getGuardians());
                click(enrollmentOptions.getGuardians());
                implicitWaitInSeconds(3);
                if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
                    confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
                } else {
                    addNewParent(enrollmentOptions.getParentName());
                }
                break;
                case (Enrollments.GRADE_NINE):
            case (GRADE_TWELVE):
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
                bringElementIntoView(enrollmentOptions.getGuardians());
                click(enrollmentOptions.getGuardians());
                implicitWaitInSeconds(3);
                if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
                    confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
                } else {
                    addNewParent(enrollmentOptions.getParentName());
                }
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getAssessmentType())));
                break;
            default:
                throw new Exception("Wrong grade passed"+ studentDetails.getGrade());
        }
        return this;
    }

    public EnrollmentsScreen confirmExistingParentGuardian(String parent, String relation){
        selectByVisibleText(Enrollments.parentSelectBox,parent);
        selectByVisibleText(Enrollments.relationSelectBox,relation);
        click(Enrollments.confirmButton);
        waitForPageTobeLoaded();
        return this;
    }

    public EnrollmentsScreen signEnrollmentAgreement(String signature){
        bringElementIntoView(Enrollments.agreementSignatureInputBox);
        type(Enrollments.agreementSignatureInputBox, signature);
        return this;
    }

   public EnrollmentsScreen submitEnrollment(){
        waitForElementTobeVisibleOrMoveAhead(Enrollments.applicationNumber, veryLongWait);
        getUserAccountDetails().setApplicationNumber(getElementText(applicationNumber));
       clickOnNextButton();
       return this;
   }

   public EnrollmentsScreen fillProofOfCompletion(String gradeTobeEnrolled){
        selectByVisibleText(previousGrade,getLastCompletionGrade(gradeTobeEnrolled));
        selectByVisibleText(previousProgram, HOME_SCHOOLING);
        getElements(Enrollments.RADIO_NO).stream().forEach(e->clickByJavaScript(e));
//        click(bringElementIntoView(Enrollments.firstSemesterNo));
//        click(bringElementIntoView(Enrollments.firstSemesterNo));
//        click(bringElementIntoView(Enrollments.repeatNo));
//        click(bringElementIntoView(Enrollments.diplomaFromAbekaNo));
//        click(bringElementIntoView(Enrollments.enrolledAnotherProgramNo));
        return this;
   }

   public EnrollmentsScreen fillAddClasses(){
        waitForElementTobeExist(Enrollments.mailThisFormLaterChkBox, veryLongWait);
        clickByJavaScript(Enrollments.mailThisFormLaterChkBox);
        return this;
   }

    private String getLastCompletionGrade(String gradeTobeEnrolled) {
        switch (gradeTobeEnrolled){
            case Enrollments.GRADE_FOUR_ACCREDITED:
                return "3rd";
            case Enrollments.GRADE_NINE:
                return "8th";
            case GRADE_TWELVE:
                return "11th";
        }
        return null;
    }

    public EnrollmentsScreen validateAllSetMessage(){
//        if(!(isElementExists(Enrollments.ALL_SET, false)||
//                isElementExists(applicationStatusCompleted, false, veryLongWait))){
//            if(isElementExists(Enrollments.nextButton, false, elementLoadWait)){
//                clickOnNextButton();
//            }
//        }
        softAssertions.assertThat(isElementExists(Enrollments.ALL_SET, false)||
                isElementExists(applicationStatusCompleted, false, veryLongWait)).as(Enrollments.ALL_SET+" or Completed status is not appeared on screen.").isTrue();
        return this;
   }

    public EnrollmentsScreen addParentAccountDetailsToTestData(String userName, String password){
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.setCellData("ParentCredentials",new String[]{userName,password});
        return this;
    }

    public EnrollmentsScreen addNewParent(String signature) {
        clickIfExists(NEW_PARENT);
        try {
            selectByIndex(guardianTitle, 1);
        }catch (Exception e){
            click(bringElementIntoView(NEW_PARENT));
            selectByIndex(guardianTitle, 1);
        }
        type(guardianFirstName,signature.split("\\s")[0]);
        type(guardianLastName, signature.split("\\s")[1]);
        try {
            selectByIndex(guardianRelation, 1);
        }catch (Exception e){
            clickIfExists(NEW_PARENT);
            selectByIndex(guardianRelation, 1);
        }
        click(addNewParentDoneBtn);
        implicitWaitInSeconds(3);
        waitForElementTobeDisappear(pleaseWait);
        implicitWaitInSeconds(3);
        return this;
    }
}
