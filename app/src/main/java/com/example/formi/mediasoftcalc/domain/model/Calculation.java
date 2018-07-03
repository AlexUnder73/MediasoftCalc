package com.example.formi.mediasoftcalc.domain.model;

public class Calculation {

    private String currentCalc;
    private String result;

    public Calculation(){}

    public Calculation(String currentCalc, String result){
        this.currentCalc = currentCalc;
        this.result = result;
    }

    public String getCurrentCalc() {
        return currentCalc;
    }

    public void setCurrentCalc(String currentCalc) {
        this.currentCalc = currentCalc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
