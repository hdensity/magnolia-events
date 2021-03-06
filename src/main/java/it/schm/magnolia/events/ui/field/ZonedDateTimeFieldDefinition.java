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

package it.schm.magnolia.events.ui.field;

import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.FieldType;
import it.schm.magnolia.events.ui.converter.ZonedDateTimeToStringConverter;
import it.schm.magnolia.events.ui.factory.ZonedDateTimeFieldFactory;
import lombok.Getter;
import lombok.Setter;

import java.time.format.FormatStyle;

import static java.time.format.FormatStyle.SHORT;

/**
 * Field definition for {@code ZonedDateTime} fields.
 */
@Getter
@Setter
@FieldType("zonedDateTimeField")
public class ZonedDateTimeFieldDefinition extends ConfiguredFieldDefinition<Object> {

    private FormatStyle dateFormat = SHORT;
    private FormatStyle timeFormat = SHORT;

    /**
     * Creates a new ZonedDateTimeFieldDefinition with default configuration.
     */
    public ZonedDateTimeFieldDefinition() {
        setFactoryClass(ZonedDateTimeFieldFactory.class);
        setConverterClass(ZonedDateTimeToStringConverter.class);
    }

}
