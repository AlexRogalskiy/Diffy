package com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.property.enumeration.PropertyType;
import com.wildbeeslabs.sensiblemetrics.diffy.property.impl.AbstractPropertyInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Parameter type {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyTypeMatcher<T extends AbstractPropertyInfo<?>> extends AbstractMatcher<T> {
    private final Matcher<? super PropertyType> matcher;

    public PropertyTypeMatcher(final Matcher<? super PropertyType> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(final T target) {
        return this.matcher.matches(target.getPropertyType());
    }
}
