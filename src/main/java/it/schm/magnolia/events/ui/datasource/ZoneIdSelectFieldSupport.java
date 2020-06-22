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

package it.schm.magnolia.events.ui.datasource;

import com.vaadin.data.Converter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.editor.LocaleContext;
import info.magnolia.ui.field.SelectFieldSupport;
import it.schm.magnolia.events.ui.converter.ZoneIdConverter;

import javax.inject.Inject;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Support for select fields backed by Java's {@link ZoneId}s.
 */
public class ZoneIdSelectFieldSupport implements SelectFieldSupport<ZoneId> {

    protected static final List<ZoneId> ZONE_IDS = ZoneId.getAvailableZoneIds().stream()
            .sorted().map(ZoneId::of).collect(Collectors.toList());

    private final ZoneIdItemCaptionGenerator zoneIdItemCaptionGenerator;
    private final ZoneIdConverter zoneIdConverter;

    /**
     * Creates an instance of this {@link SelectFieldSupport} with the provided {@link LocaleContext}.
     *
     * @param localeContext The item caption generator for ZoneIds
     * @param zoneIdConverter The default converter for ZoneIds
     */
    @Inject
    public ZoneIdSelectFieldSupport(LocaleContext localeContext, ZoneIdConverter zoneIdConverter) {
        this.zoneIdItemCaptionGenerator = new ZoneIdItemCaptionGenerator(localeContext);
        this.zoneIdConverter = zoneIdConverter;
    }

    /**
     * Returns a {@link DataProvider} implementation capable of providing available {@link ZoneId}s.
     *
     * @return a {@link DataProvider} implementation capable of providing available {@link ZoneId}s
     */
    @Override
    public DataProvider<ZoneId, ?> getDataProvider() {
        return new ListDataProvider<>(ZONE_IDS);
    }

    /**
     * Returns an {@link ItemCaptionGenerator} which returns the display name for a given time zone.
     *
     * @return an {@link ItemCaptionGenerator} which returns the display name for a given time zone
     * @see ZoneId#getDisplayName(TextStyle, Locale)
     */
    @Override
    public ItemCaptionGenerator<ZoneId> getItemCaptionGenerator() {
        return zoneIdItemCaptionGenerator;
    }

    /**
     * Returns an {@link IconGenerator} which returns the appropriate icon for time zones.
     *
     * @return an {@link IconGenerator} which returns the appropriate icon for time zones
     * @see MagnoliaIcons#TIMESTAMP
     */
    @Override
    public IconGenerator<ZoneId> getIconGenerator() {
        return locale -> MagnoliaIcons.TIMESTAMP;
    }

    /**
     * Returns a converter capable of converting {@link ZoneId} to and from a string.
     *
     * @return a converter capable of converting {@link ZoneId} to and from a string
     */
    @Override
    public Converter<ZoneId, String> defaultConverter() {
        return zoneIdConverter;
    }

}
