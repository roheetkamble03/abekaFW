package pageObjects;

import base.GenericAction;
import constants.CommonConstants;
import constants.EnrollmentOptions;
import constants.StudentDetails;
import elementConstants.Enrollments;

import java.util.ArrayList;
import java.util.Arrays;

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

    public EnrollmentsScreen fillAndSubmitNewStudentDetails(StudentDetails studentDetails){
        type(Enrollments.formFirstNameInputBox,studentDetails.getFirstName());
        type(Enrollments.formMiddleNameInputBox,studentDetails.getMiddleName());
        type(Enrollments.formLastNameInputBox,studentDetails.getLastName());
        selectByVisibleText(Enrollments.formSuffixSelectBox,studentDetails.getSuffix());
        selectByVisibleText(Enrollments.formGenderSelectBox,studentDetails.getGender());
        type(Enrollments.formBirthDateInputBox,studentDetails.getBirthDate());
        validateUserNameAlreadyExistsMessage();
        type(Enrollments.formUserNameInputBox,studentDetails.getUserName());
        click(Enrollments.formPasswordInputBox);
        implicitWaitInSeconds(3);
        type(Enrollments.formPasswordInputBox,studentDetails.getPassword());
        click(Enrollments.CREATE);
        waitForPageTobeLoaded();
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

    public EnrollmentsScreen fillEnrollmentOptionsDetails(EnrollmentOptions enrollmentOptions){
        click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getPenmanship())));
        click(bringElementIntoView(String.format(Enrollments.enrollmentOptionRadioBtn,enrollmentOptions.getStreaming())));
        bringElementIntoView(enrollmentOptions.getGuardians());
        click(enrollmentOptions.getGuardians());
        implicitWaitInSeconds(3);
        if(enrollmentOptions.getGuardians().equals(Enrollments.EXISTING_PARENT)){
            confirmExistingParentGuardian(enrollmentOptions.getParentName(),enrollmentOptions.getRelation());
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

   public EnrollmentsScreen validateAllSetMessage(){
        softAssertions.assertThat(isElementExists(Enrollments.ALL_SET)).as(Enrollments.ALL_SET+" message is not appeared on screen.").isTrue();
        return this;
   }
}
