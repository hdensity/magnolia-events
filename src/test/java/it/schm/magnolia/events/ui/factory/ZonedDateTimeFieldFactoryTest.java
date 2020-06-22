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

import info.magnolia.objectfactory.ComponentProvider;
import it.schm.magnolia.events.ui.field.ZonedDateTimeField;
import it.schm.magnolia.events.ui.field.ZonedDateTimeFieldDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZonedDateTimeFieldFactoryTest {

    @Mock private ZonedDateTimeFieldDefinition definitionMock;
    @Mock private ComponentProvider componentProviderMock;

    @InjectMocks
    private ZonedDateTimeFieldFactory fieldFactory;

    @Test
    void when_createFieldComponent_then_expectedComponentIsCreated() {
        ZonedDateTimeField fieldMock = mock(ZonedDateTimeField.class);
        when(componentProviderMock.newInstance(ZonedDateTimeField.class, definitionMock)).thenReturn(fieldMock);

        assertThat(fieldFactory.createFieldComponent()).isEqualTo(fieldMock);
    }

}
