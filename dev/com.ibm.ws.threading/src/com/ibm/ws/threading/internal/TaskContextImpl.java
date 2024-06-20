/*
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.ibm.ws.threading.internal;

import java.util.EnumMap;
import java.util.stream.Stream;

import com.ibm.ws.threading.TaskContextFactory.TaskContextSetter;
import com.ibm.wsspi.threading.TaskContext;

public class TaskContextImpl implements TaskContextSetter, TaskContext {
    private final Type type;
    private final EnumMap<Key, String> map = new EnumMap<>(Key.class);

    public TaskContextImpl(Type type) {
        this.type = type;
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public String get(Key key) {
        return map.get(key);
    }

    @Override
    public Stream<Key> keys() {
        return map.keySet().stream();
    }

    @Override
    public TaskContextSetter set(Key key, String value) {
        String existingVal = map.putIfAbsent(key, value);
        assert null == existingVal;
        return this;
    }
}
