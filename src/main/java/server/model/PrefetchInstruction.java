package server.model;

/**
 * Created by florian on 06.05.15.
 */
public class PrefetchInstruction {

    private String contentName;
    private LocationInfo locationInfo = new LocationInfo();

    public PrefetchInstruction() {
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }
}
