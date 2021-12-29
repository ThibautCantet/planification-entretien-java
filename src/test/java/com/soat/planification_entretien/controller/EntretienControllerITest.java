package com.soat.planification_entretien.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class EntretienControllerITest {
    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    @BeforeEach
    public void initIntegrationTest() {
        RestAssured.port = port;
        RestAssured.basePath += "PATH"; //TODO: implement controller
    }

    @Test
    void name() throws JsonProcessingException {
        Integer[] payload = {};
        String body = objectMapper.writeValueAsString(payload);
        //@formatter:off
        given()
                .log().all()
                .header("Content-Type", ContentType.JSON)
                .body(body)
        .when()
                .post("planifier")
        .then()
                .statusCode(HttpStatus.SC_CREATED);
        //@formatter:on
    }
}
