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

package org.springframework.geode.security;

import java.util.Properties;

import org.apache.geode.LogWriter;
import org.apache.geode.distributed.DistributedMember;
import org.apache.geode.management.internal.security.ResourceConstants;
import org.apache.geode.security.AuthInitialize;
import org.apache.geode.security.AuthenticationFailedException;

/**
 * Simple, test {@link AuthInitialize} implementation.
 *
 * @author John Blum
 * @see org.apache.geode.security.AuthInitialize
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class TestAuthInitialize implements AuthInitialize {

	private static final String DEFAULT_USERNAME = "test";
	private static final String DEFAULT_PASSWORD = DEFAULT_USERNAME;

	public static TestAuthInitialize create() {
		return new TestAuthInitialize();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void init(LogWriter systemLogger, LogWriter securityLogger) throws AuthenticationFailedException { }

	@Override
	public Properties getCredentials(Properties securityProperties, DistributedMember server, boolean isPeer)
			throws AuthenticationFailedException {

		Properties credentials = new Properties();

		credentials.setProperty(ResourceConstants.USER_NAME,
			securityProperties.getProperty(ResourceConstants.USER_NAME, DEFAULT_USERNAME));

		credentials.setProperty(ResourceConstants.PASSWORD,
			securityProperties.getProperty(ResourceConstants.PASSWORD, DEFAULT_PASSWORD));

		return credentials;
	}

	@Override
	public void close() { }

}
