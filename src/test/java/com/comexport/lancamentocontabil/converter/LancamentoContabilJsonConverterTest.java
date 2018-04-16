package com.comexport.lancamentocontabil.converter;

import static org.junit.Assert.assertEquals;

import com.comexport.lancamentocontabil.controller.json.LancamentoContabilJson;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import java.time.LocalDate;
import org.junit.Test;

public class LancamentoContabilJsonConverterTest {

  private final LancamentoContabilJsonConverter converter = new LancamentoContabilJsonConverter();

  @Test
  public void testConverter() {

    final LancamentoContabil source = new LancamentoContabil();
    source.setContaContabil(123L);
    source.setData(LocalDate.now());
    source.setValor(1234.43);

    final LancamentoContabilJson target = converter.convert(source);

    assertEquals(source.getContaContabil(), target.getContaContabil());
    assertEquals(source.getData(), target.getData());
    assertEquals(source.getValor(), target.getValor());
  }
}