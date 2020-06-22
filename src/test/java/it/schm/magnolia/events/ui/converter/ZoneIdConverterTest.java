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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ZoneIdConverterTest {

    private static final ZoneId HERE = ZoneId.of("Europe/Luxembourg");
    private final ZoneIdConverter converter = new ZoneIdConverter();

    @Test
    void given_zoneIdId_when_convertToPresentation_then_expectedValueIsReturned() {
        assertThat(converter.convertToPresentation(HERE.getId(), null)).isEqualTo(HERE);
    }

    @Test
    void given_nullZoneIdId_when_convertToPresentation_then_expectedValueIsReturned() {
        assertThat(converter.convertToPresentation(null, null)).isNull();
    }

    @Test
    void given_zoneId_when_convertToModel_then_expectedValueIsReturned() {
        Result<String> result = converter.convertToModel(HERE, null);

        result.ifOk(value -> assertThat(value).isEqualTo(HERE.getId()));
        result.ifError(Assertions::fail);
    }

    @Test
    void given_nullZoneId_when_convertToModel_then_expectedValueIsReturned() {
        Result<String> result = converter.convertToModel(null, null);

        result.ifOk(value -> assertThat(value).isNull());
        result.ifError(Assertions::fail);
    }

}
