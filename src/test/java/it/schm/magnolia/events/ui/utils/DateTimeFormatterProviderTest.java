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
import info.magnolia.cms.security.User;
import info.magnolia.context.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Provider;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static java.time.format.FormatStyle.SHORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateTimeFormatterProviderTest {

    private static final ZonedDateTime TEST_TIME =
            ZonedDateTime.of(1982, 4, 7, 22, 5, 6, 0, ZoneId.of("Europe/Luxembourg"));

    private static final String TEST_TIME_ZONE_ID = TimeZone.getTimeZone(ZoneOffset.UTC).getID();

    @Mock
    private DateTimeFormatProvider dateTimeFormatProviderMock;
    @Mock
    private Provider<Context> contextProviderMock;

    @InjectMocks
    private DateTimeFormatterProvider dateTimeFormatterProvider;

    @Test
    void given_useLocalTime_when_get_then_expectedFormatterIsReturned() {
        // GIVEN
        setUpTest(false, TEST_TIME_ZONE_ID);

        // WHEN
        DateTimeFormatter formatter = dateTimeFormatterProvider.get(SHORT, SHORT, false);

        // THEN
        assertThat(TEST_TIME.format(formatter)).isEqualTo("07/04/1982, 22:05 CEST");
    }

    @Test
    void given_useUserTimeAndUserTimeZone_when_get_then_expectedFormatterIsReturned() {
        // GIVEN
        setUpTest(true, TEST_TIME_ZONE_ID);

        // WHEN
        DateTimeFormatter formatter = dateTimeFormatterProvider.get(SHORT, SHORT, true);

        // THEN
        assertThat(TEST_TIME.format(formatter)).isEqualTo("07/04/1982, 20:05 UTC");
    }

    @Test
    void given_useUserTimeAndNoUserTimeZone_when_get_then_expectedFormatterIsReturned() {
        // GIVEN
        setUpTest(true, null);

        // WHEN
        DateTimeFormatter formatter = dateTimeFormatterProvider.get(SHORT, SHORT, true);

        // THEN
        assertThat(formatter.getZone()).isEqualTo(ZoneId.systemDefault());
    }

    private void setUpTest(boolean userTime, String timeZoneId) {
        User userMock = mock(User.class);
        when(userMock.getLanguage()).thenReturn("en_150");
        if (userTime) {
            when(userMock.getProperty(MgnlUserManager.PROPERTY_TIMEZONE)).thenReturn(timeZoneId);
        }

        Context contextMock = mock(Context.class);
        when(contextMock.getUser()).thenReturn(userMock);

        when(contextProviderMock.get()).thenReturn(contextMock);
        when(dateTimeFormatProviderMock.get(SHORT, SHORT)).thenReturn("dd/MM/y, HH:mm zz");
    }

}
