package com.gestor.utils.sanitizer.customFormatter;

import org.apache.commons.lang3.StringUtils;

public class CustomFormatter {

    public static String capitalizeNames(String str){
        str = str.toLowerCase();
        String[] words = str.split("\\s");
        String capitalizeWord = "";
        for( String w : words){
            if(StringUtils.isBlank(w)) continue;

            String first = w.substring(0,1);
            String afterfirst = w.substring(1);
            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizeWord.trim();
    }

    public static String capitilizeText(String str){
        String[] words = str.split("\\s");
        String capitalizeWord = "";
        for( String w : words){
            if(StringUtils.isBlank(w)) continue;

            w = w.trim();
            String first = w.substring(0,1);
            String afterfirst = w.substring(1);
            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizeWord.trim();
    }
}
