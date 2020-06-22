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

import info.magnolia.ui.datasource.DatasourceDefinition;
import info.magnolia.ui.datasource.DatasourceType;

import java.time.ZoneId;

/**
 * Definition of an {@link java.util.TimeZone} based datasource.
 */
@DatasourceType("zoneIdsDatasource")
public class ZoneIdDataSourceDefinition implements DatasourceDefinition {

    protected static final String NAME = "zoneIds";

    @Override
    public Class<ZoneId> getEntityType() {
        return ZoneId.class;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
