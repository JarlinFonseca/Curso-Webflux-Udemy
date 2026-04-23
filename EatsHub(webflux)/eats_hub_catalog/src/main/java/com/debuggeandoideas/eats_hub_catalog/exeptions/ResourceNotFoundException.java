package com.debuggeandoideas.eats_hub_catalog.exeptions;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
