package com.fase5.techchallenge.fiap.mscliente.endereco.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.AtualizarEndereco;
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
class AtualizarEnderecoIT {

    @Autowired
    AtualizarEndereco atualizarEndereco;
    @Autowired
    ClienteGateway clienteGateway;
    @Autowired
    EnderecoGateway enderecoGateway;

    @Test
    void devePermitirAtualizarEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        var enderecoRetornado = atualizarEndereco.execute(cliente.getEmail(), endereco.getId(), enderecoUpdateDTO);

        assertThat(enderecoRetornado).isInstanceOf(Endereco.class);
        assertThat(enderecoRetornado.getId()).isNotNull();
        assertThat(enderecoRetornado.getLogradouro()).isNotEqualTo(endereco.getLogradouro());
        assertThat(enderecoRetornado.getBairro()).isNotEqualTo(endereco.getBairro());
        assertThat(enderecoRetornado.getCidade()).isNotEqualTo(endereco.getCidade());
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarEndereco_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        Endereco endereco = EnderecoHelper.gerarEndereco(ClienteHelper.gerarCliente());
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        assertThatThrownBy(() -> atualizarEndereco.execute(emailCliente, endereco.getId(), enderecoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarEndereco_EnderecoNaoExiste() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        assertThatThrownBy(() -> atualizarEndereco.execute(cliente.getEmail(), 10, enderecoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não existe.");
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarEndereco_EnderecoOutroCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);

        assertThatThrownBy(() -> atualizarEndereco.execute(cliente.getEmail(), endereco.getId(), enderecoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não pertence ao cliente.");
    }
}
