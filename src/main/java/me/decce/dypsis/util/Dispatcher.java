package me.decce.dypsis.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;

public class Dispatcher {
    private static final ObjectArrayFIFOQueue<Runnable> queue = new ObjectArrayFIFOQueue<>();

    public static void invokeLater(Runnable runnable) {
        queue.enqueue(runnable);
    }

    public static void replayQueue() {
        while (!queue.isEmpty()) {
            queue.dequeue().run();
        }
    }
}
