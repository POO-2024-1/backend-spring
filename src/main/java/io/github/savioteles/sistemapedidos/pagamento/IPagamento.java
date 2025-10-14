package io.github.savioteles.sistemapedidos.pagamento;

import io.github.savioteles.sistemapedidos.status.StatusPagamento;

public interface IPagamento {
    boolean processarPagamento(double valor);
    StatusPagamento getStatus();
}
