package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class BadQualityCountAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.quality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult<>("Сессий с плохим качеством", count);
    }
}
