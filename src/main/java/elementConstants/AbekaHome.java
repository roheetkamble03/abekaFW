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
    String firstGrade = "text=1st Grade";
    String cart = "text=Cart";
    String closeSignup = "xpath=//div[@id='newsLetterSignup']//button[@onclick='dockEmailSubscribe()']";
    String abekaBGProcessLogo = "xpath=//i[@class='abekaLogoLoading']";
    String accountGreeting = "xpath=//*[@id='AccountGreeting']/a";
    String account = "text=Account";
    String HOME = "Home";
    String CALENDAR = "Calendar";
}
