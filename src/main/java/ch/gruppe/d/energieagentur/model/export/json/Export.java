package ch.gruppe.d.energieagentur.model.export.json;

/**
 * This class is used as a Model class for the json export
 */
public class Export {
    private String sensorId;
    private Zaehlerstand[] data;

    /**
     * constructor of Export class
     * @param sensorId sensorId of Export class
     * @param data data of Export class
     */
    public Export(SensorId sensorId, Zaehlerstand[] data) {
        this.sensorId = sensorId.name();
        this.data = data;
    }

    /**
     * sensorId getter
     * @return sensorId
     */
    public String getSensorId() {
        return sensorId;
    }


    public Export setSensorId(SensorId sensorId) {
        this.sensorId = sensorId.name();
        return this;
    }

    public Zaehlerstand[] getData() {
        return data;
    }

    public Export setData(Zaehlerstand[] data) {
        this.data = data;
        return this;
    }
}
