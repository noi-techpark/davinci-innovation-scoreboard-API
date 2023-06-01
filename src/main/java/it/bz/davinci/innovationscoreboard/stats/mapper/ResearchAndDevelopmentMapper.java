// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.mapper;

import it.bz.davinci.innovationscoreboard.stats.csv.ResearchAndDevelopmentCsv;
import it.bz.davinci.innovationscoreboard.stats.es.ResearchAndDevelopmentEs;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ResearchAndDevelopmentMapper extends CsvMapper<ResearchAndDevelopmentCsv, ResearchAndDevelopmentEs> {

    ResearchAndDevelopmentMapper INSTANCE = Mappers.getMapper(ResearchAndDevelopmentMapper.class);

    @Mappings({})
    @Override
    ResearchAndDevelopmentEs toEs(ResearchAndDevelopmentCsv researchAndDevelopmentCsv);
}
