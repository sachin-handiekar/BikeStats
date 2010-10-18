package com.appengine.dockstats;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.entities.DockStation;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.Map;

public class FeedTableData {
    private MemcacheService memcache;

    public FeedTableData() {
        memcache = MemcacheServiceFactory.getMemcacheService();
    }

    /**
     * Get Dock Stats for all docks.
     * @return xmlFeed
     */
    public String getTableData() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("{\"aaData\": [");

        if (memcache.contains("cycleData")) {
            String                    time = (String) memcache.get("updateTime");
            Map<Integer, DockStation> mp   = (Map<Integer, DockStation>) memcache.get("cycleData");
            Iterator                  it   = mp.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                buffer.append("[");

                DockStation ds = (DockStation) pairs.getValue();

                buffer.append("\"");
                buffer.append(ds.getId());
                buffer.append("\"");
                buffer.append(Constants.COMMA);
                buffer.append("\"");
                buffer.append(ds.getName());
                buffer.append("\"");
                buffer.append(Constants.COMMA);
                buffer.append("\"");
                buffer.append(ds.getNbBikeAvailable());
                buffer.append("\"");
                buffer.append(Constants.COMMA);
                buffer.append("\"");
                buffer.append(ds.getNbEmptyDocks());
                buffer.append("\"");
                buffer.append(Constants.COMMA);
                buffer.append("\"");
                buffer.append(time);
                buffer.append("\"");
                buffer.append("]");

                if (it.hasNext()) {
                    buffer.append(Constants.COMMA);
                }
            }
        } else {

            // Error
        }

        buffer.append("] }");

        return buffer.toString();
    }
}
