package com.comexport.lancamentocontabil.usecase;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.Stats;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.gateway.LancamentoContabilStatsGateway;
import com.comexport.lancamentocontabil.template.StatsTemplate;
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
public class LancamentoContabilStatsTest {

  @Autowired
  private LancamentoContabilStats lancamentoContabilStats;

  @MockBean
  private LancamentoContabilStatsGateway lancamentoContabilStatsGateway;

  @MockBean
  private ContaContabilGateway contaContabilGateway;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testCreateLancamentoContabilStats() {

    final Stats stats = Fixture.from(Stats.class).gimme(StatsTemplate.VALID);

    given(lancamentoContabilStatsGateway.calculate()).willReturn(stats);

    final Stats result = lancamentoContabilStats.execute(null);

    assertEquals(stats, result);

    then(lancamentoContabilStatsGateway).should().calculate();
  }

  @Test
  public void testCreateLancamentoContabilStatsEmpty() {

    final Stats stats = new Stats();

    given(lancamentoContabilStatsGateway.calculate()).willReturn(stats);

    final Stats result = lancamentoContabilStats.execute(null);

    assertEquals(stats, result);

    then(lancamentoContabilStatsGateway).should().calculate();
  }

  @Test
  public void testCreateLancamentoContabilStatsByContaContabil() {

    final Stats stats = Fixture.from(Stats.class).gimme(StatsTemplate.VALID);
    final long contaContabil = 123456L;

    given(contaContabilGateway.count(contaContabil)).willReturn(1L);
    given(lancamentoContabilStatsGateway.calculate(contaContabil)).willReturn(stats);

    final Stats result = lancamentoContabilStats.execute(contaContabil);

    assertEquals(stats, result);

    then(contaContabilGateway).should().count(contaContabil);
    then(lancamentoContabilStatsGateway).should().calculate(contaContabil);
  }

  @Test
  public void testCreateLancamentoContabilStatsByContaContabilEmpty() {

    final long contaContabil = 123456L;
    final Stats stats = new Stats();

    given(contaContabilGateway.count(contaContabil)).willReturn(1L);
    given(lancamentoContabilStatsGateway.calculate(contaContabil)).willReturn(stats);

    final Stats result = lancamentoContabilStats.execute(contaContabil);

    assertEquals(stats, result);

    then(contaContabilGateway).should().count(contaContabil);
    then(lancamentoContabilStatsGateway).should().calculate(contaContabil);
  }

  @Test(expected = NotFoundException.class)
  public void testCreateLancamentoContabilStatsByContaContabilNotFound() {

    final long contaContabil = 123456L;

    given(contaContabilGateway.count(contaContabil)).willReturn(0L);

    lancamentoContabilStats.execute(contaContabil);

    then(contaContabilGateway).should().count(contaContabil);
    then(lancamentoContabilStatsGateway).shouldHaveZeroInteractions();
  }
}