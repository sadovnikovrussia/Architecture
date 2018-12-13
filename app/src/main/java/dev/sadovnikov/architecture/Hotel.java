package dev.sadovnikov.architecture;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Hotel {
    @SerializedName("id")
    private long id;

    @SerializedName("flights")
    private ArrayList<Long> flightsIds;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Long price;

    Hotel(long id, ArrayList<Long> flightsIds, String name, long price) {
        this.id = id;
        this.flightsIds = flightsIds;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Long> getFlightsIds() {
        return flightsIds;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", flightsIds=" + flightsIds +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
