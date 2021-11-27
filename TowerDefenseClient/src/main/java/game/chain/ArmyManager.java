package game.chain;

import game.chain.handlers.AddSubjectHandler;
import game.chain.handlers.Handler;
import game.chain.handlers.MoveSoldierHandler;
import game.chain.handlers.RemoveSubjectHandler;
import game.chain.requests.Request;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ArmyManager {
    private static ArmyManager instance;

    public static synchronized ArmyManager getInstance() {
        if (instance == null)
            instance = new ArmyManager();
        return instance;
    }

    private ReentrantLock queueMutex = new ReentrantLock();
    private ReentrantLock objectsMutex = new ReentrantLock();

    private Handler handler;
    private final Queue<Request> requests = new LinkedList<>();

    private ArmyManager() {
        handler = new AddSubjectHandler();
        handler.setNext(new RemoveSubjectHandler())
                .setNext(new MoveSoldierHandler());
    }

    public void lock() {
        objectsMutex.lock();
    }

    public void unlock() {
        objectsMutex.unlock();
    }

    public void add(Request request) {
        queueMutex.lock();
        this.requests.add(request);
        queueMutex.unlock();
    }

    public void handle() {
        objectsMutex.lock();
        queueMutex.lock();
        while (true) {
            Request request = this.requests.poll();
            if (request == null)
                break;
            handler.handle(request);
        }
        queueMutex.unlock();
        objectsMutex.unlock();
    }
}
