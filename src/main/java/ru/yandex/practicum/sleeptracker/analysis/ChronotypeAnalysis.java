package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisFunction;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        Map<Chronotype, Long> counts = sessions.stream()
                .filter(s -> {
                    // Игнорируем дневные сессии (начало после 12)
                    return s.sleepTime().getHour() >= 12 || s.sleepTime().getHour() < 6;
                })
                .map(s -> {
                    LocalTime sleep = s.sleepTime().toLocalTime();
                    LocalTime wake = s.wakeTime().toLocalTime();

                    if (sleep.isAfter(LocalTime.of(23, 0)) && wake.isAfter(LocalTime.of(9, 0))) {
                        return Chronotype.OWL;
                    } else if (sleep.isBefore(LocalTime.of(22, 0)) && wake.isBefore(LocalTime.of(7, 0))) {
                        return Chronotype.LARK;
                    } else {
                        return Chronotype.PIGEON;
                    }
                })
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        // Выбираем максимум, при равенстве — PIGEON
        Chronotype result = counts.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(Chronotype.PIGEON);

        return new SleepAnalysisResult<>("Хронотип", result);
    }
}