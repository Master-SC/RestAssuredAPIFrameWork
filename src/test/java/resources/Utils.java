package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;

    public RequestSpecification
    requestSpecification() throws IOException {

        if (req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
                    .addQueryParam("key", "qaclick123").
                            addFilter(RequestLoggingFilter.logRequestTo(log)).
                            addFilter(ResponseLoggingFilter.logResponseTo(log)).
                            setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public String getGlobalValues(String key) throws IOException {
        Properties prop = new Properties();

        FileInputStream fs = new FileInputStream("src" +
                "\\test\\java\\resources\\global.properties");

        prop.load(fs);
        return prop.getProperty(key);



    }

    public String getJSONPath(Response response, String path){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(path).toString();
    }
}
