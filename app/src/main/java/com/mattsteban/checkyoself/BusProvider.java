package com.mattsteban.checkyoself;

import android.os.Handler;

/**
 * Created by Esteban on 8/1/16.
 */

public class BusProvider {
    private static final MainThreadBus BUS = new MainThreadBus();

    public static MainThreadBus getInstance(){
        return BUS;
    }

    private BusProvider(){}

    public static Handler getHandler(){
        return BUS.getHandler();
    }
}