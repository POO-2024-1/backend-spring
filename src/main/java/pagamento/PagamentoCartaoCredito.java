package pagamento;

import status.StatusPagamento;

import java.time.LocalDate;

public class PagamentoCartaoCredito implements IPagamento {
    private String numeroCartao;
    private String nomeTitular;
    private LocalDate dataValidade;
    private StatusPagamento statusPagamento;

    public PagamentoCartaoCredito(String numeroCartao, String nomeTitular, LocalDate dataValidade) {
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.dataValidade = dataValidade;
        this.statusPagamento = StatusPagamento.PENDENTE;
    }

    @Override
    public boolean processarPagamento(double valor) {
        if (valor > 0 && valor < 5000.0) {
            this.statusPagamento = StatusPagamento.APROVADO;
            return true;
        } else {
            this.statusPagamento = StatusPagamento.RECUSADO;
            return false;
        }
    }

    @Override
    public StatusPagamento getStatus() {
        return this.statusPagamento;
    }
}
