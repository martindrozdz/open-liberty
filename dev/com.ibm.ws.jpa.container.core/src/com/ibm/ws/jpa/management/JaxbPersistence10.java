/*******************************************************************************
 * Copyright (c) 2009, 2010 IBM Corporation and others.
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
package com.ibm.ws.jpa.management;

import static com.ibm.ws.jpa.management.JPAConstants.JPA_RESOURCE_BUNDLE_NAME;
import static com.ibm.ws.jpa.management.JPAConstants.JPA_TRACE_GROUP;
import static com.ibm.ws.jpa.management.JPAConstants.PERSISTENCE_10_XML_JAXB_PACKAGE_NAME;

import java.util.ArrayList;
import java.util.List;

import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;
import com.ibm.ws.jpa.pxml10.Persistence;
import com.ibm.ws.jpa.pxml10.Persistence.PersistenceUnit;

/**
 * Provides a concrete implementation of the JaxbPersistence abstraction,
 * representing the <persistence> stanza in a 1.0 version persistence.xml. <p>
 * 
 * This implementation wraps the JAXB generated class that represent a
 * <persistence> stanza in a 1.0 version persistence.xml. <p>
 * 
 * Get methods on the generated JAXB class which return other JAXB generated
 * classes will instead return either a java primitive or javax.persistence
 * representation of that data or another abstraction interface; allowing the
 * client of this class to be coded independent of the JAXB implementation. <p>
 **/
class JaxbPersistence10 extends JaxbPersistence
{
    private static final TraceComponent tc = Tr.register(JaxbPersistence10.class,
                                                         JPA_TRACE_GROUP,
                                                         JPA_RESOURCE_BUNDLE_NAME);

    static final String SCHEMA_VERSION = "1.0";

    private static final String XSD_NAME = "persistence_1_0.xsd";

    /** The <persistence-unit>s found in this persistence.xml **/
    private List<JaxbPUnit> ivPUnits;

    /**
     * Constructs an instance representing the specified persistence.xml file,
     * parsing using JAXB and validating against the 1.0 schema version.
     **/
    JaxbPersistence10(JPAPXml pxml)
    {
        super(pxml, PERSISTENCE_10_XML_JAXB_PACKAGE_NAME, XSD_NAME); // d656864
    }

    void setResult(Object result) // d656864
    {
        Persistence p = (Persistence) result;

        // Obtain the list of <persistence-unit>s and provide an abstraction
        // for them as well.
        List<PersistenceUnit> pUnits = p.getPersistenceUnit();

        if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled())
            Tr.debug(tc, "JaxbPersistence10.setResult : persistence units : " +
                         ((pUnits == null) ? "null" : pUnits.size()));

        if (pUnits != null)
        {
            ivPUnits = new ArrayList<JaxbPUnit>(pUnits.size());
            for (PersistenceUnit pUnit : pUnits)
            {
                ivPUnits.add(new JaxbPUnit10(pUnit));
            }
        }
        else
        {
            // Insure an empty list is returned to avoid NPE
            ivPUnits = new ArrayList<JaxbPUnit>();
        }
    }

    /**
     * Gets the <persistence-unit>s defined in this <persistence> stanza.
     * 
     * @return <persistence-unit>s defined in this <persistence> stanza.
     */
    public List<JaxbPUnit> getPersistenceUnit()
    {
        return ivPUnits;
    }

    /**
     * Gets the persistence.xml schema version.
     * 
     * @return persistence.xml schema version.
     */
    public String getVersion()
    {
        return SCHEMA_VERSION;
    }

}
