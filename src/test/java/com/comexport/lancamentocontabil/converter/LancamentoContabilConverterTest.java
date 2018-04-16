package com.comexport.lancamentocontabil.converter;

import static org.junit.Assert.assertEquals;

import com.comexport.lancamentocontabil.controller.json.LancamentoContabilJson;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import java.time.LocalDate;
import org.junit.Test;

public class LancamentoContabilConverterTest {

  private final LancamentoContabilConverter converter = new LancamentoContabilConverter();

  @Test
  public void testConverter() {

    final LancamentoContabilJson source = LancamentoContabilJson.builder()
        .valor(123.22)
        .data(LocalDate.now())
        .contaContabil(12345L)
        .build();

    final LancamentoContabil target = converter.convert(source);

    assertEquals(source.getContaContabil(), target.getContaContabil());
    assertEquals(source.getData(), target.getData());
    assertEquals(source.getValor(), target.getValor());
  }
}