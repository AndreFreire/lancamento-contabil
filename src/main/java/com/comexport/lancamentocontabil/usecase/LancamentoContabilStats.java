package com.comexport.lancamentocontabil.usecase;

import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.entity.Stats;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.gateway.LancamentoContabilStatsGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LancamentoContabilStats {

  private final LancamentoContabilStatsGateway lancamentoContabilStatsGateway;
  private final ContaContabilGateway contaContabilGateway;

  public Stats execute(final Long contaContabil) {

    final Stats stats;
    if (contaContabil == null) {

      log.info("Calculando estátisticas de todos os lançamentos contábeis");

      stats = lancamentoContabilStatsGateway.calculate();
    } else {

      if (contaContabilGateway.count(contaContabil) < 1) {
        throw new NotFoundException(ContaContabil.COLLECTION);
      }

      log.info("Calculando estátisticas de todos os lançamentos contábeis da conta contábil: {}", contaContabil);

      stats = lancamentoContabilStatsGateway.calculate(contaContabil);
    }

    return stats;
  }
}