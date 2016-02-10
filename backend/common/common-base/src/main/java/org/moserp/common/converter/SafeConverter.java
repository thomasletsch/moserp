/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.moserp.common.converter;

import org.springframework.core.convert.converter.Converter;

public abstract class SafeConverter<S, T> implements Converter<S, T> {
    @Override
    public T convert(S source) {
        if (source == null) {
            return null;
        }
        try {
            return doConvert(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T doConvert(S source) throws Exception;
}
