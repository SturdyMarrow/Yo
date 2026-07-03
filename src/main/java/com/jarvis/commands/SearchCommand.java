package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Desktop;
import java.net.URLEncoder;

public class SearchCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SearchCommand.class);

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            return "Please specify a search query.";
        }

        try {
            String query = String.join(" ", args);
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String searchUrl = "https://www.google.com/search?q=" + encodedQuery;
            Desktop.getDesktop().browse(new java.net.URI(searchUrl));
            logger.info("Searching for: " + query);
            return "Searching for " + query + ".";
        } catch (Exception e) {
            logger.error("Error performing search", e);
            return "Error performing search: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public String getDescription() {
        return "Search the web for a query (e.g., 'search machine learning')";
    }
}
