package com.comexport.lancamentocontabil.controller;

import com.comexport.lancamentocontabil.controller.json.StatsJson;
import com.comexport.lancamentocontabil.converter.StatsJsonConverter;
import com.comexport.lancamentocontabil.usecase.LancamentoContabilStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(LancamentoContabilStatsController.ROOT_PATH)
public class LancamentoContabilStatsController {

  static final String ROOT_PATH = "/lancamentos-contabeis/_stats";

  private final LancamentoContabilStats lancamentoContabilStats;
  private final StatsJsonConverter jsonConverter;

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public StatsJson calculateStats(@RequestParam(name = "contaContabil", required = false) final Long contaContabil) {
    return jsonConverter.convert(lancamentoContabilStats.execute(contaContabil));
  }
}