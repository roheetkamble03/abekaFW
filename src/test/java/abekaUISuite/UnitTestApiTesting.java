package abekaUISuite;

import base.GenericAction;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UnitTestApiTesting extends GenericAction {

    @Test
    public void testFetchingDigitalAssessmentSubjects(){
        String endURL = "http://plmradz.com/pcc/api/digital";
        Response response = given().when().queryParam("id","100").when().get(endURL);
        List<DigitalAssessmentPojo> digitalAssessmentPojoList = response.getBody().as(new TypeRef<List<DigitalAssessmentPojo>>(){});
        DigitalAssessmentPojo digitalAssessmentPojo = digitalAssessmentPojoList.get(0);
        softAssertions.assertThat(digitalAssessmentPojo.getShort_description().equals("Hebrew History"))
                .as("Short description is not equal to Hebrew History").isTrue();

       response = given().when().when().get(endURL);
       softAssertions.assertThat(response.getBody().asString().equals("{\"Message\":\"Studentid is mandatory.\"}"))
               .as("Application not giving, student id is mandatory message").isTrue();
    }
}
