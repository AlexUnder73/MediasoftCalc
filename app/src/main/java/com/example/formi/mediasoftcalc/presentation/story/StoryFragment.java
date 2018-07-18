package com.example.formi.mediasoftcalc.presentation.story;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.formi.mediasoftcalc.R;
import com.example.formi.mediasoftcalc.data.db.DbHelper;
import com.example.formi.mediasoftcalc.domain.model.Calculation;
import com.example.formi.mediasoftcalc.presentation.story.adapter.CalculationAdapter;

import java.util.List;

public class StoryFragment extends Fragment {

    private RecyclerView recView;
    private CalculationAdapter adapter;

    private List<Calculation> calculationList;

    private DbHelper dbHelper;

    private FloatingActionButton fab;
    private TextView txtNoCalculations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtNoCalculations = view.findViewById(R.id.txtNoCalc);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(onFabClickListener);

        dbHelper = new DbHelper(getContext());

        calculationList = dbHelper.getCalculationList();
        if(calculationList.size() == 0){
            fab.setVisibility(View.GONE);
            txtNoCalculations.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.VISIBLE);
            txtNoCalculations.setVisibility(View.GONE);
        }

        adapter = new CalculationAdapter(getContext(), calculationList);

        recView = view.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        recView.setAdapter(adapter);
    }

    View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        adapter.notifyItemRangeRemoved(0, size);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbHelper.getDataToLogs();
    }

    public void updateRecyclerView(Calculation calculation){
        calculationList.add(calculation);
        adapter.notifyDataSetChanged();
        txtNoCalculations.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }
}
