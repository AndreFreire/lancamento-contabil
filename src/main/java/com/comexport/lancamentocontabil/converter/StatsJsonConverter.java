package com.comexport.lancamentocontabil.converter;

import com.comexport.lancamentocontabil.controller.json.StatsJson;
import com.comexport.lancamentocontabil.entity.Stats;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatsJsonConverter implements Converter<Stats, StatsJson> {

  @Override
  public StatsJson convert(final Stats source) {
    final StatsJson target = new StatsJson();
    BeanUtils.copyProperties(source, target);
    return target;
  }
}