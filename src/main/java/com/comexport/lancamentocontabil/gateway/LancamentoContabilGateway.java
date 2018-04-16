package com.comexport.lancamentocontabil.gateway;

import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import java.util.List;
import java.util.Optional;

public interface LancamentoContabilGateway {

  LancamentoContabil create(LancamentoContabil lancamentoContabil);

  Optional<LancamentoContabil> findById(String id);

  List<LancamentoContabil> findByContaContabil(Long contaContabil);
}