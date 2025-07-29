package me.decce.dypsis.util;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class String2IntNoopMap extends Object2IntOpenHashMap<String> {
    public String2IntNoopMap() {
        super(0);
    }
    
    @Override
    public int put(String s, int o) {
        return defaultReturnValue();
    }

    @Override
    public int getInt(Object k) {
        return defaultReturnValue();
    }
}
