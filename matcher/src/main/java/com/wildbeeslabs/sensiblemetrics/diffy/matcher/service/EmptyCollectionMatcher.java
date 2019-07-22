package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;

import java.util.Collection;
import java.util.List;

/**
 * Matches any empty collection.
 *
 * @author Allard Buijze
 * @since 1.1
 */
public class EmptyCollectionMatcher<T> extends AbstractMatcher<List<T>> {

    private final String contentDescription;

    /**
     * Creates a matcher of a list of empty items. The name of the item type (in plural) is passed in the given
     * {@code contentDescription} and will be part of the description of this matcher.
     *
     * @param contentDescription The description of the content type of the collection
     */
    public EmptyCollectionMatcher(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    @Override
    public boolean matches(final List<T> item) {
        return item instanceof Collection && ((Collection) item).isEmpty();
    }

    /**
     * Describes current {@link AbstractBaseMatcher} by input parameters
     *
     * @param description - initial input {@link MatchDescription}
     */
    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText("no ");
        description.appendText(contentDescription);
    }
}
