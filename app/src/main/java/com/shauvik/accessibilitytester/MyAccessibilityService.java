package com.shauvik.accessibilitytester;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by shauvik on 11/23/15.
 */
public class MyAccessibilityService extends AccessibilityService {

    static String TAG = "MyAccessibilityService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.BIND_ACCESSIBILITY_SERVICE);
        //MainActivity.log("Service has permission=" + permissionCheck);

        final int eventType = event.getEventType();
        String eventText = "";
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Clicked: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused: ";
                break;
        }

        AccessibilityNodeInfo node = event.getSource();
        eventText = eventText + event.getContentDescription() +" -- "+ ((node==null)? "null" : node.getViewIdResourceName());
        MainActivity.log(eventText);

    }

    @Override
    public void onInterrupt() {

    }
}
