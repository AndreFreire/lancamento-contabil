package com.comexport.lancamentocontabil.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.comexport.lancamentocontabil.controller.json.ContaContabilJson;
import com.comexport.lancamentocontabil.controller.json.IdJson;
import com.comexport.lancamentocontabil.converter.ContaContabilConverter;
import com.comexport.lancamentocontabil.usecase.CreateContaContabil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ContaContabilController.ROOT_PATH)
public class ContaContabilController {

  static final String ROOT_PATH = "/contas-contabeis";

  private final CreateContaContabil createContaContabil;
  private final ContaContabilConverter converter;

  @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public IdJson create(@Valid @RequestBody final ContaContabilJson json) {
    return new IdJson(createContaContabil.execute(converter.convert(json)).getId());
  }
}