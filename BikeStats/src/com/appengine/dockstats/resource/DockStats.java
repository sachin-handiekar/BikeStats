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

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

/**
 * DockStats resource file.
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class DockStats extends ServerResource {
    @Get
    public Representation getDefault() {
        Request              request        = this.getRequest();
        Form                 form           = request.getResourceRef().getQueryAsForm();
        DataGrabber          dg             = new DataGrabber();
        String               xml            = dg.getStats();
        StringRepresentation representation = null;

        representation = new StringRepresentation(xml, MediaType.APPLICATION_XML);

        for (Parameter parameter : form) {
            if (parameter.getName().equals("format")) {
                if (parameter.getValue().equals("xml")) {
                    representation = new StringRepresentation(xml, MediaType.APPLICATION_XML);
                } else if (parameter.getValue().equals("json")) {
                    String json = dg.getStatsAsJSON();

                    representation = new StringRepresentation(json, MediaType.APPLICATION_JSON);
                } else if (parameter.getValue().equals("csv")) {
                    String csv = null;

                    try {
                        csv = dg.getStatsAsCSV();
                    } catch (IOException e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    representation = new StringRepresentation(csv, MediaType.TEXT_CSV);
                } else if (parameter.getValue().equals("yaml")) {
                    String yaml = dg.getStatsAsYAML();

                    representation = new StringRepresentation(yaml, MediaType.TEXT_PLAIN);
                }
            }
        }

        return representation;
    }

    /*
     *
     * @Get("xml")
     * public Representation getXML() {
     *   DataGrabber          dg             = new DataGrabber();
     *   String               xml            = dg.getStats();
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
     *   DataGrabber          dg             = new DataGrabber();
     *   String               xml            = dg.getStatsAsJSON();
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
