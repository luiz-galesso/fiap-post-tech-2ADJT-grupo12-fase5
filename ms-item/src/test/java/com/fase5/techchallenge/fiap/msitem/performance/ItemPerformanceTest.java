package com.fase5.techchallenge.fiap.msitem.performance;

import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.EstoqueDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fase5.techchallenge.fiap.msitem.utils.ItemHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ItemPerformanceTest extends Simulation
{
    String port = System.getenv("PORT");

    private final HttpProtocolBuilder httpProtocolBuilder
            = http.baseUrl("http://localhost:"+ (port == null ? "8092" : port) + "/ms-item")
            .header("Content-Type", "application/json");

    ActionBuilder cadastrarItem = http("Cadastrar item")
            .post("/itens")
            .body(StringBody(session -> {
                ItemInsertDTO itemInsertDTO = ItemHelper.gerarItem("Item " + new Random().nextLong());
                try {
                    return ItemHelper.asJsonString(itemInsertDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .asJson()
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.codItem").saveAs("id"));

    ActionBuilder atualizarItem = http("Atualizar item")
            .put("/itens/#{id}")
            .body(StringBody(session -> {
                ItemUpdateDTO updateDTO = ItemHelper.gerarItemUpdate(ItemHelper.gerarItem("Item Update " + new Random().nextLong()));
                try {
                    return ItemHelper.asJsonString(updateDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.ACCEPTED.value()));

    ActionBuilder buscarItem = http("Buscar item")
            .get("/itens/#{id}")
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ActionBuilder aumentarEstoque = http("Aumentar estoque")
            .put("/itens/#{id}/aumenta-estoque")
            .body(StringBody(session -> {
                EstoqueDTO estoqueDTO = new EstoqueDTO(10L);
                try {
                    return ItemHelper.asJsonString(estoqueDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ActionBuilder consumirEstoque = http("Consumir estoque")
            .put("/itens/#{id}/consome-estoque")
            .body(StringBody(session -> {
                EstoqueDTO estoqueDTO = new EstoqueDTO(10L);
                try {
                    return ItemHelper.asJsonString(estoqueDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ScenarioBuilder cenarioCadastrarItem = scenario("Cadastar item")
            .exec(cadastrarItem);

    ScenarioBuilder cenarioAtualizarItem = scenario("Atualizar item")
            .exec(cadastrarItem)
            .exec(atualizarItem);

    ScenarioBuilder cenarioBuscarItem = scenario("Buscar item")
            .exec(cadastrarItem)
            .exec(buscarItem);

    ScenarioBuilder cenarioAumentarEstoque = scenario("Aumentar estoque")
            .exec(cadastrarItem)
            .exec(aumentarEstoque);

    ScenarioBuilder cenarioConsumirEstoque = scenario("Consumir estoque")
            .exec(cadastrarItem)
            .exec(consumirEstoque);

    {
        setUp(
                cenarioCadastrarItem.injectOpen(
                        rampUsersPerSec(2)
                                .to(20)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioAtualizarItem.injectOpen(
                        rampUsersPerSec(2)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioBuscarItem.injectOpen(
                                rampUsersPerSec(2)
                                        .to(100)
                                        .during(Duration.ofSeconds(21)),
                                constantUsersPerSec(20)
                                        .during(Duration.ofSeconds(60)),
                                rampUsersPerSec(10)
                                        .to(1)
                                        .during(Duration.ofSeconds(20))
                ),
                cenarioAumentarEstoque.injectOpen(
                        rampUsersPerSec(2)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(20)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioConsumirEstoque.injectOpen(
                        rampUsersPerSec(10)
                                .to(10)
                                .during(Duration.ofSeconds(25)),
                        constantUsersPerSec(16)
                                .during(Duration.ofSeconds(40)),
                        rampUsersPerSec(10)
                                .to(10)
                                .during(Duration.ofSeconds(10))
                )
        )
                .protocols(httpProtocolBuilder)
                .assertions(
                        global().responseTime().max().lt(1000)
                );
    }
}
