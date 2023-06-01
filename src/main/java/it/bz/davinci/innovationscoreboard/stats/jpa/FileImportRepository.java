// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.jpa;

import it.bz.davinci.innovationscoreboard.stats.model.FileImport;
import it.bz.davinci.innovationscoreboard.stats.model.StatsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileImportRepository extends JpaRepository<FileImport, Integer> {
    List<FileImport> findAllByType(StatsType type);
}
