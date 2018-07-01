package com.example.formi.mediasoftcalc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.formi.mediasoftcalc.R;
import com.example.formi.mediasoftcalc.model.Calculation;

import java.util.List;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationAdapter.CalculationViewHolder> {

    private Context mCtx;
    private List<Calculation> calculationList;

    public CalculationAdapter(Context ctx, List<Calculation> calculationList){
        mCtx = ctx;
        this.calculationList = calculationList;
    }

    @NonNull
    @Override
    public CalculationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.calculation_item, parent, false);
        return new CalculationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculationViewHolder holder, int position) {
        Calculation currentCalculation = calculationList.get(position);

        holder.txtCurrentCalc.setText(currentCalculation.getCurrentCalc());
        holder.txtItemResult.setText(currentCalculation.getResult());
    }

    @Override
    public int getItemCount() {
        return calculationList.size();
    }

    public class CalculationViewHolder extends RecyclerView.ViewHolder{

        private TextView txtCurrentCalc, txtItemResult;

        public CalculationViewHolder(View itemView) {
            super(itemView);

            txtCurrentCalc = itemView.findViewById(R.id.txtCurrentCalc);
            txtItemResult = itemView.findViewById(R.id.txtItemResult);
        }
    }
}
