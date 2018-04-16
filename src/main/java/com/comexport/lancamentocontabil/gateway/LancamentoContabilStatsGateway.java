package com.comexport.lancamentocontabil.gateway;

import com.comexport.lancamentocontabil.entity.Stats;

public interface LancamentoContabilStatsGateway {

  Stats calculate();

  Stats calculate(Long contaContabil);
}