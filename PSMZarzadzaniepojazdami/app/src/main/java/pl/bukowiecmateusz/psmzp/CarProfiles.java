package pl.bukowiecmateusz.psmzp;

public class CarProfiles {
    private long id;
    private String rejestracja;
    private boolean usun;
    private String marka;
    private String ubezpieczenie;
    private String przeglad;

    public CarProfiles(long id, String rejestracja, boolean usun, String marka, String ubezpieczenie, String przeglad) {
        this.id = id;
        this.rejestracja = rejestracja;
        this.usun = usun;
        this.marka = marka;
        this.ubezpieczenie = ubezpieczenie;
        this.przeglad = przeglad;

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getRejestracja() {
        return rejestracja;
    }
    public void setRejestracja(String rejestracja) {
        this.rejestracja = rejestracja;
    }

    public boolean isUsun() { return usun; }
    public void setUsun(boolean usun) {
        this.usun = usun;
    }

    public String getMarka() { return marka; }
    public void setMarka(String marka) { this.marka = marka; }

    public String getUbezpieczenie() { return ubezpieczenie; }
    public void setUbezpieczenie(String ubezpieczenie) {
        this.ubezpieczenie = ubezpieczenie;
    }

    public String getPrzeglad() {
        return przeglad;
    }
    public void setPrzeglad(String przeglad) {
        this.przeglad = przeglad;
    }
}