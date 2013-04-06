package com.mycompany.restfuse;

import static com.eclipsesource.restfuse.Assert.*;
import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Request;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Callback;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Rule;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;

@RunWith(HttpJUnitRunner.class)
public class GetResource1Test {

    @Rule
    public Destination restfuse = getDestination();
    @Context
    private Response response;

    private Destination getDestination() {
        Destination destination = new Destination(this, "http://localhost:8080/web-app2/webresources/services");
        return destination;
    }

    @HttpTest(method = Method.GET, path = "/getResource1")
    @Callback(port = 9090, path = "/callback", resource = TestCallbackResource.class, timeout = 10000)
    public void testGetResource1() {
        Logger.getLogger(GetResource2Test.class.getName()).log(Level.INFO, "testGetResource1");
        assertAccepted(response);
    }

//    private Destination getDestination() {
//        Destination destination = new Destination(this,
//                "http://search.maven.org/remotecontent?filepath="
//                + "com/restfuse/com.eclipsesource.restfuse/{version}/");
//        RequestContext context = destination.getRequestContext();
//        context.addPathSegment("file", "com.eclipsesource.restfuse-1.1.1").addPathSegment("version", "1.1.1");
//        return destination;
//    }
    private class TestCallbackResource extends DefaultCallbackResource {

        public TestCallbackResource() {
            Logger.getLogger(GetResource2Test.class.getName()).log(Level.INFO, "TestCallbackResource init");
        }

        @Override
        public Response get(Request request) {
            Logger.getLogger(GetResource2Test.class.getName()).log(Level.INFO, "TestCallbackResource get");

            Runner runner = null;
            try {
                runner = new HttpJUnitRunner(GetResource2Test.class);
            } catch (InitializationError ex) {
                Logger.getLogger(GetResource1Test.class.getName()).log(Level.SEVERE, null, ex);
            }

            JUnitCore juc = new JUnitCore();
            juc.run(runner);

//            assertNotNull(request.getHeaders().get("some header").get(0));
            return super.get(request);
        }
    }
}
