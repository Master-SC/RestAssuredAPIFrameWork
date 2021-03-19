package resources;

import pojo.AddPlaceAPI.AddPlaceAPIRequest;
import pojo.AddPlaceAPI.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public HashMap<String, Object> addPlacePayLoadFromExcel(String sheetName, String columnName, String rowName )
            throws IOException {
        HashMap<String, Object> mapData = new HashMap<>();

        Utils ut = new Utils();
        ArrayList<String> alist =ut.getDataFromExcelSheet(sheetName, columnName, rowName);

        HashMap<String,Object> location= new HashMap<>();
        location.put("lat",alist.get(1));
        location.put("lng",alist.get(2));
        mapData.put("location",location);
        mapData.put("accuracy",alist.get(3));
        mapData.put("name",alist.get(4));
        mapData.put("phone_number",alist.get(5));
        mapData.put("address",alist.get(6));
        mapData.put("website",alist.get(7));
        mapData.put("language",alist.get(8));
        ArrayList<String> types = new ArrayList<>();
        types.add(alist.get(9));
        types.add(alist.get(10));
        mapData.put("types",types);

        return mapData;
    }

    public HashMap<String, Object> addNewJiraCommentData(String comment){
        HashMap<String, Object> hmp = new HashMap<>();
        HashMap<String, Object> bMap = new HashMap<>();
        hmp.put("body", bMap);
        bMap.put("type","doc");
        bMap.put("version",1);
        ArrayList<HashMap<String, Object>> content = new ArrayList<>();
        bMap.put("content",content);
        HashMap<String, Object> cMap = new HashMap<>();
        content.add(cMap);
        cMap.put("type","paragraph");
        ArrayList<HashMap<String, Object>> typeArray = new ArrayList<>();
        cMap.put("content", typeArray);
        HashMap<String, Object> contMap = new HashMap<>();
        typeArray.add(contMap);
        contMap.put("text",comment);
        contMap.put("type","text");
        return hmp;
    }
}
