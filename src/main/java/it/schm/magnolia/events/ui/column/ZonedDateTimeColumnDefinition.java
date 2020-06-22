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

import java.time.ZonedDateTime;
import java.time.format.FormatStyle;

import static java.time.format.FormatStyle.MEDIUM;
import static java.time.format.FormatStyle.SHORT;

/**
 * {@code ZonedDateTime} column definition.
 */
@Getter
@Setter
@ColumnType("zonedDateTimeColumn")
public class ZonedDateTimeColumnDefinition extends ConfiguredColumnDefinition<String> {

    private FormatStyle dateFormat = SHORT;
    private FormatStyle timeFormat = MEDIUM;
    private boolean userTime;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ZonedDateTimeColumnDefinition() {
        setValueProvider((Class) ZonedDateTimeValueProvider.class);
    }

    /**
     * Converts a value to a formatted ZonedDateTime string.
     *
     * @param <D> definition type.
     */
    public static class ZonedDateTimeValueProvider<D extends ZonedDateTimeColumnDefinition>
            implements ValueProvider<Node, String> {

        private final D definition;
        private final DateTimeFormatterProvider dateTimeFormatterProvider;

        @Inject
        public ZonedDateTimeValueProvider(D definition, DateTimeFormatterProvider dateTimeFormatterProvider) {
            this.definition = definition;
            this.dateTimeFormatterProvider = dateTimeFormatterProvider;
        }

        @SneakyThrows
        @Override
        public String apply(Node node) {
            Property property = node.getProperty(definition.getName());

            return ZonedDateTime.parse(property.getString()).format(
                    dateTimeFormatterProvider.get(
                            definition.getDateFormat(), definition.getTimeFormat(), definition.isUserTime()));
        }

    }

}
