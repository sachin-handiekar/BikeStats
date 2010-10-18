package com.appengine.dockstats.resource;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.DataGrabber;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * DockStat resource file.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class DockStat extends ServerResource {
    @Get
    public Representation getDefault() {
        int dockID = -1;

        try {
            dockID = Integer.parseInt((String) getRequest().getAttributes().get("dockID"));
        } catch (Exception e) {
            dockID = -1;
        }

        Request              request        = this.getRequest();
        Form                 form           = request.getResourceRef().getQueryAsForm();
        DataGrabber          dg             = new DataGrabber();
        String               xml            = dg.getStats(dockID);
        StringRepresentation representation = null;

        representation = new StringRepresentation(xml, MediaType.APPLICATION_XML);

        for (Parameter parameter : form) {
            if (parameter.getName().equals("format")) {
                if (parameter.getValue().equals("xml")) {
                    representation = new StringRepresentation(xml, MediaType.APPLICATION_XML);
                } else if (parameter.getValue().equals("json")) {
                    String json = dg.getStatsAsJSON(dockID);

                    representation = new StringRepresentation(json, MediaType.APPLICATION_JSON);
                }
            }
        }

        return representation;
    }

    /*
     * @Get("xml")
     * public Representation getXML() {
     *
     *
     *   DataGrabber          dg             = new DataGrabber();
     *
     *   StringRepresentation representation = null;
     *
     *   representation = new StringRepresentation(xml, MediaType.APPLICATION_XML);
     *
     *   if (xml != null) {
     *       return representation;
     *   } else {
     *       setStatus(Status.CLIENT_ERROR_NOT_FOUND);
     *
     *       return null;
     *   }
     * }
     *
     * @Get("json")
     * public Representation getJSON() {
     *   int dockID = -1;
     *
     *   try {
     *       dockID = Integer.parseInt((String) getRequest().getAttributes().get("dockID"));
     *   } catch (Exception e) {
     *       dockID = -1;
     *   }
     *
     *   DataGrabber          dg             = new DataGrabber();
     *   String               xml            = dg.getStatsAsJSON(dockID);
     *   StringRepresentation representation = null;
     *
     *   representation = new StringRepresentation(xml, MediaType.APPLICATION_JSON);
     *
     *   if (xml != null) {
     *       return representation;
     *   } else {
     *       setStatus(Status.CLIENT_ERROR_NOT_FOUND);
     *
     *       return null;
     *   }
     * }
     */
}
