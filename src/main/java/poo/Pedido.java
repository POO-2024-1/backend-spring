package poo;
import poo.IPagamento;
import poo.StatusPedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private LocalDate data;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private IPagamento metodoPagamento;
    private StatusPedido statusPedido;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.data = LocalDate.now();
        this.itens = new ArrayList<>();
        this.statusPedido = StatusPedido.PENDENTE;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (produto != null && quantidade > 0) {
            this.itens.add(new ItemPedido(produto, quantidade));
        }
    }

    public void removerItem(ItemPedido item) {
        this.itens.remove(item);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : this.itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void aplicarDesconto(double percentual) {
        // Lógica de desconto a ser implementada
    }

    public boolean confirmarPedido() {
        if (this.metodoPagamento == null || this.statusPedido != StatusPedido.PENDENTE) {
            return false;
        }

        boolean pagamentoOk = this.metodoPagamento.processarPagamento(this.calcularTotal());

        if (pagamentoOk) {
            this.statusPedido = StatusPedido.PROCESSANDO;
            return true;
        } else {
            return false;
        }
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public IPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(IPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}