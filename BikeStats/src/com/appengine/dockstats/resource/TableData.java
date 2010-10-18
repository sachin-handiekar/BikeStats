package com.appengine.dockstats.resource;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.FeedTableData;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Sachin Handiekar
 * @version 1.0
 */
public class TableData extends ServerResource {
    @Get
    public Representation getDefault() {
        FeedTableData        td             = new FeedTableData();
        String               jsonData       = td.getTableData();
        StringRepresentation representation = null;

        representation = new StringRepresentation(jsonData, MediaType.APPLICATION_JSON);

        if (jsonData != null) {
            return representation;
        } else {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);

            return null;
        }
    }
}
