package iterator;


import java.util.Optional;

/**
 * A buffered Iterator is an iterator that saves the first element.
 * @param <A>
 */
public interface BufferedIterator<A> extends RichIterator<A> {
    /**
     * Provides the first element of the iterator
     * WITHOUT effecting the next().
     * @return the first element
     */
    A head();

    /**
     * @return Optional.of(head) if exists, Optional.empty() otherwise.
     */
    Optional<A> headOptional();
}
