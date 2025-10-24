package poo;

public interface IPagamento {
    boolean processarPagamento(double valor);
    StatusPagamento getStatus();
}
