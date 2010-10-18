package com.appengine.dockstats.webservice;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.resource.DockStat;
import com.appengine.dockstats.resource.DockStats;
import com.appengine.dockstats.resource.TableData;
import com.appengine.dockstats.resource.TestGetParam;
import com.appengine.dockstats.resource.UpdatedTime;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * 
 * @author Sachin Handiekar
 * @version 1.0
 */
public class DSWebService extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/bikestats", DockStats.class);
        router.attach("/bikestat/{dockID}", DockStat.class);
        router.attach("/tabledata", TableData.class);
        router.attach("/updatedTime", UpdatedTime.class);
        router.attach("/test", TestGetParam.class);

        return router;
    }
}
