package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import java.io.IOException;
import static io.restassured.RestAssured.given;


public class stepDefinations extends Utils {

    RequestSpecification request;
    Response response;
    TestDataBuild data = new TestDataBuild();
    Response reqWithAPIResource;
    static String place_id;
    APIResources resourceAPI;
    RequestSpecification GenericRequest;

    @Given("The user creates Generic RequestSpecification for API Requests")
    public void the_user_creates_request_specification_for_api_requests() throws IOException {
        GenericRequest = given().spec(requestSpecification());
    }

    @Given("Add Place Payload is generated {string} {string} {string} {string}")
    public void add_place_payload_is_generated(String name, String address, String phone_number, String languages)
            throws IOException {
        request = GenericRequest.
                body(data.addPlaceAPIPayload(name,address,phone_number,languages));
    }



    @When("User calls the {string} with {string} http request")
    public void user_calls_the_with_http_request(String APIResource, String httpMethod) {

        //Constructor will be called with the value of API that is Passed
        resourceAPI = APIResources.valueOf(APIResource);
        switch (httpMethod.toUpperCase()) {
            case "POST":
                reqWithAPIResource = request.when().post(resourceAPI.getResource());
                break;
            case "DELETE":
                reqWithAPIResource = request.when().delete(resourceAPI.getResource());
                break;
            case "PUT":
                reqWithAPIResource = request.when().put(resourceAPI.getResource());
                break;
            case "GET":
                reqWithAPIResource = request.when().get(resourceAPI.getResource());
                break;
        }
    }

    @And("API Response gets generated")
    public void api_response_gets_generated() {
        // Write code here that turns the phrase above into concrete actions
        response = reqWithAPIResource.then().extract().response();
    }

    @Then("The API call got successful with status code {int}")
    public void the_api_call_got_successful_with_status_code(int Int) {
        api_response_gets_generated();
        Assert.assertEquals(Int, response.getStatusCode());
    }

    @And("The {string} in the response body should be {string}")
    public void the_in_the_response_body_should_be(String string, String string2) {
        Assert.assertEquals(getJSONPath(response,string),string2);
    }

    @And("The user saves the {string} from the response")
    public void the_user_saves_the_from_the_response(String string) {
        place_id = getJSONPath(response,string);
        System.out.println(place_id);
    }

    @And("Verify the place_id created matches with {string} using the {string}")
    public void verify_the_place_id_created_matches_with_using_the(String name, String APIResource) throws IOException {
        resourceAPI = APIResources.valueOf(APIResource);
        the_user_creates_request_specification_for_api_requests();
        request = GenericRequest.queryParam("place_id",place_id);
        user_calls_the_with_http_request(APIResource,"GET");
        api_response_gets_generated();
        Assert.assertEquals(getJSONPath(response,"name"),name);
    }

    @Then("The user deletes the place created using the {string}")
    public void the_user_deletes_the_place_created_using_the(String APIResource) throws IOException {
        resourceAPI = APIResources.valueOf(APIResource);
        request = GenericRequest
                .body(data.DeletePlaceAPIRequest(place_id));
        user_calls_the_with_http_request(APIResource,"POST");
        api_response_gets_generated();
        Assert.assertEquals(getJSONPath(response,"status"),"OK");
    }

    @Then("The user tries to delete the same place again using {string} and gets Error")
    public void the_user_tries_to_delete_the_same_place_again_using_and_gets_error(String APIResource) throws IOException {
        resourceAPI = APIResources.valueOf(APIResource);
        request = GenericRequest
                .body(data.DeletePlaceAPIRequest(place_id));
        user_calls_the_with_http_request(APIResource, "POST");
        api_response_gets_generated();
        System.out.println("The Error Message is ::" +getJSONPath(response,"msg"));
    }

    @Given("Delete Place payload is generated")
    public void delete_place_payload_is_generated() throws IOException {
        the_user_creates_request_specification_for_api_requests();
        request = GenericRequest.queryParam("place_id",place_id);
    }
    @Given("Add Place Payload is generated from Excel sheet Sheet {string} Column {string} Row {string}")
    public void add_place_payload_is_generated_from_excel_sheet(String sheet, String Column, String Row)
            throws IOException {
        request = GenericRequest.
                body(data.addPlacePayLoadFromExcel(sheet,Column,Row));
    }
}
