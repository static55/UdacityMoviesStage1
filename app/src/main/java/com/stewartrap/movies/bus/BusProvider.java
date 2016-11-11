package com.stewartrap.movies.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusProvider {

    private static final MainThreadBus BUS = new MainThreadBus();

    public static MainThreadBus getInstance() { return BUS; }
}
