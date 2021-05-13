package elementConstants;

public @interface AcademyEnrollments {

    /**
     * Element xpath
     */
    String newEnrolledCourse = "xpath=//h2[normalize-space(text())='New']/following-sibling::div[1]/descendant::a[normalize-space(text())='%s']";
}
