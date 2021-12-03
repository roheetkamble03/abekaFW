package abekaUISuite;

import base.GenericAction;
import constants.ApiServiceConstants;
import constants.CommonConstants;
import dataProvider.DataProviders;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pageObjects.EnrollmentsScreen;
import utility.ParentAccountDetails;
import utility.RetryUtility;

import java.util.List;

import static constants.CommonConstants.API_AUTH_KEY;
import static constants.CommonConstants.APP_KEY;
import static constants.DataProviderName.PARENT_CREDENTIALS_GRADE_TWELVE;
import static io.restassured.RestAssured.given;

public class UnitTestApiTesting extends GenericAction {

    @Test
    public void testFetchingDigitalAssessmentSubjects(){
        String endURL = "http://plmradz.com/pcc/api/digital";
        //Response response = given().when().queryParam("id",getUserAccountDetails().getStudentId()).when().get(endURL);
        Response response = given().when().queryParam("id","100").when().get(endURL);
        List<DigitalAssessmentPojo> digitalAssessmentPojoList = response.getBody().as(new TypeRef<List<DigitalAssessmentPojo>>(){});
        DigitalAssessmentPojo digitalAssessmentPojo = digitalAssessmentPojoList.get(0);

        softAssertions.assertThat(digitalAssessmentPojo.getStudentid().equals("100"))
                .as("StudentId is not equal to 100").isTrue();
        softAssertions.assertThat(digitalAssessmentPojo.getShort_description().equals("Hebrew History"))
                .as("Short description is not equal to Hebrew History").isTrue();
        softAssertions.assertThat(digitalAssessmentPojo.getApref().equals("1257222"))
                .as("Application number is not equal to 1257222").isTrue();

       response = given().when().when().get(endURL);
       softAssertions.assertThat(response.getBody().asString().equals("{\"Message\":\"Studentid is mandatory.\"}"))
               .as("Application not giving, student id is mandatory message").isTrue();
       softAssertions.assertAll();
    }

    @Test(testName = "Test-11")
    public void testCreateParentAccount(String testDataExcelName){
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(0, true, testDataExcelName);
        parentAccountDetails.getParentUserName();
        parentAccountDetails.getParentPassword();

        CreateAccountApiResponsePojo response = given().when().multiPart(ApiServiceConstants.request,ApiServiceConstants.createRequestType)
                .multiPart(ApiServiceConstants.key,properties.getProperty(APP_KEY))
                .multiPart(ApiServiceConstants.customerType,"80")
                .multiPart(ApiServiceConstants.name,"Joe Customer")
                .multiPart(ApiServiceConstants.address1,"50 Brent Lane")
                .multiPart(ApiServiceConstants.address2,"")
                .multiPart(ApiServiceConstants.city,"Pensacola")
                .multiPart(ApiServiceConstants.state,"FL")
                .multiPart(ApiServiceConstants.zip,"32523")
                .multiPart(ApiServiceConstants.country,"USA")
                .multiPart(ApiServiceConstants.phone,"1233211232")
                .header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER + properties.getProperty(API_AUTH_KEY))
                .get(properties.get(CommonConstants.API_END_URL).toString()).getBody().as(new TypeRef<CreateAccountApiResponsePojo>(){});

        if(response.getResponse().equalsIgnoreCase(CommonConstants.OK)){
            new EnrollmentsScreen().addParentAccountDetailsToTestData("rcg+"+response.getCustomerNumber()+"@pcci.edu", "rcg"+response.getCustomerNumber(), testDataExcelName);
        }else {
            softAssertions.fail("Parent account creation failed");
        }
        softAssertions.assertAll();
    }

    @Test(testName = "Test-11", dataProvider = PARENT_CREDENTIALS_GRADE_TWELVE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testDeleteParentAccount(String userId, String password, String userName, String customerNumber){
        DeleteAccountApiResponse response = given().when().multiPart(ApiServiceConstants.request, ApiServiceConstants.deleteRequestType)
                .multiPart(ApiServiceConstants.key,properties.getProperty(APP_KEY))
                .multiPart(ApiServiceConstants.customerNumber,customerNumber)
                .header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER + properties.getProperty(API_AUTH_KEY))
                .get(properties.get(CommonConstants.API_END_URL).toString()).getBody().as(new TypeRef<DeleteAccountApiResponse>(){});
        if(!response.getResponse().equalsIgnoreCase(CommonConstants.OK)){
            softAssertions.fail("Parent account deletion failed for customer number ["+customerNumber);
        }
        softAssertions.assertAll();
    }
}
