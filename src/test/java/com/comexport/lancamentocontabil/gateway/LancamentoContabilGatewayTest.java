package com.comexport.lancamentocontabil.gateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.template.LancamentoContabilTemplate;
import java.util.List;
import java.util.UUID;
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
public class LancamentoContabilGatewayTest {

  @Autowired
  private LancamentoContabilGateway lancamentoContabilGateway;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");

    mongoTemplate.findAllAndRemove(new Query(), LancamentoContabil.class);
  }

  @Test
  public void testCreateLancamentoContabil() {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);
    lancamentoContabil.setId(null);

    final LancamentoContabil created = lancamentoContabilGateway.create(lancamentoContabil);

    assertNotNull(created.getId());
  }

  @Test
  public void testCreateLancamentoContabilDuplicated() {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);
    lancamentoContabil.setId(null);

    mongoTemplate.save(lancamentoContabil);

    lancamentoContabil.setId(null);

    final LancamentoContabil created = lancamentoContabilGateway.create(lancamentoContabil);

    assertNotNull(created.getId());

    assertEquals(2, mongoTemplate.count(new Query(), LancamentoContabil.class));
  }

  @Test
  public void testFindLancamentoContabilById() {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);
    mongoTemplate.save(lancamentoContabil);

    assertEquals(lancamentoContabil, lancamentoContabilGateway.findById(lancamentoContabil.getId()).orElse(null));
  }

  @Test
  public void testFindLancamentoContabilByIdNotFound() {

    assertFalse(lancamentoContabilGateway.findById(UUID.randomUUID().toString()).isPresent());
  }

  @Test
  public void testFindLancamentoContabilByContaContabil() {

    final List<LancamentoContabil> lancamentosContabeis = Fixture.from(LancamentoContabil.class)
        .gimme(3, LancamentoContabilTemplate.VALID);
    final LancamentoContabil first = lancamentosContabeis.get(2);

    mongoTemplate.insert(lancamentosContabeis, LancamentoContabil.class);

    final List<LancamentoContabil> list = lancamentoContabilGateway
        .findByContaContabil(first.getContaContabil());

    assertEquals(lancamentosContabeis, list);
  }

  @Test
  public void testFindLancamentoContabilByContaContabilSortedByDate() {

    final List<LancamentoContabil> lancamentosContabeis = Fixture.from(LancamentoContabil.class)
        .gimme(3, LancamentoContabilTemplate.VALID);
    final LancamentoContabil first = lancamentosContabeis.get(2);
    first.setData(first.getData().minusDays(1));

    mongoTemplate.insert(lancamentosContabeis, LancamentoContabil.class);

    final List<LancamentoContabil> list = lancamentoContabilGateway
        .findByContaContabil(first.getContaContabil());

    assertEquals(first.getId(), list.get(0).getId());
  }

  @Test
  public void testFindLancamentoContabilByContaContabilEmpty() {

    final List<LancamentoContabil> list = lancamentoContabilGateway.findByContaContabil(12345L);

    assertEquals(0, list.size());
  }
}