package com.example.anuja.bakingapp.app.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * This class provides methods to add/replace a fragment
 */
public class FragmentUtils {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int containerId, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int containerId, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment, tag);
        fragmentTransaction.commit();
    }
}
