package com.example.formi.mediasoftcalc.presentation.main;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.formi.mediasoftcalc.R;
import com.example.formi.mediasoftcalc.data.db.DbHelper;
import com.example.formi.mediasoftcalc.domain.model.Calculation;
import com.example.formi.mediasoftcalc.other.utils.CalcUtils;

import java.util.regex.Pattern;

public class MainFragment extends Fragment {

    private static final String KEY_ENTERED = "key_entered";
    private static final String KEY_RESULT = "key_result";
    private static final String KEY_OPERATION = "key_operation";

    public interface OnButtonStoryClickListener{
        void onButtonClickListener();
        void onEqualsClickListener(Calculation calculation);
    }

    private OnButtonStoryClickListener onButtonStoryClickListener;


    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btn0, btnResult,
            btnDel, btnDiv, btnMult, btnMinus, btnPlus;
    private TextView txtEntered, txtResult;
    private Button btnAllCalcs;

    private HorizontalScrollView scrollView;

    private double result;

    private String entered = "";
    private String oper = "";
    private boolean isOper = false;

    private DbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dbHelper = new DbHelper(getContext());

        initViews(view); // Инициализация view-элементов

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
                if (onButtonStoryClickListener != null) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        onButtonStoryClickListener.onButtonClickListener();
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onButtonStoryClickListener = (OnButtonStoryClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

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
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
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

        result = CalcUtils.calculate(val1, val2, op);

        txtResult.setText(String.valueOf(result));

        Calculation calculation = new Calculation(entered, String.valueOf(result));

        dbHelper.addCalculation(calculation);

        onButtonStoryClickListener.onEqualsClickListener(calculation);
        /*calculationList.add(cal);
        adadpter.notifyDataSetChanged();*/

        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_ENTERED, entered);
        outState.putDouble(KEY_RESULT, result);
        outState.putString(KEY_OPERATION, oper);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            entered = savedInstanceState.getString(KEY_ENTERED);
            result = savedInstanceState.getDouble(KEY_RESULT);
            oper = savedInstanceState.getString(KEY_OPERATION);

            txtEntered.setText(entered);
            txtResult.setText(String.valueOf(result));
        }
    }

    private void initViews(View view){
        btn0 = view.findViewById(R.id.btn0);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        btn7 = view.findViewById(R.id.btn7);
        btn8 = view.findViewById(R.id.btn8);
        btn9 = view.findViewById(R.id.btn9);

        btnDot = view.findViewById(R.id.btnDot);
        btnResult = view.findViewById(R.id.btnResult);
        btnDel = view.findViewById(R.id.btnDelete);
        btnDiv = view.findViewById(R.id.btnDiv);
        btnMult = view.findViewById(R.id.btnMult);
        btnMinus = view.findViewById(R.id.btnMinus);
        btnPlus = view.findViewById(R.id.btnPlus);

        txtEntered = view.findViewById(R.id.txtEntered);
        txtResult = view.findViewById(R.id.txtResult);

        scrollView = view.findViewById(R.id.horScrollView);

        btnAllCalcs = view.findViewById(R.id.btnAllCalcs);
    }

}
