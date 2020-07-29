package com.example.duresources;
import java.util.Random;
public class PasswordGenerator {
    private String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private String randomString;
    int length=8;
    Random rand = new Random();
    private char[] text = new char[length];
    public PasswordGenerator(){
        randomString="";
    }
    public String generate(){
        for(int i=0;i<this.length;i++)
        {
            text[i]=characters.charAt(rand.nextInt(characters.length()));
        }
        for(int j=0;j<length;j++){
            randomString+=text[j];
        }
        return randomString;
    }
}
