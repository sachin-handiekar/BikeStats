package com.appengine.dockstats.factory;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.XmlUtils;
import com.appengine.dockstats.entities.DockStation;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.Map;

public class XmlBikeData extends BikeData {
    private MemcacheService memcache;

    public XmlBikeData() {
        memcache = MemcacheServiceFactory.getMemcacheService();
    }

    public String getData() {

        // memcache = MemcacheServiceFactory.getMemcacheService();
        StringBuffer buffer = new StringBuffer();

        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<dockStationList>");

        if (memcache.contains("cycleData")) {
            buffer.append("<updatedOn>" + memcache.get("updateTime") + "</updatedOn>");

            Map<Integer, DockStation> mp = (Map<Integer, DockStation>) memcache.get("cycleData");
            Iterator                  it = mp.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                buffer.append(pairs.getValue());
            }
        } else {
            buffer.append("<error>Data Feed not available</error>");
        }

        buffer.append("</dockStationList>");

        String xml = XmlUtils.formatXml(buffer.toString());

        return xml;
    }

    @Override
    public String getData(int dockID) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<dockStationList>");

        if (memcache.contains("cycleData")) {
            Map<Integer, DockStation> mp = (Map<Integer, DockStation>) memcache.get("cycleData");

            if (mp.containsKey(dockID)) {
                buffer.append("<updatedOn>" + memcache.get("updateTime") + "</updatedOn>");
                buffer.append(mp.get(dockID));
            } else {
                String error = "<error>dockID not found</error>";

                buffer.append(error);
            }
        } else {
            buffer.append("<error>Data Feed not available</error>");
        }

        buffer.append("</dockStationList>");

        String xml = XmlUtils.formatXml(buffer.toString());

        return xml;
    }
}
