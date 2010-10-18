package com.appengine.dockstats.resource;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.DataGrabber;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Sachin Handiekar
 * @version 1.0
 */
public class UpdatedTime extends ServerResource {
    @Get
    public String getDefault() {
        DataGrabber td          = new DataGrabber();
        String      updatedTime = td.getUpdateTime();

        return updatedTime;
    }
}
