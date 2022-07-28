package com.mycompany.organizationdemo.businessservices.configuration.ioc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/* the annotations drive this functionality */
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class MyIocXmlConfiguration {
}
