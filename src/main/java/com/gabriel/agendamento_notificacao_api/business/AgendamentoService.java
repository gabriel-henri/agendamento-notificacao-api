package com.gabriel.agendamento_notificacao_api.business;

import com.gabriel.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.gabriel.agendamento_notificacao_api.controller.dto.in.AgendamentoRecord;
import com.gabriel.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.gabriel.agendamento_notificacao_api.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final IAgendamentoMapper agendamentoMapper;

    public AgendamentoRecordOut gravarAgendamento(AgendamentoRecord agendamento){
        return agendamentoMapper.paraOut(agendamentoRepository.save(
                agendamentoMapper.paraEntity(agendamento)));
    }
}
