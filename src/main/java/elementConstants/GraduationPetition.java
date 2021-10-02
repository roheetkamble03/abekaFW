package elementConstants;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public @interface GraduationPetition {
    String TITLE_OPTION = new String[]{"Mr.","Miss"}[new Random().nextInt(1)];
    String STUDENT_NAME = "Auto" + RandomStringUtils.randomAlphabetic(4) + " " +RandomStringUtils.randomAlphabetic(8);
    String SAVE_AND_CONTINUE = "Save and Continue";
    String REVIEW = "Review";
    String PURCHASE = "Purchase";
    String REVIEW_PETITION = "Review Petition";
    String NEW_PETITION_TO_MARCH = "New Petition to March";
    String ORDER_CONFIRMATION = "Order Confirmation";
    String PETITION_SENT_FOR_APPROVAL = "Petition Sent for Approval";




    String titleDropdown = "id=ddlTitle";
    String studentNameTextBox = "id=txtDiplomaName";
    String ceremonyDropdown = "id=ddlCeremony";
    String activitiesTextArea = "id=txtActivities";
    String heightFTDropDown = "id=ddlHeightFeet";
    String heightInchDropDwon = "id=ddlHeightInches";
    String weightTextBox = "id=txtWeight";
    String countryDropDown = "id=ddlCountry";
    String aboutParagraphTextAreay = "id=txtParagraph";
    String uploadPhotoBtn = "id=filePhoto";
    String chooseAudioFileBtn = "id=uploadAudio";
    String agreedCheckBox = "id=AgreementCheck";
    String dressAgreedCheckBox = "id=DressRequireCheck";
}
