package com.fase5.techchallenge.fiap.mscliente.cliente.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.RemoverClientePeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.ObterEnderecosPeloCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.RemoverEnderecoPeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RemoverClientePeloIdTest {

    @Mock
    ClienteGateway clienteGateway;
    @Mock
    RemoverEnderecoPeloId removerEnderecoPeloId;
    @Mock
    ObterEnderecosPeloCliente obterEnderecosPeloCliente;
    RemoverClientePeloId removerClientePeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        removerClientePeloId = new RemoverClientePeloId(clienteGateway, obterEnderecosPeloCliente, removerEnderecoPeloId);
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
        doNothing().when(clienteGateway).remove(cliente.getEmail());

        removerClientePeloId.execute(cliente.getEmail());

        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, times(1)).remove(any(String.class));
    }

    @Test
    void deveGerarExcecao_QuandoRemoverCliente_EmailNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> removerClientePeloId.execute(emailCliente))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o cliente cadastrado com o email informado.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(clienteGateway, never()).remove(any(String.class));
    }
}
