// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.csv;

import com.opencsv.exceptions.CsvException;
import it.bz.davinci.innovationscoreboard.stats.FileImportLogService;
import it.bz.davinci.innovationscoreboard.stats.dto.FileImportLogDto;
import it.bz.davinci.innovationscoreboard.stats.es.EsDao;
import it.bz.davinci.innovationscoreboard.stats.events.StatsCsvIndexedEvent;
import it.bz.davinci.innovationscoreboard.stats.mapper.CsvMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static it.bz.davinci.innovationscoreboard.stats.model.FileImport.Status.*;

@Slf4j
public class StatsCsvImporter<CSV, ES> {

    private final FileImportLogService fileImportLogService;
    private final EsDao<ES> esDao;
    private final StatsCsvParser<CSV> statsCsvParser;
    private final CsvMapper<CSV, ES> mapper;
    private final ApplicationEventPublisher publisher;

    public StatsCsvImporter(FileImportLogService fileImportLogService, EsDao<ES> esDao, Class<CSV> typeParameterClass, CsvMapper<CSV, ES> mapper, ApplicationEventPublisher publisher) {
        this.fileImportLogService = fileImportLogService;
        this.esDao = esDao;
        this.statsCsvParser = new StatsCsvParser<>(typeParameterClass);
        this.mapper = mapper;
        this.publisher = publisher;
    }

    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void importFile(String fileName, int fileImportId) {
        FileImportLogDto fileImportState = fileImportLogService.getById(fileImportId);

        try {
            File file = new File(fileName);
            final ParserResult<CSV> parserResult = statsCsvParser.parse(file);

            final List<CSV> data = parserResult.getData();
            final List<CsvException> exceptions = parserResult.getExceptions();

            if (data.isEmpty() && !exceptions.isEmpty()) {
                log.error("Failed to parse all rows for file: " + fileImportState.getSource());
                fileImportState.setStatus(PROCESSED_WITH_ERRORS);
                fileImportState.setLogs(parserResult.getExceptionLog());
            } else {
                boolean indexCleaned = esDao.cleanIndex();

                if (indexCleaned) {
                    final boolean indexed = esDao.bulkIndex(parserResult.getData().stream()
                            .map(mapper::toEs)
                            .collect(Collectors.toList()));
                    if (!indexed) {
                        fileImportState.setStatus(PROCESSED_WITH_ERRORS);
                        fileImportState.setLogs("Failed to index documents in ES");
                    } else if (parserResult.getExceptions().isEmpty()) {
                        fileImportState.setStatus(PROCESSED_WITH_SUCCESS);
                    } else {
                        fileImportState.setStatus(PROCESSED_WITH_WARNINGS);
                        fileImportState.setLogs(parserResult.getExceptionLog());
                    }
                } else {
                    log.error("Failed to clean index for file: " + fileImportState.getSource());
                    fileImportState.setStatus(PROCESSED_WITH_ERRORS);
                    fileImportState.setLogs("Failed to delete index for file: " + fileImportState.getSource());
                }
            }

        } catch (Exception e) {
            log.error("Failed to import stats for file: " + fileImportState.getSource(), e);
            fileImportState.setStatus(PROCESSED_WITH_ERRORS);
            fileImportState.setLogs("Failed to open temp file: " + fileImportState.getSource());
        }

        fileImportLogService.save(fileImportState);
        publisher.publishEvent(new StatsCsvIndexedEvent(fileImportState.getId(), fileName));
    }

}
