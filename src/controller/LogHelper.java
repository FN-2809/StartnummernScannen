package controller;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHelper {
    private static final String LOG_FILE = "log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public static void writeLog(String message) {
        try {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            
            String logEntry = "[" + timestamp + "] " + message;
            
            Path logPath = Path.of(System.getProperty("user.dir"), LOG_FILE);
            
            Files.writeString(
                    logPath,
                    logEntry + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
