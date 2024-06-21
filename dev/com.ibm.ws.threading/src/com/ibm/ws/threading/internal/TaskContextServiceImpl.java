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

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

import com.ibm.ws.threading.TaskContextFactory;
import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContext.Type;
import com.ibm.wsspi.threading.TaskContextService;

@Component
public class TaskContextServiceImpl implements TaskContextFactory, TaskContextService {
    private final ThreadLocal<TaskContext> ctx = new ThreadLocal<>();

    @Override
    public TaskContext getTaskContext() {
        return ctx.get();
    }

    @Override
    public TaskContextSetter createTaskContext(Type type) {
        Optional.ofNullable(ctx.get()).ifPresent(tc -> {
            throw new IllegalStateException("Cannot create context if it already exists. Existing context of type " + tc.type() + " must be zapped first.");
        });
        TaskContextImpl tc = new TaskContextImpl(type);
        ctx.set(tc);
        return tc;
    }

    @Override
    public void zapTaskContext() {
        ctx.remove();
    }
}
