/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package test.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import componenttest.app.FATServlet;
import test.bundle.TestService;

@SuppressWarnings("serial")
@WebServlet("/TTCSS")
public class TestTaskContextServiceServlet extends FATServlet {
    @Test
    public void basicTest() throws Exception {
        System.out.println("Test is running in an HttpServlet");
        Assert.assertTrue("Can also use JUnit assertions", true);
    }

    @Test
    public void testHttpServletRequest(HttpServletRequest request, HttpServletResponse resp) throws Exception {
        resp.getWriter().println("Running test method 'testHttpServletRequest'");
    }
    
    @Test
    public void testTestServiceIsActivated() {
        Assert.assertNotSame("TestService should have been activated", 0, TestService.getActivatedCount());
    }
}
