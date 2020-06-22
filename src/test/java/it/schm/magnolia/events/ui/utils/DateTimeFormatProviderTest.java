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

import info.magnolia.cms.security.User;
import info.magnolia.context.Context;
import it.schm.magnolia.events.ui.utils.DateTimeFormatProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Provider;
import java.time.format.FormatStyle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateTimeFormatProviderTest {

    @Mock private Provider<Context> contextProviderMock;

    @InjectMocks
    private DateTimeFormatProvider dateTimeFormatProvider;

    @Test
    void given_userWithGermanGermanLanguage_when_get_then_expectedPatternIsReturned() {
        runProviderTest("de_DE", "dd.MM.yy HH:mm");
    }

    @Test
    void given_userWithEnglishLanguage_when_get_then_expectedPatternIsReturned() {
        runProviderTest("en", "M/d/yy h:mm a");
    }

    private void runProviderTest(String language, String expectedPattern) {
        // GIVEN
        User userMock = mock(User.class);
        when(userMock.getLanguage()).thenReturn(language);

        Context contextMock = mock(Context.class);
        when(contextMock.getUser()).thenReturn(userMock);

        when(contextProviderMock.get()).thenReturn(contextMock);

        // WHEN & THEN
        assertThat(dateTimeFormatProvider.get(FormatStyle.SHORT, FormatStyle.SHORT)).isEqualTo(expectedPattern);
    }

}
