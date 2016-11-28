package com.dwalldorf.owbackend.model;

public enum CSGOMap {
    de_dust2("de_dust2"),
    DE_COBBLESTONE("de_cobblestone"),
    DE_CACHE("de_cache"),
    DE_OVERPASS("de_overpass"),
    de_mirage("de_mirage"),
    de_nuke("de_nuke"),
    DE_TRAIN("de_train"),

    DE_INFERNO("de_info"),
    DE_DUST("de_dust"),
    CS_ASSAULT("cs_assault"),
    CS_MILITIA("cs_militia"),
    CS_OFFICE("cs_office"),
    DE_VERTIGO("de_vertigo"),
    CS_ITALY("cs_italy"),
    DE_AZTEC("de_aztec");

    private final String name;

    CSGOMap(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
