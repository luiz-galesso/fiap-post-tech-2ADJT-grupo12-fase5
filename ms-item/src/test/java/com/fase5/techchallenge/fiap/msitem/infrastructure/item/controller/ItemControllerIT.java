package com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.EstoqueDTO;
import com.fase5.techchallenge.fiap.msitem.usecase.item.*;
import com.fase5.techchallenge.fiap.msitem.usecase.item.CadastraItem;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItemUpdate;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemControllerIT
{
    @Autowired
    private CadastraItem cadastraItem;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    void devePermitirCadastrarItem() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gerarItem())
        .when().post("/ms-item/itens")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json"));
    }

    @Test
    void devePermitirAtualizarItem() {

        Item item = cadastraItem.execute(gerarItem());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gerarItemUpdate(gerarItem()))
        .when()
                .put("/ms-item/itens/{id}", item.getId())
        .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json"));
    }

    @Test
    void devePermitirBuscarPeloId() {
        Item item = cadastraItem.execute(gerarItem());
        when()
                .get("/ms-item/itens/{id}", item.getId())
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void devePermitirApagarItem() {
        Item item = cadastraItem.execute(gerarItem());
        when()
                .delete("/ms-item/itens/{id}", item.getId())
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void devePermitirAumentarEstoqueDeUmItem(){
        Item item = cadastraItem.execute(gerarItem());
        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(estoqueDTO)
        .when()
            .put("/ms-item/itens/{id}/aumenta-estoque", item.getId())
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json"));
    }

    @Test
    void devePermitirConsumirEstoqueDeUmItem(){
        Item item = cadastraItem.execute(gerarItem());
        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(estoqueDTO)
        .when()
            .put("/ms-item/itens/{id}/consome-estoque", item.getId())
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json"));
    }
}
