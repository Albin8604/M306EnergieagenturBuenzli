package ch.gruppe.d.energieagentur.util.files.export.model;

/**
 * This class is used as a Model class for the json export
 */
public class JSONExport {
    private String sensorId;
    private Zaehlerstand[] data;

    /**
     * constructor of Export class
     * @param sensorId sensorId of Export class
     * @param data data of Export class
     */
    public JSONExport(SensorId sensorId, Zaehlerstand[] data) {
        this.sensorId = sensorId.name();
        this.data = data;
    }

    /**
     * gets the value of sensorId
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * sets the value of sensorId
     *
     * @return JSONExport
     */
    public JSONExport setSensorId(String sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    /**
     * gets the value of data
     */
    public Zaehlerstand[] getData() {
        return data;
    }

    /**
     * sets the value of data
     *
     * @return JSONExport
     */
    public JSONExport setData(Zaehlerstand[] data) {
        this.data = data;
        return this;
    }
}
