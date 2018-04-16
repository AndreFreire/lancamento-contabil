package com.comexport.lancamentocontabil.usecase;

import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.exception.DuplicateException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateContaContabil {

  private static final String CONTA_CONTABIL = "conta-contabil";

  private final ContaContabilGateway contaContabilGateway;

  public ContaContabil execute(final ContaContabil contaContabil) {

    if (contaContabilGateway.count(contaContabil.getNumero()) > 0) {
      throw new DuplicateException(CONTA_CONTABIL);
    }

    contaContabil.setId(null);

    log.info("Criando conta contábil: {}", contaContabil.toString());

    return contaContabilGateway.create(contaContabil);
  }
}