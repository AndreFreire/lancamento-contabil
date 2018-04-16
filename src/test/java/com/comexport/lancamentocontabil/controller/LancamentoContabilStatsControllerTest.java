package com.comexport.lancamentocontabil.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.entity.Stats;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.template.StatsTemplate;
import com.comexport.lancamentocontabil.usecase.LancamentoContabilStats;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LancamentoContabilStatsController.class)
public class LancamentoContabilStatsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LancamentoContabilStats lancamentoContabilStats;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testCalculateLancamentoContabilStats() throws Exception {

    final Stats stats = Fixture.from(Stats.class).gimme(StatsTemplate.VALID);

    given(lancamentoContabilStats.execute(null)).willReturn(stats);

    mockMvc.perform(
        get(LancamentoContabilStatsController.ROOT_PATH))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.soma", equalTo(stats.getSoma())))
        .andExpect(jsonPath("$.min", equalTo(stats.getMin())))
        .andExpect(jsonPath("$.max", equalTo(stats.getMax())))
        .andExpect(jsonPath("$.media", equalTo(stats.getMedia())))
        .andExpect(jsonPath("$.qtde", equalTo(stats.getQtde())));

    then(lancamentoContabilStats).should().execute(null);
  }

  @Test
  public void testCalculateLancamentoContabilStatsEmpty() throws Exception {

    given(lancamentoContabilStats.execute(null)).willReturn(new Stats());

    mockMvc.perform(
        get(LancamentoContabilStatsController.ROOT_PATH))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.soma", equalTo(0.0)))
        .andExpect(jsonPath("$.min", equalTo(0.0)))
        .andExpect(jsonPath("$.max", equalTo(0.0)))
        .andExpect(jsonPath("$.media", equalTo(0.0)))
        .andExpect(jsonPath("$.qtde", equalTo(0)));

    then(lancamentoContabilStats).should().execute(null);
  }

  @Test
  public void testCalculateLancamentoContabilStatsByContaContabil() throws Exception {

    final Stats stats = Fixture.from(Stats.class).gimme(StatsTemplate.VALID);

    final Long contaContabil = 12345L;
    given(lancamentoContabilStats.execute(contaContabil)).willReturn(stats);

    mockMvc.perform(
        get(LancamentoContabilStatsController.ROOT_PATH)
            .param("contaContabil", contaContabil.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.soma", equalTo(stats.getSoma())))
        .andExpect(jsonPath("$.min", equalTo(stats.getMin())))
        .andExpect(jsonPath("$.max", equalTo(stats.getMax())))
        .andExpect(jsonPath("$.media", equalTo(stats.getMedia())))
        .andExpect(jsonPath("$.qtde", equalTo(stats.getQtde())));

    then(lancamentoContabilStats).should().execute(contaContabil);
  }

  @Test
  public void testCalculateLancamentoContabilStatsByContaContabilEmpty() throws Exception {

    final Long contaContabil = 12345L;
    given(lancamentoContabilStats.execute(contaContabil)).willReturn(new Stats());

    mockMvc.perform(
        get(LancamentoContabilStatsController.ROOT_PATH)
            .param("contaContabil", contaContabil.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.soma", equalTo(0.0)))
        .andExpect(jsonPath("$.min", equalTo(0.0)))
        .andExpect(jsonPath("$.max", equalTo(0.0)))
        .andExpect(jsonPath("$.media", equalTo(0.0)))
        .andExpect(jsonPath("$.qtde", equalTo(0)));

    then(lancamentoContabilStats).should().execute(contaContabil);
  }

  @Test
  public void testCalculateLancamentoContabilStatsByContaContabilNotFound() throws Exception {

    final Long contaContabil = 12345L;
    given(lancamentoContabilStats.execute(contaContabil)).willThrow(new NotFoundException(ContaContabil.COLLECTION));

    mockMvc.perform(
        get(LancamentoContabilStatsController.ROOT_PATH)
            .param("contaContabil", contaContabil.toString()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", equalTo("Entidade não encontrada: conta-contabil")));

    then(lancamentoContabilStats).should().execute(contaContabil);
  }
}