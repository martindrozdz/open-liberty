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

public interface TaskContextService {
    /**
     * Gets the context of the task currently being submitted.
     *
     * @return the task context if provided,
     *         <code>null</code> if no task is being submitted
     *         or if no context is available for the current task.
     */
    public TaskContext getTaskContext();
}
