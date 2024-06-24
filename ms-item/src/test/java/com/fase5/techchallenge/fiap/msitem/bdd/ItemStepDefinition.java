package com.fase5.techchallenge.fiap.msitem.bdd;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fase5.techchallenge.fiap.msitem.utils.ItemHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ItemStepDefinition
{
    private Response response;

    private Item item;

    private ItemInsertDTO itemInsertDTO;

    private ItemUpdateDTO itemUpdateDTO;

    private static final String ENDPOINT_API_ITEM = "http://localhost:8080/ms-item/itens";

    /** CADASTRAR **/
    @Dado("que tenha um item para cadastrar")
    public void que_tenha_um_item_para_cadastrar() {
        itemInsertDTO = ItemHelper.gerarItem("Item " + new Random().nextLong());
    }

    @Quando("eu cadastro o item")
    public Item cadastro_o_item() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemInsertDTO)
                .when().post(ENDPOINT_API_ITEM);

        item = response.then().extract().as(Item.class);
        return item;
    }

    @Entao("o item é cadastrado")
    public void o_item_é_cadastrado() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @Entao("deverá ser apresentado")
    public void deverá_ser_apresentado() {
        response.then().body(
            JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json")
        );
    }

    /** OBTER ITEM PELO ID **/
    @Dado("que o item já foi cadastrado")
    public void que_o_item_já_foi_cadastrado() {
        que_tenha_um_item_para_cadastrar();
        item = cadastro_o_item();
    }
    @Quando("efetuar a busca pelo ID")
    public void efetuar_a_busca_pelo_id() {
        response = when()
                .get(ENDPOINT_API_ITEM+ "/{id}", item.getId());
    }
    @Então("exibir item")
    public void exibir_item() {
        response.then().body(
          JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json")
        );
    }

    /** ATUALIZAR **/
    @Dado("que eu deseje atualizar os dados desse item")
    public void que_eu_desejo_atualizar_os_dados_desse_item() {
        itemUpdateDTO = new ItemUpdateDTO(
                item.getDescricao(),
                item.getMarca(),
                item.getCategoria(),
                new Random().nextLong(),
                new Random().nextDouble()
        );

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemInsertDTO)
                .when().put(ENDPOINT_API_ITEM + "/{id}", item.getId());
    }
    @Quando("eu atualizo o item com novas informações")
    public void eu_atualizo_o_item_com_novas_informações() {
        response.then().statusCode(HttpStatus.ACCEPTED.value());
    }
    @Então("o item deve ser atualizado com sucesso")
    public void o_item_deve_ser_atualizado_com_sucesso() {
        response.then().body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/item.schema.json")
        );
    }

    /**REMOVER ITEM**/
    @Quando("deletar o item")
    public void deletar_o_item() {
        response = when()
                .delete(ENDPOINT_API_ITEM + "/{id}", item.getId());
    }

    @Entao("o item deve ser deletado")
    public void o_item_deve_ser_deletado() {
        response.then().statusCode(HttpStatus.OK.value());
    }
}
