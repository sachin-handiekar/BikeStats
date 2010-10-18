package com.appengine.dockstats.resource;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.entities.DockStation;
import com.appengine.dockstats.factory.BikeFactory;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import org.ho.yaml.YamlEncoder;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestGetParam extends ServerResource {
    @Get
    public String getDefault() throws IOException {
    	String                   temp         = "YAML";

    		temp = BikeFactory.generateBikeData(BikeFactory.DataType.YAML).getData();
    		return temp;
    	
    }

    /**
     *  String                   temp         = "YAML";
        MemcacheService          memcache     = MemcacheServiceFactory.getMemcacheService();
        StringBufferOutputStream outputStream = new StringBufferOutputStream();
        YamlEncoder              enc          = new YamlEncoder(outputStream);
        Map<String, String>      columnValues = null;

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
                columnValues.put("UpdateTime", updateTime);
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
     */
    
    /*
     * @Get("xml")
     * public String getXML() throws IOException {
     *   String          temp       = "CSV";
     *
     *
     *   StringWriter    strWriter  = new StringWriter();
     *   char            delimeter  = ',';
     *   CsvWriter       writer     = new CsvWriter(strWriter, delimeter);
     *   MemcacheService memcache   = MemcacheServiceFactory.getMemcacheService();
     *   String[]        columnName = {
     *       "ID", "Name", "Latitude", "Longitude", "BikesAvailable", "EmptySlots", "Installed", "Locked", "Temporary",
     *       "UpdateTime"
     *   };
     *
     *   writer.writeRecord(columnName);
     *
     *   if (memcache.contains("cycleData")) {
     *
     *       // buffer.append("<updatedOn>" + memcache.get("updateTime") + "</updatedOn>");
     *       Map<Integer, DockStation> mp           = (Map<Integer, DockStation>) memcache.get("cycleData");
     *       Iterator                  it           = mp.entrySet().iterator();
     *       String                    updateTime   = (String) memcache.get("updateTime");
     *       List<String>              columnValues = null;
     *
     *       // writer.writeRecord(columns);
     *       while (it.hasNext()) {
     *           columnValues = new ArrayList<String>();
     *
     *           Map.Entry   pairs = (Map.Entry) it.next();
     *           DockStation ds    = (DockStation) pairs.getValue();
     *
     *           columnValues.add(ds.getId() + "");
     *           columnValues.add(ds.getName() + "");
     *           columnValues.add(ds.getLatitude() + "");
     *           columnValues.add(ds.getLongitude() + "");
     *           columnValues.add(ds.getNbBikeAvailable() + "");
     *           columnValues.add(ds.getNbEmptyDocks() + "");
     *           columnValues.add(ds.isInstalled() + "");
     *           columnValues.add(ds.isLocked() + "");
     *           columnValues.add(ds.isTemporary() + "");
     *           columnValues.add(updateTime);
     *
     *           String str[] = (String[]) columnValues.toArray(new String[columnValues.size()]);
     *
     *           writer.writeRecord(str);
     *       }
     *   } else {
     *
     *       // buffer.append("<error>Data Feed not available</error>");
     *   }
     *
     *   temp = strWriter.toString();
     *
     *   return temp;
     * }
     */
}
