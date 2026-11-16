package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SleeplessNightsAnalysis implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<?> analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) return new SleepAnalysisResult<>("Бессонных ночей", 0L);

        LocalDateTime first = sessions.get(0).sleepTime();
        LocalDateTime last = sessions.get(sessions.size() - 1).wakeTime();

        long totalNights = ChronoUnit.DAYS.between(
                first.toLocalDate(),
                last.toLocalDate()
        ) + 1;

        long nightsWithSleep = sessions.stream()
                .filter(s -> {
                    LocalDateTime sleep = s.sleepTime();
                    LocalDateTime wake = s.wakeTime();

                    LocalDateTime nightStart = sleep.toLocalDate().atStartOfDay();
                    LocalDateTime nightEnd = nightStart.plusHours(6);

                    if (sleep.getHour() >= 12) {
                        nightStart = nightStart.plusDays(1);
                        nightEnd = nightEnd.plusDays(1);
                    }

                    return sleep.isBefore(nightEnd) && wake.isAfter(nightStart);
                })
                .map(s -> {
                    LocalDateTime sleep = s.sleepTime();
                    if (sleep.getHour() < 6) {
                        return sleep.toLocalDate().minusDays(1);
                    }
                    return sleep.toLocalDate();
                })
                .distinct()
                .count();

        long sleepless = Math.max(0, totalNights - nightsWithSleep);
        return new SleepAnalysisResult<>("Бессонных ночей", sleepless);
    }
}
