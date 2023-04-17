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

package io.github.iwyfewwnt.vavrgson.deserializers;

import com.google.gson.*;
import io.vavr.control.Try;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A {@link Try} JSON deserializer.
 */
@SuppressWarnings("unused")
public final class VavrTryJsonDeserializer implements JsonDeserializer<Try<?>> {

	/**
	 * Initialize a {@link VavrTryJsonDeserializer} instance.
	 */
	public VavrTryJsonDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Try<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
		if (json == null || json.isJsonNull()) {
			return Try.failure(new NullPointerException());
		}

		try {
			Object obj = context.deserialize(json, ((ParameterizedType) type).getActualTypeArguments()[0]);
			if (obj == null) {
				return Try.failure(new NullPointerException());
			}

			return Try.success(obj);
		} catch (Throwable t) {
			t.printStackTrace();

			return Try.failure(t);
		}
	}
}
