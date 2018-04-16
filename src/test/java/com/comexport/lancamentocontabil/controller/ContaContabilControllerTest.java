package com.comexport.lancamentocontabil.controller;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.comexport.lancamentocontabil.controller.json.ContaContabilJson;
import com.comexport.lancamentocontabil.entity.ContaContabil;
import com.comexport.lancamentocontabil.exception.DuplicateException;
import com.comexport.lancamentocontabil.template.ContaContabilTemplate;
import com.comexport.lancamentocontabil.usecase.CreateContaContabil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(ContaContabilController.class)
public class ContaContabilControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CreateContaContabil createContaContabil;

  @Before
  public void setup() {
    FixtureFactoryLoader.loadTemplates("com.comexport.lancamentocontabil.template");
  }

  @Test
  public void testCreateContaContabil() throws Exception {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);

    given(createContaContabil.execute(any(ContaContabil.class))).willReturn(contaContabil);

    final ContaContabilJson contaContabilJson = ContaContabilJson.builder()
        .numero(contaContabil.getNumero())
        .descricao(contaContabil.getDescricao())
        .build();

    mockMvc.perform(
        post(ContaContabilController.ROOT_PATH)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(contaContabilJson)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", equalTo(contaContabil.getId())));

    final ArgumentCaptor<ContaContabil> argumentCaptor = ArgumentCaptor.forClass(ContaContabil.class);

    then(createContaContabil).should().execute(argumentCaptor.capture());

    assertEquals(contaContabil.getNumero(), argumentCaptor.getValue().getNumero());
    assertEquals(contaContabil.getDescricao(), argumentCaptor.getValue().getDescricao());
    assertNull(argumentCaptor.getValue().getId());
  }

  @Test
  public void testCreateContaContabilConflict() throws Exception {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);

    given(createContaContabil.execute(any(ContaContabil.class))).willThrow(new DuplicateException(ContaContabil.COLLECTION));

    final ContaContabilJson contaContabilJson = ContaContabilJson.builder()
        .numero(contaContabil.getNumero())
        .descricao(contaContabil.getDescricao())
        .build();

    mockMvc.perform(
        post(ContaContabilController.ROOT_PATH)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(contaContabilJson)))
        .andDo(print())
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message", equalTo("Entidade já cadastrada: conta-contabil")));

    final ArgumentCaptor<ContaContabil> argumentCaptor = ArgumentCaptor.forClass(ContaContabil.class);

    then(createContaContabil).should().execute(argumentCaptor.capture());

    assertEquals(contaContabil.getNumero(), argumentCaptor.getValue().getNumero());
    assertEquals(contaContabil.getDescricao(), argumentCaptor.getValue().getDescricao());
    assertNull(argumentCaptor.getValue().getId());
  }

  @Test
  public void testCreateContaContabilBadRequest() throws Exception {

    final ContaContabil contaContabil = from(ContaContabil.class).gimme(ContaContabilTemplate.VALID);
    final ContaContabilJson contaContabilJson = ContaContabilJson.builder()
        .numero(contaContabil.getNumero())
        .build();

    mockMvc.perform(
        post(ContaContabilController.ROOT_PATH)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(contaContabilJson)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors", not(empty())));

    then(createContaContabil).shouldHaveZeroInteractions();
  }
}