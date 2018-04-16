package com.comexport.lancamentocontabil.converter;

import com.comexport.lancamentocontabil.controller.json.ContaContabilJson;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContaContabilConverter implements Converter<ContaContabilJson, ContaContabil> {

  @Override
  public ContaContabil convert(final ContaContabilJson source) {
    final ContaContabil target = new ContaContabil();
    BeanUtils.copyProperties(source, target);
    return target;
  }
}
