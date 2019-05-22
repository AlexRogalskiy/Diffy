package com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums;

/**
 * Matcher mode type {@link Enum} to process malformed and unexpected data
 * <p>
 * 2 basic implementations are provided:
 * <ul>
 * <li>{@link #STRICT} return "true" on any occurrence</li>
 * <li>{@link #SILENT} ignores any problem</li>
 * </ul>
 */
public enum MatcherMode {

    STRICT {
        @Override
        public boolean isViolated(final Throwable throwable, final String description) {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    SILENT {
        @Override
        public boolean isViolated(final Throwable throwable, final String description) {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    };

    /**
     * Returns binary flag based on input parameters
     *
     * @param throwable   - initial input target error {@link Throwable}
     * @param description - initial input error description {@link String}
     * @return true - if error violates contract agreement, false - otherwise
     */
    public abstract boolean isViolated(final Throwable throwable, final String description);

    /**
     * Returns binary flag based on mode status
     *
     * @return true - if matcher mode is enabled, false - otherwise
     */
    public abstract boolean isEnabled();
}
