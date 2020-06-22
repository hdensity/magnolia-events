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
import it.schm.magnolia.events.ui.utils.DateTimeFormatterProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;

import java.time.LocalDate;
import java.time.format.FormatStyle;

import static java.time.format.FormatStyle.SHORT;

/**
 * {@code LocalDate} column definition.
 */
@Getter
@Setter
@ColumnType("localDateColumn")
public class LocalDateColumnDefinition extends ConfiguredColumnDefinition<String> {

    private FormatStyle dateFormat = SHORT;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public LocalDateColumnDefinition() {
        setValueProvider((Class) LocalDateValueProvider.class);
    }

    /**
     * Converts a value to a formatted LocalDate string.
     *
     * @param <D> definition type.
     */
    public static class LocalDateValueProvider<D extends LocalDateColumnDefinition>
            implements ValueProvider<Node, String> {

        private final D definition;
        private final DateTimeFormatterProvider dateTimeFormatterProvider;

        @Inject
        public LocalDateValueProvider(D definition, DateTimeFormatterProvider dateTimeFormatterProvider) {
            this.definition = definition;
            this.dateTimeFormatterProvider = dateTimeFormatterProvider;
        }

        @SneakyThrows
        @Override
        public String apply(Node node) {
            Property property = node.getProperty(definition.getName());

            return LocalDate.parse(property.getString()).format(
                    dateTimeFormatterProvider.get(definition.getDateFormat(), null, false));
        }

    }

}
