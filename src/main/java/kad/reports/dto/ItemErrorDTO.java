package kad.reports.dto;


public class ItemErrorDTO {
    private int id;
    private String name;
    private String shifr;

    public ItemErrorDTO(int id, String name, String shifr) {
        this.id = id;
        this.name = name;
        this.shifr = shifr;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShifr() {
        return shifr;
    }

    public void setShifr(String shifr) {
        this.shifr = shifr;
    }

}
