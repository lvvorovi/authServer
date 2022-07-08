package com.trackerauth.AuthServer.config.app;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppValuesHolder {

    public Boolean isDevelopmentMode;

}