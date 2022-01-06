package abekaUISuite;

import apiPojo.CreateAccountApiResponsePojo;
import apiPojo.DeleteAccountApiResponse;
import base.GenericAction;
import constants.ApiServiceConstants;
import constants.CommonConstants;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.NoInjection;
import org.testng.annotations.Test;
import pageObjects.EnrollmentsScreen;
import utility.ParentAccountDetails;
import utility.RetryUtility;

import static constants.CommonConstants.*;
import static constants.DataProviderName.PARENT_CREDENTIALS_GENERIC;
import static constants.DataProviderName.PARENT_CREDENTIALS_GRADE_TWELVE;
import static io.restassured.RestAssured.given;

public class UnitTestApiTesting extends GenericAction {

//    @Test
//    public void testFetchingDigitalAssessmentSubjects(){
//        String endURL = "http://plmradz.com/pcc/api/digital";
//        //Response response = given().when().queryParam("id",getUserAccountDetails().getStudentId()).when().get(endURL);
//        Response response = given().when().queryParam("id","100").when().get(endURL);
//        List<DigitalAssessmentPojo> digitalAssessmentPojoList = response.getBody().as(new TypeRef<List<DigitalAssessmentPojo>>(){});
//        DigitalAssessmentPojo digitalAssessmentPojo = digitalAssessmentPojoList.get(0);
//
//        softAssertions.assertThat(digitalAssessmentPojo.getStudentid().equals("100"))
//                .as("StudentId is not equal to 100").isTrue();
//        softAssertions.assertThat(digitalAssessmentPojo.getShort_description().equals("Hebrew History"))
//                .as("Short description is not equal to Hebrew History").isTrue();
//        softAssertions.assertThat(digitalAssessmentPojo.getApref().equals("1257222"))
//                .as("Application number is not equal to 1257222").isTrue();
//
//       response = given().when().when().get(endURL);
//       softAssertions.assertThat(response.getBody().asString().equals("{\"Message\":\"Studentid is mandatory.\"}"))
//               .as("Application not giving, student id is mandatory message").isTrue();
//       softAssertions.assertAll();
//    }

    @Test(testName = "testCreateParentAccount", retryAnalyzer = RetryUtility.class, groups = "UnitTest")
    public void testCreateParentAccount(){
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(1, true, TEST_DATA_GRADE_ONE);
        parentAccountDetails.getParentUserName();
        parentAccountDetails.getParentPassword();

        if(!(parentAccountDetails.getParentCustomerNumber().length()>0)){
           softAssertions.fail("Create parent API service is not working");
        }

        DeleteAccountApiResponse response = given().when().multiPart(ApiServiceConstants.request, ApiServiceConstants.deleteRequestType)
                .multiPart(ApiServiceConstants.key,properties.getProperty(APP_KEY))
                .multiPart(ApiServiceConstants.customerNumber,parentAccountDetails.getParentCustomerNumber())
                .header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER + properties.getProperty(API_AUTH_KEY))
                .get(properties.get(CommonConstants.API_END_URL).toString()).getBody().as(new TypeRef<DeleteAccountApiResponse>(){});
        if(!response.getResponse().equalsIgnoreCase(CommonConstants.OK)){
            softAssertions.fail("Parent account deletion failed for customer number ["+parentAccountDetails.getParentCustomerNumber());
        }
        softAssertions.assertAll();

        softAssertions.assertAll();
    }
}
