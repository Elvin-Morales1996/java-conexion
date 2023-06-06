
package conexion;


public class planilla {
    private String nonit;
    private String nombre;
    private String direccion;
    private double ingresos;
    private double impuestos;

    public String getNonit() {
        return nonit;
    }

    public void setNonit(String nonit) {
        this.nonit = nonit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }
}
