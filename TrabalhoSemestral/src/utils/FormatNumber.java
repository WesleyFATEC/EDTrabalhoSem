package utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Formatter;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class FormatNumber {
	
	public static NumberFormatter formatint () { 
	NumberFormat numberFormat = NumberFormat.getIntegerInstance();
    NumberFormatter formatter = new NumberFormatter(numberFormat);
	formatter.setValueClass(Integer.class);
	formatter.setMinimum(0);
	formatter.setAllowsInvalid(false);
	return formatter;
	}
	
	public static NumberFormatter formatDouble () { 
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
        NumberFormatter formatter = new NumberFormatter(numberFormat);
		formatter.setValueClass(Double.class);
		formatter.setMinimum(0);
		formatter.setAllowsInvalid(false);
		return formatter;
		}
	
	
	public static MaskFormatter cnpjformat() { 
	
		MaskFormatter maskCnpj = null;
	    try {
			maskCnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    maskCnpj.setPlaceholderCharacter('_');
	    return maskCnpj;
	}
	
	public static MaskFormatter cpfformat() { 
		
		MaskFormatter maskCpf = null;
	    try {
			maskCpf = new MaskFormatter("###.###.###-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    maskCpf.setPlaceholderCharacter('_');
	    return maskCpf;
	}
	
public static MaskFormatter cepformat() { 
		
		MaskFormatter maskCep = null;
	    try {
			maskCep = new MaskFormatter("#####-###");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    maskCep.setPlaceholderCharacter('_');
	    return maskCep;
	}

public static MaskFormatter telformat() { 
	
	MaskFormatter maskTel = null;
    try {
    	maskTel = new MaskFormatter("(##)#####-####");
	} catch (ParseException e) {
		e.printStackTrace();
	}
    maskTel.setPlaceholderCharacter('_');
    return maskTel;
}

}
