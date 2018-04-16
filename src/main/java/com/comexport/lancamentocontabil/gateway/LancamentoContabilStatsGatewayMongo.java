package com.comexport.lancamentocontabil.gateway;

import com.comexport.lancamentocontabil.entity.LancamentoContabil;
import com.comexport.lancamentocontabil.entity.Stats;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LancamentoContabilStatsGatewayMongo implements LancamentoContabilStatsGateway {

  private final MongoTemplate mongoTemplate;

  @Override
  public Stats calculate() {
    return getStats(null);
  }

  @Override
  public Stats calculate(final Long contaContabil) {
    return getStats(contaContabil);
  }

  private Stats getStats(final Long contaContabil) {

    final MatchOperation match;
    if (contaContabil == null) {
      match = Aggregation.match(new Criteria());
    } else {
      match = Aggregation.match(new Criteria("contaContabil").is(contaContabil));
    }

    final GroupOperation groupOperation = Aggregation.group()
        .count().as("qtde")
        .sum("valor").as("soma")
        .min("valor").as("min")
        .max("valor").as("max")
        .avg("valor").as("media");
    final ProjectionOperation projectionOperation = Aggregation.project("min", "max", "soma", "media", "qtde").andExclude("_id");
    final Aggregation aggregation = Aggregation.newAggregation(match, groupOperation, projectionOperation);

    //noinspection ConstantConditions
    return Optional.ofNullable(mongoTemplate.aggregate(aggregation, LancamentoContabil.class, Stats.class).getUniqueMappedResult())
        .orElse(new Stats());
  }
}