package com.comexport.lancamentocontabil.usecase;

import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.gateway.LancamentoContabilGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateLancamentoContabil {

  private final ContaContabilGateway contaContabilGateway;
  private final LancamentoContabilGateway lancamentoContabilGateway;

  public LancamentoContabil execute(final LancamentoContabil lancamentoContabil) {

    if (contaContabilGateway.count(lancamentoContabil.getContaContabil()) < 1) {
      throw new NotFoundException(ContaContabil.COLLECTION);
    }

    lancamentoContabil.setId(null);

    log.info("Criando lançamento contábil: {}", lancamentoContabil.toString());

    return lancamentoContabilGateway.create(lancamentoContabil);
  }
}