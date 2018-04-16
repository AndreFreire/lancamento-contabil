package com.comexport.lancamentocontabil.gateway;

import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.exception.DuplicateException;
import com.comexport.lancamentocontabil.gateway.repository.ContaContabilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContaContabilGatewayMongo implements ContaContabilGateway {

  private final ContaContabilRepository repository;

  @Override
  public Long count(final Long numero) {
    return repository.countContaContabilByNumero(numero);
  }

  @Override
  public ContaContabil create(final ContaContabil contaContabil) {

    final ContaContabil created;
    try {
      created = repository.save(contaContabil);
    } catch (final DuplicateKeyException e) {
      throw new DuplicateException(ContaContabil.COLLECTION);
    }

    return created;
  }
}