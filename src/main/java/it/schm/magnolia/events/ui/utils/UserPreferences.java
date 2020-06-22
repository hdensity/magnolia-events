/*
 * MIT License
 *
 * Copyright (c) 2020 Sam Schmit-Van Werweke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package it.schm.magnolia.events.ui.utils;

import info.magnolia.cms.security.MgnlUserManager;
import info.magnolia.context.Context;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Provider;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class for accessing user preferences.
 */
public class UserPreferences {

    private final Provider<Context> contextProvider;

    public UserPreferences(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    /**
     * Returns the user's configured zone ID, or the system default if no configuration can be found.
     *
     * @return The user's configured zone ID or the system default if no configuration can be found.
     */
    public ZoneId getZoneId() {
        String timeZoneId = contextProvider.get().getUser().getProperty(MgnlUserManager.PROPERTY_TIMEZONE);

        return StringUtils.isEmpty(timeZoneId) ? ZoneId.systemDefault() : TimeZone.getTimeZone(timeZoneId).toZoneId();
    }

    /**
     * Returns the user's configured Locale.
     *
     * @return the user's configured Locale.
     */
    public Locale getLocale() {
        return LocaleUtils.toLocale(contextProvider.get().getUser().getLanguage());
    }

}
