package utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ParentAccountDetails {
    private String parentUserName = null;
    private String parentPassword;
    private String parentUserSignature;
    private String parentCustomerNumber;
    private String parentName = "Joe Customer";
    private String parentSignature = "Joe Customer";
    private String parentAddressOne = "5081 Crestwood Rd,";
    private String parentAddressTwo = "";
    private String parentCity = "Pensacola";
    private String parentState = "FL";
    private String parentZipCode = "32503";
    private String parentCountry = "USA";
    private String parentPhoneNumber = "1233211232";
}
