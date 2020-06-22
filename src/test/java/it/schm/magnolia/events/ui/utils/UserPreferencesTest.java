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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Provider;

import java.time.ZoneId;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPreferencesTest {

    @Mock private Provider<Context> contextProviderMock;
    @Mock private Context contextMock;
    @Mock private User userMock;

    @InjectMocks
    private UserPreferences userPreferences;

    @BeforeEach
    void setUpMocks() {
        when(contextProviderMock.get()).thenReturn(contextMock);
        when(contextMock.getUser()).thenReturn(userMock);
    }

    @Test
    void given_noConfiguredZoneId_when_getZoneId_then_expectedZoneIdIsReturned() {
        assertThat(userPreferences.getZoneId()).isEqualTo(ZoneId.systemDefault());
    }

    @Test
    void given_configuredZoneId_when_getZoneId_then_expectedZoneIdIsReturned() {
        when(userMock.getProperty(MgnlUserManager.PROPERTY_TIMEZONE)).thenReturn("Europe/Luxembourg");
        assertThat(userPreferences.getZoneId()).isEqualTo(ZoneId.of("Europe/Luxembourg"));
    }

    @Test
    void when_getLocale_then_expectedLocaleIsReturned() {
        when(userMock.getLanguage()).thenReturn("de_DE");
        assertThat(userPreferences.getLocale()).isEqualTo(Locale.forLanguageTag("de-DE"));
    }

}
