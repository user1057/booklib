package com.jhipster.booklib.utils;

public class Checker {
    public static boolean isbnValid(String isbn){
        if(isbn!=null && isbn.length()>0) {
            String strippedIsbn = isbn.replaceAll("[^\\d]", "");
            if(strippedIsbn.length()==13){
                int even = 0;
                int odd = 0;
                for (int i = 0; i < 6; i++) {
                    even += (int) strippedIsbn.charAt(2*i);
                    odd += ((int)strippedIsbn.charAt(2*i+1))*3;
                }
                int checkbit = 10 - (even+odd)%10;
                if(checkbit == (int)strippedIsbn.charAt(12)) return true;
            }
        }

        return false;
    }
}
