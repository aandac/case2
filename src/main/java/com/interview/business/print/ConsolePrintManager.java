package com.interview.business.print;

public class ConsolePrintManager implements PrintManager {


    @Override
    public void print(String printStr) {
        System.out.println(printStr);
    }
}
