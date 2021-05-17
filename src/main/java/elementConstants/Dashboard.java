package elementConstants;

public @interface Dashboard {
    /**
     * Text
     */
    String TAB_TITLE = "Abeka | Dashboard";
    String URL = "/Account/";
    int TAB_COUNT = 2;
    String ENROLLMENTS = "Enrollments";
    String DIGITAL_CLIP_ART = "Digital Clip Art";
    String DIGITAL_ASSESSMENTS = "Digital Assessments";
    String DIGITAL_TEXTBOOKS = "Digital Textbooks";
    String DIGITAL_TEACHING_AIDS = "Digital Teaching Aids";
    String MY_ABEKA_NAV_PAGE_TITLE = "Abeka | %s";
    String MY_ABEKA_NAV_PAGE_URL = "%s/Fulfillment/%s/";
    String MY_ORDERS = "My Orders";
    String VIDEO_MANUAL_PDFS = "Video Manual PDFs";
    String NOTIFICATIONS = "Notifications";
    String MY_STUDENTS = "My Students";
    String STUDENT_TAB_TITLE = "Abeka | My Students";
    String STUDENT_TAB_URL = URL+"Students/";
    String STUDENT_NAME = "Syed";

    /**
     * Element xpath
     */
    String myOrdersLinks = "xpath=//a[@href='/Fulfillment/%s/']//label[normalize-space(text())='%s']";
    String videoManualPdfLink = "xpath=//div[@id='VideoManual']/descendant::li/descendant::a";
    String notificationLinks = "xpath=//div[@id='pnlMessageCenter']/descendant::table/descendant::td/a";
    String studentLink = "xpath=//li[@id='MyStudents']/descendant::*[@id='listStudent']/descendant::span[normalize-space(text())='%s']";
    String widgetTourPopupClose = "xpath=//div[@class='tourClose']/a";
}
