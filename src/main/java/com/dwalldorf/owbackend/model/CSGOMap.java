package com.dwalldorf.owbackend.model;

public enum CSGOMap {
    de_dust2("de_dust2"),
    de_cobblestone("de_cobblestone"),
    de_cache("de_cache"),
    de_overpass("de_overpass"),
    de_mirage("de_mirage"),
    de_nuke("de_nuke"),
    de_train("de_train"),

    de_inferno("de_inferno"),
    de_dust("de_dust"),
    cs_assault("cs_assault"),
    cs_militia("cs_militia"),
    cs_office("cs_office"),
    de_vertigo("de_vertigo"),
    cs_italy("cs_italy"),
    de_aztec("de_aztec");

    private final String name;

    CSGOMap(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
