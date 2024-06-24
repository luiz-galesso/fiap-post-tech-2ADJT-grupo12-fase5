package com.fase5.techchallenge.fiap.mscliente.endereco.infrastructure;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.EnderecoController;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.*;
import com.fase5.techchallenge.fiap.mscliente.utils.ClienteHelper;
import com.fase5.techchallenge.fiap.mscliente.utils.EnderecoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnderecoControllerTest {

    @Mock
    private CadastrarEndereco cadastrarEndereco;
    @Mock
    private AtualizarEndereco atualizarEndereco;
    @Mock
    private ObterEnderecoPeloId obterEnderecoPeloId;
    @Mock
    private ObterEnderecosPeloCliente obterEnderecosPeloCliente;
    @Mock
    private RemoverEnderecoPeloId removerEnderecoPeloId;
    private MockMvc mockMvc;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        EnderecoController enderecoController = new EnderecoController(cadastrarEndereco, atualizarEndereco, obterEnderecoPeloId, obterEnderecosPeloCliente, removerEnderecoPeloId);
        mockMvc = MockMvcBuilders.standaloneSetup(enderecoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void devePermitirCadastrarEndereco() throws Exception {
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setEmail("john_wick@email.com");

        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);

        when(cadastrarEndereco.execute(any(String.class), any(EnderecoInsertDTO.class))).thenReturn(endereco);

        mockMvc.perform(post("/clientes/{idCliente}/enderecos", cliente.getEmail())
                .content(ClienteHelper.asJsonString(enderecoInsertDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        verify(cadastrarEndereco, times(1)).execute(any(String.class), any(EnderecoInsertDTO.class));
    }

    @Test
    void devePermitirAtualizarEndereco() throws Exception {
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setEmail("pedro.almeida@example.com");
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        when(atualizarEndereco.execute(any(String.class), any(Integer.class), any(EnderecoUpdateDTO.class))).thenReturn(endereco);

        mockMvc.perform(put("/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClienteHelper.asJsonString(enderecoUpdateDTO)))
                .andExpect(status().isAccepted());
        verify(atualizarEndereco, times(1)).execute(any(String.class), any(Integer.class), any(EnderecoUpdateDTO.class));

    }

    @Test
    void devePermitirObterEnderecoPorId() throws Exception {
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setEmail("joao.silva@example.com");
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        endereco.setId(1);
        when(obterEnderecoPeloId.execute(any(String.class), any(Integer.class))).thenReturn(endereco);

        mockMvc.perform(get("/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId()))
                .andExpect(status()
                        .isOk());
        verify(obterEnderecoPeloId, times(1)).execute(any(String.class), any(Integer.class));
    }

    @Test
    void devePermitirObterEnderecosPorCliente() throws Exception {
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setEmail("joao.silva@example.com");
        List<Endereco> enderecos = Arrays.asList(EnderecoHelper.gerarEndereco(cliente));

        when(obterEnderecosPeloCliente.execute(any(String.class))).thenReturn(enderecos);

        mockMvc.perform(get("/clientes/{idCliente}/enderecos", cliente.getEmail()))
                .andExpect(status().isOk());
        verify(obterEnderecosPeloCliente, times(1)).execute(any(String.class));
    }

    @Test
    void devePermitirRemoverEndereco() throws Exception {
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setEmail("ana.ferreira@example.com");
        Endereco endereco = EnderecoHelper.gerarEndereco(cliente);
        endereco.setId(10);

        when(obterEnderecoPeloId.execute(any(String.class), any(Integer.class))).thenReturn(endereco);
        doNothing().when(removerEnderecoPeloId).execute(any(String.class), any(Integer.class));

        mockMvc.perform(delete("/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId()))
                .andExpect(status().isOk());
    }

}
