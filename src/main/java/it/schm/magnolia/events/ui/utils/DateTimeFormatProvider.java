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

import info.magnolia.context.Context;
import org.apache.commons.lang3.LocaleUtils;

import javax.inject.Inject;
import javax.inject.Provider;

import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * A provider of locale dependent date-time formats.
 */
public class DateTimeFormatProvider {

    private final Provider<Context> contextProvider;

    @Inject
    public DateTimeFormatProvider(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    /**
     * Returns a date-time format pattern, based on the provided {@code FormatStyle}s and the user's selected time zone.
     *
     * @param dateFormatStyle The date format style to use
     * @param timeFormatStyle The time format style to use
     * @return The date and time format pattern
     */
    public String get(FormatStyle dateFormatStyle, FormatStyle timeFormatStyle) {
        Locale current = LocaleUtils.toLocale(contextProvider.get().getUser().getLanguage());

        return DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                dateFormatStyle, timeFormatStyle, Chronology.ofLocale(current), current);
    }

}
