package org.selenium.pom.utils;

import org.selenium.pom.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configlLoader;

    private ConfigLoader(){
        //mvn clean test -Denv=STAGE
        String env = System.getProperty("env", String.valueOf(EnvType.STAGE)); //second parameter is default env
        switch (EnvType.valueOf(env)){
            case STAGE:
                properties = PropertyUtils.propertyLoader("src/test/resources/stg_config.properties");
                break;
            case PRODUCTION:
                properties = PropertyUtils.propertyLoader("src/test/resources/prod_config.properties");
                break;
            default:
                throw new IllegalStateException("Invalid env type: " + env);
        }
    }

    public static ConfigLoader getInstance(){
        if(configlLoader == null){
            configlLoader = new ConfigLoader();
        }
        return configlLoader;
    }

    public String getBaseUrl(){
        String prop = properties.getProperty("baseUrl");
        if(prop != null)
            return prop;
        else throw new RuntimeException("property baseUrl is not specified in the prod_config.properties file");
    }

    public String getUsername(){
        String prop = properties.getProperty("username");
        if(prop != null)
            return prop;
        else throw new RuntimeException("property username is not specified in the prod_config.properties file");
    }

    public String getPassword(){
        String prop = properties.getProperty("password");
        if(prop != null)
            return prop;
        else throw new RuntimeException("property password is not specified in the prod_config.properties file");
    }

}
