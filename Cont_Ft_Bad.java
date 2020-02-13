package com.tpkb.mascater;

/**
 * Created by Agung on 31-12-2016.
 */

public class Cont_Ft_Bad {
    String Bad_CD ;
    String Bad_Nm ;

    public Cont_Ft_Bad(String bad_CD, String bad_Nm) {
        Bad_CD = bad_CD;
        Bad_Nm = bad_Nm;
    }

    public String getBad_CD() {        return Bad_CD;    }
    public void setBad_CD(String bad_CD) {        Bad_CD = bad_CD;    }

    public String getBad_Nm() {        return Bad_Nm;    }
    public void setBad_Nm(String bad_Nm) {        Bad_Nm = bad_Nm;    }

}
