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

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import info.magnolia.ui.field.factory.FormFieldFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * A field allowing a user to select a local date and time, as well as a time zone.
 */
public class ZonedDateTimeField extends CustomField<ZonedDateTime> {

    private final FormFieldFactory formFieldFactory;
    private final ZonedDateTimeFieldDefinition definition;

    private transient LocalDateTime localDateTime = null;
    private transient ZoneId zoneId = null;

    private ZonedDateTime zonedDateTime = null;

    public ZonedDateTimeField(FormFieldFactory formFieldFactory, ZonedDateTimeFieldDefinition definition) {
        this.formFieldFactory = formFieldFactory;
        this.definition = definition;
    }

    @Override
    protected Component initContent() {
        HorizontalLayout rootLayout = new HorizontalLayout();
        rootLayout.setSizeFull();
        rootLayout.setMargin(false);
        rootLayout.setSpacing(true);

        DateTimeField dateTimeField = createDateTimeField();
        ComboBox<ZoneId> zoneIdField = createTimeZoneField();

        rootLayout.addComponent(dateTimeField);
        rootLayout.setExpandRatio(dateTimeField, 1);
        rootLayout.addComponent(zoneIdField);
        rootLayout.setExpandRatio(zoneIdField, 2);

        return rootLayout;
    }

    private DateTimeField createDateTimeField() {
        LocalDateTimeFieldDefinition localDateTimeFieldDefinition = new LocalDateTimeFieldDefinition();
        localDateTimeFieldDefinition.setDateFormat(definition.getDateFormat());
        localDateTimeFieldDefinition.setTimeFormat(definition.getTimeFormat());

        Component dateTimeComponent = formFieldFactory.createField(localDateTimeFieldDefinition);
        DateTimeField dateTimeField = (DateTimeField) dateTimeComponent;
        dateTimeField.setValue(localDateTime);
        dateTimeField.addValueChangeListener(event -> updateValue(event.getValue()));

        return dateTimeField;
    }

    private ComboBox<ZoneId> createTimeZoneField() {
        ZoneIdFieldDefinition zoneIdFieldDefinition = new ZoneIdFieldDefinition();
        zoneIdFieldDefinition.setEmptySelectionAllowed(!definition.isRequired());
        zoneIdFieldDefinition.setTextInputAllowed(true);

        ComboBox<ZoneId> zoneIdField = formFieldFactory.createField(zoneIdFieldDefinition);
        zoneIdField.setValue(zoneId);
        zoneIdField.addValueChangeListener(event -> updateValue(event.getValue()));

        return zoneIdField;
    }

    private void updateValue(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        updateParent();
    }

    private void updateValue(ZoneId zoneId) {
        this.zoneId = zoneId;
        updateParent();
    }

    private void updateParent() {
        setValue(localDateTime == null || zoneId == null ? null : localDateTime.atZone(zoneId));
    }

    @Override
    protected void doSetValue(ZonedDateTime value) {
        zonedDateTime = value;

        if (value != null) {
            localDateTime = zonedDateTime.toLocalDateTime();
            zoneId = zonedDateTime.getZone();
        }
    }

    @Override
    public ZonedDateTime getValue() {
        return zonedDateTime;
    }

}
