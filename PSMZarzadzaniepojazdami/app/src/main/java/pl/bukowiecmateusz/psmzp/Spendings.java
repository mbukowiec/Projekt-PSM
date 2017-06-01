package pl.bukowiecmateusz.psmzp;

public class Spendings {
    private long id;
    private String opis;
    private String cena;
    private boolean usun;

    public Spendings(long id, String opis, String cena, boolean usun) {
        this.id = id;
        this.opis = opis;
        this.cena = cena;
        this.usun = usun;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public boolean isUsun() {
        return usun;
    }

    public void setUsun(boolean usun) {
        this.usun = usun;
    }
}