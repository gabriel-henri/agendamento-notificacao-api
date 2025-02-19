package com.gabriel.agendamento_notificacao_api.infrastructure.repository;

import com.gabriel.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
