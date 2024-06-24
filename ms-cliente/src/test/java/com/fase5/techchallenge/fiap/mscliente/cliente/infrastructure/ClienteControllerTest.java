package com.fase5.techchallenge.fiap.mscliente.cliente.infrastructure;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.ClienteController;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.AtualizarCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.CadastrarCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.ObterClientePeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.RemoverClientePeloId;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest {

    @Mock
    private CadastrarCliente cadastrarCliente;
    @Mock
    private AtualizarCliente atualizarCliente;
    @Mock
    private ObterClientePeloId obterClientePeloId;
    @Mock
    private RemoverClientePeloId removerClientePeloId;
    private MockMvc mockMvc;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ClienteController clienteController = new ClienteController(cadastrarCliente, atualizarCliente, obterClientePeloId, removerClientePeloId);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void devePermitirCadastrarCliente() throws Exception {

        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteInsertDTO.setEmail("john_wick@email.com");
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setEmail("john_wick@email.com");
        when(cadastrarCliente.execute(any(ClienteInsertDTO.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .content(ClienteHelper.asJsonString(clienteInsertDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(cadastrarCliente, times(1)).execute(any(ClienteInsertDTO.class));
    }


    @Test
    void devePermitirAtualizarCliente() throws Exception {
        String emailCliente = "pedro.almeida@example.com";
        ClienteUpdateDTO clienteUpdateDTO = ClienteHelper.gerarClienteUpdate();
        Cliente cliente = ClienteHelper.gerarCliente();
        when(atualizarCliente.execute(any(String.class), any(ClienteUpdateDTO.class))).thenReturn(cliente);

        mockMvc.perform(put("/clientes/{id}", emailCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClienteHelper.asJsonString(clienteUpdateDTO)))
                .andExpect(status().isAccepted());
        verify(atualizarCliente, times(1)).execute(any(String.class), any(ClienteUpdateDTO.class));

    }


    @Test
    void devePermitirObterCliente() throws Exception {
        String emailCliente = "joao.silva@example.com";
        Cliente cliente = ClienteHelper.gerarCliente();
        when(obterClientePeloId.execute(any(String.class))).thenReturn(cliente);

        mockMvc.perform(get("/clientes/{id}", emailCliente))
                .andExpect(status()
                        .isOk());
        verify(obterClientePeloId, times(1)).execute(any(String.class));
    }

    @Test
    void devePermitirRemoverCliente() throws Exception {
        String emailCliente = "ana.ferreira@example.com";
        doNothing().when(removerClientePeloId).execute(any(String.class));

        mockMvc.perform(delete("/clientes/{id}", emailCliente))
                .andExpect(status().isOk());
        verify(removerClientePeloId, times(1)).execute(any(String.class));
    }

}
