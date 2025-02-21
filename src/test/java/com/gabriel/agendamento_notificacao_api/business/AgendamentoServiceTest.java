package com.gabriel.agendamento_notificacao_api.business;

import com.gabriel.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.gabriel.agendamento_notificacao_api.controller.dto.in.AgendamentoRecord;
import com.gabriel.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.gabriel.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import com.gabriel.agendamento_notificacao_api.infrastructure.enums.StatusNotificacaoEnum;
import com.gabriel.agendamento_notificacao_api.infrastructure.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private IAgendamentoMapper agendamentoMapper;

    private AgendamentoRecord agendamentoRecord;
    private AgendamentoRecordOut agendamentoOut;
    private Agendamento agendamentoEntity;

    @BeforeEach
    void setUp(){
        agendamentoEntity = new Agendamento(1L, "email@gmail.com",
                "400289923",  LocalDateTime.of(2025, 01, 02, 00, 00, 00),
                LocalDateTime.now(), null, "Não importa a mensagem",StatusNotificacaoEnum.AGENDADO);

        agendamentoRecord = new AgendamentoRecord("email@gmail.com",
                "400289923", "Não importa a mensagem", LocalDateTime.of(2025, 01, 02, 00, 00, 00));

        agendamentoOut = new AgendamentoRecordOut(1L, "email@gmail.com",
                "400289923", "Não importa a mensagem", LocalDateTime.of(2025, 01, 02, 00, 00, 00),
                StatusNotificacaoEnum.AGENDADO);
    }

    @Test
    void deveGravarAgendamentoComSucesso(){
        when(agendamentoMapper.paraEntity(agendamentoRecord)).thenReturn(agendamentoEntity);
        when(agendamentoRepository.save(agendamentoEntity)).thenReturn(agendamentoEntity);
        when(agendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoOut);

        AgendamentoRecordOut out = agendamentoService.gravarAgendamento(agendamentoRecord);

        verify(agendamentoMapper, times(1)).paraEntity(agendamentoRecord);
        verify(agendamentoRepository, times(1)).save(agendamentoEntity);
        verify(agendamentoMapper, times(1)).paraOut(agendamentoEntity);

        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoOut);
    }
}
