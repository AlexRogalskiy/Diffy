package com.wildbeeslabs.sensiblemetrics.diffy.common.utility.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Value object to represent a Version consisting of major, minor and bugfix part.
 *
 * @author Oliver Gierke
 */
public class Version implements Comparable<Version> {

    /**
     * Default parser error
     */
    private static final String VERSION_PARSE_ERROR = "Invalid version string! Could not parse segment %s within %s.";

    private final int major;
    private final int minor;
    private final int bugfix;
    private final int build;

    /**
     * Creates a new {@link Version} from the given integer values. At least one value has to be given but a maximum of 4.
     *
     * @param parts must not be {@literal null} or empty.
     */
    public Version(final int... parts) {
        ValidationUtils.notNull(parts, "Parts must not be null!");
        ValidationUtils.isTrue(parts.length > 0 && parts.length < 5, String.format("Invalid parts length. 0 < %s < 5", parts.length));

        this.major = parts[0];
        this.minor = parts.length > 1 ? parts[1] : 0;
        this.bugfix = parts.length > 2 ? parts[2] : 0;
        this.build = parts.length > 3 ? parts[3] : 0;

        ValidationUtils.isTrue(major >= 0, "Major version must be greater or equal zero!");
        ValidationUtils.isTrue(minor >= 0, "Minor version must be greater or equal zero!");
        ValidationUtils.isTrue(bugfix >= 0, "Bugfix version must be greater or equal zero!");
        ValidationUtils.isTrue(build >= 0, "Build version must be greater or equal zero!");
    }

    /**
     * Parses the given string representation of a version into a {@link Version} object.
     *
     * @param version must not be {@literal null} or empty.
     * @return returns version
     */
    public static Version parse(final String version) {
        ValidationUtils.isTrue(isNotBlank(version), "Version must not be null o empty!");

        final String[] parts = version.trim().split("\\.");
        int[] intParts = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            final String input = i == parts.length - 1 ? parts[i].replaceAll("\\D.*", "") : parts[i];
            if (isNotBlank(input)) {
                try {
                    intParts[i] = Integer.parseInt(input);
                } catch (IllegalArgumentException o_O) {
                    throw new IllegalArgumentException(String.format(VERSION_PARSE_ERROR, input, version), o_O);
                }
            }
        }
        return new Version(intParts);
    }

    /**
     * Returns whether the current {@link Version} is greater (newer) than the given one.
     *
     * @param version - candidate version
     * @return true or false based on version comparison
     */
    public boolean isGreaterThan(final Version version) {
        return this.compareTo(version) > 0;
    }

    /**
     * Returns whether the current {@link Version} is greater (newer) or the same as the given one.
     *
     * @param version - candidate version
     * @return true or false based on version comparison
     */
    public boolean isGreaterThanOrEqualTo(final Version version) {
        return this.compareTo(version) >= 0;
    }

    /**
     * Returns whether the current {@link Version} is the same as the given one.
     *
     * @param version - candidate version
     * @return true or false based on version comparison
     */
    public boolean is(final Version version) {
        return this.equals(version);
    }

    /**
     * Returns whether the current {@link Version} is less (older) than the given one.
     *
     * @param version - candidate version
     * @return true or false based on version comparison
     */
    public boolean isLessThan(final Version version) {
        return this.compareTo(version) < 0;
    }

    /**
     * Returns whether the current {@link Version} is less (older) or equal to the current one.
     *
     * @param version - candidate version
     * @return true or false based on version comparison
     */
    public boolean isLessThanOrEqualTo(final Version version) {
        return this.compareTo(version) <= 0;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Version that) {
        if (Objects.isNull(that)) {
            return 1;
        }
        if (this.major != that.major) {
            return this.major - that.major;
        }
        if (this.minor != that.minor) {
            return this.minor - that.minor;
        }
        if (this.bugfix != that.bugfix) {
            return this.bugfix - that.bugfix;
        }
        if (this.build != that.build) {
            return this.build - that.build;
        }
        return 0;
    }
}
