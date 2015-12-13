package com.jifflenow.cis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.io.File;

@ConfigurationProperties(prefix="jifflenow")
public class AppProperties {

    @NotNull
    private String accountEmail;

    private File keyFile;

    private int poolSize;

    private int maxPoolSize;

    private int queueCapacity;

    private String applicationName;

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public File getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(File keyFile) {
        if(!keyFile.exists())
            throw new RuntimeException("Could not find file " + keyFile);
        this.keyFile = keyFile;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
