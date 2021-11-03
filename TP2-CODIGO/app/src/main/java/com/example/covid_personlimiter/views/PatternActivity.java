package com.example.covid_personlimiter.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.covid_personlimiter.R;
import com.example.covid_personlimiter.presenters.PatternPresenter;

import java.util.List;

public class PatternActivity extends AppCompatActivity implements PatternLockViewListener {
    private PatternLockView pattern;
    private PatternPresenter patternPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patternactivity);

        pattern = (PatternLockView) findViewById(R.id.pattern_lock_view);
        pattern.addPatternLockListener(this);

        //GetBatteryInfo
        patternPresenter = new PatternPresenter();
        patternPresenter.getBatteryInfo(this.getBaseContext());
    }

    @Override
    public void onStarted() {
    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {
    }

    @Override
    public void onComplete(List<PatternLockView.Dot> patternCompleted) {
        String patternCompletedString = PatternLockUtils.patternToString(pattern, patternCompleted);
        String desiredPatternString = this.getResources().getString(R.string.desired_pattern);
        if (patternCompletedString.equalsIgnoreCase(desiredPatternString)) {
            pattern.setViewMode(PatternLockView.PatternViewMode.CORRECT);
            Toast.makeText(this,"Patron Ingresado con Exito",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(PatternActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            pattern.setViewMode(PatternLockView.PatternViewMode.WRONG);
        }
    }

    @Override
    public void onStop() {
        pattern.clearPattern();
        super.onStop();
    }

    @Override
    public void onCleared() {
        Log.d("RESULT", "Pattern has been cleared");
    }
}
