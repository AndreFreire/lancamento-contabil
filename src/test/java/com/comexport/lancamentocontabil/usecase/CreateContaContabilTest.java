package com.comexport.lancamentocontabil.usecase;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.exception.DuplicateException;
import com.comexport.lancamentocontabil.gateway.ContaContabilGateway;
import com.comexport.lancamentocontabil.template.ContaContabilTemplate;
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
public class CreateContaContabilTest {

  @Autowired
  private CreateContaContabil createContaContabil;

  @MockBean
  private ContaContabilGateway contaContabilGateway;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testCreateContaContabil() {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);

    given(contaContabilGateway.count(contaContabil.getNumero())).willReturn(0L);
    given(contaContabilGateway.create(contaContabil)).willReturn(contaContabil);

    final ContaContabil created = createContaContabil.execute(contaContabil);

    assertNotNull(created);
    assertThat(created.getId(), not(isEmptyString()));
    assertEquals(contaContabil.getNumero(), created.getNumero());
    assertEquals(contaContabil.getDescricao(), created.getDescricao());

    then(contaContabilGateway).should().count(contaContabil.getNumero());
    then(contaContabilGateway).should().create(contaContabil);
  }

  @Test(expected = DuplicateException.class)
  public void testCreateContaContabilConflict() {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);

    given(contaContabilGateway.count(contaContabil.getNumero())).willReturn(1L);

    createContaContabil.execute(contaContabil);

    then(contaContabilGateway).should().count(contaContabil.getNumero());
    then(contaContabilGateway).shouldHaveZeroInteractions();
  }
}