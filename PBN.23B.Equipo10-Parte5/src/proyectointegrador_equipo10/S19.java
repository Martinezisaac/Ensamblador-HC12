
package proyectointegrador_equipo10;

public class S19 {
    private String STipo;
    private String cc;
    private String address;
    private String data;
    private String ck;

    public S19(String STipo, String cc, String address, String data, String ck) {
        this.STipo = STipo;
        this.cc = cc;
        this.address = address;
        this.data = data;
        this.ck = ck;
    } //Fin de constructor 

    //Getters y Setters 
    
    public String getSTipo() {
        return STipo;
    }

    public void setSTipo(String STipo) {
        this.STipo = STipo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }
} //Fin de la clase S19
