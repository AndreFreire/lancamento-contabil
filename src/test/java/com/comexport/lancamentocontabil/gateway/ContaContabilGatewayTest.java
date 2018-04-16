package com.comexport.lancamentocontabil.gateway;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.exception.DuplicateException;
import com.comexport.lancamentocontabil.template.ContaContabilTemplate;
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
public class ContaContabilGatewayTest {

  @Autowired
  private ContaContabilGateway contaContabilGateway;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");

    mongoTemplate.findAllAndRemove(new Query(), ContaContabil.class);
  }

  @Test
  public void testCountContaContabil() {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);
    contaContabil.setId(null);

    mongoTemplate.save(contaContabil);

    assertEquals((Long) 1L, contaContabilGateway.count(contaContabil.getNumero()));
  }

  @Test
  public void testCountContaContabilEmpty() {
    assertEquals((Long) 0L, contaContabilGateway.count(123L));
  }

  @Test
  public void testCreateContaContabil() {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);
    contaContabil.setId(null);

    contaContabilGateway.create(contaContabil);

    assertNotNull(contaContabil.getId());
  }

  @Test(expected = DuplicateException.class)
  public void testCreateContaContabilConflict() {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);
    contaContabil.setId(null);

    mongoTemplate.save(contaContabil);

    contaContabil.setId(null);

    contaContabilGateway.create(contaContabil);
  }
}