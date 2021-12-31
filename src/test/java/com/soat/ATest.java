package com.soat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.web.server.LocalServerPort;

public abstract class ATest {
    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    protected abstract void setUp();

    public void initIntegrationTest() {
        RestAssured.port = port;
    }

    protected abstract void initPath();

    @Autowired
    protected TestEntityManager entityManager;

    protected Response response;
}
