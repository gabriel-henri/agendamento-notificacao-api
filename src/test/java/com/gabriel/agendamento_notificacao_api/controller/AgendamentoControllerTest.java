package com.gabriel.agendamento_notificacao_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gabriel.agendamento_notificacao_api.business.AgendamentoService;
import com.gabriel.agendamento_notificacao_api.controller.dto.in.AgendamentoRecord;
import com.gabriel.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.gabriel.agendamento_notificacao_api.infrastructure.enums.StatusNotificacaoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @Mock
    private AgendamentoService agendamentoService;

    @InjectMocks
    private AgendamentoController agendamentoController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private AgendamentoRecord agendamentoRecord;
    private AgendamentoRecordOut agendamentoOut;

    @BeforeEach
    void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();

        objectMapper.registerModule(new JavaTimeModule());

        agendamentoRecord = new AgendamentoRecord("email@gmail.com",
                "400289923", "Não importa a mensagem", LocalDateTime.of(2025, 01, 02, 00, 00, 00));
        agendamentoOut = new AgendamentoRecordOut(1L, "email@gmail.com",
                "400289923", "Não importa a mensagem", LocalDateTime.of(2025, 01, 02, 00, 00, 00),
                StatusNotificacaoEnum.AGENDADO);
    }

    @Test
    public void deveCriarAgendamentoComSucesso() throws Exception {
        when(agendamentoService.gravarAgendamento(agendamentoRecord)).thenReturn(agendamentoOut);

        mockMvc.perform(post("/agendamento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(agendamentoRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailDestinatario").value(agendamentoOut.emailDestinatario()))
                .andExpect(jsonPath("$.telefoneDestinatario").value(agendamentoOut.telefoneDestinatario()))
                .andExpect(jsonPath("$.mensagem").value(agendamentoOut.mensagem()))
                .andExpect(jsonPath("$.dataHoraEnvio").value("02-01-2025 00:00:00"))
                .andExpect(jsonPath("$.statusNotificacao").value("AGENDADO"));
    }
}
