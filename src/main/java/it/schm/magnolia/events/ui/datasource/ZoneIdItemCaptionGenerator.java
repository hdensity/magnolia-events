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

import com.vaadin.ui.ItemCaptionGenerator;
import info.magnolia.ui.editor.LocaleContext;

import javax.inject.Inject;

import java.time.ZoneId;
import java.time.format.TextStyle;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link ItemCaptionGenerator} implementation to be used with {@link ZoneId}s.
 */
public class ZoneIdItemCaptionGenerator implements ItemCaptionGenerator<ZoneId> {

    private final LocaleContext localeContext;

    @Inject
    public ZoneIdItemCaptionGenerator(LocaleContext localeContext) {
        this.localeContext = checkNotNull(localeContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String apply(ZoneId zoneId) {
        return zoneId.getDisplayName(TextStyle.NARROW, localeContext.getCurrent())
                .replaceAll("/", " / ")
                .replaceAll("_", " ") +
                " (" + zoneId.getDisplayName(TextStyle.FULL, localeContext.getCurrent()) + ")";
    }

}
