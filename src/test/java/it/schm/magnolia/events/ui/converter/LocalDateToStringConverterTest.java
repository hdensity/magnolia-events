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

package it.schm.magnolia.events.ui.converter;

import com.vaadin.data.Result;
import it.schm.magnolia.events.ui.utils.UserPreferences;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalDateToStringConverterTest {

    private static final LocalDate NOW = LocalDate.now();

    @Mock private UserPreferences userPreferences;

    @InjectMocks
    private LocalDateToStringConverter converter;

    @Test
    void given_localDateString_when_convertToPresentation_then_expectedValueIsReturned() {
        assertThat(converter.convertToPresentation(NOW.toString(), null)).isEqualTo(NOW);
    }

    @Test
    void given_nullLocalDateString_when_convertToPresentation_then_expectedValueIsReturned() {
        assertThat(converter.convertToPresentation(null, null)).isNull();
    }

    @Test
    void given_nowLocalDateString_when_convertToPresentation_then_expectedValueIsReturned() {
        ZoneId zoneId = ZoneId.of("Europe/Luxembourg");

        when(userPreferences.getZoneId()).thenReturn(zoneId);
        assertThat(converter.convertToPresentation("NOW", null)).isEqualTo(LocalDate.now(zoneId));
    }

    @Test
    void given_localDate_when_convertToModel_then_expectedValueIsReturned() {
        Result<String> result = converter.convertToModel(NOW, null);

        result.ifOk(value -> assertThat(value).isEqualTo(NOW.toString()));
        result.ifError(Assertions::fail);
    }

    @Test
    void given_nullLocalDate_when_convertToModel_then_expectedValueIsReturned() {
        Result<String> result = converter.convertToModel(null, null);

        result.ifOk(value -> assertThat(value).isNull());
        result.ifError(Assertions::fail);
    }

}
