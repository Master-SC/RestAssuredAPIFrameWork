package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.*;
import java.util.Base64;

public class JiraUtils extends Utils {
    public static RequestSpecification req;
    public ResponseSpecification reqWithAPIResource;

    public RequestSpecification requestSpecification() throws IOException {
        if(req == null){

            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValues("jiraBaseUrl")).
                    addHeader("Authorization","Basic "+getEncodedString()).
                    addFilter(RequestLoggingFilter.logRequestTo(log)).
                    addFilter(ResponseLoggingFilter.logResponseTo(log)).
                    setContentType(ContentType.JSON).build();
            return req;
        }
        return req;

    }
    public String getEncodedString() throws IOException {
        String Email = "chaudhuri.manju@gmail.com" ;
        String APIToken = "lIuCzSyZdfzxcGNdhfPc486F";
        String OriginalInput = getGlobalValues("jiraMailId")+":"+getGlobalValues("APIToken");
        String EncodedString = Base64.getEncoder().encodeToString(OriginalInput.getBytes());
        return EncodedString;
    }


}
