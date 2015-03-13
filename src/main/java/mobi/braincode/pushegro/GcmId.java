package mobi.braincode.pushegro;

public class GcmId {
    private String id;

    public GcmId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GcmId gcmId = (GcmId) o;

        if (id != null ? !id.equals(gcmId.id) : gcmId.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
