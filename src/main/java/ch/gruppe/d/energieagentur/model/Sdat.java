package ch.gruppe.d.energieagentur.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Sdat {
    private boolean isPurchased;
    private LocalDateTime start;
    private LocalDateTime end;
    private int intervalMin;
    private List<BigDecimal> volumes;

    /**
     * emtpy constructor
     */
    public Sdat() {
    }

    /**
     * full param constructor
     * @param isPurchased
     * @param start
     * @param end
     * @param intervalMin
     * @param sequence
     * @param volumes
     */
    public Sdat(boolean isPurchased, LocalDateTime start, LocalDateTime end, int intervalMin, int sequence, List<BigDecimal> volumes) {
        this.isPurchased = isPurchased;
        this.start = start;
        this.end = end;
        this.intervalMin = intervalMin;
        this.volumes = volumes;
    }

    /**
     * gets the value of isPurchased
     */
    public boolean isPurchased() {
        return isPurchased;
    }

    /**
     * sets the value of isPurchased
     *
     * @return Sdat
     */
    public Sdat setPurchased(boolean purchased) {
        isPurchased = purchased;
        return this;
    }

    /**
     * gets the value of start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * sets the value of start
     *
     * @return Sdat
     */
    public Sdat setStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    /**
     * gets the value of end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * sets the value of end
     *
     * @return Sdat
     */
    public Sdat setEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }

    /**
     * gets the value of intervalMin
     */
    public int getIntervalMin() {
        return intervalMin;
    }

    /**
     * sets the value of intervalMin
     *
     * @return Sdat
     */
    public Sdat setIntervalMin(int intervalMin) {
        this.intervalMin = intervalMin;
        return this;
    }

    /**
     * gets the value of volumes
     */
    public List<BigDecimal> getVolumes() {
        return volumes;
    }

    /**
     * sets the value of volumes
     *
     * @return Sdat
     */
    public Sdat setVolumes(List<BigDecimal> volumes) {
        this.volumes = volumes;
        return this;
    }

    /**
     * adds a volume to list
     * @param volume
     * @return Sdat
     */
    public Sdat addVolume(BigDecimal volume){
        getVolumes().add(volume);
        return this;
    }

    /**
     * adds volumes to list
     * @param volumes
     * @return Sdat
     */
    public Sdat addVolumes(BigDecimal... volumes){
        getVolumes().addAll(List.of(volumes));
        return this;
    }
}
