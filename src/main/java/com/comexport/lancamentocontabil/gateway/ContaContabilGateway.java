package com.comexport.lancamentocontabil.gateway;

import com.comexport.lancamentocontabil.entity.ContaContabil;

public interface ContaContabilGateway {

  Long count(Long numero);

  ContaContabil create(ContaContabil contaContabil);
}