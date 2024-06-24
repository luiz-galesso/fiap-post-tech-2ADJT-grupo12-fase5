package com.fase5.techchallenge.fiap.mscliente.cliente.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.ObterClientePeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.EntityNotFoundException;
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

class ObterClientePeloIdTest {
    @Mock
    ClienteGateway clienteGateway;
    ObterClientePeloId obterClientePeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterClientePeloId = new ObterClientePeloId(clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void devePermitirRemoverCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteGateway.create(any(Cliente.class))).thenReturn(cliente);

        var clienteRetornado = obterClientePeloId.execute(cliente.getEmail());

        assertThat(clienteRetornado).isInstanceOf(Cliente.class);
        assertThat(clienteRetornado.getEmail()).isNotNull();
        assertThat(clienteRetornado.getDataRegistro()).isNotNull();
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, times(1)).create(any(Cliente.class));
    }

    @Test
    void deveGerarExcecao_QuandoRemoverCliente_EmailNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> obterClientePeloId.execute(emailCliente))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Cliente não localizado");
        verify(clienteGateway, times(1)).findById(any(String.class));
    }
}
