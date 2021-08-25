package elementConstants;

public @interface AbekaHome {

    /**
     * Text
     */
    String LOG_OUT = "Log out";
    String DASHBOARD = "Dashboard";
    String DIGITAL_ASSESSMENTS = "Digital Assessments";

    /**
     * Element xpath
     */
    String login = "text=Log in";
    String logInCreateAccount = "text=Log in / Create Account";
    String shop = "text=Shop";
    String firstGrade = "1st Grade";
    String fourthGrade = "4th Grade";
    String ninthGrade = "9th Grade";
    String twelfthGrade = "12th Grade";
    String cart = "text=Cart";
    String closeSignup = "xpath=//form[@id='aspnetForm']//div[@id='newsLetterSignup']//button[@onclick='dockEmailSubscribe()']";
    String abekaBGProcessLogo = "xpath=//i[@class='abekaLogoLoading']";
    String accountGreeting = "xpath=//*[@id='AccountGreeting']/a";
    String account = "text=Account";
    String HOME = "Home";
    String CALENDAR = "Calendar";
    String HEADER_SUB_MENU = "xpath=//ul[@class='mzr-content smallIconList']/descendant::*/descendant::span[contains(normalize-space(text()),'%s')]";
    String STUDENT_ACCESS_CONTROL_URL_CONTENT = "abeka.com/Student/";
    String PARENT_ACCESS_CONTROL_URL_CONTENT = "abeka.com/Account/";
}
