package com.comexport.lancamentocontabil.converter;

import com.comexport.lancamentocontabil.controller.json.LancamentoContabilJson;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LancamentoContabilJsonConverter implements Converter<LancamentoContabil, LancamentoContabilJson> {

  @Override
  public LancamentoContabilJson convert(final LancamentoContabil source) {
    final LancamentoContabilJson target = new LancamentoContabilJson();
    BeanUtils.copyProperties(source, target);
    return target;
  }
}
