package com.wmstein.transektcount;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmstein.transektcount.database.Head;
import com.wmstein.transektcount.database.HeadDataSource;
import com.wmstein.transektcount.database.Meta;
import com.wmstein.transektcount.database.MetaDataSource;
import com.wmstein.transektcount.widgets.EditHeadWidget;
import com.wmstein.transektcount.widgets.EditMetaWidget;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/*
 * EditMetaActivity collects meta info 
 * Created by wmstein on 31.03.2016.
 */
public class EditMetaActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private static String TAG = "transektcountEditMetaActivity";
    TransektCountApplication transektCount;

    Head head;
    Meta meta;
    
    private HeadDataSource headDataSource;
    private MetaDataSource metaDataSource;

    LinearLayout head_area;
    
    EditHeadWidget ehw;
    EditHeadWidget eiw;
    EditMetaWidget etw;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_head);

        head_area = (LinearLayout) findViewById(R.id.edit_head);
        
        transektCount = (TransektCountApplication) getApplication();

        ScrollView editHead_screen = (ScrollView) findViewById(R.id.editHeadScreen);
        editHead_screen.setBackground(transektCount.getBackground());
        try
        {
            getSupportActionBar().setTitle(getString(R.string.editHeadTitle));
        }
        catch (NullPointerException e)
        {
            Log.i(TAG, "NullPointerException: No head title!");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        
        //clear existing view
        head_area.removeAllViews();
        
        //setup data sources
        headDataSource = new HeadDataSource(this);
        headDataSource.open();
        metaDataSource = new MetaDataSource(this);
        metaDataSource.open();
        
        //load head and meta data
        head = headDataSource.getHead();
        meta = metaDataSource.getMeta();
        
        // display the editable head data
        ehw = new EditHeadWidget(this, null);
        ehw.setWidgetNo(getString(R.string.transectnumber));
        ehw.setWidgetNo1(head.transect_no);
        ehw.setWidgetName(getString(R.string.inspector));
        ehw.setWidgetName1(head.inspector_name);
        head_area.addView(ehw);

        // display the editable meta data
        etw = new EditMetaWidget(this, null);
        etw.setWidgetMeta1(getString(R.string.temperature));
        etw.setWidgetItem1(meta.temp);
        etw.setWidgetMeta2(getString(R.string.wind));
        etw.setWidgetItem2(meta.wind);
        etw.setWidgetMeta3(getString(R.string.clouds));
        etw.setWidgetItem3(meta.clouds);
        etw.setWidgetDate1(getString(R.string.date));
        etw.setWidgetDate2(meta.date);
        etw.setWidgetTime1(getString(R.string.starttm));
        etw.setWidgetItem4(meta.start_tm);
        etw.setWidgetTime2(getString(R.string.endtm));
        etw.setWidgetItem5(meta.end_tm);
        head_area.addView(etw);

        // check for focus
        String newTransectNo = head.transect_no;
        if (StringUtils.isNotEmpty(newTransectNo))
        {
            etw.requestFocus();
        }
        else
        {
            ehw.requestFocus();
        }

    }

    // getSDate()
    public void getSDate(View view)
    {
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
            .hideSoftInputFromWindow(view.getWindowToken(), 0);
        TextView sDate = (TextView) this.findViewById(R.id.widgetDate2);
        sDate.setText(getcurDate());
    }

    // getSTime()
    public void getSTime(View view)
    {
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
            .hideSoftInputFromWindow(view.getWindowToken(), 0);
        TextView sTime = (TextView) this.findViewById(R.id.widgetItem4);
        sTime.setText(getcurTime());
    }

    // getETime()
    public void getETime(View view)
    {
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
            .hideSoftInputFromWindow(view.getWindowToken(), 0);
        TextView eTime = (TextView) this.findViewById(R.id.widgetItem5);
        eTime.setText(getcurTime());
    }

    // Date for date
    // by wmstein
    public String getcurDate()
    {
        Date date = new Date();
        DateFormat dform;
        String lng = Locale.getDefault().toString().substring(0, 2);
        
        if (lng.equals("de"))
        {
            dform = new SimpleDateFormat("dd.MM.yyyy");
        }
        else
        {
            dform = new SimpleDateFormat("yyyy-MM-dd");
        }
        return dform.format(date);
    }

    // Date for start_tm and end_tm
    // by wmstein
    public String getcurTime()
    {
        Date date = new Date();
        DateFormat dform = new SimpleDateFormat("HH:mm");
        return dform.format(date);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // close the data sources
        headDataSource.close();
        metaDataSource.close();
    }

    /***************/
    public void saveAndExit(View view)
    {
        if (saveData())
            super.finish();
    }

    public boolean saveData()
    {
        // Save head data
        head.transect_no = ehw.getWidgetNo1();
        head.inspector_name = ehw.getWidgetName1();

        headDataSource.saveHead(head);

        // Save meta data
        meta.temp = etw.getWidgetItem1();
        if (meta.temp > 50 || meta.temp < -10)
        {
            Toast.makeText(this, getString(R.string.valTemp), Toast.LENGTH_SHORT).show();
            return false;
        }
        meta.wind = etw.getWidgetItem2();
        if (meta.wind > 4 || meta.wind < 0)
        {
            Toast.makeText(this, getString(R.string.valWind), Toast.LENGTH_SHORT).show();
            return false;
        }
        meta.clouds = etw.getWidgetItem3();
        if (meta.clouds > 100 || meta.clouds < 0)
        {
            Toast.makeText(this, getString(R.string.valClouds), Toast.LENGTH_SHORT).show();
            return false;
        }
        meta.date = etw.getWidgetDate2();
        meta.start_tm = etw.getWidgetItem4();
        meta.end_tm = etw.getWidgetItem5();

        metaDataSource.saveMeta(meta);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_meta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menuSaveExit)
        {
            if (saveData())
                super.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
    {
        ScrollView editHead_screen = (ScrollView) findViewById(R.id.editHeadScreen);
        editHead_screen.setBackground(null);
        editHead_screen.setBackground(transektCount.setBackground());
    }

}