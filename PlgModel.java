package com.tpkb.mascater;

/**
 * Created by Agung on 24-11-2016.
 */
public class PlgModel {
    String SEnama ;
    String SEalamat ;

    public PlgModel(String SEnama, String SEalamat ) {

        this.SEnama = SEnama;
        this.SEalamat = SEalamat;

    }


    public String getSEnama() {        return SEnama;    }
    public void setSEnama(String SEnama) {        this.SEnama = SEnama;    }

    public String getSEalamat() {        return SEalamat;    }
    public void setSEalamat(String SEalamat) {        this.SEalamat = SEalamat;    }

}