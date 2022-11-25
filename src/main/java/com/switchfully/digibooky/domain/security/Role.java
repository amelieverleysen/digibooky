package com.switchfully.digibooky.domain.security;

import java.util.List;

public enum Role {
    MEMBER(Feature.LEND_ITEM), LIBRARIAN(Feature.CREATE_BOOK, Feature.UPDATE_BOOK, Feature.DELETE_BOOK), ADMIN(Feature.CREATE_LIBRARIAN, Feature.GET_ALL_MEMBERS);

    private final List<Feature> features;

    Role(Feature... features) {
        this.features = List.of(features);
    }

    public  boolean hasFeature(Feature feature){
        return this.features.contains(feature);
    }
}
