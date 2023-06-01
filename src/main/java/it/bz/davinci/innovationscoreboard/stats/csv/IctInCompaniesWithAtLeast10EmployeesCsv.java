// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IctInCompaniesWithAtLeast10EmployeesCsv {
    @CsvBindByName(column = "ITTER107")
    private String ITTER107;
    @CsvBindByName(column = "Territory")
    private String Territory;
    @CsvBindByName(column = "VARICT")
    private String VARICT;
    @CsvBindByName(column = "Data type")
    private String dataType;
    @CsvBindByName(column = "ATECO_2007")
    private String ATECO_2007;
    @CsvBindByName(column = "NACE 2007")
    private String NACE_2007;
    @CsvBindByName(column = "CLLVT")
    private String CLLVT;
    @CsvBindByName(column = "Size classes of persons employed")
    private String sizeClassesOfPersonsEmployed;
    @CsvBindByName(column = "TIME")
    private String TIME;
    @CsvBindByName(column = "Select time")
    private String selectTime;
    @CsvBindByName(column = "Value")
    private BigDecimal value;
    @CsvBindByName(column = "Flag Codes")
    private String flagCodes;
    @CsvBindByName(column = "Flags")
    private String flags;

}
