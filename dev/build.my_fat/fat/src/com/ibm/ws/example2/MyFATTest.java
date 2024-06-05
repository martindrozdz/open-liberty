/**
 *
 */
package com.ibm.ws.example2;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ibm.websphere.simplicity.ShrinkHelper;
import com.ibm.websphere.simplicity.log.Log;

import app2.web.TestServletB;
import componenttest.annotation.Server;
import componenttest.annotation.TestServlet;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.topology.impl.LibertyServer;
import componenttest.topology.utils.FATServletClient;

/**
 *
 */
@RunWith(FATRunner.class)
public class MyFATTest extends FATServletClient {

    public static final String APP_NAME = "app2";
    private static Class<?> logClass = MyFATTest.class;

    @Server("MyFATServer")
    @TestServlet(servlet = TestServletB.class, contextRoot = APP_NAME)
    public static LibertyServer server;

    @BeforeClass
    public static void setUp() throws Exception {
        // Create a WebArchive that will have the file name 'app1.war' once it's written to a file
        // Include the 'app1.web' package and all of it's java classes and sub-packages
        // Automatically includes resources under 'test-applications/APP_NAME/resources/' folder
        // Exports the resulting application to the ${server.config.dir}/apps/ directory
        ShrinkHelper.defaultApp(server, APP_NAME, APP_NAME + ".web");
        server.startServer();
    }

    @Test
    //@Mode(TestMode.LITE)
    public void waitInfLogFileForSSLKeyCreation() throws Exception {
        // CWPKI0803A: SSL certificate created in {x} seconds
        // This method tests if the SSL certificate was created successfully
        // CWPKI0804E verifies if the SSL certificate could not be created at the specified location.
        // The above error code could also be used

        Log.info(logClass, "waitInfLogFileForSSLKeyCreation", "Waiting for 'CWPKI0803A.*ssl'");
        assertNotNull("'CWPKI0803A.*ssl' should have been generated on server", server.waitForStringInLog("CWPKI0803A"));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stopServer();
    }
}
