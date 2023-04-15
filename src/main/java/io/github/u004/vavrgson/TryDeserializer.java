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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.vavr.control.Try;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * {@code TryDeserializer} is the JSON deserializer
 * for vavr's {@link Try} type.
 *
 * <p><hr>
 * <pre>{@code
 *     new GsonBuilder()
 *             .registerTypeAdapter(Try.class, new TryDeserializer())
 *             .create();
 * }</pre>
 * <hr>
 */
@SuppressWarnings("unused")
public final class TryDeserializer implements JsonDeserializer<Try<?>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Try<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		Object object = context.deserialize(json, ((ParameterizedType) type).getActualTypeArguments()[0]);

		if (object == null) {
			return Try.failure(new NullPointerException());
		}

		return Try.success(object);
	}
}
