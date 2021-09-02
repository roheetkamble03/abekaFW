package abekaUISuite;

import base.GenericAction;
import com.mashape.unirest.request.body.MultipartBody;
import constants.ApiServiceConstants;
import constants.CommonConstants;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import org.openqa.selenium.json.Json;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import utility.RetryUtility;

import java.io.File;
import java.util.List;

import static constants.DataProviderName.PARENT_CREDENTIALS;
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

    @Test(testName = "Test-11", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testCreateParentAccount(String userId, String password, String userName, String signature){
        //Response response = given().when().multiPart(ApiServiceConstants.request,"CreateCustomer").multiPart;
        CreateAccountApiResponsePojo response = given().when().multiPart(ApiServiceConstants.request,"CreateCustomer")
                .multiPart(ApiServiceConstants.key,"GJ3vTwQ87Tarb721xUhW")
                .multiPart(ApiServiceConstants.customerType,"80")
                .multiPart(ApiServiceConstants.name,"Joe Customer")
                .multiPart(ApiServiceConstants.address1,"50 Brent Lane")
                .multiPart(ApiServiceConstants.address2,"")
                .multiPart(ApiServiceConstants.city,"Pensacola")
                .multiPart(ApiServiceConstants.state,"FL")
                .multiPart(ApiServiceConstants.zip,"32523")
                .multiPart(ApiServiceConstants.country,"USA")
                .multiPart(ApiServiceConstants.phone,"1233211232")
                .header("Authorization", "Bearer "+"eyJ0dCI6InAiLCJhbGciOiJIUzI1NiIsInR2IjoiMSJ9.eyJkIjoie1wiYVwiOjE5MDI1MDYsXCJpXCI6NzgyNDkzMSxcImNcIjo0NTk5ODE4LFwidVwiOjQwMDczMzksXCJyXCI6XCJVU1wiLFwic1wiOltcIldcIixcIkZcIixcIklcIixcIlVcIixcIktcIixcIkNcIixcIkRcIixcIk1cIixcIkFcIixcIkxcIixcIlBcIl0sXCJ6XCI6W10sXCJ0XCI6MH0iLCJpYXQiOjE2MjU2OTEyNDN9.pl5TxgFZN0VeMhOoWq8XF7GNi-Z-CpRIrnOoNcId9vY")
                .get(properties.get(CommonConstants.APIEndUrl).toString()).getBody().as(new TypeRef<CreateAccountApiResponsePojo>(){});
        response.getCustomerNumber();
        softAssertions.assertAll();
    }
}
