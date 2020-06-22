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

package it.schm.magnolia.events.ui.datasource;

import com.vaadin.data.provider.ListDataProvider;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.editor.LocaleContext;
import it.schm.magnolia.events.ui.converter.ZoneIdConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ZoneIdSelectFieldSupportTest {

    @Mock private LocaleContext localeContextMock;
    @Mock private ZoneIdConverter zoneIdConverterMock;

    @InjectMocks
    private ZoneIdSelectFieldSupport selectFieldSupport;

    @Test
    void when_getDataProvider_then_expectedProviderIsReturned() {
        assertThat(selectFieldSupport.getDataProvider())
                .isInstanceOf(ListDataProvider.class)
                .extracting("backend")
                .isEqualTo(ZoneIdSelectFieldSupport.ZONE_IDS);
    }

    @Test
    void when_getItemCaptionGenerator_then_expectedItemCaptionGeneratorIsReturned() {
        assertThat(selectFieldSupport.getItemCaptionGenerator()).isInstanceOf(ZoneIdItemCaptionGenerator.class);
    }

    @Test
    void when_getIconGenerator_then_expectedIconGeneratorIsReturned() {
        assertThat(selectFieldSupport.getIconGenerator().apply(ZoneId.of("Europe/Luxembourg")))
                .isEqualTo(MagnoliaIcons.TIMESTAMP);
    }

    @Test
    void when_defaultConverter_then_expectedConverterIsReturned() {
        assertThat(selectFieldSupport.defaultConverter()).isEqualTo(zoneIdConverterMock);
    }

}
