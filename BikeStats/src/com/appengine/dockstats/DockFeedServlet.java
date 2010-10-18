package com.appengine.dockstats;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.entities.DockStation;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DockFeedServlet
 * 
 * @author Sachin Handiekar
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DockFeedServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();

        out.println("-- TFL Cycle Hire Crawler --");

        URL            page   = new URL("https://web.barclayscyclehire.tfl.gov.uk/maps");
        URLConnection  urlc   = page.openConnection();
        BufferedReader br     = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        StringBuffer   buffer = new StringBuffer();
        String         temp   = Constants.EMPTY_STRING;

        while ((temp = br.readLine()) != null) {
            buffer.append(temp);
        }

        br.close();

        Map<Integer, DockStation> mp      = new HashMap<Integer, DockStation>();
        DockStation               station = null;

        try {
            Pattern regex =
                Pattern.compile(
                    "(\\{id:\"(\\d+)\".+?name:\"(.+?)\".+?lat:\"(.+?)\".+?long:\"(.+?)\".+?nbBikes:\"(\\d+)\".+?nbEmptyDocks:\"(\\d+)\".+?installed:\"(.+?)\".+?locked:\"(.+?)\".+?temporary:\"(.+?)\"\\})");
            Matcher regexMatcher = regex.matcher(buffer);

            while (regexMatcher.find()) {
                station = new DockStation();

                int id = Integer.parseInt(regexMatcher.group(2));

                station.setId(id);
                station.setName(regexMatcher.group(3));
                station.setLatitude((Double.parseDouble(regexMatcher.group(4))));
                station.setLongitude((Double.parseDouble(regexMatcher.group(5))));
                station.setNbBikeAvailable(Integer.parseInt(regexMatcher.group(6)));
                station.setNbEmptyDocks(Integer.parseInt(regexMatcher.group(7)));
                station.setInstalled(Boolean.getBoolean(regexMatcher.group(8)));
                station.setLocked(Boolean.getBoolean(regexMatcher.group(9)));
                station.setTemporary(Boolean.getBoolean(regexMatcher.group(10)));
                mp.put(id, station);
            }
        } catch (PatternSyntaxException ex) {

            // Syntax error in the regular expression
        }

        String updatedTime = Constants.EMPTY_STRING;

        // Get Time
        try {
            Pattern regex        = Pattern.compile("(var hour='(\\d\\d:\\d\\d)')");
            Matcher regexMatcher = regex.matcher(buffer);

            while (regexMatcher.find()) {
                updatedTime = regexMatcher.group(2);
            }
        } catch (PatternSyntaxException ex) {

            // Syntax error in the regular expression
        }

        MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

        memcache.put("cycleData", mp, Expiration.byDeltaSeconds(120));
        memcache.put("updateTime", updatedTime, Expiration.byDeltaSeconds(120));
    }
}
