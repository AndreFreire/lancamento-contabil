package com.comexport.lancamentocontabil.gateway.repository;


import com.comexport.lancamentocontabil.entity.ContaContabil;
import org.springframework.data.repository.CrudRepository;

public interface ContaContabilRepository extends CrudRepository<ContaContabil, String> {

  Long countContaContabilByNumero(Long numero);
}
