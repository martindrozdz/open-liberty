/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.ws.threading.internal;

import static com.ibm.wsspi.threading.TaskContext.Key.APP_NAME;
import static com.ibm.wsspi.threading.TaskContext.Key.BEAN_NAME;
import static com.ibm.wsspi.threading.TaskContext.Key.MODULE_NAME;
import static com.ibm.wsspi.threading.TaskContext.Type.HTTP;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContext.Key;

public class TaskContextServiceTest {

    private TaskContextServiceImpl service;

    @Before
    public void createService() {
        service = new TaskContextServiceImpl();
    }

    @Test
    public void testTaskContextDoesNotExistByDefault() {
        assertThat(service.getTaskContext(), is(nullValue()));
    }

    @Test
    public void testCreatingContext() {
        assertThat(service.createTaskContext(HTTP), is(not(nullValue())));
        TaskContext tc = service.getTaskContext();
        assertThat(tc, is(not(nullValue())));
        assertThat(tc.type(), is(HTTP));
        assertThat(tc.keys().findFirst(), is(Optional.empty()));
    }

    @Test
    public void testAddingContext() {
        service.createTaskContext(HTTP)
                .set(APP_NAME, "test app")
                .set(BEAN_NAME, "test bean")
                .set(MODULE_NAME, "test module");
        TaskContext tc = service.getTaskContext();
        Set<Key> keys = tc.keys().collect(toSet());
        assertThat(keys.size(), is(3));
        assertThat(keys, is(Stream.of(APP_NAME, BEAN_NAME, MODULE_NAME).collect(toSet())));
        assertThat(tc.get(APP_NAME), is("test app"));
        assertThat(tc.get(BEAN_NAME), is("test bean"));
        assertThat(tc.get(MODULE_NAME), is("test module"));
    }
}
