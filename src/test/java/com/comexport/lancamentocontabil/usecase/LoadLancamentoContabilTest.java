package com.comexport.lancamentocontabil.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.gateway.LancamentoContabilGateway;
import com.comexport.lancamentocontabil.template.LancamentoContabilTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
public class LoadLancamentoContabilTest {

  @Autowired
  private LoadLancamentoContabil loadLancamentoContabil;

  @MockBean
  private LancamentoContabilGateway lancamentoContabilGateway;

  @MockBean
  private ContaContabilGateway contaContabilGateway;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testLoadLancamentoContabilById() {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(lancamentoContabilGateway.findById(lancamentoContabil.getId())).willReturn(Optional.of(lancamentoContabil));

    final LancamentoContabil loaded = loadLancamentoContabil.byId(lancamentoContabil.getId());

    assertNotNull(loaded);
    assertEquals(lancamentoContabil.getId(), loaded.getId());
    assertEquals(lancamentoContabil.getContaContabil(), loaded.getContaContabil());
    assertEquals(lancamentoContabil.getData(), loaded.getData());
    assertEquals(lancamentoContabil.getValor(), loaded.getValor());

    then(lancamentoContabilGateway).should().findById(lancamentoContabil.getId());
  }

  @Test(expected = NotFoundException.class)
  public void testLoadLancamentoContabilByIdNotFound() {

    final String id = UUID.randomUUID().toString();
    given(lancamentoContabilGateway.findById(id)).willReturn(Optional.empty());

    loadLancamentoContabil.byId(id);

    then(lancamentoContabilGateway).should().findById(id);
  }

  @Test
  public void testListLancamentoContabilByContaContabil() {

    final List<LancamentoContabil> lancamentosContabeis = Fixture.from(LancamentoContabil.class)
        .gimme(3, LancamentoContabilTemplate.VALID);
    final Long contaContabil = lancamentosContabeis.get(0).getContaContabil();

    given(contaContabilGateway.count(contaContabil)).willReturn(1L);
    given(lancamentoContabilGateway.findByContaContabil(contaContabil)).willReturn(lancamentosContabeis);

    final List<LancamentoContabil> list = loadLancamentoContabil.byContaContabil(contaContabil);

    assertEquals(lancamentosContabeis, list);

    then(contaContabilGateway).should().count(contaContabil);
    then(lancamentoContabilGateway).should().findByContaContabil(contaContabil);
  }

  @Test
  public void testListLancamentoContabilByContaContabilEmpty() {

    final Long contaContabil = 12345L;

    given(contaContabilGateway.count(contaContabil)).willReturn(1L);
    given(lancamentoContabilGateway.findByContaContabil(contaContabil)).willReturn(new ArrayList<>());

    final List<LancamentoContabil> list = loadLancamentoContabil.byContaContabil(contaContabil);

    assertEquals(0, list.size());

    then(contaContabilGateway).should().count(contaContabil);
    then(lancamentoContabilGateway).should().findByContaContabil(contaContabil);
  }

  @Test(expected = NotFoundException.class)
  public void testListLancamentoContabilByContaContabilNotFound() {

    final Long contaContabil = 12345L;

    given(contaContabilGateway.count(contaContabil)).willReturn(0L);

    loadLancamentoContabil.byContaContabil(contaContabil);

    then(contaContabilGateway).should().count(contaContabil);
    then(lancamentoContabilGateway).shouldHaveZeroInteractions();
  }
}