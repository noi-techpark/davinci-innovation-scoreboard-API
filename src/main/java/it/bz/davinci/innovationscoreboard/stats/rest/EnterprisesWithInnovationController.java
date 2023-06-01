// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.rest;

import it.bz.davinci.innovationscoreboard.stats.EnterprisesWithInnovationAggregator;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/statistics")
public class EnterprisesWithInnovationController {

    private final EnterprisesWithInnovationAggregator enterprisesWithInnovationAggregator;

    @Autowired
    public EnterprisesWithInnovationController(EnterprisesWithInnovationAggregator enterprisesWithInnovationAggregator) {
        this.enterprisesWithInnovationAggregator = enterprisesWithInnovationAggregator;
    }

    @GetMapping(value = "/enterprises-with-innovation-activities-divided-by-territory")
    public StatisticsResponseDto getEnterprisesWithInnovationActivitiesDividedByTerritory() {
        return enterprisesWithInnovationAggregator.getEnterprisesWithInnovationActivitiesDividedByTerritory();
    }

    @GetMapping(value = "/enterprises-with-innovation-activities-in-italy-divided-by-nace")
    public StatisticsResponseDto getEnterprisesWithInnovationActivitiesInItalyDividedByNACE() {
        return enterprisesWithInnovationAggregator.getEnterprisesWithInnovationActivitiesInItalyDividedByNACE();
    }

    @GetMapping(value = "/enterprises-that-have-introduced-product-or-process-innovations-divided-by-territory")
    public StatisticsResponseDto getEnterprisesThatHaveIntroducedProductOrProcessInnovationsDividedByTerritory() {
        return enterprisesWithInnovationAggregator.getEnterprisesThatHaveIntroducedProductOrProcessInnovationsDividedByTerritory();
    }

    @GetMapping(value = "/enterprises-that-have-introduced-product-or-process-innovations-in-italy-divided-by-nace")
    public StatisticsResponseDto getEnterprisesThatHaveIntroducedProductOrProcessInnovationsInItalyDividedByNace() {
        return enterprisesWithInnovationAggregator.getEnterprisesThatHaveIntroducedProductOrProcessInnovationsInItalyDividedByNace();
    }

    @GetMapping(value = "/innovation-expenditure-divided-by-territory")
    public StatisticsResponseDto getInnovationExpenditureDividedByTerritory() {
        return enterprisesWithInnovationAggregator.getInnovationExpenditureDividedByTerritory();
    }

    @GetMapping(value = "/innovation-expenditure-in-italy-divided-by-nace")
    public StatisticsResponseDto getInnovationExpenditureInItalyDividedByNace() {
        return enterprisesWithInnovationAggregator.getInnovationExpenditureInItalyDividedByNace();
    }

    @GetMapping(value = "/innovation-expenditure-per-number-of-persons-employed-divided-by-territory")
    public StatisticsResponseDto getInnovationExpenditurePerNumberOfPersonsEmployedDividedByTerritory() {
        return enterprisesWithInnovationAggregator.getInnovationExpenditurePerNumberOfPersonsEmployedDividedByTerritory();
    }

    @GetMapping(value = "/innovation-expenditure-per-number-of-persons-employed-in-italy-divided-by-nace")
    public StatisticsResponseDto getInnovationExpenditurePerNumberOfPersonsEmployedInItalyDividedByNace() {
        return enterprisesWithInnovationAggregator.getInnovationExpenditurePerNumberOfPersonsEmployedInItalyDividedByNace();
    }

}
