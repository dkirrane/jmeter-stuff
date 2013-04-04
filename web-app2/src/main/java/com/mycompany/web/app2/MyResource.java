/* *****************************************************************************
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * ****************************************************************************/
package com.mycompany.web.app2;

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
        return "getResource1";
    }

    @GET
    @Path("getResource2")
    @Produces("application/xml")
    public String getResource2() {
        return "getResource2";
    }
}
