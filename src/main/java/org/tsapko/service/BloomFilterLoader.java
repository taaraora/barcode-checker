package org.tsapko.service;

import orestes.bloomfilter.BloomFilter;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tsapko.dao.ShipmentRepository;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class BloomFilterLoader {

    private static Logger logger = LoggerFactory.getLogger(BloomFilterLoader.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private BloomFilter<String> filter;

    @Autowired
    private SearchFilter searchFilter;

    //PT2M13.858S - PT2M23.304S for 10M records
    @Async
    @Transactional(readOnly = true)
    public void loadBloomFilter() {
        Instant start = Instant.now();
        if(searchFilter.isInitialised()){
            logger.info("async BloomFilter loading not needed.");
            return;
        }
        logger.info("async BloomFilter loading started.");
        List<String> barcodes = shipmentRepository.loadAllBarcodes();
        logger.info("{} barcodes found in table shipment.", barcodes.size());
        filter.addAll(barcodes);
        logger.info("async BloomFilter loading finished!!!");
        searchFilter.setInitialised(true);
        Instant end = Instant.now();
        logger.info("async BloomFilter loaded in {}", Duration.between(start, end).toString());
    }

    //PT3M18.458S-PT3M22.529S for 10M records
    @Async
    public void loadBloomFilterStateless() {
        Instant start = Instant.now();
        if(searchFilter.isInitialised()){
            logger.info("async BloomFilter loading not needed.");
            return;
        }
        logger.info("async BloomFilter loading started.");
        ScrollableResults scrollableResults = null;
        long count = 0;
        try {
            StatelessSession statelessSession = entityManagerFactory.unwrap(SessionFactory.class).openStatelessSession();
            List<String> target  = statelessSession
                    .createSQLQuery("SELECT s.barcode from shipment s")
                    .setReadOnly(true).list();
//                    .scroll(ScrollMode.FORWARD_ONLY);
//            while (scrollableResults.next()){
//                filter.add((String) scrollableResults.get()[0]);
//                scrollableResults.
//                count++;
//            }


            filter.addAll(target);
            logger.info("async BloomFilter loading finished!!!");
        }catch (Exception e){
            logger.error("error while bloomfilter loading application works incorrect!!!! {}", e);
        }finally {
            logger.info("{} barcodes found in table shipment.", count);
            if (scrollableResults != null) {
                scrollableResults.close();
            }
        }
        searchFilter.setInitialised(true);
        Instant end = Instant.now();
        logger.info("async BloomFilter loaded in {}", Duration.between(start, end).toString());
    }

}
