package com.comexport.lancamentocontabil.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import java.util.UUID;

public class ContaContabilTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    Fixture.of(ContaContabil.class).addTemplate(VALID, new Rule() {{
      add("id", UUID.randomUUID().toString());
      add("numero", 1111001L);
      add("descricao", "Conta do Darth Vader");
    }});
  }
}
