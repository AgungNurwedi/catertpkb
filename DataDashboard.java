package com.tpkb.mascater;

/**
 * Created by Agung on 24-11-2016.
 */
public class DataDashboard {

    public String getData_Jml() {        return Data_Jml;    }
    public void setData_Jml(String data_Jml) {        Data_Jml = data_Jml;    }

    public String getData_Sts() {        return Data_Sts;    }
    public void setData_Sts(String data_Sts) {        Data_Sts = data_Sts;    }

    String Data_Sts      ;
    String Data_Jml      ;

    public DataDashboard( String data_STS, String data_JML )
    {

        this.Data_Sts = data_STS;
        this.Data_Jml = data_JML;

    }

}