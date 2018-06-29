package com.example.formi.mediasoftcalc;

import java.util.regex.Pattern;

public class CalcUtils {

    // Реализация функционала калькулятора
    public static double calculate(double a, double b, String operation){

        switch (operation){
            case "/":
                return a/b;
            case "*":
                return a*b;
            case "-":
                return a-b;
            case "+":
                return a+b;
            default:
                return -1;

        }
    }

    // Проверка - является ли символ оператором
    public static boolean isOperSymbol(char symbol){
        return symbol == '/' || symbol == '*' || symbol == '-' || symbol == '+';
    }

    // Можно ли ставить символ "." в текущем значении
    public static boolean isAbility(String str, String operator){
        if(!operator.equals("")) {
            String[] splitedString = str.split(Pattern.quote(operator));
            if(splitedString.length == 2){
                if(splitedString[1].contains(".")){
                    return false;
                }
                return true;
            }
            return false;
        }else{
            if(str.contains(".")) {
                return false;
            }
            return true;
        }
    }

    // Определяет, можно ли вводить символ (максимальное количество введенных символов в 1 значении - 15)
    public static boolean isAbleToEnter(String str, String operator){
        if(!operator.equals("")){
            if(!CalcUtils.isOperSymbol(str.charAt(str.length()-1))) {
                String[] splitedEntered = str.split(Pattern.quote(operator));

                if (splitedEntered.length != 2) {
                    return false;
                }

                String val = splitedEntered[1];

                if (val.length() > 15) {
                    return false;
                }

                return true;
            }else{
                return true;
            }
        }else{
            if(str.length() > 15){
                return false;
            }
            return true;
        }
    }


}
