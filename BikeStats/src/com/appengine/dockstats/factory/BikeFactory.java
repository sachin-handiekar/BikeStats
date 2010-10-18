package com.appengine.dockstats.factory;

public class BikeFactory {
    public enum DataType {
        XML, CSV, JSON, YAML
    }

    public static BikeData generateBikeData(DataType dataType) {
        switch (dataType) {
        case XML :
            return new XmlBikeData();

        case CSV :
            return new CSVBikeData();

        case JSON :
            return new JSONBikeData();

        case YAML :
            return new YAMLBikeData();
        }

        return new XmlBikeData();
    }
}
