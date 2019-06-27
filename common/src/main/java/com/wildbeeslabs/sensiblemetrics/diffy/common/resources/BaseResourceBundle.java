/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.resources;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Base {@link ListResourceBundle} implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BaseResourceBundle extends ListResourceBundle {
    /**
     * Default bundle source {@link String}
     */
    protected static final String DEFAULT_BUNDLE_SOURCE = "i18n";
    /**
     * Default bundle source {@link String}
     */
    protected static final String DEFAULT_BUNDLE_NAME = "messages";
    /**
     * Default bundle {@link Locale}
     */
    protected static final Locale DEFAULT_BUNDLE_LOCALE = Locale.ENGLISH;
    /**
     * Default bundle {@link Control}
     */
    protected static final Control DEFAULT_BUNDLE_CONTROL = ResourceBundle.Control.getControl(Control.FORMAT_PROPERTIES);

    /**
     * Default bundle {@link String}
     */
    protected final String source;
    /**
     * Default bundle {@link Locale}
     */
    protected final Locale locale;
    /**
     * Default bundle {@link Control}
     */
    protected final Control control;

    /**
     * Default resource properties
     */
    protected ResourceBundle resources = null;

    public BaseResourceBundle() {
        this(DEFAULT_BUNDLE_LOCALE);
    }

    public BaseResourceBundle(final Locale locale) {
        this(DEFAULT_BUNDLE_NAME, locale);
    }

    public BaseResourceBundle(final String source, final Locale locale) {
        this(source, locale, DEFAULT_BUNDLE_CONTROL);
    }

    public BaseResourceBundle(final String source, final Locale locale, final Control control) {
        this.source = Optional.ofNullable(source).orElse(DEFAULT_BUNDLE_NAME);
        this.locale = Optional.ofNullable(locale).orElse(DEFAULT_BUNDLE_LOCALE);
        this.control = Optional.ofNullable(control).orElse(DEFAULT_BUNDLE_CONTROL);
        this.loadResources();
    }

    protected void loadResources() {
        final URL[] url = {BaseResourceBundle.class.getClassLoader().getResource(DEFAULT_BUNDLE_SOURCE)};
        this.resources = ResourceBundle.getBundle(this.source, this.locale, new URLClassLoader(url));
    }

    public String getBundleName() {
        return this.resources.getBaseBundleName();
    }

    public Object getResource(final String key) {
        if (Objects.isNull(this.resources)) {
            this.loadResources();
        }
        return this.resources.getObject(key);
    }

    public boolean contains(final String key) {
        if (Objects.isNull(this.resources)) {
            this.loadResources();
        }
        return this.resources.containsKey(key);
    }

    public Object[] getKeysAsArray() {
        return this.resources.keySet().stream().filter(Objects::nonNull).toArray(Object[]::new);
    }

    public Object[] getValuesAsArray() {
        return this.resources.keySet().stream().filter(Objects::nonNull).map(key -> this.resources.getObject(key)).toArray(Object[]::new);
    }

    public Object[][] getResources() {
        return this.getContents();
    }

    @Override
    public Enumeration<String> getKeys() {
        return this.resources.getKeys();
    }

    @Override
    protected Object[][] getContents() {
        return this.resources.keySet().stream().filter(Objects::nonNull).map(key -> new Object[]{key, this.resources.getObject(key)}).toArray(Object[][]::new);
    }

    /**
     * Returns new {@link BaseResourceBundle} instance by input parameters
     *
     * @param locale - initial input {@link Locale}
     * @return new {@link BaseResourceBundle} instance
     */
    @Factory
    public static BaseResourceBundle getInstance(final Locale locale) {
        return new BaseResourceBundle(locale);
    }

    /**
     * Returns new {@link BaseResourceBundle} instance by input parameters
     *
     * @param source - initial input {@link String}
     * @param locale - initial input {@link Locale}
     * @param loader - initial input {@link ClassLoader}
     * @return new {@link BaseResourceBundle} instance
     */
    @Factory
    public static BaseResourceBundle getInstance(final String source, final Locale locale, final ClassLoader loader) {
        return BaseResourceBundle.getInstance(source, locale, loader);
    }

    /**
     * Returns new {@link BaseResourceBundle} instance by input parameters
     *
     * @param source - initial input {@link String}
     * @param locale - initial input {@link Locale}
     * @return new {@link BaseResourceBundle} instance
     */
    @Factory
    public static ResourceBundle getInstance(final String source, final Locale locale) {
        return new BaseResourceBundle(source, locale);
    }

    public static Control getMyControl() {
        return new Control() {

            @Override
            public List<String> getFormats(@NonNull final String baseName) {
                return Arrays.asList("db");
            }

            //            @Override
//            public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException,
//                    InstantiationException, IOException {
//                if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
//                    throw new NullPointerException();
//                }
//                ResourceBundle bundle = null;
//                if (format.equals("db")) {
//                    Properties p = new Properties();
//                    DataSource ds = (DataSource) ContextFactory.getApplicationContext().getBean("clinicalDataSource");
//                    Connection con = null;
//                    Statement s = null;
//                    ResultSet rs = null;
//                    try {
//                        con = ds.getConnection();
//                        StringBuilder query = new StringBuilder();
//                        query.append("select label, value from i18n where bundle='" + StringEscapeUtils.escapeSql(baseName) + "' ");
//
//                        if (locale != null) {
//                            if (StringUtils.isNotBlank(locale.getCountry())) {
//                                query.append("and country='" + escapeSql(locale.getCountry()) + "' ");
//
//                            }
//                            if (StringUtils.isNotBlank(locale.getLanguage())) {
//                                query.append("and language='" + escapeSql(locale.getLanguage()) + "' ");
//
//                            }
//                            if (StringUtils.isNotBlank(locale.getVariant())) {
//                                query.append("and variant='" + escapeSql(locale.getVariant()) + "' ");
//
//                            }
//                        }
//                        s = con.createStatement();
//                        rs = s.executeQuery(query.toString());
//                        while (rs.next()) {
//                            p.setProperty(rs.getString(1), rs.getString(2));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException("Can not build properties: " + e);
//                    } finally {
//                        DbUtils.closeQuietly(con, s, rs);
//                    }
//                    bundle = new DbResourceBundle(p);
//                }
//                return bundle;
//            }
            @Override
            public long getTimeToLive(final String baseName, final Locale locale) {
                return (1000 * 60 * 30);
            }

            @Override
            public boolean needsReload(final String baseName, final Locale locale, final String format, final ClassLoader loader, final ResourceBundle bundle, long loadTime) {
                return true;
            }
        };
    }
}
