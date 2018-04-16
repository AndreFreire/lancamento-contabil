package com.comexport.lancamentocontabil.controller;

import static com.comexport.lancamentocontabil.conf.json.LocalDateSerializer.LOCAL_DATE_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.controller.json.LancamentoContabilJson;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.exception.NotFoundException;
import com.comexport.lancamentocontabil.template.LancamentoContabilTemplate;
import com.comexport.lancamentocontabil.usecase.CreateLancamentoContabil;
import com.comexport.lancamentocontabil.usecase.LoadLancamentoContabil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LancamentoContabilController.class)
public class LancamentoContabilControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CreateLancamentoContabil createLancamentoContabil;

  @MockBean
  private LoadLancamentoContabil loadLancamentoContabil;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testCreateLancamentoContabil() throws Exception {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(createLancamentoContabil.execute(any(LancamentoContabil.class))).willReturn(lancamentoContabil);

    final LancamentoContabilJson lancamentoContabilJson = LancamentoContabilJson.builder()
        .contaContabil(lancamentoContabil.getContaContabil())
        .data(lancamentoContabil.getData())
        .valor(lancamentoContabil.getValor())
        .build();

    mockMvc.perform(
        post(LancamentoContabilController.ROOT_PATH)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(lancamentoContabilJson)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", equalTo(lancamentoContabil.getId())));

    final ArgumentCaptor<LancamentoContabil> argumentCaptor = ArgumentCaptor.forClass(LancamentoContabil.class);

    then(createLancamentoContabil).should().execute(argumentCaptor.capture());

    final LancamentoContabil value = argumentCaptor.getValue();
    assertNull(value.getId());
    assertEquals(lancamentoContabil.getContaContabil(), value.getContaContabil());
    assertEquals(lancamentoContabil.getData(), value.getData());
    assertEquals(lancamentoContabil.getValor(), value.getValor());
  }

  @Test
  public void testCreateLancamentoContabilNotFoundContaContabil() throws Exception {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(createLancamentoContabil.execute(any(LancamentoContabil.class))).willThrow(new NotFoundException(ContaContabil.COLLECTION));

    final LancamentoContabilJson lancamentoContabilJson = LancamentoContabilJson.builder()
        .contaContabil(lancamentoContabil.getContaContabil())
        .data(lancamentoContabil.getData())
        .valor(lancamentoContabil.getValor())
        .build();

    mockMvc.perform(
        post(LancamentoContabilController.ROOT_PATH)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(lancamentoContabilJson)))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", equalTo("Entidade não encontrada: conta-contabil")));

    final ArgumentCaptor<LancamentoContabil> argumentCaptor = ArgumentCaptor.forClass(LancamentoContabil.class);

    then(createLancamentoContabil).should().execute(argumentCaptor.capture());

    final LancamentoContabil value = argumentCaptor.getValue();
    assertNull(value.getId());
    assertEquals(lancamentoContabil.getContaContabil(), value.getContaContabil());
    assertEquals(lancamentoContabil.getData(), value.getData());
    assertEquals(lancamentoContabil.getValor(), value.getValor());
  }

  @Test
  public void testLoadLancamentoContabilById() throws Exception {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(loadLancamentoContabil.byId(lancamentoContabil.getId())).willReturn(lancamentoContabil);

    mockMvc.perform(
        get(LancamentoContabilController.ROOT_PATH + "/{id}", lancamentoContabil.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.contaContabil", equalTo(lancamentoContabil.getContaContabil().intValue())))
        .andExpect(jsonPath("$.data", equalTo(Integer.valueOf(lancamentoContabil.getData().format(ofPattern(LOCAL_DATE_PATTERN))))))
        .andExpect(jsonPath("$.valor", equalTo(lancamentoContabil.getValor())));
  }

  @Test
  public void testLoadLancamentoContabilByIdNotFound() throws Exception {

    final LancamentoContabil lancamentoContabil = Fixture.from(LancamentoContabil.class).gimme(LancamentoContabilTemplate.VALID);

    given(loadLancamentoContabil.byId(lancamentoContabil.getId())).willThrow(new NotFoundException(LancamentoContabil.COLLECTION));

    mockMvc.perform(
        get(LancamentoContabilController.ROOT_PATH + "/{id}", lancamentoContabil.getId()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", equalTo("Entidade não encontrada: lancamento-contabil")));
  }

  @Test
  public void testListLancamentoContabilByContaContabil() throws Exception {
    final List<LancamentoContabil> lancamentosContabeis = Fixture.from(LancamentoContabil.class)
        .gimme(3, LancamentoContabilTemplate.VALID);

    final Long contaContabil = lancamentosContabeis.get(0).getContaContabil();
    given(loadLancamentoContabil.byContaContabil(contaContabil)).willReturn(lancamentosContabeis);

    final int cc = contaContabil.intValue();
    mockMvc.perform(
        get(LancamentoContabilController.ROOT_PATH).param("contaContabil", contaContabil.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$..contaContabil", hasSize(3)))
        .andExpect(jsonPath("$..data", hasSize(3)))
        .andExpect(jsonPath("$..valor", hasSize(3)))
        .andExpect(jsonPath("$..contaContabil", contains(cc, cc, cc)));
  }

  @Test
  public void testListLancamentoContabilByContaContabilEmpty() throws Exception {

    final Long contaContabil = 12345L;
    given(loadLancamentoContabil.byContaContabil(contaContabil)).willReturn(new ArrayList<>());

    mockMvc.perform(
        get(LancamentoContabilController.ROOT_PATH)
            .param("contaContabil", contaContabil.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void testListLancamentoContabilByContaContabilNotFound() throws Exception {

    final Long contaContabil = 12345L;
    given(loadLancamentoContabil.byContaContabil(contaContabil)).willThrow(new NotFoundException(ContaContabil.COLLECTION));

    mockMvc.perform(
        get(LancamentoContabilController.ROOT_PATH)
            .param("contaContabil", contaContabil.toString()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", equalTo("Entidade não encontrada: conta-contabil")));
  }
}