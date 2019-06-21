/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.iface.State;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.iface.Transition;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.io.Serializable;
import java.util.*;

/**
 * <tt>Automaton</tt> state.
 *
 * @author Anders M&oslash;ller &lt;<a href="mailto:amoeller@cs.au.dk">amoeller@cs.au.dk</a>&gt;
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultState<T> implements Serializable, State<T, DefaultTransition<T>>, Comparable<DefaultState<T>> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6984105665088971911L;

    /**
     * Default static identifier
     */
    private static int next_id;

    private final List<DefaultTransition<T>> transitions = new ArrayList<>();
    private final int id;
    private int number;
    private boolean accept;
    /**
     * Default {@link Comparator}
     */
    private final Comparator<? super T> comparator;

    /**
     * Constructs a new state
     */
    public DefaultState() {
        this(null);
    }

    /**
     * Constructs a new state by input parameters
     */
    public DefaultState(final Comparator<? super T> comparator) {
        this.resetTransitions();
        this.comparator = Optional.ofNullable(comparator).orElseGet(ComparableComparator::getInstance);
        this.id = next_id++;
    }

    /**
     * Resets transition set
     */
    private void resetTransitions() {
        this.transitions.clear();
    }

    /**
     * Adds an outgoing transition.
     *
     * @param transition - initial input {@link DefaultTransition}
     */
    @Override
    public State<T, DefaultTransition<T>> add(final DefaultTransition<T> transition) {
        this.transitions.add(transition);
        return this;
    }

    @Override
    public State<T, DefaultTransition<T>> remove(final DefaultTransition<T> transition) {
        this.transitions.remove(transition);
        return this;
    }

    @Override
    public State<T, DefaultTransition<T>> transit(final T value) {
        return this.transitions
            .stream()
            .filter(t -> t.isPossible(value))
            .map(Transition::state)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(String.format("ERROR: transition {%s} not supported", value)));
    }

    @Override
    public boolean isFinal() {
        return !this.accept;
    }

    /**
     * Performs lookup in transitions, assuming determinism.
     *
     * @param c character to look up
     * @return destination state, null if no matching outgoing transition
     */
    public DefaultState step(final T c) {
        for (final DefaultTransition<T> t : this.transitions) {
            if (Objects.compare(t.getMin(), c, this.getComparator()) <= 0 && Objects.compare(c, t.getMax(), this.getComparator()) <= 0) {
                return t.getTo();
            }
        }
        return null;
    }

    /**
     * Performs lookup in transitions, allowing nondeterminism.
     *
     * @param c    character to look up
     * @param dest collection where destination states are stored
     */
    public void step(final T c, final Collection<DefaultState<T>> dest) {
        for (final DefaultTransition<T> t : this.transitions) {
            if (Objects.compare(t.getMin(), c, this.getComparator()) <= 0 && Objects.compare(c, t.getMax(), this.getComparator()) <= 0) {
                dest.add(t.getTo());
            }
        }
    }

    private void addEpsilon(final DefaultState<T> to) {
        if (to.accept) {
            this.accept = true;
        }
        for (final DefaultTransition<T> t : to.transitions) {
            this.transitions.add(t);
        }
    }

    /**
     * Returns transitions sorted by (min, reverse max, to) or (to, min, reverse max)
     */
    private DefaultTransition<T>[] getSortedTransitionArray(final boolean to_first) {
        final DefaultTransition<T>[] transitionArray = this.transitions.toArray(new DefaultTransition[this.transitions.size()]);
        Arrays.sort(transitionArray, new ComparatorUtils.TransitionComparator(to_first));
        return transitionArray;
    }

    /**
     * Returns sorted list of outgoing transitions.
     *
     * @param to_first if true, order by (to, min, reverse max); otherwise (min, reverse max, to)
     * @return transition list
     */
    public List<DefaultTransition<T>> getSortedTransitions(final boolean to_first) {
        return Arrays.asList(this.getSortedTransitionArray(to_first));
    }

    /**
     * Compares this object with the specified object for order.
     * States are ordered by the time of construction.
     */
    @Override
    public int compareTo(final DefaultState<T> state) {
        Objects.requireNonNull(state, "State should not be null");
        return state.id - this.id;
    }
}
