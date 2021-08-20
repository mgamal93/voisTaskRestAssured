package test.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class ColourLoverStepDef {
    public static Response response;
    static String colorsViewsStr;
    static String baseUrl;
    int[] colorsViewsArr;
    int resultCount;

    @Given("^the user perform get request for \"([^\"]*)\"$")
    public void theUserPerformGetRequestFor(String url) {
        baseUrl = url;

        response = given().header("User-Agent", "PostmanRuntime/7.26.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .when().get(baseUrl);


//        responseBody=response.getBody().asString();
//        System.out.println(response.statusCode());
//        System.out.println(responseBody);
    }

    @Then("^the response code should be (\\d+)$")
    public void theResponseCodeShouldBe(int status_code) {
        Assert.assertEquals(status_code, response.getStatusCode());
    }

    @When("^user make filter with number of views to be greater than (\\d+)$")
    public void userMakeFilterWithNumberOfViewsToBeGreaterThan(int filter_value) {

        NodeChildrenImpl colours = response.then()
                .extract().path("patterns.pattern.numViews");

        colorsViewsStr = colours.list().toString();
        System.out.println("list : " + colorsViewsStr);

        colorsViewsArr = toIntArray(colorsViewsStr);
        resultCount = 0;

        for (int i = 0; i < colorsViewsArr.length; i++) {
            if (filter_value <= colorsViewsArr[i]) {
                resultCount++;
            }
        }
        System.out.println("Count of the elements that are greater than the given value are:" + resultCount);

    }

    private int[] toIntArray(String colorsViewsStr) {
        String beforeSplit = colorsViewsStr.replaceAll("\\[|\\]|\\s", "");
        String[] split = beforeSplit.split("\\,");
        int[] result = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = Integer.parseInt(split[i]);
        }
        return result;
    }

    @Then("^the result count should be (\\d+)$")
    public void theResultCountShouldBe(int count) {
        Assert.assertEquals(count, resultCount);
    }
}
