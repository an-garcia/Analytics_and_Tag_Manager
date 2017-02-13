/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.dinnerapp;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jocelyn on 12/10/14.
 */
public class Utility {

    public static void showMyToast (String toastText, Context appContext) {

        // Show a toast with tonights dinner
        // Context context = getApplicationContext();
        // CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(appContext, toastText, duration);
        toast.show();

    }

    public static String[] combine(String[] a, String[] b){
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static String[] combine(String[] a, String[] b, String[] c, String[] d){
        return combine(combine(a, b), combine(c, d));
    }

    //
    public static String getDinnerId (String dinner) {
        return dinner.substring(0,2);
    }


    /**
     * Google Analytics. Tracks a screen hit.
     * @param activity Activity
     * @param screen screen
     */
    public static void trackHit(Activity activity, String screen) {
        //Get tracker
        Tracker tracker = ((MyApplication) activity.getApplication()).getTracker();

        // Set screen name
        tracker.setScreenName(screen);

        // Send a screen view
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Google Analytics. Tracks an event.
     * @param activity Activity
     * @param category category
     * @param action action
     * @param label label
     */
    public static void trackEvent(Activity activity, String category, String action, String label){
        //Get tracker
        Tracker tracker = ((MyApplication) activity.getApplication()).getTracker();

        // Send a screen view
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }


    /**
     * Get a unique transaction id using the local time.
     * @param productId
     * @return
     */
    public static String getUniqueTransactionId (String productId) {
        return ("T-" + getCurrentTime() + productId );
    }

    // Gets the current time as a String
    public static String getCurrentTime () {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-M hh:mm:ss");
        return format.format(curDate);
    }


    /**
     * Tracks caught exceptions.
     * @param activity
     * @param description
     * @param fatal
     */
    public static void trackCaughtExceptions(Activity activity, String description, boolean fatal){
        //Get tracker
        Tracker tracker = ((MyApplication) activity.getApplication()).getTracker();

        // Send a screen view
        tracker.send(new HitBuilders.ExceptionBuilder()
                .setDescription(description)
                .setFatal(fatal)
                .build());
    }

}
