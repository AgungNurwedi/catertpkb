package com.tpkb.mascater;

import com.google.gson.annotations.SerializedName;

/**
 * Created by keagu on 29/05/2018.
 */


public class ResponseApiModel {
    @SerializedName("kode")
    String kode;
    @SerializedName("pesan")
    String pesan;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
