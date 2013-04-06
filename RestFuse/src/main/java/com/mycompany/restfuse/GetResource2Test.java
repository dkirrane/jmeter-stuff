package com.mycompany.restfuse;

import static com.eclipsesource.restfuse.Assert.*;
import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(HttpJUnitRunner.class)
public class GetResource2Test {

    @Rule
    public Destination restfuse = getDestination();
    @Context
    private Response response;

    @HttpTest(method = Method.GET, path = "/getResource2")
    public void testGetResource2() {
        Logger.getLogger(GetResource2Test.class.getName()).log(Level.INFO, "testGetResource2");
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
}
