package stepDefinations;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import pojo.CreateResource;
import pojo.CreateResourceResponse;
import resources.APIResources;
import resources.Utility;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPlaceHolderAPI extends Utility {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;

	@Given("^Upcoming movies api$")
	public void upcoming_movies_api() throws Throwable {

		resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
		reqSpec = given().spec(requestSpecification());

	}

	@When("^User calls \"([^\"]*)\" API with \"([^\"]*)\" HTTP Request$")
	public void user_calls_something_api_with_something_http_request(String resource, String httpMethod)
			throws Throwable {
		File filLoc = new File("ExcelData\\APITestData.xlsx");
		FileInputStream fis = new FileInputStream(filLoc.getAbsolutePath());
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");
		Row row;
		Cell cell = null;
		row = sheet.getRow(0);
		cell = row.getCell(0);

		String requestBody = cell.getStringCellValue();
		ObjectMapper mapper = new ObjectMapper();

		CreateResource requestPOJO = mapper.readValue(requestBody, CreateResource.class);
		CreateResourceResponse response = null;
		APIResources resources = APIResources.valueOf(resource);
		if (httpMethod.equalsIgnoreCase("POST")) {
			response = reqSpec.body(requestPOJO).when().post(resources.getResources()).then().spec(resSpec).extract()
					.response().as(CreateResourceResponse.class);
		}

		System.out.println(response.getUserId());
		System.out.println(response.getId());

		assertEquals("Chetan Mali", response.getTitle());

	}

	@Then("^User should display status code as \"([^\"]*)\"$")
	public void user_should_display_status_code_as_something(String strArg1) throws Throwable {

	}

}
