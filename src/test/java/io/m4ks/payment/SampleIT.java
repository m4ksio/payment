package io.m4ks.payment;

import com.github.geowarin.junit.DockerRule;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class SampleIT  {

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
    public void testHttp() {

        get("/").getBody().print();
    }

}