package com.comexport.lancamentocontabil.gateway;

import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.gateway.repository.LancamentoContabilRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LancamentoContabilGatewayMongo implements LancamentoContabilGateway {

  private static final String DATA = "data";

  private final LancamentoContabilRepository repository;

  @Override
  public LancamentoContabil create(final LancamentoContabil lancamentoContabil) {
    return repository.save(lancamentoContabil);
  }

  @Override
  public Optional<LancamentoContabil> findById(final String id) {
    return repository.findById(id);
  }

  @Override
  public List<LancamentoContabil> findByContaContabil(final Long contaContabil) {
    return repository.findByContaContabil(contaContabil, new Sort(Direction.ASC, DATA));
  }
}
