package com.comexport.lancamentocontabil.gateway.repository;

import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface LancamentoContabilRepository extends CrudRepository<LancamentoContabil, String> {

  List<LancamentoContabil> findByContaContabil(Long contaContabil, Sort sort);
}
