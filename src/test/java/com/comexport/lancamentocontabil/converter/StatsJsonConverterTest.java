package com.comexport.lancamentocontabil.converter;

import static org.junit.Assert.assertEquals;

import com.comexport.lancamentocontabil.controller.json.StatsJson;
import com.comexport.lancamentocontabil.entity.Stats;
import org.junit.Test;

public class StatsJsonConverterTest {

  private final StatsJsonConverter converter = new StatsJsonConverter();

  @Test
  public void testConverter() {

    final Stats source = new Stats();
    source.setMax(1000.00);
    source.setMin(1.0);
    source.setMedia(100.00);
    source.setQtde(5);
    source.setSoma(2000.00);

    final StatsJson target = converter.convert(source);

    assertEquals(source.getMax(), target.getMax(), 0.0);
    assertEquals(source.getMedia(), target.getMedia(), 0.0);
    assertEquals(source.getMin(), target.getMin(), 0.0);
    assertEquals(source.getQtde(), target.getQtde());
    assertEquals(source.getSoma(), target.getSoma(), 0.0);
  }
}