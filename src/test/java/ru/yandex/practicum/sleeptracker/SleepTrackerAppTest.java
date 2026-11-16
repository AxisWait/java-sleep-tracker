package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.ChronotypeAnalysis;
import ru.yandex.practicum.sleeptracker.analysis.MinDurationAnalysis;
import ru.yandex.practicum.sleeptracker.analysis.SleeplessNightsAnalysis;
import ru.yandex.practicum.sleeptracker.analysis.TotalSessionsAnalysis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private SleepingSession session(String start, String end, SleepQuality q) {
        return new SleepingSession(LocalDateTime.parse(start, fmt), LocalDateTime.parse(end, fmt), q);
    }

    @Test
    void testTotalSessions() {
        List<SleepingSession> sessions = List.of(
                session("01.10.25 22:15", "02.10.25 08:00", SleepQuality.GOOD),
                session("02.10.25 23:00", "03.10.25 08:00", SleepQuality.NORMAL)
        );
        TotalSessionsAnalysis func = new TotalSessionsAnalysis();
        assertEquals(2L, func.analyze(sessions).value());
    }

    @Test
    void testMinDuration() {
        List<SleepingSession> sessions = List.of(
                session("01.10.25 22:00", "02.10.25 06:00", SleepQuality.GOOD), // 480 мин
                session("03.10.25 23:00", "04.10.25 07:00", SleepQuality.BAD)   // 480 мин
        );
        MinDurationAnalysis func = new MinDurationAnalysis();
        assertEquals(480L, func.analyze(sessions).value());
    }

    @Test
    void testSleeplessNights() {
        // Период: 1-е, 2-е, 3-е, 4-е октября (4 ночи)
        List<SleepingSession> sessions = List.of(
                session("01.10.25 22:00", "02.10.25 08:00", SleepQuality.GOOD), // Ночь 1 (спал)
                // Ночь 2 (02->03) - пропущена (бессонная)
                session("03.10.25 23:00", "04.10.25 07:00", SleepQuality.NORMAL), // Ночь 3 (спал)
                session("04.10.25 14:00", "04.10.25 16:00", SleepQuality.NORMAL)  // Дневная (не в счет)
        );
        SleeplessNightsAnalysis func = new SleeplessNightsAnalysis();
        assertEquals(2L, func.analyze(sessions).value()); // Ночи 2 и 4
    }

    @Test
    void testChronotypeOwl() {
        List<SleepingSession> sessions = List.of(
                session("01.10.25 23:30", "02.10.25 09:30", SleepQuality.GOOD), // Сова
                session("02.10.25 23:45", "03.10.25 10:00", SleepQuality.GOOD)  // Сова
        );
        ChronotypeAnalysis func = new ChronotypeAnalysis();
        assertEquals(Chronotype.OWL, func.analyze(sessions).value());
    }
}