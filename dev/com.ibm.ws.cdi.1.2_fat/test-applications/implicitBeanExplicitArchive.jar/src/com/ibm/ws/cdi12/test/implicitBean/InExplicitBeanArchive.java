/*
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * Copyright IBM Corp. 2015
 *
 * The source code for this program is not published or otherwise divested 
 * of its trade secrets, irrespective of what has been deposited with the 
 * U.S. Copyright Office.
 */
package com.ibm.ws.cdi12.test.implicitBean;

import javax.enterprise.context.Dependent;

import com.ibm.ws.cdi12.test.utils.SimpleAbstract;

/**
 * This bean is in an archive with {@code bean-discovery-mode=all}. This is an <em>explicit</em> bean archive.
 */
@Dependent
public class InExplicitBeanArchive extends SimpleAbstract {}
