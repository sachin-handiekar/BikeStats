package com.appengine.dockstats.factory;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.entities.DockStation;
import com.appengine.dockstats.resource.StringBufferOutputStream;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import org.ho.yaml.YamlEncoder;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class YAMLBikeData extends BikeData {
    @Override
    public String getData() {
        String                   temp         = "YAML";
        MemcacheService          memcache     = MemcacheServiceFactory.getMemcacheService();
        StringBufferOutputStream outputStream = new StringBufferOutputStream();
        YamlEncoder              enc          = new YamlEncoder(outputStream);
        HashMap<String, String>  columnValues = null;

        if (memcache.contains("cycleData")) {

            // buffer.append("<updatedOn>" + memcache.get("updateTime") + "</updatedOn>");
            Map<Integer, DockStation> mp         = (Map<Integer, DockStation>) memcache.get("cycleData");
            Iterator                  it         = mp.entrySet().iterator();
            String                    updateTime = (String) memcache.get("updateTime");

            while (it.hasNext()) {
                columnValues = new HashMap<String, String>();

                Map.Entry   pairs = (Map.Entry) it.next();
                DockStation ds    = (DockStation) pairs.getValue();

                columnValues.put("ID", ds.getId() + "");
                columnValues.put("Name", ds.getName() + "");
                columnValues.put("Latitude", ds.getLatitude() + "");
                columnValues.put("Longitude", ds.getLongitude() + "");
                columnValues.put("BikesAvailable", ds.getNbBikeAvailable() + "");
                columnValues.put("EmptySlots", ds.getNbEmptyDocks() + "");
                columnValues.put("Installed", ds.isInstalled() + "");
                columnValues.put("Locked", ds.isLocked() + "");
                columnValues.put("Temporary", ds.isTemporary() + "");
                columnValues.put("UpdateTime", updateTime + "");
                enc.writeObject(columnValues);
            }
        } else {
            columnValues = new HashMap<String, String>();
            columnValues.put("Error", "Data Feed not available");
            enc.writeObject(columnValues);
        }

        enc.close();
        temp = outputStream.toString();

        return temp;
    }

    @Override
    public String getData(int id) {

        // TODO Auto-generated method stub
        return null;
    }
}
