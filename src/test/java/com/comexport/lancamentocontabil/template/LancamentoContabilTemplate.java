package com.comexport.lancamentocontabil.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import java.time.LocalDate;
import java.util.UUID;

public class LancamentoContabilTemplate implements TemplateLoader {

  public static final String VALID = "valid";
  public static final String RANDOM_VALUE = "random-value";

  @Override
  public void load() {

    Fixture.of(LancamentoContabil.class).addTemplate(VALID, new Rule() {{
      add("id", uniqueRandom(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
          UUID.randomUUID().toString()));
      add("contaContabil", 1111001L);
      add("data", LocalDate.now());
      add("valor", 25000.15);
    }});

    Fixture.of(LancamentoContabil.class).addTemplate(RANDOM_VALUE, new Rule() {{
      add("id", uniqueRandom(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
          UUID.randomUUID().toString(), UUID.randomUUID().toString()));
      add("contaContabil", 1111001L);
      add("data", LocalDate.now());
      add("valor", uniqueRandom(1000.15, 3000.15, 7000.15, 15000.15, 25000.15));
    }});
  }
}
