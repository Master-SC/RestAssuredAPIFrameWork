package stepDefinations;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletedPlaceVerification")
    public void beforeScenario() throws IOException {
        stepDefinations sd = new stepDefinations();
        if(stepDefinations.place_id == null) {
            sd.the_user_creates_request_specification_for_api_requests();
            sd.add_place_payload_is_generated("Demo Criss", "78, Upper Road , Uk 42",
                    "(+44) 587 458", "French - FR");

            sd.user_calls_the_with_http_request("addPlaceAPI", "post");
            sd.api_response_gets_generated();
            sd.the_user_saves_the_from_the_response("place_id");
            sd.the_user_deletes_the_place_created_using_the("deletePlaceAPI");
        }
    }
}
