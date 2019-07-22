package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract implementation for matchers that use event-specific matchers to match against a list of items.
 *
 * @author Allard Buijze
 * @since 1.1
 */
public abstract class ListMatcher<T> extends AbstractMatcher<List<T>> {

    private final List<Matcher<? super T>> failedMatchers = new ArrayList<>();
    private final Matcher<? super T>[] matchers;

    /**
     * Creates an abstract matcher to match a number of Matchers against Events contained inside a Collection.
     *
     * @param matchers The matchers to match the individual Events in the Collection
     */
    @SafeVarargs
    protected ListMatcher(Matcher<? super T>... matchers) {
        this.matchers = matchers;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(final List<T> items) {
        return List.class.isInstance(items) && matchesList(items);
    }

    /**
     * Evaluates the matcher for argument {@code item}. The item has been verified to be a list, but the exact
     * type of contents of a list cannot be verified, due to Erasure of Generic Types.
     *
     * @param item the object against which the matcher is evaluated.
     * @return {@code true} if {@code item} matches, otherwise {@code false}.
     * @see AbstractMatcher
     */
    protected abstract boolean matchesList(final List<T> item);

    /**
     * Matches all the remaining Matchers in the given {@code matcherIterator} against {@code null}.
     *
     * @param matcherIterator The iterator potentially containing more matchers
     * @return true if no matchers remain or all matchers succeeded
     */
    protected boolean matchRemainder(final Iterator<Matcher<? super T>> matcherIterator) {
        while (matcherIterator.hasNext()) {
            final Matcher<? super T> matcher = matcherIterator.next();
            if (!matcher.matches(null)) {
                this.failedMatchers.add(matcher);
                return false;
            }
        }
        return true;
    }

    /**
     * Report the given {@code matcher} as a failing matcher. This will be used in the error reporting.
     *
     * @param matcher The failing matcher.
     */
    protected void reportFailed(final Matcher<? super T> matcher) {
        this.failedMatchers.add(matcher);
    }

    /**
     * Returns a read-only list of Matchers, in the order they were provided in the constructor.
     *
     * @return a read-only list of Matchers, in the order they were provided in the constructor
     */
    protected List<Matcher<? super T>> getMatchers() {
        return Arrays.asList(this.matchers);
    }

    /**
     * Describes the type of collection expected. To be used in the sentence: "list with ... of: <description of
     * matchers>". E.g. "all" or "sequence".
     *
     * @param description the description to append the collection type to
     */
    protected abstract void describeCollectionType(final MatchDescription description);

    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText("list with ");
        describeCollectionType(description);
        description.appendText(" of: ");
        for (int t = 0; t < this.matchers.length; t++) {
            if (t != 0 && t < this.matchers.length - 1) {
                description.appendText(", ");
            } else if (t == this.matchers.length - 1 && t > 0) {
                description.appendText(" ");
                description.appendText(getLastSeparator());
                description.appendText(" ");
            }
//            description.appendText("<");
//            this.matchers[t].describeBy(null, description);
//            description.appendText(">");
            if (this.failedMatchers.contains(this.matchers[t])) {
                description.appendText(" (");
                description.appendText(this.failedMatcherMessage());
                description.appendText(")");
            }
        }
    }

    /**
     * The message to append behind a failing matcher. Defaults to FAILED!.
     *
     * @return The message to append behind a failing matcher
     */
    protected String failedMatcherMessage() {
        return "FAILED!";
    }

    /**
     * The separator to use between the two last events. Defaults to "and".
     *
     * @return The separator to use between the two last events. Defaults to "and".
     */
    protected String getLastSeparator() {
        return "and";
    }
}
