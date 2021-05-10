package pageObjects;

import base.GenericAction;
import elementConstants.AcademyEnrollments;
import org.testng.Assert;

public class AcademyEnrollmentsScreen extends GenericAction {

    @Override
    protected void setUp(String browserName, String platform) {
        
    }

    public void validateNewlyEnrolledCourses(String courseName){
        softAssertions.assertThat(isElementDisplayed(String.format(AcademyEnrollments.newEnrolledCourse,courseName)))
                .as(courseName + " course is not appeared in new section").isTrue();
    }
}
