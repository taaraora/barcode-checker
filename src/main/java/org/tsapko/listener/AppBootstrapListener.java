package org.tsapko.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.tsapko.service.BloomFilterLoader;

@Component
public class AppBootstrapListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    BloomFilterLoader bloomFilterLoader;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        bloomFilterLoader.loadBloomFilter();
    }
}
