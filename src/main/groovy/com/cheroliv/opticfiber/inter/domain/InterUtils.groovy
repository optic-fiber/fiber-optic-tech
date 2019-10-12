package com.cheroliv.opticfiber.inter.domain


import groovy.transform.CompileStatic

import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle

@CompileStatic
class InterUtils {



    static LocalTime parseStringHeureToLocalTime(String strHeure) {
        LocalTime.of(Integer.parseInt("${strHeure.charAt(0)}${strHeure.charAt(1)}"), 0)
    }


    static String convertNombreEnMois(Integer mois) throws NumberFormatException {
        assert mois > 0 && mois < 13: "mois doit etre entre 1 et 12"
        switch (mois) {
            case 1: return Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 2: return Month.FEBRUARY.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 3: return Month.MARCH.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 4: return Month.APRIL.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 5: return Month.MAY.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 6: return Month.JUNE.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 7: return Month.JULY.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 8: return Month.AUGUST.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 9: return Month.SEPTEMBER.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 10: return Month.OCTOBER.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 11: return Month.NOVEMBER.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            case 12: return Month.DECEMBER.getDisplayName(TextStyle.FULL, Locale.FRANCE)
            default: throw new IllegalArgumentException("mauvais mois dans la base")
        }
    }


    static LocalDate parseStringDateToLocalDate(String strDate) {
        LocalDate.parse(strDate,
                DateTimeFormatter
                        .ofPattern("yyyy-MM-dd"))
    }

    static Integer timeStringToInteger(String strHeure) {
        Integer.parseInt "${strHeure.charAt(0)}${strHeure.charAt(1)}"
    }
}