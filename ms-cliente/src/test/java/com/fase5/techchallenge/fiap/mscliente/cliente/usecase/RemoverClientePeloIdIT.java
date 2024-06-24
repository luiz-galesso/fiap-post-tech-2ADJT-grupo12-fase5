package com.fase5.techchallenge.fiap.mscliente.cliente.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.RemoverClientePeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class RemoverClientePeloIdIT {

    @Autowired
    RemoverClientePeloId removerClientePeloId;
    @Autowired
    ClienteGateway clienteGateway;

    @Test
    void devePermitirRemoverCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        removerClientePeloId.execute(cliente.getEmail());
        var retorno = clienteGateway.findById(cliente.getEmail());
        assertThat(retorno).isEmpty();
    }

    @Test
    void deveGerarExcecao_QuandoRemoverCliente_EmailNaoExiste() {
        String emailCliente = "joao-silva@email.com";

        assertThatThrownBy(() -> removerClientePeloId.execute(emailCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
    }
}
