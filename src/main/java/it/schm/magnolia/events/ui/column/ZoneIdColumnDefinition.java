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

import com.vaadin.data.ValueProvider;
import info.magnolia.ui.contentapp.configuration.column.ColumnType;
import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;
import info.magnolia.ui.editor.LocaleContext;
import it.schm.magnolia.events.ui.datasource.ZoneIdItemCaptionGenerator;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;

import java.time.ZoneId;

/**
 * {@code ZoneId} column definition.
 */
@ColumnType("zoneIdColumn")
public class ZoneIdColumnDefinition extends ConfiguredColumnDefinition<String> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ZoneIdColumnDefinition() {
        setValueProvider((Class) ZoneIdColumnDefinition.ZoneIdValueProvider.class);
    }

    /**
     * Converts a value to a formatted ZonedDateTime string.
     *
     * @param <D> definition type.
     */
    public static class ZoneIdValueProvider<D extends ZoneIdColumnDefinition>
            implements ValueProvider<Node, String> {

        private final D definition;
        private final ZoneIdItemCaptionGenerator itemCaptionGenerator;

        @Inject
        public ZoneIdValueProvider(D definition, LocaleContext localeContext) {
            this.definition = definition;
            this.itemCaptionGenerator = new ZoneIdItemCaptionGenerator(localeContext);
        }

        @SneakyThrows
        @Override
        public String apply(Node node) {
            Property property = node.getProperty(definition.getName());

            return itemCaptionGenerator.apply(ZoneId.of(property.getString()));
        }

    }

}
