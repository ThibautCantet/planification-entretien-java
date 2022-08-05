package com.soat.planification_entretien.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienCommandController;
import com.soat.planification_entretien.entretien.command.application.controller.EntretienDto;
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
class EntretienCommandControllerITest {
    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    @BeforeEach
    public void initIntegrationTest() {
        RestAssured.port = port;
        RestAssured.basePath = EntretienCommandController.PATH;
    }

    @Test
    void name() throws JsonProcessingException {
        EntretienDto entretienDto = new EntretienDto(UUID.randomUUID(), "1", LocalDateTime.of(2021, 12, 30, 1, 1, 1));
        String body = objectMapper.writeValueAsString(entretienDto);
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
