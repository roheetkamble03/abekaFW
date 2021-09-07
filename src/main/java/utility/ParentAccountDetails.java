package utility;

import apiPojo.CreateAccountApiResponsePojo;
import constants.ApiServiceConstants;
import constants.CommonConstants;
import io.restassured.common.mapper.TypeRef;
import lombok.*;

import static base.BaseClass.properties;
import static constants.CommonConstants.API_AUTH_KEY;
import static constants.CommonConstants.APP_KEY;
import static io.restassured.RestAssured.given;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ParentAccountDetails {
    private String parentUserName = null;
    private String parentPassword;
    private String parentUserSignature;
    private String parentCustomerNumber;
    private String parentName = "Joe Customer";
    private String parentSignature = "Joe Customer";
    private String parentAddressOne = "50 Brent Lane";
    private String parentAddressTwo = "";
    private String parentCity = "Pensacola";
    private String parentState = "FL";
    private String parentZipCode = "32523";
    private String parentCountry = "USA";
    private String parentPhoneNumber = "1233211232";
}
