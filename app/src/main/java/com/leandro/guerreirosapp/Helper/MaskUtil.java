package com.leandro.guerreirosapp.Helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by leani on 05/01/2018.
 */

public class MaskUtil {

    public static final String CPFMask = "###.###.###-##";
    public static final String CNPJMask = "##.###.###/####-##";
    public static final String RGMask = "##.###.###-#";
    public static final String CELMask = "(##) #####-####";
    public static final String TELEFONEMask = "(##) ####-####";
    public static final String CEPMask = "#####-###";

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    private static String getDefaultMask(String str) {
        String defaultMask = CPFMask;
        if (str.length() == 14){
            defaultMask = CNPJMask;
        }
        return CPFMask;
    }

    public static TextWatcher insert(final EditText editText, final MaskType maskType) {
        return new TextWatcher() {

            boolean isUpdating;
            String oldValue = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = MaskUtil.unmask(s.toString());
                String mask;
                switch (maskType) {
                    case CPF:
                        mask = CPFMask;
                        break;
                    case CNPJ:
                        mask = CNPJMask;
                        break;
                    case RG:
                        mask = RGMask;
                        break;
                    case TELEFONE:
                        mask = TELEFONEMask;
                        break;
                    case CEP:
                        mask = CEPMask;
                        break;
                    case CELULAR:
                        mask = CELMask;
                        break;
                    default:
                        mask = getDefaultMask(value);
                        break;
                }

                String maskAux = "";
                if (isUpdating) {
                    oldValue = value;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && value.length() > oldValue.length()) || (m != '#' && value.length() < oldValue.length() && value.length() != i)) {
                        maskAux += m;
                        continue;
                    }

                    try {
                        maskAux += value.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(maskAux);
                editText.setSelection(maskAux.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }

    public static String addMask(final String textoAFormatar, final String mask){
        String formatado = "";
        int i = 0;

        for (char m : mask.toCharArray()) {
            if (m != '#') {
                formatado += m;
                continue;
            }

            try {
                formatado += textoAFormatar.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        if(formatado.contains("--")){
            formatado = formatado.replace("--", "-0");
        }
        return formatado;
    }
}



