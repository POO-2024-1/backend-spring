package pagamento;

import status.StatusPagamento;

public interface IPagamento {
    boolean processarPagamento(double valor);
    StatusPagamento getStatus();
}
