package com.switchfully.digibooky.domain.security;

import java.util.List;

public enum Role {
    MEMBER(Feature.LEND_ITEM, Feature.GET_ALL_BOOKS), LIBRARIAN(Feature.CREATE_BOOK, Feature.UPDATE_BOOK, Feature.DELETE_BOOK, Feature.LENDITEM_FOR_MEMBER), ADMIN(Feature.CREATE_LIBRARIAN, Feature.GET_ALL_MEMBERS);

    private final List<Feature> features;

    Role(Feature... features) {
        this.features = List.of(features);
    }

    public  boolean hasFeature(Feature feature){
        return this.features.contains(feature);
    }
}
