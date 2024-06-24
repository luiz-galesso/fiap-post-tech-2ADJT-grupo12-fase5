package com.fase5.techchallenge.fiap.mscliente.cliente.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.CadastrarCliente;
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

class CadastrarClienteTest {
    @Mock
    ClienteGateway clienteGateway;
    CadastrarCliente cadastrarCliente;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cadastrarCliente = new CadastrarCliente(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCadastrarCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        when(clienteGateway.create(any(Cliente.class))).thenReturn(cliente);

        var clienteRetornado = cadastrarCliente.execute(clienteInsertDTO);

        assertThat(clienteRetornado).isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isNotNull();
        assertThat(clienteRetornado.getDataRegistro()).isNotNull();
        verify(clienteGateway, times(1)).create(any(Cliente.class));
    }

    @Test
    void deveGerarExcecao_QuandoCadastrarCliente_ClienteJaCadastrado() {
        Cliente cliente = ClienteHelper.gerarCliente();
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteGateway.create(cliente);
        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteGateway.create(any(Cliente.class))).thenReturn(cliente);

        assertThatThrownBy(() -> cadastrarCliente.execute(clienteInsertDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Já existe um cliente cadastrado com o email informado.");
        verify(clienteGateway, times(1)).create(any(Cliente.class));
    }
}
