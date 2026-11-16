package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public record SleepingSession(LocalDateTime sleepTime, LocalDateTime wakeTime, SleepQuality quality) {
    public long durationInMinutes() {
        return java.time.Duration.between(sleepTime, wakeTime).toMinutes();
    }
}
