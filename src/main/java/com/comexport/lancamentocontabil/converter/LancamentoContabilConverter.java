package com.comexport.lancamentocontabil.converter;

import com.comexport.lancamentocontabil.controller.json.LancamentoContabilJson;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LancamentoContabilConverter implements Converter<LancamentoContabilJson, LancamentoContabil> {

  @Override
  public LancamentoContabil convert(final LancamentoContabilJson source) {
    final LancamentoContabil target = new LancamentoContabil();
    BeanUtils.copyProperties(source, target);
    return target;
  }
}
