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
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.field.factory.AbstractFieldFactory;
import it.schm.magnolia.events.ui.field.LocalDateFieldDefinition;
import it.schm.magnolia.events.ui.utils.DateTimeFormatProvider;

import javax.inject.Inject;

/**
 * Creates and configures a {@link DateField}.
 */
public class LocalDateFieldFactory extends AbstractFieldFactory<Object, LocalDateFieldDefinition> {

    private final DateTimeFormatProvider dateTimeFormatProvider;

    /**
     * Creates a new {@link LocalDateFieldFactory}.
     *
     * @param definition The field definition to apply when configuring a newly created field component
     * @param componentProvider The component provider to use to create new field component instances
     * @param dateTimeFormatProvider The {@code DateTimeFormatProvider} which supplies the required date format
     */
    @Inject
    public LocalDateFieldFactory(
            LocalDateFieldDefinition definition,
            ComponentProvider componentProvider,
            DateTimeFormatProvider dateTimeFormatProvider) {
        super(definition, componentProvider);

        this.dateTimeFormatProvider = dateTimeFormatProvider;
    }

    @Override
    protected Component createFieldComponent() {
        DateField field = new DateField();
        field.setDateFormat(dateTimeFormatProvider.get(getDefinition().getDateFormat(), null));

        return field;
    }

}
