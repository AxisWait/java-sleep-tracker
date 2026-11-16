package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class MinDurationAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        long min = sessions.stream()
                .mapToLong(SleepingSession::durationInMinutes)
                .min()
                .orElse(0);
        return new SleepAnalysisResult<>("Мин. длительность (мин)", min);
    }
}
