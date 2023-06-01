// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats;

import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporter;
import it.bz.davinci.innovationscoreboard.stats.csv.StatsCsvImporterFactory;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import it.bz.davinci.innovationscoreboard.utils.TempFileUtil;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@AllArgsConstructor
public class CsvStatsUploader {

    private final CsvStatsProcessor csvStatsProcessor;
    private final FileImportLogService fileImportLogService;

    public FileImportLogDto importFile(MultipartFile file) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), UTF_8))) {
            String csvHeader = bufferedReader.readLine();
            final String formattedHeader = getFormattedHeader(csvHeader);

            StatsType csvType = StatsType.findByCsvHeader(formattedHeader)
                    .orElseThrow(() -> new UnsupportedOperationException("Header is not supported: " + formattedHeader));

            String tempFilePath = TempFileUtil.saveMultipartFileToTempFile(file, "csv-stats-", ".csv");

            final FileImportLogDto fileImportStatus = FileImportLogDto.builder()
                    .importDate(LocalDateTime.now())
                    .source(file.getOriginalFilename())
                    .status(FileImport.Status.UPLOADED)
                    .type(csvType)
                    .build();

            final FileImportLogDto uploadedFile = fileImportLogService.save(fileImportStatus);

            csvStatsProcessor.process(tempFilePath, uploadedFile.getId(), csvType);
            return uploadedFile;
        }
    }

    @NotNull
    private String getFormattedHeader(String csvHeader) {
        if (Objects.isNull(csvHeader)) {
            throw new IllegalArgumentException("Header cannot be null");
        }

        return csvHeader.replaceAll("[\uFEFF-\uFFFF]", "").trim();
    }
}
