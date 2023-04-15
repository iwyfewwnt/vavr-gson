/*
 * Copyright 2022 u004
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.u004.vavrgson;

import com.google.gson.*;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import io.vavr.Lazy;
import io.vavr.control.Option;
import io.vavr.control.Try;

/**
 * A type adapter factory for Vavr's types.
 *
 * <p><hr>
 * <pre>{@code
 *     new GsonBuilder()
 *             .registerTypeAdapterFactory(new VavrTypeAdapterFactory())
 *             .create();
 * }</pre>
 * <hr>
 */
@SuppressWarnings("unused")
public final class VavrTypeAdapterFactory implements TypeAdapterFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Object typeAdapter = null;

		Class<?> clazz = type.getRawType();

		if (clazz == Option.class) {
			typeAdapter = new OptionTypeAdapter();
		} else if (clazz == Lazy.class) {
			typeAdapter = new LazyTypeAdapter();
		} else if (clazz == Try.class) {
			typeAdapter = new TryTypeAdapter();
		}

		if (typeAdapter == null) {
			return null;
		}

		//noinspection unchecked
		return new TreeTypeAdapter<>((JsonSerializer<T>) typeAdapter, (JsonDeserializer<T>) typeAdapter, gson, type, null, false);
	}
}
