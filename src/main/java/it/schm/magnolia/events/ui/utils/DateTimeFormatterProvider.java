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

import javax.inject.Inject;

import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * A provider of locale dependent {@link DateTimeFormatter}s.
 */
public class DateTimeFormatterProvider {

    private final DateTimeFormatProvider dateTimeFormatProvider;
    private final UserPreferences userPreferences;

    @Inject
    public DateTimeFormatterProvider(DateTimeFormatProvider dateTimeFormatProvider, UserPreferences userPreferences) {
        this.dateTimeFormatProvider = dateTimeFormatProvider;
        this.userPreferences = userPreferences;
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
        Locale current = userPreferences.getLocale();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(dateTimeFormatProvider.get(dateFormatStyle, timeFormatStyle), current)
                        .withChronology(Chronology.ofLocale(current));

        return userTime ? formatter.withZone(userPreferences.getZoneId()) : formatter;
    }

}
