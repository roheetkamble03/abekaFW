package pageObjects;

import base.GenericAction;
import constants.CheckoutCriteria;
import elementConstants.AbekaHome;

import static constants.CommonConstants.AD_DATA_BASE;
import static constants.DataBaseQueryConstant.UPDATE_SUBMITTED_PETITION_STATUS_AD_DB;
import static constants.TableColumn.STUDENT_ID_DATA;

public class GraduationPetition extends GenericAction {

    public static String generalSubjectDropdown = "xpath=//span[normalize-space(text())='General']/ancestor::h5/following-sibling::div/select";
    public static String practicalSubjectDropdown = "xpath=//span[normalize-space(text())='Practical']/ancestor::h5/following-sibling::div/descendant::select[%s]";
    public static String START_NEXT = "Start Next";
    public static String KEYBOARDING = "Keyboarding";
    public static String HOME_SEWING = "Home Ec.- Sewing";
    public static String DOCUMENT_PROCESSING = "Document Processing";
    public static String ceremonyDropdown = "id=ddlCeremony";
    public static String activityTextBox = "id=txtActivities";
    public static String heightDropdownFt = "id=ddlHeightFeet";
    public static String heightDropdownInch = "id=ddlHeightInches";
    public static String weightTextBox = "id=txtWeight";
    public static String contactNameTextBox = "id=txtContactName";
    public static String mobileNumberTextBox = "id=txtMobileNumber";
    private static String numberOfGuestTextBox = "id=txtNumberOfGuests";
    private static String countryDropdown = "id=ddlCountry";
    private static String stateDropdown = "id=ddlState";
    private static String paragraphTextBox = "id=txtParagraph";
    private static String saveAndContinueCTA = "xpath=//a[normalize-space(text())='Save and Continue']";
    private static String radioBtnEmail = "id=rdbtnEmail";
    private String agreementAcceptChkBox = "id=AgreementCheck";
    private String dressRequestChkBox = "id=DressRequireCheck";
    private String studentSignatureTextBox = "id=txtStudentSignature";
    private String studentSignatureLabel = "xpath=//div[normalize-space(text())='Please type the graduate’s name exactly as it appears in bold below into the Graduate’s Signature box.']/strong[normalize-space(@class)='text-center']";
    private String parentSignatureTextBox = "id=txtParentSignature";
    private String reviewCTA = "xpath=//a[normalize-space(text())='Review']";
    private String purchaseCTA = "xpath=//a[normalize-space(text())='Purchase']";
    private String processingLoader = "xpath=//h1[normalize-space(text())='Processing']/descendant::span[normalize-space(text())='Loading...']";
    private String reviewPetition = "xpath=//div[normalize-space(text())='Review Petition']";
    private String approvedPetition = "xpath=//*[normalize-space(text())='Approved']/following-sibling::div/descendant::p[contains(normalize-space(text()),'%s')]";



    public GraduationPetition startPetition(){
        click(START_NEXT, false);
        return this;
    }

    public GraduationPetition fillGraduationPetitionForm(String parentSignature){
        selectByIndex(ceremonyDropdown,1);
        type(activityTextBox, "Petition requested by Automation");
        selectByIndex(heightDropdownFt, 2);
        selectByIndex(heightDropdownInch, 2);
        type(weightTextBox,"80");
        type(contactNameTextBox, "signature");
        type(mobileNumberTextBox,"123456789");
        type(numberOfGuestTextBox,"2");
        selectByIndex(countryDropdown,1);
        selectByIndex(stateDropdown,3);
        type(paragraphTextBox,"Petition requested by Automation");
        click(getVisibleElement(saveAndContinueCTA));

        click(bringElementIntoView(radioBtnEmail));
        click(getVisibleElement(saveAndContinueCTA));
        click(getVisibleElement(saveAndContinueCTA));
        click(bringElementIntoView(agreementAcceptChkBox));
        click(bringElementIntoView(dressRequestChkBox));
        click(getVisibleElement(saveAndContinueCTA));
        type(studentSignatureTextBox, getElementText(getElement(studentSignatureLabel)));
        type(parentSignatureTextBox, parentSignature);
        click(bringElementIntoView(reviewCTA));
        click(bringElementIntoView(purchaseCTA));
        waitForPageTobeLoaded();
        waitForElementTobeDisappear(processingLoader);
        new CheckoutScreen().selectCheckoutCriteria(new CheckoutCriteria(), false);
        new CheckoutScreen().clickOnPlaceOrder();
        waitForPageTobeLoaded();
        waitForElementTobeExist(reviewPetition);
        return this;
    }

    public GraduationPetition approveSubmittedPetition(){
        executeQuery(UPDATE_SUBMITTED_PETITION_STATUS_AD_DB.replaceAll(STUDENT_ID_DATA, getUserAccountDetails().getStudentId()),AD_DATA_BASE);
        return this;
    }

    public GraduationPetition validatePetitionIsApproved(){
        navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        //navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.HOME);
        new DashboardScreen().navigateToGraduationPetitionPage();
        waitForElementTobeExist(String.format(approvedPetition,userAccountDetails.getUserId()));
        return this;
    }
}
