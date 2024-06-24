package com.fase5.techchallenge.fiap.mscliente.bdd;

import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.utils.EnderecoHelper;
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
public class EnderecoStepDefinition {

    private Response response;
    private Endereco endereco;
    private EnderecoInsertDTO enderecoInsertDTO;
    private EnderecoUpdateDTO enderecoUpdateDTO;
    private String emailCliente = "carlos.souza@example.com";
    private static final String ENDPOINT_API_ENDERECOS = "http://localhost:8080/ms-cliente/clientes";

    @Quando("cadastrar um novo endereco")
    public Endereco cadastrar_um_novo_endereco() {
        enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(enderecoInsertDTO)
                .when()
                .post(ENDPOINT_API_ENDERECOS + "/{idCliente}/enderecos", emailCliente);
        return response.then().extract().as(Endereco.class);
    }

    @Entao("o endereco é registrado com sucesso")
    public void o_endereco_é_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Entao("deve ser apresentado o endereco")
    public void deve_ser_apresentado_o_endereco() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/endereco_schema.json"));
    }

    @Dado("que um endereco ja foi cadastrado")
    public void que_um_endereco_ja_foi_cadastrado() {
        endereco = cadastrar_um_novo_endereco();
    }

    @Quando("efetuar a busca pelo id do endereco")
    public void efetuar_a_busca_pelo_id_do_endereco() {
        response = when()
                .get(ENDPOINT_API_ENDERECOS + "/{idCliente}/enderecos/{idEndereco}", emailCliente, endereco.getId());
    }

    @Entao("o endereco é exibido com sucesso")
    public void o_endereco_é_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/endereco_schema.json"));
    }

    @Quando("efetuar requisição para atualização do endereco")
    public void efetuar_requisição_para_atualização_do_endereco() {
        enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(enderecoUpdateDTO)
                .when()
                .put(ENDPOINT_API_ENDERECOS + "/{idCliente}/enderecos/{idEndereco}", emailCliente, endereco.getId());
    }

    @Entao("o endereco é atualizado com sucesso")
    public void o_endereco_é_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remoção do endereco")
    public void requisitar_a_remoção_do_endereco() {
        response = when()
                .delete(ENDPOINT_API_ENDERECOS + "/{idCliente}/enderecos/{idEndereco}", emailCliente, endereco.getId());
    }

    @Entao("o endereco é removido com sucesso")
    public void o_endereco_é_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
}
