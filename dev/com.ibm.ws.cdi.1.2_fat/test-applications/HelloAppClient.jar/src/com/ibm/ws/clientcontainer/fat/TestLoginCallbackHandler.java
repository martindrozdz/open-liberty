/*
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * WLP Copyright IBM Corp. 2015
 *
 * The source code for this program is not published or otherwise divested 
 * of its trade secrets, irrespective of what has been deposited with the 
 * U.S. Copyright Office.
 */
package com.ibm.ws.clientcontainer.fat;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * A login callback handler which is a bean but doesn't do anything
 * <p>
 * This class is not used directly in a test but is here to test that we don't completely break if the login callback handler is registered as a bean.
 */
@ApplicationScoped
public class TestLoginCallbackHandler implements CallbackHandler {

    /*
     * (non-Javadoc)
     * 
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        // Do nothing
    }

}
