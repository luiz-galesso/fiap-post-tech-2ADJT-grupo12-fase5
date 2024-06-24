package com.fase5.techchallenge.fiap.mscliente.bdd;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ClienteStepDefinition {

    private Response response;
    private Cliente cliente;
    private ClienteInsertDTO clienteInsertDTO;
    private ClienteUpdateDTO clienteUpdateDTO;
    private static final String ENDPOINT_API_CLIENTES = "http://localhost:8080/ms-cliente/clientes";

    @Quando("cadastrar um novo cliente")
    public Cliente cadastrar_um_novo_cliente() {
        clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteInsertDTO.setEmail(ClienteHelper.gerarNomeAleatorio() + "@email.com");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteInsertDTO)
                .when()
                .post(ENDPOINT_API_CLIENTES);
        return response.then().extract().as(Cliente.class);
    }

    @Entao("o cliente é registrado com sucesso")
    public void o_client_é_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Entao("deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/cliente_schema.json"));
    }

    @Dado("que um cliente ja foi cadastrado")
    public void que_um_cliente_ja_foi_cadastrado() {
        cliente = cadastrar_um_novo_cliente();
    }

    @Quando("efetuar a busca pelo email do cliente")
    public void efetuar_a_busca_pelo_email_do_cliente() {
        response = when()
                .get(ENDPOINT_API_CLIENTES + "/{id}", cliente.getEmail());
    }

    @Entao("o cliente é exibido com sucesso")
    public void o_cliente_é_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/cliente_schema.json"));
    }

    @Quando("efetuar requisição para atualização do cliente")
    public void efetuar_requisição_para_atualização_do_cliente() {
        clienteUpdateDTO = ClienteHelper.gerarClienteUpdate();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteUpdateDTO)
                .when()
                .put(ENDPOINT_API_CLIENTES + "/{id}", cliente.getEmail());
    }

    @Entao("o cliente é atualizado com sucesso")
    public void o_cliente_é_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remoção do cliente")
    public void requisitar_a_remoção_do_cliente() {
        response = when()
                .delete(ENDPOINT_API_CLIENTES + "/{id}", cliente.getEmail());
    }

    @Entao("o cliente é removido com sucesso")
    public void o_cliente_é_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
}
