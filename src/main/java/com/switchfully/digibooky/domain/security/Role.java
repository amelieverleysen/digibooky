package com.switchfully.digibooky.domain.security;

import java.util.List;

public enum Role {
    MEMBER(), LIBRARIAN(), ADMIN(Feature.GET_ALL_MEMBERS);

    private List<Feature> features;

    Role(Feature... features) {
        this.features = List.of(features);
    }

    public  boolean hasFeature(Feature feature){
        return this.features.contains(feature);
    }
}
