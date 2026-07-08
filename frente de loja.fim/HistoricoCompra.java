public class HistoricoCompra {

    private int numeroCompra;
    private double total;
    private double valorPago;
    private double troco;

    public HistoricoCompra(int numeroCompra, double total,
                           double valorPago, double troco) {

        this.numeroCompra = numeroCompra;
        this.total = total;
        this.valorPago = valorPago;
        this.troco = troco;
    }

    public int getNumeroCompra() {
        return numeroCompra;
    }

    public double getTotal() {
        return total;
    }

    public double getValorPago() {
        return valorPago;
    }

    public double getTroco() {
        return troco;
    }
}