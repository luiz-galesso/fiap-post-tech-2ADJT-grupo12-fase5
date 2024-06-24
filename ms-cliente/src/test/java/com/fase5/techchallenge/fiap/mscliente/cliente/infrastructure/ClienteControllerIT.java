package com.fase5.techchallenge.fiap.mscliente.cliente.infrastructure;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.CadastrarCliente;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class ClienteControllerIT {

    @Autowired
    CadastrarCliente cadastrarCliente;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void devePermitirCadastrarCliente() {
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteInsertDTO.setEmail("john_wick@email.com");

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteInsertDTO)
                .when()
                .post("/ms-cliente/clientes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/cliente_schema.json"));
    }

    @Test
    void devePermitirAtualizarCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();//cadastrarCliente.execute(ClienteHelper.gerarClienteInsert());
        ClienteUpdateDTO clienteUpdateDTO = ClienteHelper.gerarClienteUpdate();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteUpdateDTO)
                .when()
                .put("/ms-cliente/clientes/{id}", cliente.getEmail())
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/cliente_schema.json"));
    }


    @Test
    void devePermitirObterCliente() {
        Cliente cliente = cadastrarCliente.execute(ClienteHelper.gerarClienteInsert());

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/ms-cliente/clientes/{id}", cliente.getEmail())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/cliente_schema.json"));
    }


    @Test
    void devePermitirRemoverCliente() {
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteInsertDTO.setEmail("elizeu-oliveira@example.com");
        clienteInsertDTO.setNome("Elizeu Oliveira");
        Cliente cliente = cadastrarCliente.execute(clienteInsertDTO);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/ms-cliente/clientes/{id}", cliente.getEmail())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
