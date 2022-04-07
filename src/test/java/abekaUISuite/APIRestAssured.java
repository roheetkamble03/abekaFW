package abekaUISuite;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.core.IsNot;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class arraySort {
    public static void main(String[] args) {
        int[] numArray = {5, 1, 4, 7};
        int temp = 0;
        for(int i=0; i<numArray.length;i++){
            for(int j=0;j<i;j++){
                if(numArray[i]>numArray[j]){
                    temp = numArray[i];
                    numArray[i] = numArray[j];
                    numArray[j] = temp;
                }
            }
        }
        IntStream.range(0,numArray.length).forEach(e->System.out.println(numArray[e]));

        given()
                .param("page",2).auth().none().
        when().log().all().get("https://reqres.in/api/users").
        then().statusCode(200).body("page",equalTo(2)).log().all();

        Response response = (Response) given().param("page",2).when().get("https://reqres.in/api/users").then().statusCode(200).body("page",equalTo(2)).extract();
        response.getStatusCode();
        response.time();
        response.timeIn(TimeUnit.SECONDS);
        response.getTime();
        response.getTimeIn(TimeUnit.SECONDS);
        response.getBody().jsonPath().get("data[0]");
        response.getStatusCode();
        System.out.println(response.getHeaders());

        System.out.println(response.jsonPath().getString("support.url"));
        System.out.println((String) response.jsonPath().get("support.text"));

        Assert.assertEquals(response.jsonPath().getString("support.url"),"https://reqres.in/#support-heading");

        given().param("page",2)
                .when().get("https://reqres.in/api/users")
                .then().statusCode(200).body("page", equalTo(2),"total",
                        equalTo(12),"data[1].id",equalTo(8), "data[1].email",equalTo("lindsay.ferguson@reqres.in")).extract();

        ApiResponse apiResponse = given().param("page",2).
                when().get("https://reqres.in/api/users").as(ApiResponse.class);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(apiResponse.getPage()==2).as("page not found 2").isTrue();
        System.out.println(apiResponse.getData());
    }
}
