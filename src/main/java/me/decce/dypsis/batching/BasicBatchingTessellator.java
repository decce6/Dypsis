package me.decce.dypsis.batching;

public class BasicBatchingTessellator extends BatchingTessellator {
    private static final BasicBatchingTessellator instance = new BasicBatchingTessellator();

    public BasicBatchingTessellator() {
        super();
        BatchingManager.register(this);
    }

    public static BasicBatchingTessellator instance() {
        return instance;
    }
}
