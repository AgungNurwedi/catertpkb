package com.tpkb.mascater;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Rupiah {
    NumberFormat rupiahFormat = null;
             
    public String toRupiahFormat(String nominal) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
 
        String rupiah = "";
        rupiahFormat = NumberFormat.getCurrencyInstance();
       
        rupiah = new DecimalFormat("###,###",symbols).format(Double.parseDouble(nominal));
        
        return rupiah;
    }
    
    /* Reff 
     * https://itjbg.wordpress.com/2012/06/09/rupiah-currency-format-in-java/
     * http://www.felhie.blogspot.com/2014/12/membuat-format-currency-indonesia-rupiah.html
     * http://tipspengetahuan.com/java-format-angka-ke-bentuk-mata-uang-beginner-95.html
     * https://arbysan.wordpress.com/tag/indonesia-rupiah/
     * */
}
