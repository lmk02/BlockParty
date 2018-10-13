package de.leonkoth.blockparty.thread;

import lombok.Getter;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static de.leonkoth.blockparty.BlockParty.PLUGIN_NAME;

public class BlockPartyThreadFactory implements ThreadFactory {

    @Getter
    private static AtomicInteger lastThreadID = new AtomicInteger(0); // 0 = no threads yet

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(PLUGIN_NAME + " Task #" + lastThreadID.incrementAndGet());
        return thread;
    }

}
