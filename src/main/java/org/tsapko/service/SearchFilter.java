package org.tsapko.service;

import orestes.bloomfilter.BloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchFilter {

    @Autowired
    private BloomFilter<String> filter;

    private boolean isInitialised = false;

    public boolean contains(String element){
        if (isInitialised) {
            return filter.contains(element);
        } else {
            return true;
        }
    }

    public boolean add(String element){
        return filter.add(element);
    }

    public boolean isInitialised() {
        return isInitialised;
    }

    public void setInitialised(boolean initialised) {
        isInitialised = initialised;
    }
}
