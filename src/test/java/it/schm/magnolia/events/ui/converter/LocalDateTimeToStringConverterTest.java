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
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalDateTimeToStringConverterTest {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Mock private UserPreferences userPreferences;

    @InjectMocks
    private LocalDateTimeToStringConverter converter;

    @Test
    void given_nullLocalDateTimeString_when_convertToPresentation_then_expectedValueIsReturned() {
        assertThat(converter.convertToPresentation(null, null)).isNull();
    }

    @Test
    void given_localDateTimeString_when_convertToPresentation_then_expectedValueIsReturned() {
        assertThat(converter.convertToPresentation(NOW.toString(), null)).isEqualTo(NOW);
    }

    @Test
    void given_nowLocalDateTimeString_when_convertToPresentation_then_expectedValueIsReturned() {
        ZoneId zoneId = ZoneId.of("Europe/Luxembourg");

        when(userPreferences.getZoneId()).thenReturn(zoneId);
        assertThat(converter.convertToPresentation("NOW", null))
                .isCloseTo(LocalDateTime.now(zoneId), new TemporalUnitLessThanOffset(1, ChronoUnit.SECONDS));
    }

    @Test
    void given_nullLocalDateTime_when_convertToModel_then_expectedValueIsReturned() {
        Result<String> result = converter.convertToModel(null, null);

        result.ifOk(value -> assertThat(value).isNull());
        result.ifError(Assertions::fail);
    }

    @Test
    void given_localDateTime_when_convertToModel_then_expectedValueIsReturned() {
        Result<String> result = converter.convertToModel(NOW, null);

        result.ifOk(value -> assertThat(value).isEqualTo(NOW.toString()));
        result.ifError(Assertions::fail);
    }

}
