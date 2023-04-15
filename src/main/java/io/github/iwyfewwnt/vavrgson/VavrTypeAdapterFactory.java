/*
 * Copyright 2023 iwyfewwnt
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

package io.github.iwyfewwnt.vavrgson;

import com.google.gson.*;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import io.github.iwyfewwnt.vavrgson.deserializers.VavrLazyJsonDeserializer;
import io.github.iwyfewwnt.vavrgson.deserializers.VavrOptionJsonDeserializer;
import io.github.iwyfewwnt.vavrgson.deserializers.VavrTryJsonDeserializer;
import io.vavr.Lazy;
import io.vavr.control.Option;
import io.vavr.control.Try;

/**
 * A type adapter factory.
 *
 * <p><hr>
 * <pre>{@code
 *     new GsonBuilder()
 *             .registerTypeAdapterFactory(new VavrTypeAdapterFactory())
 *             .create();
 * }</pre>
 * <hr>
 */
@SuppressWarnings({"unused", "unchecked"})
public final class VavrTypeAdapterFactory implements TypeAdapterFactory {

	/**
	 * Initialize a {@link VavrTypeAdapterFactory} instance.
	 */
	public VavrTypeAdapterFactory() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Object typeAdapter = getTypeAdapter(type.getRawType());

		if (typeAdapter == null) {
			return null;
		}

		JsonSerializer<T> serializer = null;
		if (typeAdapter instanceof JsonSerializer) {
			serializer = (JsonSerializer<T>) typeAdapter;
		}

		JsonDeserializer<T> deserializer = null;
		if (typeAdapter instanceof JsonDeserializer) {
			deserializer = (JsonDeserializer<T>) typeAdapter;
		}

		// #nullSafe argument is added since google/gson#2.10
		return new TreeTypeAdapter<>(serializer, deserializer, gson, type, null, false);
	}

	/**
	 * Get a type adapter for the provided class.
	 *
	 * @param clazz		class type of the type adapter
	 * @return			type adapter or {@code null}
	 */
	private static Object getTypeAdapter(Class<?> clazz) {
		if (clazz == Option.class) {
			return new VavrOptionJsonDeserializer();
		}

		if (clazz == Lazy.class) {
			return new VavrLazyJsonDeserializer();
		}

		if (clazz == Try.class) {
			return new VavrTryJsonDeserializer();
		}

		return null;
	}
}
