package com.gabriel.agendamento_notificacao_api.business;

import com.gabriel.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.gabriel.agendamento_notificacao_api.controller.dto.in.AgendamentoRecord;
import com.gabriel.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.gabriel.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import com.gabriel.agendamento_notificacao_api.infrastructure.exception.NotFoundException;
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

    public AgendamentoRecordOut buscarAgendamentosPorId(Long id){
        return agendamentoMapper.paraOut(
                agendamentoRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Id não encontrado")));
    }

    public void cancelarAgendamento(Long id){
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Id não encontrado"));
        agendamentoRepository.save(
          agendamentoMapper.paraEntityCancelamento(agendamento)
        );
    }
}
