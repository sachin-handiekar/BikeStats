package com.appengine.dockstats.factory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.appengine.dockstats.entities.DockStation;
import com.csvreader.CsvWriter;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CSVBikeData extends BikeData {

	@Override
	public String getData() {
		   String          temp       = "CSV";
	        StringWriter    strWriter  = new StringWriter();
	        char            delimeter  = ',';
	        CsvWriter       writer     = new CsvWriter(strWriter, delimeter);
	        MemcacheService memcache   = MemcacheServiceFactory.getMemcacheService();
	        String[]        columnName = {
	            "ID", "Name", "Latitude", "Longitude", "BikesAvailable", "EmptySlots", "Installed", "Locked", "Temporary",
	            "UpdateTime"
	        };

	        try {
				writer.writeRecord(columnName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

	                try {
						writer.writeRecord(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        } else {

	            // buffer.append("<error>Data Feed not available</error>");
	        }

	        temp = strWriter.toString();

	        return temp;
	}

	@Override
	public String getData(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
