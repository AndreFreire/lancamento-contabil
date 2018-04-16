package com.comexport.lancamentocontabil.gateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.entity.Stats;
import com.comexport.lancamentocontabil.template.LancamentoContabilTemplate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
@ComponentScan("com.comexport.lancamentocontabil.gateway")
public class LancamentoContabilStatsGatewayTest {

  @Autowired
  private LancamentoContabilStatsGateway lancamentoContabilStatsGateway;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");

    mongoTemplate.findAllAndRemove(new Query(), LancamentoContabil.class);
  }

  @Test
  public void testCalculateStats() {

    final List<LancamentoContabil> lancamentosContabeis = Fixture.from(LancamentoContabil.class)
        .gimme(5, LancamentoContabilTemplate.RANDOM_VALUE);

    mongoTemplate.insert(lancamentosContabeis, LancamentoContabil.class);

    final Stats stats = lancamentoContabilStatsGateway.calculate();

    final List<Double> doubleList = lancamentosContabeis.stream().map(LancamentoContabil::getValor).collect(Collectors.toList());

    assertNotNull(stats);
    assertEquals(doubleList.stream().min(Double::compareTo).orElse(0.0), stats.getMin(), 0.1);
    assertEquals(doubleList.stream().max(Double::compareTo).orElse(0.0), stats.getMax(), 0.1);
    assertEquals(doubleList.stream().mapToDouble(Double::valueOf).average().orElse(0.0), stats.getMedia(), 0.1);
    assertEquals(doubleList.stream().mapToDouble(Double::valueOf).sum(), stats.getSoma(), 0.1);
    assertEquals(doubleList.size(), stats.getQtde());
  }

  @Test
  public void testCalculateStatsEmpty() {

    final Stats stats = lancamentoContabilStatsGateway.calculate();

    assertNotNull(stats);
    assertEquals(0.0, stats.getMin(), 0.0);
    assertEquals(0.0, stats.getMax(), 0.0);
    assertEquals(0.0, stats.getMedia(), 0.0);
    assertEquals(0.0, stats.getSoma(), 0.0);
    assertEquals(0, stats.getQtde());
  }

  @Test
  public void testCalculateStatsByContaContabil() {

    final List<LancamentoContabil> lancamentosContabeis = Fixture.from(LancamentoContabil.class)
        .gimme(5, LancamentoContabilTemplate.RANDOM_VALUE);

    final LancamentoContabil different = lancamentosContabeis.get(4);
    different.setContaContabil(12345L);

    mongoTemplate.insert(lancamentosContabeis, LancamentoContabil.class);

    final Stats stats = lancamentoContabilStatsGateway.calculate(lancamentosContabeis.get(0).getContaContabil());

    lancamentosContabeis.remove(different);

    final List<Double> doubleList = lancamentosContabeis.stream().map(LancamentoContabil::getValor).collect(Collectors.toList());

    assertNotNull(stats);
    assertEquals(doubleList.stream().min(Double::compareTo).orElse(0.0), stats.getMin(), 0.1);
    assertEquals(doubleList.stream().max(Double::compareTo).orElse(0.0), stats.getMax(), 0.1);
    assertEquals(doubleList.stream().mapToDouble(Double::valueOf).average().orElse(0.0), stats.getMedia(), 0.1);
    assertEquals(doubleList.stream().mapToDouble(Double::valueOf).sum(), stats.getSoma(), 0.1);
    assertEquals(doubleList.size(), stats.getQtde());
  }

  @Test
  public void testCalculateStatsByContaContabilEmpty() {

    final Stats stats = lancamentoContabilStatsGateway.calculate(12345L);

    assertNotNull(stats);
    assertEquals(0.0, stats.getMin(), 0.0);
    assertEquals(0.0, stats.getMax(), 0.0);
    assertEquals(0.0, stats.getMedia(), 0.0);
    assertEquals(0.0, stats.getSoma(), 0.0);
    assertEquals(0, stats.getQtde());
  }
}