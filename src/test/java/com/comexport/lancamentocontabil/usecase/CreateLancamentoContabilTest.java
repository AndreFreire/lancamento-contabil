package com.comexport.lancamentocontabil.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.gateway.LancamentoContabilGateway;
import com.comexport.lancamentocontabil.template.LancamentoContabilTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CreateLancamentoContabilTest {

  @Autowired
  private CreateLancamentoContabil createLancamentoContabil;

  @MockBean
  private LancamentoContabilGateway lancamentoContabilGateway;

  @MockBean
  private ContaContabilGateway contaContabilGateway;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testCreateLancamentoContabil() {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(contaContabilGateway.count(lancamentoContabil.getContaContabil())).willReturn(1L);
    given(lancamentoContabilGateway.create(any(LancamentoContabil.class))).willReturn(lancamentoContabil);

    final LancamentoContabil created = createLancamentoContabil.execute(lancamentoContabil);

    assertNotNull(created);
    assertEquals(lancamentoContabil.getId(), created.getId());
    assertEquals(lancamentoContabil.getContaContabil(), created.getContaContabil());
    assertEquals(lancamentoContabil.getData(), created.getData());
    assertEquals(lancamentoContabil.getValor(), created.getValor());

    then(contaContabilGateway).should().count(lancamentoContabil.getContaContabil());
    then(lancamentoContabilGateway).should().create(lancamentoContabil);
  }

  @Test(expected = NotFoundException.class)
  public void testCreateLancamentoContabilNotFoundContaContabil() {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(contaContabilGateway.count(lancamentoContabil.getContaContabil())).willReturn(0L);

    createLancamentoContabil.execute(lancamentoContabil);

    then(contaContabilGateway).should().count(lancamentoContabil.getContaContabil());
    then(lancamentoContabilGateway).shouldHaveZeroInteractions();
  }
}