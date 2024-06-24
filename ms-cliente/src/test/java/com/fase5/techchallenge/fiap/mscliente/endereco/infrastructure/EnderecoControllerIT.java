package com.fase5.techchallenge.fiap.mscliente.endereco.infrastructure;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.CadastrarCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.CadastrarEndereco;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import com.fase5.techchallenge.fiap.mscliente.utils.EnderecoHelper;
import com.github.javafaker.Faker;
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
public class EnderecoControllerIT {

    @Autowired
    CadastrarCliente cadastrarCliente;
    @Autowired
    CadastrarEndereco cadastrarEndereco;
    @Autowired
    EnderecoGateway enderecoGateway;
    private final Faker faker = new Faker();

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void devePermitirCadastrarEndereco() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(enderecoInsertDTO)
                .when()
                .post("/ms-cliente/clientes/{idCliente}/enderecos", cliente.getEmail())
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_schema.json"));
    }


    @Test
    void devePermitirAtualizarEndereco() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(enderecoUpdateDTO)
                .when()
                .put("/ms-cliente/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_schema.json"));
    }


    @Test
    void devePermitirObterEnderecoPorId() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/ms-cliente/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_schema.json"));
    }

    @Test
    void devePermitirObterEnderecosPorCliente() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/ms-cliente/clientes/{idCliente}/enderecos", cliente.getEmail())
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_list_schema.json"));
    }

    @Test
    void devePermitirRemoverEndereco() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteInsertDTO.setEmail(ClienteHelper.gerarNomeAleatorio() + "@email.com");
        Cliente cliente = cadastrarCliente.execute(clienteInsertDTO);
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);

        given()
                .filter(new AllureRestAssured())
                .when().delete("/ms-cliente/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                .then();
    }

}
