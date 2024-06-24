package com.fase5.techchallenge.fiap.mscliente.performance;

import com.github.javafaker.Faker;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.HttpStatus;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ClientePerformanceTest extends Simulation {
    private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8091/ms-cliente")
            .header("Content-Type", "application/json");
    private final Faker faker = new Faker();
    ActionBuilder cadastrarClienteRequest = http("cadastrar cliente")
            .post("/clientes")
            .body(StringBody(session -> {
                int index = session.getString("email").indexOf('@');
                var nome = session.getString("email").substring(0, index).replace(".", " ");
                return "{\"email\":\"" + session.getString("email") + "\",\"nome\":\"" + nome + "\",\"dataNascimento\":\"1983-12-27\"}}";
            }))
            .asJson()
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.email").saveAs("idCliente"));

    ActionBuilder buscarClienteRequest = http("buscar cliente")
            .get("/clientes/#{idCliente}")
            .checkIf((response, session) -> {
                return session.getString("idCliente") != null;
            }).then()
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder removerClienteRequest = http("remover cliente")
            .delete("/clientes/#{idCliente}")
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder alterarClienteRequest = http("alterar cliente")
            .put("/clientes/#{idCliente}")
            .body(StringBody(session -> {
                int index = session.getString("email").indexOf('@');
                var nome = session.getString("email").substring(0, index).replace(".", " ");
                return "{\"nome\":\"" + nome + "\",\"dataNascimento\":\"1991-01-13\"}}";
            }))
            .asJson()
            .check(status().is(HttpStatus.ACCEPTED.value()));

    ActionBuilder cadastrarEnderecoRequest = http("cadastrar endereco")
            .post("/clientes/#{idCliente}/enderecos")
            .body(StringBody("{\"logradouro\":\"RuadoChuva\",\"numero\":\"11\",\"bairro\":\"RiosdasPedras\",\"complemento\":\"AP1BlocoD\",\"cep\":39281313,\"cidade\":\"SantaRitadoSapucai\",\"estado\":\"MinasGerais\",\"referencia\":\"Proximoaolagoazul\"}"))
            .asJson()
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.id").saveAs("idEndereco"));

    ActionBuilder buscarEnderecoRequest = http("buscar endereco")
            .get("/clientes/#{idCliente}/enderecos/#{idEndereco}")
            .checkIf((response, session) -> {
                return session.getString("idEndereco") != null;
            }).then()
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder removerEnderecoRequest = http("remover endereco")
            .delete("/clientes/#{idCliente}/enderecos/#{idEndereco}")
            .check(status().is(HttpStatus.OK.value()));

    ActionBuilder alterarEnderecoRequest = http("alterar endereco")
            .put("/clientes/#{idCliente}/enderecos/#{idEndereco}")
            .body(StringBody("{\"logradouro\":\"RuadoChuva\",\"numero\":\"11\",\"bairro\":\"RiosdasPedras\",\"complemento\":\"AP1BlocoD\",\"cep\":39281313,\"cidade\":\"SantaRitadoSapucai\",\"estado\":\"MinasGerais\",\"referencia\":\"Proximoaolagoazul\"}"))
            .asJson()
            .check(status().is(HttpStatus.ACCEPTED.value()));

    ScenarioBuilder cenarioCadastrarCliente = scenario("Cadastrar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest);

    ScenarioBuilder cenarioAdicionarBuscarCliente = scenario("Adicionar e Buscar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(buscarClienteRequest);

    ScenarioBuilder cenarioAdicionarRemoverCliente = scenario("Adicionar e Remover Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(removerClienteRequest);

    ScenarioBuilder cenarioAdicionarAlterarCliente = scenario("Adicionar e Alterar Cliente")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(alterarClienteRequest);

    ScenarioBuilder cenarioCadastrarEndereco = scenario("Cadastrar Cliente e Endereco")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(cadastrarEnderecoRequest);

    ScenarioBuilder cenarioAdicionarBuscarEndereco = scenario("Adicionar Cliente ,Endereco e Buscar Endereco")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(cadastrarEnderecoRequest)
            .exec(buscarEnderecoRequest);

    ScenarioBuilder cenarioAdicionarRemoverEndereco = scenario("Adicionar Cliente ,Endereco e Remover Endereco ")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(cadastrarEnderecoRequest)
            .exec(removerEnderecoRequest);

    ScenarioBuilder cenarioAdicionarAlterarEndereco = scenario("Adicionar Cliente ,Endereco e Alterar Endereco ")
            .exec(session -> {
                String email = faker.internet().emailAddress();
                return session.set("email", email);
            })
            .exec(cadastrarClienteRequest)
            .exec(cadastrarEnderecoRequest)
            .exec(alterarEnderecoRequest);

    {
        setUp(cenarioCadastrarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarBuscarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarRemoverCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarAlterarCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioCadastrarEndereco.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarBuscarEndereco.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarRemoverEndereco.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarAlterarEndereco.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10)))

        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(360)
                );
    }
}
