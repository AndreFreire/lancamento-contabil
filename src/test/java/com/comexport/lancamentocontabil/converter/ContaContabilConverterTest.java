package com.comexport.lancamentocontabil.converter;

import static org.junit.Assert.assertEquals;

import com.comexport.lancamentocontabil.controller.json.ContaContabilJson;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import org.junit.Test;

public class ContaContabilConverterTest {

  private final ContaContabilConverter converter = new ContaContabilConverter();

  @Test
  public void testConverter() {

    final ContaContabilJson source = ContaContabilJson.builder()
        .numero(12345L)
        .descricao("teste")
        .build();

    final ContaContabil target = converter.convert(source);

    assertEquals(source.getNumero(), target.getNumero());
    assertEquals(source.getDescricao(), target.getDescricao());
  }
}