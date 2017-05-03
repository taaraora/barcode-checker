package org.tsapko.config;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tsapko.dao.ShipmentRepository;

@Configuration
public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    BloomFilter<String> createBloomFilter(@Value("${bloomfilter.size}") int size,
            @Value("${bloomfilter.fpp}") double fpp) {
        logger.info("bloomfilter.size={}, bloomfilter.fpp={}", size, fpp);
        BloomFilter<String> bf = new FilterBuilder(size, fpp).buildBloomFilter();

//        very slow initialization during App startup for 500M records
//        shipmentRepository.findAll().forEach(s -> bf.add(s.getBarcode()));

        long count = shipmentRepository.count();
        if(count > 0){
            logger.error("BARCODES WHICH ARE ALREADY IN DB ARE NOT PROCESSED BY BLOOM FILTER THIS CAUSE FALSE NEGATIVES!!!!");
            logger.error("PLEASE CLEAR Shipment TABLE before application startup");
//            throw new ApplicationContextException("Shipment table is not empty, there is " + count + " entries");
        }

        return bf;
    }

}
