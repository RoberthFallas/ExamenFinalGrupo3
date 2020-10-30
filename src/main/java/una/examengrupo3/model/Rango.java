package una.examengrupo3.model;

public class Rango {

    long desde;
    long hasta;
    String  color;

    public Rango(long desde, long hasta, String color) {
        this.desde = desde;
        this.hasta = hasta;
        this.color = color;
    }

    public long getPercentInRange(){
        return hasta - desde;
    }

    public String getColor(){
        return color;
    }

    public boolean isInRange(long percent){
        return percent >= desde && percent <=hasta;
    }

    public String getRangoInWords(){
        return  desde + "%" + " - " + hasta + "%";
    }

    public long getHasta(){
        return  hasta;
    }
}
