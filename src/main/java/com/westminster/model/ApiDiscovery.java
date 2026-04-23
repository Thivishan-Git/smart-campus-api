package com.westminster.model;

import java.util.Map;

public class ApiDiscovery {

    private String name;
    private String version;
    private String contact;
    private Map<String, String> resources;

    public ApiDiscovery() {
    }

    public ApiDiscovery(String name, String version, String contact, Map<String, String> resources) {
        this.name = name;
        this.version = version;
        this.contact = contact;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getContact() {
        return contact;
    }

    public Map<String, String> getResources() {
        return resources;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setResources(Map<String, String> resources) {
        this.resources = resources;
    }
}