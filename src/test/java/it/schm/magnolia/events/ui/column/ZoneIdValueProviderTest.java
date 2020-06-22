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

package it.schm.magnolia.events.ui.column;

import info.magnolia.test.mock.jcr.MockNode;
import info.magnolia.test.mock.jcr.MockProperty;
import info.magnolia.ui.editor.LocaleContext;
import it.schm.magnolia.events.ui.datasource.ZoneIdItemCaptionGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.time.ZoneId;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZoneIdValueProviderTest {

    @Mock private ZoneIdColumnDefinition definitionMock;
    @Mock private LocaleContext localeContextMock;

    @InjectMocks
    private ZoneIdColumnDefinition.ZoneIdValueProvider<ZoneIdColumnDefinition> valueProvider;

    @Test
    void when_apply_then_expectedValueIsReturned() {
        when(definitionMock.getName()).thenReturn("name");

        ZoneId zoneId = ZoneId.of("Europe/Luxembourg");

        when(localeContextMock.getCurrent()).thenReturn(Locale.GERMAN);

        MockNode mockNode = new MockNode();
        MockProperty mockProperty = new MockProperty("name", zoneId.getId(), mockNode);
        mockNode.addProperty(mockProperty);

        assertThat(valueProvider.apply(mockNode)).isEqualTo("Europe / Luxembourg (Mitteleuropäische Zeit)");
    }

    @Test
    void when_applyThrows_then_exceptionIsCatchable() throws Exception {
        when(definitionMock.getName()).thenReturn("name");

        RepositoryException repositoryException = new RepositoryException();

        Node nodeMock = mock(Node.class);
        when(nodeMock.getProperty("name")).thenThrow(repositoryException);

        assertThatThrownBy(() -> valueProvider.apply(nodeMock)).isEqualTo(repositoryException);
    }

}
