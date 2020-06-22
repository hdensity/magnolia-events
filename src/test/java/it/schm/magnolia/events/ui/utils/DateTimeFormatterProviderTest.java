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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.FormatStyle.SHORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateTimeFormatterProviderTest {

    private static final ZonedDateTime TEST_TIME =
            ZonedDateTime.of(1982, 4, 7, 22, 5, 6, 0, ZoneId.of("Europe/Luxembourg"));

    @Mock private DateTimeFormatProvider dateTimeFormatProviderMock;
    @Mock private UserPreferences userPreferencesMock;

    @InjectMocks
    private DateTimeFormatterProvider dateTimeFormatterProvider;

    @Test
    void given_useLocalTime_when_get_then_expectedFormatterIsReturned() {
        // GIVEN
        when(userPreferencesMock.getLocale()).thenReturn(Locale.GERMAN);
        when(dateTimeFormatProviderMock.get(SHORT, SHORT)).thenReturn("dd/MM/y, HH:mm zz");

        // WHEN
        DateTimeFormatter formatter = dateTimeFormatterProvider.get(SHORT, SHORT, false);

        // THEN
        assertThat(TEST_TIME.format(formatter)).isEqualTo("07/04/1982, 22:05 MESZ");
    }

    @Test
    void given_useUserTimeAndUserTimeZone_when_get_then_expectedFormatterIsReturned() {
        // GIVEN
        when(userPreferencesMock.getLocale()).thenReturn(Locale.GERMAN);
        when(userPreferencesMock.getZoneId()).thenReturn(ZoneOffset.UTC);
        when(dateTimeFormatProviderMock.get(SHORT, SHORT)).thenReturn("dd/MM/y, HH:mm zz");

        // WHEN
        DateTimeFormatter formatter = dateTimeFormatterProvider.get(SHORT, SHORT, true);

        // THEN
        assertThat(TEST_TIME.format(formatter)).isEqualTo("07/04/1982, 20:05 Z");
    }

}
