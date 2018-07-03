package com.example.formi.mediasoftcalc.presentation.StoryActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.formi.mediasoftcalc.domain.model.Calculation;
import com.example.formi.mediasoftcalc.presentation.StoryActivity.adapter.CalculationAdapter;
import com.example.formi.mediasoftcalc.data.db.DbHelper;
import com.example.formi.mediasoftcalc.R;

import java.util.List;

public class StoryActivity extends AppCompatActivity {

    private RecyclerView recView;
    private CalculationAdapter mAdapter;

    private List<Calculation> calculationList;

    private DbHelper dbHelper;

    private FloatingActionButton fab;
    private TextView txtNoCalculations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        txtNoCalculations = findViewById(R.id.txtNoCalc);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(onFabClickListener);

        dbHelper = new DbHelper(this);

        calculationList = dbHelper.getCalculationList();
        if(calculationList.size() == 0){
            fab.setVisibility(View.INVISIBLE);
            txtNoCalculations.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.VISIBLE);
            txtNoCalculations.setVisibility(View.INVISIBLE);
        }

        mAdapter = new CalculationAdapter(this, calculationList);

        recView = findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(this));

        recView.setAdapter(mAdapter);
    }

    View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(StoryActivity.this);
            builder.setTitle("Подтверждение действия")
                    .setMessage("Вы действительно хотите удалить всю историю вычислений?")
                    .setCancelable(false)
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbHelper.deleteAllData();
                            clear();
                            fab.setVisibility(View.INVISIBLE);
                            txtNoCalculations.setVisibility(View.VISIBLE);
                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };

    public void clear(){
        final int size = calculationList.size();
        calculationList.clear();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper.getDataToLogs();
    }
}
