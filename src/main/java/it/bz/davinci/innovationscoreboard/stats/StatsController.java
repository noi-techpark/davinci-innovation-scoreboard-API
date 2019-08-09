package it.bz.davinci.innovationscoreboard.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class StatsController {

    private final StatsImporter statsService;

    @Autowired
    public StatsController(StatsImporter statsService) {
        this.statsService = statsService;
    }

    @PostMapping(value = "/upload/csv")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        statsService.uploadFile(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/admin";
    }

}
