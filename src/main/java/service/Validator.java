package service;

public class Validator {


    public static boolean validateISBN(String isbn){
        if(isbn.length() != 13) return false;
        char[] a = isbn.toCharArray();
        int checkSum = 0;
        for(int i = 0; i < a.length - 1; i++){
            checkSum += i % 2 == 0 ? a[i] : 3*a[i];
        }
        return checkSum % 10 == a[a.length-1];
    }
}
