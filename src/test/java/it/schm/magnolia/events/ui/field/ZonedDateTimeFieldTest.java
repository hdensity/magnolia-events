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

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import info.magnolia.ui.field.ConfiguredFieldDefinition;
import info.magnolia.ui.field.factory.FormFieldFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.FormatStyle;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"rawtypes", "unchecked"})
@ExtendWith(MockitoExtension.class)
class ZonedDateTimeFieldTest {

    @Mock private FormFieldFactory formFieldFactoryMock;
    @Mock private ZonedDateTimeFieldDefinition definitionMock;

    @InjectMocks
    private ZonedDateTimeField zonedDateTimeField;

    @Mock private DateTimeField dateTimeFieldMock;
    @Mock private ComboBox<ZoneId> zoneIdFieldMock;

    @Captor private ArgumentCaptor<ValueChangeListener<LocalDateTime>> localDateTimeValueChangeListenerArgumentCaptor;
    @Captor private ArgumentCaptor<ValueChangeListener<ZoneId>> zoneIdValueChangeListenerArgumentCaptor;

    @Test
    void given_required_when_initContent_then_subFieldsAreCreatedAndConfiguredAsExpected() {
        runFieldTest(ZonedDateTime.now(ZoneOffset.UTC), true);
    }

    @Test
    void given_notRequired_when_initContent_then_emptyZoneIdSelectionIsAllowed() {
        runFieldTest(ZonedDateTime.now(ZoneOffset.UTC), false);
    }

    @Test
    void given_nullValue_when_dateTimeValueChanges_then_valueIsUpdatedAccordingly() {
        runFieldTest(null, true);

        LocalDateTime localDateTimeValue = LocalDateTime.of(1982, 4, 7, 22, 5, 0);
        when(dateTimeFieldMock.getValue()).thenReturn(localDateTimeValue);

        localDateTimeValueChangeListenerArgumentCaptor.getValue()
                .valueChange(new ValueChangeEvent<>(dateTimeFieldMock, LocalDateTime.now(), true));

        assertThat(zonedDateTimeField.getValue()).isNull();
    }

    @Test
    void given_nonNullValue_when_dateTimeValueChanges_then_valueIsUpdatedAccordingly() {
        runFieldTest(ZonedDateTime.now(ZoneOffset.UTC), true);

        LocalDateTime localDateTimeValue = LocalDateTime.of(1982, 4, 7, 22, 5, 0);
        when(dateTimeFieldMock.getValue()).thenReturn(localDateTimeValue);

        localDateTimeValueChangeListenerArgumentCaptor.getValue()
                .valueChange(new ValueChangeEvent<>(dateTimeFieldMock, LocalDateTime.now(), true));

        assertThat(zonedDateTimeField.getValue()).isEqualTo(localDateTimeValue.atZone(ZoneOffset.UTC));
    }

    @Test
    void given_nonNullValue_when_dateTimeValueChangesToNull_then_valueIsUpdatedAccordingly() {
        runFieldTest(ZonedDateTime.now(ZoneOffset.UTC), true);

        when(dateTimeFieldMock.getValue()).thenReturn(null);

        localDateTimeValueChangeListenerArgumentCaptor.getValue()
                .valueChange(new ValueChangeEvent<>(dateTimeFieldMock, LocalDateTime.now(), true));

        assertThat(zonedDateTimeField.getValue()).isNull();
    }

    @Test
    void given_nullValue_when_zoneIdValueChanges_then_valueIsUpdatedAccordingly() {
        runFieldTest(null, true);

        ZoneId zoneIdValue = ZoneId.of("Europe/Luxembourg");
        when(zoneIdFieldMock.getValue()).thenReturn(zoneIdValue);

        zoneIdValueChangeListenerArgumentCaptor.getValue()
                .valueChange(new ValueChangeEvent<>(zoneIdFieldMock, ZoneOffset.UTC, true));

        assertThat(zonedDateTimeField.getValue()).isNull();
    }

    @Test
    void given_nonNullValue_when_zoneIdValueChanges_then_valueIsUpdatedAccordingly() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        runFieldTest(now, true);

        ZoneId zoneIdValue = ZoneId.of("Europe/Luxembourg");
        when(zoneIdFieldMock.getValue()).thenReturn(zoneIdValue);

        zoneIdValueChangeListenerArgumentCaptor.getValue()
                .valueChange(new ValueChangeEvent<>(zoneIdFieldMock, ZoneOffset.UTC, true));

        assertThat(zonedDateTimeField.getValue()).isEqualTo(now.withZoneSameLocal(zoneIdValue));
    }

    @Test
    void given_nonNullValue_when_zoneIdValueChangesToNull_then_valueIsUpdatedAccordingly() {
        runFieldTest(ZonedDateTime.now(ZoneOffset.UTC), true);

        when(zoneIdFieldMock.getValue()).thenReturn(null);

        zoneIdValueChangeListenerArgumentCaptor.getValue()
                .valueChange(new ValueChangeEvent<>(zoneIdFieldMock, ZoneOffset.UTC, true));

        assertThat(zonedDateTimeField.getValue()).isNull();
    }

    private void runFieldTest(ZonedDateTime startingValue, boolean required) {
        // GIVEN
        when(definitionMock.getDateFormat()).thenReturn(FormatStyle.SHORT);
        when(definitionMock.getTimeFormat()).thenReturn(FormatStyle.LONG);
        when(definitionMock.isRequired()).thenReturn(required);

        ArgumentCaptor<ConfiguredFieldDefinition> configuredFieldDefinitionArgumentCaptor =
                ArgumentCaptor.forClass(ConfiguredFieldDefinition.class);

        when(formFieldFactoryMock.createField(configuredFieldDefinitionArgumentCaptor.capture()))
                .thenReturn(dateTimeFieldMock)
                .thenReturn(zoneIdFieldMock);

        zonedDateTimeField.setValue(startingValue);

        // WHEN
        zonedDateTimeField.initContent();

        // THEN
        List<ConfiguredFieldDefinition> configuredFieldDefinitions =
                configuredFieldDefinitionArgumentCaptor.getAllValues();

        // DateTime Field
        LocalDateTimeFieldDefinition localDateTimeFieldDefinition =
                (LocalDateTimeFieldDefinition) configuredFieldDefinitions.get(0);
        assertThat(localDateTimeFieldDefinition.getDateFormat()).isEqualTo(FormatStyle.SHORT);
        assertThat(localDateTimeFieldDefinition.getTimeFormat()).isEqualTo(FormatStyle.LONG);

        verify(dateTimeFieldMock).setValue(startingValue == null ? null : startingValue.toLocalDateTime());
        verify(dateTimeFieldMock).addValueChangeListener(localDateTimeValueChangeListenerArgumentCaptor.capture());

        // ZoneId Field
        ZoneIdFieldDefinition zoneIdFieldDefinition = (ZoneIdFieldDefinition) configuredFieldDefinitions.get(1);
        assertThat(zoneIdFieldDefinition.isEmptySelectionAllowed()).isEqualTo(!required);
        assertThat(zoneIdFieldDefinition.isTextInputAllowed()).isTrue();

        verify(zoneIdFieldMock).setValue(startingValue == null ? null : startingValue.getZone());
        verify(zoneIdFieldMock).addValueChangeListener(zoneIdValueChangeListenerArgumentCaptor.capture());
    }

}
