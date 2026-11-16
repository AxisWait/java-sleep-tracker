package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class TotalSessionsAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>("Всего сессий сна", (long) sessions.size());
    }
}
