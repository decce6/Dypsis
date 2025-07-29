package me.decce.dypsis.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class Int2ObjectNoopMap extends Int2ObjectOpenHashMap<Object> {
    public Int2ObjectNoopMap() {
        super(0);
    }

    @Override
    public Object put(int i, Object o) {
        return null;
    }

    @Override
    public Object get(Object k) {
        return null;
    }
}
