package com.appengine.dockstats.entities;

//~--- non-JDK imports --------------------------------------------------------

import com.appengine.dockstats.Constants;
import com.appengine.dockstats.HtmlEncoder;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * DockStation
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
@PersistenceCapable
public class DockStation implements Serializable {

    /**
     *     Default Serial Version.
     */
    private static final long serialVersionUID = 1L;

    /** A field to store the id. */
    @Persistent
    private int id;

    /** A field to store the installed. */
    @Persistent
    private boolean installed;

    /** A field to store the latitude of the docking station. */
    @Persistent
    private double latitude;

    /** A field to store the locked. */
    @Persistent
    private boolean locked;

    /** A field to store the longitude of the docking station. */
    @Persistent
    private double longitude;

    /** A field to store the id. */
    @Persistent
    private String name;

    /** A field to store the number of bikes available. */
    @Persistent
    private int nbBikeAvailable;

    /** A field to store the number of empty docks. */
    @Persistent
    private int nbEmptyDocks;

    /** A field to store the temporary. */
    @Persistent
    private boolean temporary;

    /**
     *
     * DOCUMENT ME!
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the nbBikeAvailable
     */
    public int getNbBikeAvailable() {
        return nbBikeAvailable;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param nbBikeAvailable
     *            the nbBikeAvailable to set
     */
    public void setNbBikeAvailable(int nbBikeAvailable) {
        this.nbBikeAvailable = nbBikeAvailable;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the nbEmptyDocks
     */
    public int getNbEmptyDocks() {
        return nbEmptyDocks;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param nbEmptyDocks
     *            the nbEmptyDocks to set
     */
    public void setNbEmptyDocks(int nbEmptyDocks) {
        this.nbEmptyDocks = nbEmptyDocks;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the installed
     */
    public boolean isInstalled() {
        return installed;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param installed
     *            the installed to set
     */
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param locked
     *            the locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @return the temporary
     */
    public boolean isTemporary() {
        return temporary;
    }

    /**
     *
     * DOCUMENT ME!
     *
     * @param temporary
     *            the temporary to set
     */
    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    /**
     * CycleStation toString method
     *
     * @return xmlString
     */
    public String toString() {
        String ret = "<dockStation ID=\"" + this.id + "\">" + Constants.EMPTY_LINE + "<name>"
                     + HtmlEncoder.encode(this.name) + "</name>" + Constants.EMPTY_LINE + "<latitude>" + this.latitude
                     + "</latitude>" + Constants.EMPTY_LINE + "<longitude>" + this.longitude + "</longitude>"
                     + Constants.EMPTY_LINE + "<bikesAvailable>" + this.nbBikeAvailable + "</bikesAvailable>"
                     + Constants.EMPTY_LINE + "<emptySlots>" + this.nbEmptyDocks + "</emptySlots>"
                     + Constants.EMPTY_LINE + "<installed>" + this.installed + "</installed>" + Constants.EMPTY_LINE
                     + "<locked>" + this.locked + "</locked>" + Constants.EMPTY_LINE + "<temporary>" + this.temporary
                     + "</temporary>" + Constants.EMPTY_LINE + "</dockStation>";

        return ret;
    }
}
