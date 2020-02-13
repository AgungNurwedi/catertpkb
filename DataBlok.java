package com.tpkb.mascater;

/**
 * Created by Agung on 24-11-2016.
 */
public class DataBlok {
    String name;
    String kode;
    boolean box;

    public String getName() { return name; }
    public String getKode() {
        return kode;
    }
    public boolean isBox() {
        return box;
    }

    DataBlok(String _describe, String _kode, boolean _box) {
        name = _describe;
        kode = _kode;
        box = _box;
    }
}