package com.example.formi.mediasoftcalc.presentation.MainActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.formi.mediasoftcalc.other.utils.CalcUtils;
import com.example.formi.mediasoftcalc.domain.model.Calculation;
import com.example.formi.mediasoftcalc.data.db.DbHelper;
import com.example.formi.mediasoftcalc.R;
import com.example.formi.mediasoftcalc.presentation.StoryActivity.StoryActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btn0, btnResult,
    btnDel, btnDiv, btnMult, btnMinus, btnPlus;
    private TextView txtEntered, txtResult;
    private Button btnAllCalcs;

    private HorizontalScrollView mScrollView;

    private String entered = "";
    private String oper = "";
    private boolean isOper = false;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        initViews(); // Инициализация view-элементов

        btn0.setOnClickListener(onNumClick);
        btn1.setOnClickListener(onNumClick);
        btn2.setOnClickListener(onNumClick);
        btn3.setOnClickListener(onNumClick);
        btn4.setOnClickListener(onNumClick);
        btn5.setOnClickListener(onNumClick);
        btn6.setOnClickListener(onNumClick);
        btn7.setOnClickListener(onNumClick);
        btn8.setOnClickListener(onNumClick);
        btn9.setOnClickListener(onNumClick);
        btnDot.setOnClickListener(onNumClick);

        btnResult.setOnClickListener(onEqualClick);

        btnDel.setOnClickListener(onDeleteClick);

        btnDiv.setOnClickListener(onOperationClick);
        btnMult.setOnClickListener(onOperationClick);
        btnMinus.setOnClickListener(onOperationClick);
        btnPlus.setOnClickListener(onOperationClick);

        btnAllCalcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StoryActivity.class));
            }
        });
    }

    // Реализация слушателя при клике на "number-button"
    View.OnClickListener onNumClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if(CalcUtils.isAbleToEnter(entered, oper)) {
                switch (v.getId()) {
                    case R.id.btn0:
                        entered += "0";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn1:
                        entered += "1";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn2:
                        entered += "2";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn3:
                        entered += "3";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn4:
                        entered += "4";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn5:
                        entered += "5";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn6:
                        entered += "6";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn7:
                        entered += "7";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn8:
                        entered += "8";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btn9:
                        entered += "9";
                        txtEntered.setText(entered);
                        break;
                    case R.id.btnDot:
                        if (entered.equals("") || CalcUtils.isOperSymbol(entered.charAt(entered.length() - 1)) || entered.charAt(entered.length() - 1) == '.') {
                            break;
                        }
                        if (CalcUtils.isAbility(entered, oper)) {
                            entered += ".";
                            txtEntered.setText(entered);
                            break;
                        }
                        break;
                }
            }
            scrollToRight();
        }
    };

    // Реализация слушателя при клике на "operation-button"
    View.OnClickListener onOperationClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(entered.equals("")){
                return;
            }
            if(isOper || !oper.equals("")){
                return;
            }
            if(entered.charAt(entered.length()-1) == '.'){
                return;
            }

            switch (v.getId()){
                case R.id.btnDiv:
                    entered += "/";
                    txtEntered.setText(entered);
                    oper = "/";
                    isOper = true;
                    break;
                case R.id.btnMult:
                    entered += "*";
                    txtEntered.setText(entered);
                    oper = "*";
                    isOper = true;
                    break;
                case R.id.btnMinus:
                    entered += "-";
                    txtEntered.setText(entered);
                    oper = "-";
                    isOper = true;
                    break;
                case R.id.btnPlus:
                    entered += "+";
                    txtEntered.setText(entered);
                    oper = "+";
                    isOper = true;
                    break;
            }
            scrollToRight();
        }
    };

    // Реализация слушателя при клике на "="
    View.OnClickListener onEqualClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(!oper.equals("")){
                if(getResult(oper)) {
                    entered = "";
                    txtEntered.setText(entered);
                    oper = "";
                    isOper = false;
                }
            }
        }
    };

    // Реализация слушателя при клике на "DEL"
    View.OnClickListener onDeleteClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clear();
        }
    };

    // Вспомогательная функция, очищающее UI
    private void clear(){
        entered = "";
        txtEntered.setText(entered);
        txtResult.setText("0");
        oper = "";
        isOper = false;
    }

    // Функция, которая скролит horizontalScrollView вправо до упора
    private void scrollToRight(){
        mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0);
    }

    // Совершает непосредственный расчет и устанавливает значение в поле txtResult(TextView)
    private boolean getResult(String op){
        String[] splitedString = entered.split(Pattern.quote(op));

        if(splitedString.length > 2 || splitedString.length < 2){
            return false;
        }

        double val1 = Double.valueOf(splitedString[0]);
        double val2 = Double.valueOf(splitedString[1]);

        double result = CalcUtils.calculate(val1, val2, op);

        txtResult.setText(String.valueOf(result));

        dbHelper.addCalculation(new Calculation(entered, String.valueOf(result)));

        return true;
    }

    private void initViews(){
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnDot = findViewById(R.id.btnDot);
        btnResult = findViewById(R.id.btnResult);
        btnDel = findViewById(R.id.btnDelete);
        btnDiv = findViewById(R.id.btnDiv);
        btnMult = findViewById(R.id.btnMult);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);

        txtEntered = findViewById(R.id.txtEntered);
        txtResult = findViewById(R.id.txtResult);

        mScrollView = findViewById(R.id.horScrollView);

        btnAllCalcs = findViewById(R.id.btnAllCalcs);
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();

        dbHelper.deleteAllData();
    }*/

}
