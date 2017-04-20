package com.appsquabble.bigday.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MongoConfiguration {

	@Min(1)
	@Max(65535)
	@JsonProperty
	private int port = 27017;
	
	@NotEmpty
	@JsonProperty
	private String host = "localhost";
	
	@NotEmpty
	@JsonProperty
	private String databaseName = "bigday";

    public MongoConfiguration() {
    }

    public MongoConfiguration(int port, String host, String databaseName) {
    
        this.databaseName = databaseName;
        this.host = host;
        this.port = port;
    
    }

    
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.port;
        hash = 61 * hash + (this.host != null ? this.host.hashCode() : 0);
        hash = 61 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MongoConfiguration other = (MongoConfiguration) obj;
        if (this.port != other.port) {
            return false;
        }
        if ((this.host == null) ? (other.host != null) : !this.host.equals(other.host)) {
            return false;
        }
        if ((this.databaseName == null) ? (other.databaseName != null) : !this.databaseName.equals(other.databaseName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MongoConfiguration{" + "port=" + port + ", host=" + host + ", databaseName=" + databaseName + '}';
    }


	
	
	
}