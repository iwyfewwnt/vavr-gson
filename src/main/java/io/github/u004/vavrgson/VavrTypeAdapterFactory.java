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
	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Object typeAdapter = getTypeAdapter(type.getRawType());

		if (typeAdapter == null) {
			return null;
		}

		JsonSerializer<T> serializer = (JsonSerializer<T>) typeAdapter;
		JsonDeserializer<T> deserializer = (JsonDeserializer<T>) typeAdapter;

		return new TreeTypeAdapter<>(serializer, deserializer, gson, type, null, false);
	}

	private static Object getTypeAdapter(Class<?> clazz) {
		if (clazz == Option.class) {
			return new OptionTypeAdapter();
		}

		if (clazz == Lazy.class) {
			return new LazyTypeAdapter();
		}

		if (clazz == Try.class) {
			return new TryTypeAdapter();
		}

		return null;
	}
}
