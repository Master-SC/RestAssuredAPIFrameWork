package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import resources.APIResources;
import resources.JiraUtils;
import resources.TestDataBuild;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class stepDefinationsJira extends JiraUtils {
    RequestSpecification GenericRequest;
    APIResources resourceAPI;
    Response reqWithAPIResource;
    Response response;
    TestDataBuild jiraData = new TestDataBuild();
    RequestSpecification Request;
    static String comment_id;


    @Given("The user creates Generic Request Specification for in JIRA")
    public void the_user_creates_generic_request_specification_for_jira() throws IOException {
        GenericRequest = given().spec(requestSpecification());
    }

    @Given("The user provides the issue id {string}")
    public void the_user_provides_the_issue_id(String issueID) {
        GenericRequest = GenericRequest.pathParam("issue", issueID);
    }

    @When("User calls the JIRA API {string} with {string} http request")
    public void user_calls_the_api_with_http_request(String APIResource, String httpMethod) {
        resourceAPI = APIResources.valueOf(APIResource);
        switch (httpMethod.toUpperCase()) {
            case "POST":
                reqWithAPIResource = GenericRequest.when().post(resourceAPI.getResource());
                break;
            case "DELETE":
                reqWithAPIResource = GenericRequest.when().delete(resourceAPI.getResource());
                break;
            case "PUT":
                reqWithAPIResource = GenericRequest.when().put(resourceAPI.getResource());
                break;
            case "GET":
                reqWithAPIResource = GenericRequest.when().get(resourceAPI.getResource());
                break;
        }
    }

    @And("API Response got generated")
    public void api_response_gets_generated() {
        // Write code here that turns the phrase above into concrete actions
        response = reqWithAPIResource.then().extract().response();
    }

    @Then("The API response status code should be {int}")
    public void the_api_response_status_code_should_be(int Int) {
        api_response_gets_generated();
        Assert.assertEquals(Int, response.getStatusCode());
    }

    @Given("The user add a new comment {string}")
    public void the_user_add_a_new_comment(String comment) {
        GenericRequest = GenericRequest.body(jiraData.addNewJiraCommentData(comment));
    }

    @Then("The user saves the {string} of the new comment")
    public void the_user_saves_the_from_the_response(String string) {
        comment_id = getJSONPath(response, string);
        System.out.println(comment_id);
    }

    @Given("The user provides the comment id")
    public void the_user_provides_the_comment_id() {
            GenericRequest = GenericRequest.pathParam("id", comment_id);
    }



}




