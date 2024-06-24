package com.fase5.techchallenge.fiap.mscliente.cliente.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.AtualizarCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
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

class AtualizarClienteTest {
    @Mock
    ClienteGateway clienteGateway;
    AtualizarCliente atualizarCliente;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        atualizarCliente = new AtualizarCliente(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAtualizarCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteGateway.update(any(Cliente.class))).thenReturn(cliente);

        var clienteRetornado = atualizarCliente.execute(cliente.getEmail(), ClienteHelper.gerarClienteUpdate());

        assertThat(clienteRetornado).isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isNotNull();
        assertThat(clienteRetornado.getDataRegistro()).isNotNull();
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, times(1)).update(any(Cliente.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarCliente_ClienteNaoCadastrado() {
        Cliente cliente = ClienteHelper.gerarCliente();
        when(clienteGateway.update(any(Cliente.class))).thenReturn(cliente);

        assertThatThrownBy(() -> atualizarCliente.execute(cliente.getEmail(), ClienteHelper.gerarClienteUpdate()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
        verify(clienteGateway, never()).update(any(Cliente.class));
    }
}
