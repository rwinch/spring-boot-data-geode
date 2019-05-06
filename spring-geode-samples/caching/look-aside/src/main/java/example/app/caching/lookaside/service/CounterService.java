/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package example.app.caching.lookaside.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * The CounterService class...
 *
 * @author John Blum
 * @since 1.0.0
 */
// tag::class[]
@Service
public class CounterService {

	private ConcurrentMap<String, AtomicLong> namedCounterMap = new ConcurrentHashMap<>();

	@Cacheable("Counters")
	public long getCachedCount(String counterName) {
		return getCount(counterName);
	}

	@CachePut("Counters")
	public long getCount(String counterName) {

		AtomicLong counter = this.namedCounterMap.get(counterName);

		if (counter == null) {

			counter = new AtomicLong(0L);

			AtomicLong existingCounter = this.namedCounterMap.putIfAbsent(counterName, counter);

			counter = existingCounter != null ? existingCounter : counter;
		}

		return counter.incrementAndGet();
	}

	@CacheEvict("Counters")
	public void resetCounter(String counterName) {
		this.namedCounterMap.remove(counterName);
	}
}
// end::class[]
