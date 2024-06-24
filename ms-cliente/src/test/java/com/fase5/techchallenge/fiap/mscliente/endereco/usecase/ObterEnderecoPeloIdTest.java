package com.fase5.techchallenge.fiap.mscliente.endereco.usecase;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.ObterEnderecoPeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.EntityNotFoundException;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import com.fase5.techchallenge.fiap.mscliente.utils.EnderecoHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ObterEnderecoPeloIdTest {
    @Mock
    ClienteGateway clienteGateway;
    @Mock
    EnderecoGateway enderecoGateway;
    ObterEnderecoPeloId obterEnderecoPeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterEnderecoPeloId = new ObterEnderecoPeloId(clienteGateway, enderecoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirObterEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.of(endereco));

        var enderecoRetornado = obterEnderecoPeloId.execute(cliente.getEmail(), endereco.getId());
        assertThat(enderecoRetornado).isInstanceOf(Endereco.class);
        Assertions.assertThat(enderecoRetornado.getCliente()).isNotNull();
        assertThat(enderecoRetornado.getId()).isNotNull();
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, times(1)).findById(any(Integer.class));
    }

    @Test
    void deveGerarExcecao_QuandoObterEndereco_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        Endereco endereco = EnderecoHelper.gerarEndereco(ClienteHelper.gerarCliente());

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> obterEnderecoPeloId.execute(emailCliente, endereco.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, never()).findById(any(Integer.class));
    }

    @Test
    void deveGerarExcecao_QuandoObterEndereco_EnderecoNaoExiste() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        clienteGateway.create(cliente);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> obterEnderecoPeloId.execute(cliente.getEmail(), endereco.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Endereço não localizado");
        verify(clienteGateway, times(1)).findById(any(String.class));
    }

    @Test
    void deveGerarExcecao_QuandoObterEndereco_EnderecoOutroCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Cliente cliente2 = new Cliente("carlos.souza@example.com","Carlos Souza", LocalDateTime.now(), LocalDate.of(1991,10,19));
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        Integer idEndereco = 1;

        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente2));
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.of(endereco));
        when(enderecoGateway.create(any(Endereco.class))).thenReturn(endereco);

        assertThatThrownBy(() -> obterEnderecoPeloId.execute(cliente.getEmail(), endereco.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não pertence ao cliente.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, times(1)).findById(any(Integer.class));
    }

}
