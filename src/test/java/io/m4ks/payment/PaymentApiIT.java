package io.m4ks.payment;

import com.github.geowarin.junit.DockerRule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class PaymentApiIT {

    @ClassRule
    public static DockerRule dockerRule =
            DockerRule.builder()
                    .isLocalImage(true)
                    .image("m4ks/payment:latest")
                    .ports("8080")
//                    .waitForPort("8080/tcp")
                    .waitForLog("Tomcat started on port(s): 8080")
                    .build();

    @BeforeClass
    public static void beforeAll() {
        RestAssured.baseURI = String.format("http://%s:%d", dockerRule.getDockerHost(), dockerRule.getHostPort("8080/tcp"));
    }

    @Test
    public void testBasicCreation() throws IOException {

        given().
            contentType(ContentType.JSON).
            body(IOUtils.toString(getClass().getResourceAsStream("/payment-1.json"), Charset.forName("UTF-8"))).
        when().
            post("/payment").
        then().
            statusCode(201). //created
                and()
                .body("data.id", not(isEmptyOrNullString()));
    }

}