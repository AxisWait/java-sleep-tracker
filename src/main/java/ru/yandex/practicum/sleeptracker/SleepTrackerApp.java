package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analysis.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SleepTrackerApp {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    // Список всех функций
    private static final List<SleepAnalysisFunction> FUNCTIONS = List.of(
            new TotalSessionsAnalysis(),
            new MinDurationAnalysis(),
            new MaxDurationAnalysis(),
            new AvgDurationAnalysis(),
            new BadQualityCountAnalysis(),
            new SleeplessNightsAnalysis(),
            new ChronotypeAnalysis()
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу с логом сна.");
            return;
        }

        Path path = Path.of(args[0]);
        List<SleepingSession> sessions;

        try {
            sessions = Files.lines(path)
                    .map(SleepTrackerApp::parseLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        System.out.println("Анализ сна:");
        // Запуск всех функций
        FUNCTIONS.forEach(func -> {
            SleepAnalysisResult<?> result = func.analyze(sessions);
            System.out.println(result);
        });
    }

    private static Optional<SleepingSession> parseLine(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length < 3) return Optional.empty();

            LocalDateTime sleep = LocalDateTime.parse(parts[0].trim(), FORMATTER);
            LocalDateTime wake = LocalDateTime.parse(parts[1].trim(), FORMATTER);
            SleepQuality quality = SleepQuality.valueOf(parts[2].trim());

            return Optional.of(new SleepingSession(sleep, wake, quality));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}