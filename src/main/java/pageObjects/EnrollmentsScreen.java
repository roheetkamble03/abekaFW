package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import constants.CommonConstants;
import constants.EnrollmentOptions;
import constants.StudentDetails;
import elementConstants.Enrollments;
import elementConstants.Students;
import lombok.SneakyThrows;
import utility.ExcelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.CommonConstants.*;
import static elementConstants.Enrollments.HOME_SCHOOLING;
import static elementConstants.Students.previousGrade;
import static elementConstants.Students.previousProgram;

public class EnrollmentsScreen extends GenericAction {


    public EnrollmentsScreen validateEnrollmentPageSection() {
        ArrayList<String> sectionList = new ArrayList<>(Arrays.asList(Enrollments.NEW,Enrollments.SAVED,Enrollments.HELD,Enrollments.ACTIVE));
        for(String section:sectionList){
            softAssertions.assertThat(isElementExists(String.format(Enrollments.sectionChildLink,section)))
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
        type(Enrollments.formUserNameInputBox,studentDetails.getUserName());
        click(Enrollments.formPasswordInputBox);
        implicitWaitInSeconds(3);
        type(Enrollments.formPasswordInputBox,studentDetails.getPassword());
        click(Enrollments.CREATE);
        waitForPageTobeLoaded();
        return this;
    }

    public EnrollmentsScreen fillAvailableRecommendedCourses(){
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

    public EnrollmentsScreen validateBeginDate(){
        String beginDate = getElementText(Enrollments.beginDateLbl);
        String maxDate = getElementText(Enrollments.maxDateLbl);
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(String.format(Enrollments.BEGIN_DATE_REQUIRED,beginDate)))
                .as(String.format(Enrollments.BEGIN_DATE_BEFORE_ERROR,beginDate)+" error is not appeared").isTrue();

        type(Enrollments.beginDateInputBox,getModifiedDate(beginDate,0,0,1, CommonConstants.MINUS));
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(String.format(Enrollments.BEGIN_DATE_BEFORE_ERROR,beginDate)))
                .as(String.format(Enrollments.BEGIN_DATE_BEFORE_ERROR,beginDate)+" error is not appeared").isTrue();

        type(Enrollments.beginDateInputBox,getModifiedDate(maxDate,0,0,1, CommonConstants.PLUS));
        clickOnNextButton();
        softAssertions.assertThat(isElementExists(String.format(Enrollments.BEGIN_DATE_PAST_ERROR,maxDate)))
                .as(String.format(Enrollments.BEGIN_DATE_PAST_ERROR,maxDate)+" error is not appeared").isTrue();

        return this;
    }

    public EnrollmentsScreen addBeginDate(){
        String beginDate = getElementText(Enrollments.beginDateLbl);
        type(Enrollments.beginDateInputBox,getModifiedDate(beginDate,1,0,0, CommonConstants.PLUS));
        return this;
    }

    public EnrollmentsScreen removeExistingGuardianIfExists(){
        if(isElementExists(Enrollments.existingGuardianRemove)) {
            click(Enrollments.existingGuardianRemove);
        }
        return this;
    }

    @SneakyThrows
    public EnrollmentsScreen fillEnrollmentOptionsDetails(StudentDetails studentDetails, EnrollmentOptions enrollmentOptions){
        switch (studentDetails.getGrade()){
            case Enrollments.GRADE_ONE_ACCREDITED:
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getPenmanship())));
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
                bringElementIntoView(enrollmentOptions.getGuardians());
                click(enrollmentOptions.getGuardians());
                implicitWaitInSeconds(3);
                if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
                    confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
                }
                break;
            case (Enrollments.GRADE_FOUR):
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getProgram())));
                bringElementIntoView(enrollmentOptions.getGuardians());
                click(enrollmentOptions.getGuardians());
                implicitWaitInSeconds(3);
                if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
                    confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
                }
                break;
            case (Enrollments.GRADE_NINE):
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
                click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getProgram())));
                bringElementIntoView(enrollmentOptions.getGuardians());
                click(enrollmentOptions.getGuardians());
                implicitWaitInSeconds(3);
                if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
                    confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
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
       clickOnNextButton();
       return this;
   }

   public EnrollmentsScreen fillProofOfCompletion(String gradeTobeEnrolled){
        selectByVisibleText(previousGrade,getLastCompletionGrade(gradeTobeEnrolled));
        selectByVisibleText(previousProgram, HOME_SCHOOLING);
        click(bringElementIntoView(Enrollments.firstSemesterNo));
        click(bringElementIntoView(Enrollments.firstSemesterNo));
        click(bringElementIntoView(Enrollments.repeatNo));
        click(bringElementIntoView(Enrollments.diplomaFromAbekaNo));
        click(bringElementIntoView(Enrollments.enrolledAnotherProgramNo));
        return this;
   }

   public EnrollmentsScreen fillAddClasses(){
        waitForElementTobeExist(Enrollments.mailThisFormLaterChkBox);
        click(bringElementIntoView(Enrollments.mailThisFormLaterChkBox));
        return this;
   }

    private String getLastCompletionGrade(String gradeTobeEnrolled) {
        switch (gradeTobeEnrolled){
            case Enrollments.GRADE_FOUR:
                return "3rd";
            case Enrollments.GRADE_NINE:
                return "8th";
        }
        return null;
    }

    public EnrollmentsScreen validateAllSetMessage(){
        softAssertions.assertThat(isElementExists(Enrollments.ALL_SET)).as(Enrollments.ALL_SET+" message is not appeared on screen.").isTrue();
        return this;
   }

   public EnrollmentsScreen addStudentAccountDetailsToTestData(StudentDetails studentDetails){
       ExcelUtils excelUtils = new ExcelUtils();
       excelUtils.setCellData(studentDetails.getGrade().replaceAll("\\s","")
               .concat(STUDENT_CREDENTIALS),new String[]{studentDetails.getUserName(),studentDetails.getPassword()});
        return this;
   }
}
