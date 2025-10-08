package com.example.gymadmin.model;

import java.time.OffsetDateTime;

public class Occupancy {
    private int max;
    private int current;
    private OffsetDateTime lastUpdated;

    public Occupancy(int max, int current, OffsetDateTime lastUpdated){
        this.max = max; this.current = current; this.lastUpdated = lastUpdated;
    }
    public int getMax(){return max;}
    public void setMax(int v){this.max=v;}
    public int getCurrent(){return current;}
    public void setCurrent(int v){this.current=v;}
    public OffsetDateTime getLastUpdated(){return lastUpdated;}
    public void setLastUpdated(OffsetDateTime v){this.lastUpdated=v;}
}
