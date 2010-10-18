package com.appengine.dockstats;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.entities.DockStation;
import com.appengine.dockstats.resource.StringBufferOutputStream;

import com.csvreader.CsvWriter;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.ho.yaml.YamlEncoder;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A class to grab the data from the cache.
 * </p>
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public class DataGrabber {
    private MemcacheService memcache;

    public DataGrabber() {
        memcache = MemcacheServiceFactory.getMemcacheService();
    }

    /**
     * Get Dock Stats for all docks.
     * @return xmlFeed
     */
    public String getStats() {
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

    /**
     * <p>
     * Get Dock Stats for a specific dockID.
     * </p>
     *
     * @param dockID
     *                  dockID for a docking station.
     * @return xmlFeed
     */
    public String getStats(int dockID) {
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

    /**
     * <p>
     * Get stats as json.
     * </p>
     * @return
     */
    public String getStatsAsJSON() {
        String        xml           = getStats();
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON          json          = xmlSerializer.read(xml);
        String        jsonData      = json.toString(2);

        return jsonData;
    }

    /**
     * <p>
     * Get stats as json.
     * </p>
     *
     * @param dockID
     *                  dockStation id.
     * @return jsonFeed
     *
     */
    public String getStatsAsJSON(int dockID) {
        String        xml           = getStats(dockID);
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON          json          = xmlSerializer.read(xml);
        String        jsonData      = json.toString(2);

        return jsonData;
    }

    /**
     * <p>
     * Get stats as YAML.
     * </p>
     * @return stats as YAML.
     */
    public String getStatsAsYAML() {
        String                   temp         = "YAML";
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
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String getStatsAsCSV() throws IOException {
        String          temp       = "CSV";
        StringWriter    strWriter  = new StringWriter();
        char            delimeter  = ',';
        CsvWriter       writer     = new CsvWriter(strWriter, delimeter);
        MemcacheService memcache   = MemcacheServiceFactory.getMemcacheService();
        String[]        columnName = {
            "ID", "Name", "Latitude", "Longitude", "BikesAvailable", "EmptySlots", "Installed", "Locked", "Temporary",
            "UpdateTime"
        };

        writer.writeRecord(columnName);

        if (memcache.contains("cycleData")) {

            // buffer.append("<updatedOn>" + memcache.get("updateTime") + "</updatedOn>");
            Map<Integer, DockStation> mp           = (Map<Integer, DockStation>) memcache.get("cycleData");
            Iterator                  it           = mp.entrySet().iterator();
            String                    updateTime   = (String) memcache.get("updateTime");
            List<String>              columnValues = null;

            // writer.writeRecord(columns);
            while (it.hasNext()) {
                columnValues = new ArrayList<String>();

                Map.Entry   pairs = (Map.Entry) it.next();
                DockStation ds    = (DockStation) pairs.getValue();

                columnValues.add(ds.getId() + "");
                columnValues.add(ds.getName() + "");
                columnValues.add(ds.getLatitude() + "");
                columnValues.add(ds.getLongitude() + "");
                columnValues.add(ds.getNbBikeAvailable() + "");
                columnValues.add(ds.getNbEmptyDocks() + "");
                columnValues.add(ds.isInstalled() + "");
                columnValues.add(ds.isLocked() + "");
                columnValues.add(ds.isTemporary() + "");
                columnValues.add(updateTime);

                String str[] = (String[]) columnValues.toArray(new String[columnValues.size()]);

                writer.writeRecord(str);
            }
        } else {

            // buffer.append("<error>Data Feed not available</error>");
        }

        temp = strWriter.toString();

        return temp;
    }

    /**
     * <p>
     * Get time on which the feed was updated.
     * </p>
     *
     * @return updatedTime
     */
    public String getUpdateTime() {
        String result = Constants.EMPTY_STRING;

        if (memcache.contains("updateTime")) {
            result = "Updated on " + memcache.get("updateTime");
        } else {
            result = "Couldn't get the updated time.";
        }

        return result;
    }
}
