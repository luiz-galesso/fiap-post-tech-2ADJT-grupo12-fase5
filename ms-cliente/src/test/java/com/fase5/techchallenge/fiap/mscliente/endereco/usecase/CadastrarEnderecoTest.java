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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadastrarEnderecoTest {
    @Mock
    ClienteGateway clienteGateway;
    @Mock
    EnderecoGateway enderecoGateway;
    CadastrarEndereco cadastrarEndereco;
    ;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cadastrarEndereco = new CadastrarEndereco(clienteGateway, enderecoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCadastrarEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();

        clienteGateway.create(cliente);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(enderecoGateway.create(any(Endereco.class))).thenReturn(endereco);

        var enderecoRetornado = cadastrarEndereco.execute(cliente.getEmail(),enderecoInsertDTO);
        assertThat(enderecoRetornado).isInstanceOf(Endereco.class);
        assertThat(enderecoRetornado).isNotNull();
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, times(1)).create(any(Endereco.class));
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarEndereco_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cadastrarEndereco.execute(emailCliente,enderecoInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, never()).create(any(Endereco.class));
    }
}
