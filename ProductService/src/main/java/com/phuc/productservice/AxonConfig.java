package com.phuc.productservice;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream stream = new XStream();
        stream.addPermission(AnyTypePermission.ANY);

        return stream;
    }
}
