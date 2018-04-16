package com.comexport.lancamentocontabil.usecase;

import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.gateway.LancamentoContabilGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadLancamentoContabil {

  private final LancamentoContabilGateway lancamentoContabilGateway;
  private final ContaContabilGateway contaContabilGateway;

  public LancamentoContabil byId(final String lancamentoContabilId) {

    log.info("Carregando lançamento contábil id: {}", lancamentoContabilId);

    return lancamentoContabilGateway.findById(lancamentoContabilId)
        .orElseThrow(() -> new NotFoundException(LancamentoContabil.COLLECTION));
  }

  public List<LancamentoContabil> byContaContabil(final Long contaContabil) {

    if (contaContabilGateway.count(contaContabil) < 1) {
      throw new NotFoundException(ContaContabil.COLLECTION);
    }

    log.info("Carregando lançamentos contábeis para conta contábil: {}", contaContabil);

    return lancamentoContabilGateway.findByContaContabil(contaContabil);
  }
}