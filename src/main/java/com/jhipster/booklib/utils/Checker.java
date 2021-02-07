package com.jhipster.booklib.utils;

public class Checker {
    public static boolean isbnValid(String isbn){
        int even = 0;
        int odd = 0;

        if(isbn!=null && isbn.length()>0) {
            String strippedIsbn = stripIsbn(isbn);// isbn.replaceAll("[^\\d]", "");
            if(strippedIsbn.length()==13){
                for (int i = 0; i < 6; i++) {
                    even += Character.getNumericValue(strippedIsbn.charAt(2*i));
                    odd += (Character.getNumericValue((int)strippedIsbn.charAt(2*i+1)))*3;
                }
                int checkbit = 10 - (even+odd)%10;
                if(checkbit == Character.getNumericValue((int)strippedIsbn.charAt(12))) return true;
            }
        }

        return false;
    }

    public static String stripIsbn(String isbn){
        if(isbn!=null) {
            return isbn.replaceAll("[^\\d]", "");
        }
        return null;
    }
}
