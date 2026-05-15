package ec.edu.espe.springlab.dto;

public class StudentStatsResponse {
    private long total;
    private long active;
    private long inactive;

    public StudentStatsResponse(long total, long active, long inactive) {
        this.total = total;
        this.active = active;
        this.inactive = inactive;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getInactive() {
        return inactive;
    }

    public void setInactive(long inactive) {
        this.inactive = inactive;
    }
}
