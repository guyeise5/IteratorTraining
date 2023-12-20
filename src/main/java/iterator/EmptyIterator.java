package iterator;

import java.util.stream.Stream;

public class EmptyIterator implements RichIterator {
    public static final EmptyIterator instance =  new EmptyIterator();
    private EmptyIterator() {

    }
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return Stream.empty().iterator().next();
    }
}
