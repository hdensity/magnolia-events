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

package it.schm.magnolia.events.ui.factory;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import it.schm.magnolia.events.ui.field.LocalDateFieldDefinition;
import it.schm.magnolia.events.ui.utils.DateTimeFormatProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.FormatStyle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalDateFieldFactoryTest {

    @Mock private LocalDateFieldDefinition definitionMock;
    @Mock private DateTimeFormatProvider dateTimeFormatProviderMock;

    @InjectMocks
    private LocalDateFieldFactory fieldFactory;

    @Test
    void when_createFieldComponent_then_expectedComponentIsCreated() {
        // GIVEN
        when(definitionMock.getDateFormat()).thenReturn(FormatStyle.MEDIUM);
        when(dateTimeFormatProviderMock.get(FormatStyle.MEDIUM, null)).thenReturn("format");

        // WHEN
        Component fieldComponent = fieldFactory.createFieldComponent();

        // THEN
        assertThat(fieldComponent)
                .isExactlyInstanceOf(DateField.class)
                .extracting("dateFormat")
                .isEqualTo("format");
    }

}
