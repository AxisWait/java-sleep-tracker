package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class AvgDurationAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        double avg = sessions.stream()
                .mapToLong(SleepingSession::durationInMinutes)
                .average().orElse(0.0);
        return new SleepAnalysisResult<>("Средняя длительность (мин)", String.format("%.2f", avg));
    }
}
