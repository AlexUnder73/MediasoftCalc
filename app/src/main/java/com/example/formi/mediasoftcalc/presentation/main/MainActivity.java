package com.example.formi.mediasoftcalc.presentation.main;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.formi.mediasoftcalc.R;
import com.example.formi.mediasoftcalc.data.db.DbHelper;
import com.example.formi.mediasoftcalc.domain.model.Calculation;
import com.example.formi.mediasoftcalc.presentation.story.StoryFragment;

public class MainActivity extends AppCompatActivity  implements MainFragment.OnButtonStoryClickListener{

    private MainFragment mainFragment;
    private StoryFragment storyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_calc);
        storyFragment = (StoryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_story);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            findViewById(R.id.fragment_container_story).setVisibility(View.GONE);
        }
    }

    @Override
    public void onButtonClickListener() {
        switchFragmentVisibility(true);
    }

    @Override
    public void onEqualsClickListener(Calculation calculation) {
        storyFragment.updateRecyclerView(calculation);
    }

    @Override
    public void onBackPressed() {
        if(findViewById(R.id.fragment_container_calc).getVisibility() == View.VISIBLE){
            finish();
        }else{
            switchFragmentVisibility(false);
        }
    }

    private void switchFragmentVisibility(boolean flag){
        if(flag){
            findViewById(R.id.fragment_container_story).setVisibility(View.VISIBLE);
            findViewById(R.id.fragment_container_calc).setVisibility(View.GONE);
        }else{
            findViewById(R.id.fragment_container_story).setVisibility(View.GONE);
            findViewById(R.id.fragment_container_calc).setVisibility(View.VISIBLE);
        }
    }
}
