package biz.belcorp.salesforce.modules.postulants.utils.algoritmos;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import biz.belcorp.salesforce.modules.postulants.core.domain.constant.EC;


public class UAlgorithms {

    private UAlgorithms() {
        throw new IllegalStateException();
    }

    public static Boolean validaDocumentoPA(String documento) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9][-A-Za-z0-9]+");
        Matcher matcher = pattern.matcher(documento);
        return matcher.matches();
    }

    public static boolean validarCedulaEC(String documento) {
        if (documento != null && documento.trim().length() > 0 && documento.length() == EC.CEDULA_MAX_LENGHT) {

            String digitos = documento.substring(0, 1);
            if (digitos.equals("3") || digitos.equals("8")) return true;

            digitos = documento.substring(0, 2);
            int digitosInt = Integer.parseInt(digitos);
            if (digitosInt < 1 || digitosInt > 24) return false;


            int factor = 2;
            int suma = 0;

            for (int i = 0; i < 9; i++) {
                digitos = documento.substring(i, (i + 1));
                int result = factor * Integer.parseInt(digitos);
                result = validateFloatingDocument(result);

                suma += result;
                factor = factor == 2 ? 1 : 2;
            }

            int resid = suma % 10;
            int digVerificador = 0;
            int digVerificadorCedula = Integer.parseInt(documento.substring(9, 10));

            digVerificador = validateDigitoVerificador(resid, digVerificador);

            return digVerificador == digVerificadorCedula;

        }
        return false;
    }

    private static int validateFloatingDocument(int result) {
        if (result >= 10) {
            int q = result / 10;
            int r = result % 10;
            result = q + r;
        }
        return result;
    }

    private static int validateDigitoVerificador(int resid, int digVerificador) {
        if (resid != 0) digVerificador = 10 - resid;
        return digVerificador;
    }
}
