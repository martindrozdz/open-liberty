/*
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.ibm.wsspi.threading;

import java.util.stream.Stream;

/**
 *
 */
public interface TaskContext {
    enum Type {
        HTTP, IIOP, JCA
    }

    enum Key {
        APP_NAME,
        MODULE_NAME,
        BEAN_NAME,
        METHOD_NAME,
        RA_NAME,
        INBOUND_HOSTNAME,
        INBOUND_PORT,
        URI,
    }

    Type type();
    /**
     * Retrieve the specified context information for a task
     *
     * @param key represents the type of information required
     * @return the context information or <code>null</code> if not set
     */
    String get(Key key);
    /**
     * Get all the keys for which context has been provided
     */
    Stream<Key> keys();
}
