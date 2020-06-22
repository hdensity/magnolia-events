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

import javax.inject.Inject;
import javax.inject.Provider;

import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A provider of locale dependent {@link DateTimeFormatter}s.
 */
public class DateTimeFormatterProvider {

    private final DateTimeFormatProvider dateTimeFormatProvider;
    private final Provider<Context> contextProvider;

    @Inject
    public DateTimeFormatterProvider(DateTimeFormatProvider dateTimeFormatProvider, Provider<Context> contextProvider) {
        this.dateTimeFormatProvider = dateTimeFormatProvider;
        this.contextProvider = contextProvider;
    }

    /**
     * Returns a {@code DateTimeFormatter}, based on the provided {@code FormatStyle}s
     * and the user's selected time zone.
     *
     * @param dateFormatStyle The date format style to use
     * @param timeFormatStyle The time format style to use
     * @param userTime whether to use local-time, or to apply an offset based on the user's selected time zone.
     * @return The configured {@code DateTimeFormatter}
     */
    public DateTimeFormatter get(FormatStyle dateFormatStyle, FormatStyle timeFormatStyle, boolean userTime) {
        Locale current = LocaleUtils.toLocale(contextProvider.get().getUser().getLanguage());

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(dateTimeFormatProvider.get(dateFormatStyle, timeFormatStyle), current)
                        .withChronology(Chronology.ofLocale(current));

        return userTime ? formatter.withZone(zoneId()) : formatter;
    }

    private ZoneId zoneId() {
        final String timeZoneId = contextProvider.get().getUser().getProperty(MgnlUserManager.PROPERTY_TIMEZONE);

        return StringUtils.isEmpty(timeZoneId) ? ZoneId.systemDefault() : TimeZone.getTimeZone(timeZoneId).toZoneId();
    }

}
