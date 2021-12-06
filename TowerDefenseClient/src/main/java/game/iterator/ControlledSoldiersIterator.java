package game.iterator;

import game.entity.Soldier;
import game.net.ISubject;
import game.net.Session;
import java.util.*;

public class ControlledSoldiersIterator implements Iterable<Soldier> {

    private final List<ISubject> objects;
    private final int currentSize;

    public ControlledSoldiersIterator (Session session) {
        this.objects = new ArrayList<>(session.getObjects().values());
        this.currentSize = this.objects.size();
    }

    @Override
    public Iterator<Soldier> iterator() {
        return new Iterator<>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                boolean hasNext = false;
                for(int i = currentIndex; i < currentSize; i++) {
                    if (!(objects.get(i) instanceof Soldier soldier))
                        continue;

                    if (!soldier.isOurControlled())
                        continue;

                   hasNext = true;
                   currentIndex = i;
                   break;
                }
                return hasNext;
            }

            @Override
            public Soldier next() {
                return (Soldier)objects.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
