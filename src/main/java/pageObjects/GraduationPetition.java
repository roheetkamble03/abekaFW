package pageObjects;

import base.GenericAction;

public class GraduationPetition extends GenericAction {

    public static String generalSubjectDropdown = "xpath=//span[normalize-space(text())='General']/ancestor::h5/following-sibling::div/select";
    public static String practicalSubjectDropdown = "xpath=//span[normalize-space(text())='Practical']/ancestor::h5/following-sibling::div/descendant::select[%s]";
    public static String START_NEXT = "Start Next";
    public static String KEYBOARDING = "Keyboarding";
    public static String HOME_SEWING = "Home Ec.- Sewing";
    public static String DOCUMENT_PROCESSING = "Document Processing";

    public GraduationPetition startPetition(){
        click(START_NEXT);
        return this;
    }

    public GraduationPetition fillGraduationPetitionForm(){
        //selectByVisibleText();
        return this;
    }
}
