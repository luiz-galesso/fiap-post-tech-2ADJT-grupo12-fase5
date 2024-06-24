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

class AtualizarEnderecoTest {
    @Mock
    ClienteGateway clienteGateway;
    @Mock
    EnderecoGateway enderecoGateway;
    AtualizarEndereco atualizarEndereco;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        atualizarEndereco = new AtualizarEndereco(clienteGateway, enderecoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAtualizarEndereco() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        enderecoGateway.create(endereco);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteGateway.update(any(Cliente.class))).thenReturn(cliente);
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.of(endereco));
        when(enderecoGateway.update(any(Endereco.class))).thenReturn(endereco);

        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        var enderecoRetornado = atualizarEndereco.execute(cliente.getEmail(), endereco.getId(), enderecoUpdateDTO);

        assertThat(enderecoRetornado).isInstanceOf(Endereco.class);
        assertThat(enderecoRetornado.getId()).isNotNull();
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, times(1)).update(any(Endereco.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarEndereco_ClienteNaoExiste() {
        String emailCliente = "joao-silva@email.com";
        Endereco endereco = EnderecoHelper.gerarEndereco(ClienteHelper.gerarCliente());
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarEndereco.execute(emailCliente, endereco.getId(), enderecoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Cliente não existe.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, never()).update(any(Endereco.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarEndereco_EnderecoNaoExiste() {
        Cliente cliente = ClienteHelper.gerarCliente();
        clienteGateway.create(cliente);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarEndereco.execute(cliente.getEmail(), 10, enderecoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não existe.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, never()).update(any(Endereco.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarEndereco_EnderecoOutroCliente() {
        Cliente cliente = ClienteHelper.gerarCliente();
        Cliente cliente2 = new Cliente("carlos.souza@example.com", "Carlos Souza", LocalDateTime.now(), LocalDate.of(1991, 10, 19));
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        Integer idEndereco = 1;

        clienteGateway.create(cliente);
        enderecoGateway.create(endereco);

        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteGateway.findById(any(String.class))).thenReturn(Optional.of(cliente2));
        when(enderecoGateway.findById(any(Integer.class))).thenReturn(Optional.of(endereco));
        when(enderecoGateway.create(any(Endereco.class))).thenReturn(endereco);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        assertThatThrownBy(() -> atualizarEndereco.execute(cliente.getEmail(), endereco.getId(), enderecoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Endereço não pertence ao cliente.");
        verify(clienteGateway, times(1)).findById(any(String.class));
        verify(enderecoGateway, times(1)).findById(any(Integer.class));
        verify(enderecoGateway, never()).update(any(Endereco.class));
    }
}
