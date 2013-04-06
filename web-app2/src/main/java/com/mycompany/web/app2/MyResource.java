/* *****************************************************************************
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * ****************************************************************************/
package com.mycompany.web.app2;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author desmondkirrane
 */
@Path("services")
public class MyResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MyResource
     */
    public MyResource() {
    }

    @GET
    @Path("getResource1")
    @Produces("application/xml")
    public String getResource1() {
        Logger.getLogger(MyResource.class.getName()).log(Level.INFO, "getResource1");
        doCallback();
        return "getResource1";
    }

    @GET
    @Path("getResource2")
    @Produces("application/xml")
    public String getResource2() {
        Logger.getLogger(MyResource.class.getName()).log(Level.INFO, "getResource2");
        return "getResource2";
    }

    private void doCallback() {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Logger.getLogger(MyResource.class.getName()).log(Level.INFO, "doCallback");

                Client client = Client.create();

                WebResource webResource = client.resource("http://localhost:9090/callback");

                String input = "</hello>";

                ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);

                if (response.getStatus() != 204) {
                    Logger.getLogger(MyResource.class.getName()).log(Level.SEVERE, "doCallback failed {0}", response.getStatus());
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
                }

                System.out.println("Output from Server .... \n");
                String output = response.getEntity(String.class);
                System.out.println(response);
            }
        };

        executor.schedule(runnable, 5, TimeUnit.SECONDS);
    }
}
