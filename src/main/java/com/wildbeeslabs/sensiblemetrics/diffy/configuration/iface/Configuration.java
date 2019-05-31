package com.wildbeeslabs.sensiblemetrics.diffy.configuration.iface;

import java.util.Collections;
import java.util.Map;

/**
 * Default configuration declaration
 */
public interface Configuration {

    /**
     * Default empty {@link Configuration} implementation
     */
    Configuration EMPTY_CONFIGURATION = new Configuration() {
        @Override
        public Map<String, String> getProperties() {
            return Collections.emptyMap();
        }

        @Override
        public Object getProperty(final String key) {
            return null;
        }

        @Override
        public void addProperty(final String key, final String value) {
        }

        @Override
        public void updateProperty(final String key, final String value) {
        }

        @Override
        public void clearProperty(final String key) {
        }
    };

    /**
     * Returns all Properties. Yes, this should have ideally returned a
     * <code>Properties</code>, but doing so will make this operation dissapear
     * from the JConsole.
     */
    public Map<String, String> getProperties();

    /**
     * Returns the current value of a property given a key
     *
     * @param key
     */
    public Object getProperty(final String key);

    /**
     * Adds a new property to the configuration
     *
     * @param key
     * @param value
     */
    public void addProperty(final String key, final String value);

    /**
     * Updates an existing property with the new value
     *
     * @param key
     * @param value
     */
    public void updateProperty(final String key, final String value);

    /**
     * Deletes the property identified by the passed in key
     *
     * @param key
     */
    public void clearProperty(final String key);

}
