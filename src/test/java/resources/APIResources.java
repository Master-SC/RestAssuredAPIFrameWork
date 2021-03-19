package resources;

// enum is a special class in java which has collection of constants or methods
//Here We are using enum for the collection of API Resources.
public enum APIResources {
    addPlaceAPI("/maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    deletePlaceAPI("/maps/api/place/delete/json"),
    getJiraIssue("/rest/api/3/issue/{issue}"),
    addJiraComment("/rest/api/3/issue/{issue}/comment"),
    deleteJiraComment("rest/api/3/issue/{issue}/comment/{id}");

    final String resources;
    APIResources(String resources){
        this.resources=resources;
    }

    public String getResource(){
        return resources;
    }

}
