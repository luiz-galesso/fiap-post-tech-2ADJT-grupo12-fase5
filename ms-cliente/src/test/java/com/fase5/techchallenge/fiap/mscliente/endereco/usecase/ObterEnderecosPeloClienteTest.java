package com.fase5.techchallenge.fiap.mscliente.endereco.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.ObterEnderecosPeloCliente;
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

class ObterEnderecosPeloClienteTest {
    @Mock
    ClienteGateway clienteGateway;
    @Mock
    EnderecoGateway enderecoGateway;
    ObterEnderecosPeloCliente obterEnderecosPeloCliente;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterEnderecosPeloCliente = new ObterEnderecosPeloCliente(clienteGateway, enderecoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirObterEnderecosPorCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.of(endereco));

        var enderecoRetornado = obterEnderecosPeloCliente.execute(cliente.getEmail());
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, times(1)).findByCliente(any(Cliente.class));
    }

    @Test
    void deveGerarExcecao_QuandoObterEnderecosPorCliente_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        Endereco endereco = EnderecoHelper.gerarEndereco(ClienteHelper.gerarCliente());

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> obterEnderecosPeloCliente.execute(emailCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, never()).findByCliente(any(Cliente.class));
    }
}
