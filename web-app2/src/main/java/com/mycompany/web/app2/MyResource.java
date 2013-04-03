/* *****************************************************************************
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * ****************************************************************************/
package com.mycompany.web.app2;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

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

    /**
     * Retrieves representation of an instance of com.mycompany.web.app2.MyResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of MyResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
