package com.fase5.techchallenge.fiap.mscliente.endereco.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.CadastrarEndereco;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import com.fase5.techchallenge.fiap.mscliente.utils.EnderecoHelper;
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
class CadastrarEnderecoIT {

    @Autowired
    CadastrarEndereco cadastrarEndereco;
    @Autowired
    ClienteGateway clienteGateway;
    @Autowired
    EnderecoGateway enderecoGateway;

    @Test
    void devePermitirCadastrarEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        clienteGateway.create(cliente);

        var enderecoRetornado = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);
        assertThat(enderecoRetornado).isInstanceOf(Endereco.class);
        assertThat(enderecoRetornado).isNotNull();
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarEndereco_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();

        assertThatThrownBy(() -> cadastrarEndereco.execute(emailCliente, enderecoInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
    }
}
