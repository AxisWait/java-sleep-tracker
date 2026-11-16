package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class MaxDurationAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        long max = sessions.stream()
                .mapToLong(SleepingSession::durationInMinutes)
                .max().orElse(0);
        return new SleepAnalysisResult<>("Макс. длительность (мин)", max);
    }
}
