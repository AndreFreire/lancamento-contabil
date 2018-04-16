package com.comexport.lancamentocontabil.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.comexport.lancamentocontabil.controller.json.IdJson;
import com.comexport.lancamentocontabil.controller.json.LancamentoContabilJson;
import com.comexport.lancamentocontabil.converter.LancamentoContabilConverter;
import com.comexport.lancamentocontabil.converter.LancamentoContabilJsonConverter;
import com.comexport.lancamentocontabil.usecase.CreateLancamentoContabil;
import com.comexport.lancamentocontabil.usecase.LoadLancamentoContabil;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(LancamentoContabilController.ROOT_PATH)
public class LancamentoContabilController {

  static final String ROOT_PATH = "/lancamentos-contabeis";
  private static final String CONTA_CONTABIL = "contaContabil";

  private final CreateLancamentoContabil createLancamentoContabil;
  private final LoadLancamentoContabil loadLancamentoContabil;
  private final LancamentoContabilConverter converter;
  private final LancamentoContabilJsonConverter jsonConverter;

  @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public IdJson create(@Valid @RequestBody final LancamentoContabilJson json) {
    return new IdJson(createLancamentoContabil.execute(converter.convert(json)).getId());
  }

  @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  public LancamentoContabilJson load(@PathVariable("id") final String lancamentoContabilId) {
    return jsonConverter.convert(loadLancamentoContabil.byId(lancamentoContabilId));
  }

  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE, params = CONTA_CONTABIL)
  public List<LancamentoContabilJson> listByContaContabil(@RequestParam(CONTA_CONTABIL) final Long contaContabil) {
    return loadLancamentoContabil.byContaContabil(contaContabil).stream()
        .map(jsonConverter::convert).collect(Collectors.toList());
  }
}