package com.mycompany.restfuse;

import static com.eclipsesource.restfuse.Assert.*;
import com.eclipsesource.restfuse.DefaultCallbackResource;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Request;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import static junit.framework.TestCase.*;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(HttpJUnitRunner.class)
public class RestfuseTest {

    @Rule
    public Destination restfuse = getDestination();
    @Context
    private Response response;

    @HttpTest(method = Method.GET, path = "/getResource1")
    public void checkUrlStatus() {
        assertOk(response);
    }

//    @HttpTest(method = Method.GET, path = "/test")
//    @Callback(port = 9090, path = "/asynchron", resource = TestCallbackResource.class, timeout = 10000)
//    public void testMethod() {
//        assertAccepted(response);
//    }
    private Destination getDestination() {
        Destination destination = new Destination(this, "http://localhost:8080/web-app2/webresources/services");
        return destination;
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

        @Override
        public Response post(Request request) {
            assertNotNull(request.getHeaders().get("some header").get(0));
            return super.post(request);
        }
    }
}
