package com.comexport.lancamentocontabil.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.comexport.lancamentocontabil.entity.Stats;

public class StatsTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    Fixture.of(Stats.class).addTemplate(VALID, new Rule() {{
      add("soma", 20000.00);
      add("min", 5000.00);
      add("max", 7000.00);
      add("media", 10000.00);
      add("qtde", 5);
    }});
  }
}