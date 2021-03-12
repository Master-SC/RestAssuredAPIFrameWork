Feature: Google Place API

  @AddPlaceGetPlaceDeletePlace
  Scenario Outline: Verify that a place is being successfully Added by the Add Place API
    Given The user creates Generic RequestSpecification for API Requests
    And Add Place Payload is generated "<name>" "<address>" "<phone_number>" "<languages>"
    When User calls the "addPlaceAPI" with "post" http request
    Then The API call got successful with status code 200
    And The "status" in the response body should be "OK"
    And The "scope" in the response body should be "APP"
    And The user saves the "place_id" from the response
    And Verify the place_id created matches with "<name>" using the "getPlaceAPI"
    Then The user deletes the place created using the "deletePlaceAPI"
    And The user tries to delete the same place again using "deletePlaceAPI" and gets Error

    Examples:
      |name       |address                |phone_number    |languages    |
      |Ross Taylor|50, Low Street, USA 15 |(+1) 0478 54785 | German - GR |
      |Tony Ross  |60, Up Lane, France 5  |(+44) 0547 5478 | English - EN|

  @DeletedPlaceVerification
  Scenario: Verify that if the user tries to get the Deleted Place it shows Error
    Given Delete Place payload is generated
    When User calls the "getPlaceAPI" with "get" http request
    Then The API call got successful with status code 404
    And The "msg" in the response body should be "Get operation failed, looks like place_id  doesn't exists"