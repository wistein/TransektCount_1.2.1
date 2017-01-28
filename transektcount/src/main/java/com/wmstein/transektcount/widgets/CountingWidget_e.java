/*
 * Copyright (c) 2016. Wilhelm Stein, Bonn, Germany.
 */

package com.wmstein.transektcount.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmstein.transektcount.AutoFitText;
import com.wmstein.transektcount.R;
import com.wmstein.transektcount.database.Count;

/****************************************************
 * Interface for widget_counting_e.xml
 * Created by wmstein on 18.12.2016
 */
public class CountingWidget_e extends RelativeLayout
{
    public static String TAG = "transektcountCountingWidget_e";

    private TextView namef1e;
    private TextView namef2e;
    private TextView namef3e;
    private TextView namepe;
    private TextView namele;
    private TextView nameee;
    private AutoFitText countCountf1e; // external counters
    private AutoFitText countCountf2e;
    private AutoFitText countCountf3e;
    private AutoFitText countCountpe;
    private AutoFitText countCountle;
    private AutoFitText countCountee;

    public Count count;

    public CountingWidget_e(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_counting_e, this, true);
        namef1e = (TextView) findViewById(R.id.f1eName);
        namef2e = (TextView) findViewById(R.id.f2eName);
        namef3e = (TextView) findViewById(R.id.f3eName);
        namepe = (TextView) findViewById(R.id.peName);
        namele = (TextView) findViewById(R.id.leName);
        nameee = (TextView) findViewById(R.id.eeName);
        countCountf1e = (AutoFitText) findViewById(R.id.countCountf1e);
        countCountf2e = (AutoFitText) findViewById(R.id.countCountf2e);
        countCountf3e = (AutoFitText) findViewById(R.id.countCountf3e);
        countCountpe = (AutoFitText) findViewById(R.id.countCountpe);
        countCountle = (AutoFitText) findViewById(R.id.countCountle);
        countCountee = (AutoFitText) findViewById(R.id.countCountee);
    }

    public void setCounte(Count newcount)
    {
        count = newcount;

        namef1e.setText(getContext().getString(R.string.countImagomfHint));
        namef2e.setText(getContext().getString(R.string.countImagomHint));
        namef3e.setText(getContext().getString(R.string.countImagofHint));
        namepe.setText(getContext().getString(R.string.countPupaHint));
        namele.setText(getContext().getString(R.string.countLarvaHint));
        nameee.setText(getContext().getString(R.string.countOvoHint));
        countCountf1e.setText(String.valueOf(count.count_f1e));
        countCountf2e.setText(String.valueOf(count.count_f2e));
        countCountf3e.setText(String.valueOf(count.count_f3e));
        countCountpe.setText(String.valueOf(count.count_pe));
        countCountle.setText(String.valueOf(count.count_le));
        countCountee.setText(String.valueOf(count.count_ee));
        ImageButton countUpf1eButton = (ImageButton) findViewById(R.id.buttonUpf1e);
        countUpf1eButton.setTag(count.id);
        ImageButton countUpf2eButton = (ImageButton) findViewById(R.id.buttonUpf2e);
        countUpf2eButton.setTag(count.id);
        ImageButton countUpf3eButton = (ImageButton) findViewById(R.id.buttonUpf3e);
        countUpf3eButton.setTag(count.id);
        ImageButton countUppeButton = (ImageButton) findViewById(R.id.buttonUppe);
        countUppeButton.setTag(count.id);
        ImageButton countUpleButton = (ImageButton) findViewById(R.id.buttonUple);
        countUpleButton.setTag(count.id);
        ImageButton countUpeeButton = (ImageButton) findViewById(R.id.buttonUpee);
        countUpeeButton.setTag(count.id);
        ImageButton countDownf1eButton = (ImageButton) findViewById(R.id.buttonDownf1e);
        countDownf1eButton.setTag(count.id);
        ImageButton countDownf2eButton = (ImageButton) findViewById(R.id.buttonDownf2e);
        countDownf2eButton.setTag(count.id);
        ImageButton countDownf3eButton = (ImageButton) findViewById(R.id.buttonDownf3e);
        countDownf3eButton.setTag(count.id);
        ImageButton countDownpeButton = (ImageButton) findViewById(R.id.buttonDownpe);
        countDownpeButton.setTag(count.id);
        ImageButton countDownleButton = (ImageButton) findViewById(R.id.buttonDownle);
        countDownleButton.setTag(count.id);
        ImageButton countDowneeButton = (ImageButton) findViewById(R.id.buttonDownee);
        countDowneeButton.setTag(count.id);
    }

    // Count up/down and set value on screen
    public void countUpf1e()
    {
        count.increase_f1e();
        countCountf1e.setText(String.valueOf(count.count_f1e));
    }

    public void countDownf1e()
    {
        count.safe_decrease_f1e();
        countCountf1e.setText(String.valueOf(count.count_f1e));
    }

    public void countUpf2e()
    {
        count.increase_f2e();
        countCountf2e.setText(String.valueOf(count.count_f2e));
    }

    public void countDownf2e()
    {
        count.safe_decrease_f2e();
        countCountf2e.setText(String.valueOf(count.count_f2e));
    }

    public void countUpf3e()
    {
        count.increase_f3e();
        countCountf3e.setText(String.valueOf(count.count_f3e));
    }

    public void countDownf3e()
    {
        count.safe_decrease_f3e();
        countCountf3e.setText(String.valueOf(count.count_f3e));
    }

    public void countUppe()
    {
        count.increase_pe();
        countCountpe.setText(String.valueOf(count.count_pe));
    }

    public void countDownpe()
    {
        count.safe_decrease_pe();
        countCountpe.setText(String.valueOf(count.count_pe));
    }

    public void countUple()
    {
        count.increase_le();
        countCountle.setText(String.valueOf(count.count_le));
    }

    public void countDownle()
    {
        count.safe_decrease_le();
        countCountle.setText(String.valueOf(count.count_le));
    }

    public void countUpee()
    {
        count.increase_ee();
        countCountee.setText(String.valueOf(count.count_ee));
    }

    public void countDownee()
    {
        count.safe_decrease_ee();
        countCountee.setText(String.valueOf(count.count_ee));
    }

}