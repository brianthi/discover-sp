package com.brianthi.spotify.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
public class SpotifySyncScheduler
{
    @Scheduled(fixedDelay = 10000)
    public void runSpotifySync()
    {

    }


}
