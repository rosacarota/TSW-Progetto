package model.misura;

public class MisuraBean {
    private int IDMaglietta, quantita;
    private String taglia;

    public MisuraBean() {}

    public MisuraBean(int IDMaglietta, int quantita, String taglia) {
        this.IDMaglietta = IDMaglietta;
        this.quantita = quantita;
        this.taglia = taglia;
    }

    public int getIDMaglietta() {
        return IDMaglietta;
    }

    public void setIDMaglietta(int IDMaglietta) {
        this.IDMaglietta = IDMaglietta;
    }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "MisuraBean{" +
                "IDMaglietta=" + IDMaglietta +
                ", quantita=" + quantita +
                ", taglia='" + taglia + '\'' +
                '}';
    }
}
