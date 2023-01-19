package iterator;

import java.util.Iterator;

class Wrapper<A> implements RichIterator<A>{
    private final Iterator<A> itr;

    protected Wrapper(Iterator<A> itr) {
        this.itr = itr;
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public A next() {
        return itr.next();
    }


}
