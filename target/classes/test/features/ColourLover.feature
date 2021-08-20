Feature:
  Make a GET request to colourlovers api


  Scenario:
  User try to check the colourlovers data

    Given the user perform get request for "http://www.colourlovers.com/api/patterns"
    Then the response code should be 200


  Scenario:
  User apply filter on the colourlovers data

    Given the user perform get request for "http://www.colourlovers.com/api/patterns"
    When  user make filter with number of views to be greater than 4000
    Then the result count should be 25


