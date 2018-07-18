import com.github.geowarin.junit.DockerRule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class PaymentApiIT {

    @ClassRule
    public static DockerRule dockerRule =
            DockerRule.builder()
                    .isLocalImage(true)
                    .image("m4ks/payment:latest")
                    .ports("8080")
                    .waitForLog("Tomcat started on port(s): 8080")
                    .build();

    @BeforeClass
    public static void beforeAll() {
        RestAssured.baseURI = String.format("http://%s:%d/v1/payment", dockerRule.getDockerHost(), dockerRule.getHostPort("8080/tcp"));
    }

    @Test
    public void testBasicCreation() throws IOException {

        List<Map> before = when().
                    get("").
                then().
                    contentType(ContentType.JSON).
                    extract().
                    path("data");


        Response response = given().
                    contentType(ContentType.JSON).
                    body(IOUtils.toString(getClass().getResourceAsStream("/payment-1.json"), Charset.forName("UTF-8"))).
                when().
                    post("").
                then().
                    statusCode(201). /*created*/ and().
                    body("data", not(Matchers.isEmptyOrNullString())).and().
                    body("links.get", not(Matchers.isEmptyOrNullString())).
                extract().
                    response();

        String newId = response.path("data");
        String getURL = response.path("links.get");

        List<Map> after = when().
                    get("").
                then().
                    contentType(ContentType.JSON).
                extract().
                    path("data");

        //one more element
        assertThat(after).hasSize(before.size()+1);
        assertThat(after).extracting(m -> m.get("id")).containsOnlyOnce(newId);

        get(getURL).
        then().
            statusCode(200).and().
            body("data.id", is(newId));
    }

    @Test
    public void testDeletion() throws IOException {

        Response response = given().
                    contentType(ContentType.JSON).
                    body(IOUtils.toString(getClass().getResourceAsStream("/payment-2.json"), Charset.forName("UTF-8"))).
                when().
                    post("").
                then().
                    statusCode(201). /*created*/ and().
                    body("data", not(Matchers.isEmptyOrNullString())).and().
                    body("links.get", not(Matchers.isEmptyOrNullString())).
                extract().
                    response();

        String newId = response.path("data");
        String getURL = response.path("links.get");

        get(getURL).
            then().
            statusCode(200).and().
            body("data.id", is(newId));

        delete(getURL).
            then().
            statusCode(200);

        get(getURL).
        then().
            statusCode(404);
    }

}