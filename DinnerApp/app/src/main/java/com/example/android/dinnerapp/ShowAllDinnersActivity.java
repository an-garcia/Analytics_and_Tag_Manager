package com.example.android.dinnerapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * ShowAllDinnersActivity
 */
public class ShowAllDinnersActivity extends ListActivity {

    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_dinners);

        // Start timing how long it takes to displya the list of products.
        long startTime = System.nanoTime();


        // Get the array of all the dinners
        Dinner dinner = new Dinner();
        String [] allDinners = dinner.getAllDinners(this);

        // Create an array adapter
        /**
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
         android.R.layout.simple_list_item_1, allDinners);
         **/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.show_dinner_in_row, R.id.textview_dinner_row, allDinners);

        // Attach the ArrayAdapter to the list view
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        long stopTime = System.nanoTime();
        long timeElapsed = (stopTime - startTime) / 1000000;

        // report to analytics how long it took to display the list
        sendAnalyticsTimingHit(timeElapsed);
    }

    public void sendAnalyticsTimingHit(long time) {
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        // Send a screen view
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory("list all dinners")
                .setValue(time)
                .setLabel("display duration")
                .setVariable("duration")
                .build());
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Do something when a list item is clicked
        super.onListItemClick(l, v, position, id);

        String value = (String) getListView().getItemAtPosition(position);


        Utility.showMyToast("selected dinner is " + value, this);
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, value);

        startActivity(intent);
    }


}
