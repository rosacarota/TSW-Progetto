package model.maglietta;

public class MagliettaOrdine {
    private MagliettaBean magliettaBean;
    private int quantita;
    private String taglia;

    public MagliettaOrdine(MagliettaBean magliettaBean, String taglia) {
        this.magliettaBean = magliettaBean;
        this.taglia = taglia;
        quantita = 1;
    }

    public MagliettaBean getMagliettaBean() {
        return magliettaBean;
    }

    public void setMagliettaBean(MagliettaBean magliettaBean) {
        this.magliettaBean = magliettaBean;
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

    public void incrementaQuantita() {
        quantita++;
    }

    public void decremenetaQuantita() {
        quantita--;
    }

    public float getPrezzoTotale() {
        return quantita * magliettaBean.getPrezzo();
    }
}
