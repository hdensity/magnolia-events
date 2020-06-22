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

package it.schm.magnolia.events.ui.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractTemporalConverter<T> implements Converter<Object, String> {

    private static final String NOW = "NOW";

    private final Supplier<T> nowSupplier;
    private final Function<String, T> parserFunction;

    public AbstractTemporalConverter(Supplier<T> nowSupplier, Function<String, T> parserFunction) {
        this.nowSupplier = nowSupplier;
        this.parserFunction = parserFunction;
    }

    @Override
    public Result<String> convertToModel(Object value, ValueContext context) {
        return Result.ok(Objects.isNull(value) ? null : value.toString());
    }

    @Override
    public T convertToPresentation(String value, ValueContext context) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        return NOW.equalsIgnoreCase(value) ? nowSupplier.get() : parserFunction.apply(value);
    }

}
