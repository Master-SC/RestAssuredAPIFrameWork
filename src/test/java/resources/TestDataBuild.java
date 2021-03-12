package resources;

import pojo.AddPlaceAPI.AddPlaceAPIRequest;
import pojo.AddPlaceAPI.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlaceAPIRequest addPlaceAPIPayload(String name, String address, String phone_number, String languages){
        AddPlaceAPIRequest ap = new AddPlaceAPIRequest();
        ap.setAccuracy(50);
        ap.setAddress(address);
        ap.setName(name);
        ap.setPhone_number(phone_number);
        List<String> al = new ArrayList<String>();
        al.add("shoe park");
        al.add("shop");
        ap.setTypes(al);
        Location lo = new Location();
        lo.setLat(-38.383494);
        lo.setLng(33.427362);
        ap.setLocation(lo);
        ap.setWebsite("https://www.google.com");
        ap.setLanguage(languages);
        return ap;
    }

    public String DeletePlaceAPIRequest(String place_id){
        return "{\n" +
                "    \"place_id\":\""+place_id+"\"\n" +
                "}";
    }
}
