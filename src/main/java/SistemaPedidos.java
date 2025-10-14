import model.Cliente;
import model.ClientePF;
import model.Pedido;
import model.Produto;
import pagamento.IPagamento;
import pagamento.PagamentoCartaoCredito;
import pagamento.PagamentoBoleto;

import java.time.LocalDate;

public class SistemaPedidos {

    public static void main(String[] args) {

        System.out.println("--- 1. Cadastrando Clientes e Produtos ---");

        Cliente cliente = new ClientePF(
                "CLI001",
                "João da Silva",
                "joao.silva@email.com",
                "11987654321",
                "123.456.789-00",
                LocalDate.of(1990, 5, 15)
        );
        System.out.println("Cliente criado: " + cliente.getNome() + " (" + cliente.getIdentificadorUnico() + ")");

        Produto notebook = new Produto("PROD01", "Notebook Gamer", 4500.00, "Notebook de alta performance");
        notebook.adicionarEstoque(10);

        Produto mouse = new Produto("PROD02", "Mouse sem fio", 250.00, "Mouse ergonômico com 6 botões");
        mouse.adicionarEstoque(50);

        System.out.println("Produtos cadastrados no catálogo.\n");


        System.out.println("--- 2. Simulando um Pedido com Cartão de Crédito (Deve ser APROVADO) ---");

        Pedido pedido1 = new Pedido(cliente);
        System.out.println("Status inicial do Pedido 1: " + pedido1.getStatusPedido());

        pedido1.adicionarItem(mouse, 2);
        System.out.println(String.format("Valor total do Pedido 1: R$ %.2f", pedido1.calcularTotal()));

        IPagamento cartaoAprovado = new PagamentoCartaoCredito(
                "1234-5678-9012-3456",
                "Joao F Silva",
                LocalDate.now().plusYears(3)
        );
        pedido1.setMetodoPagamento(cartaoAprovado);
        System.out.println("Método de pagamento definido como: Cartão de Crédito");

        boolean confirmado1 = pedido1.confirmarPedido();
        System.out.println("Resultado da confirmação: " + (confirmado1 ? "SUCESSO" : "FALHA"));
        System.out.println("Status do Pagamento: " + pedido1.getMetodoPagamento().getStatus());
        System.out.println("Status final do Pedido 1: " + pedido1.getStatusPedido() + "\n");


        System.out.println("--- 3. Simulando um Pedido com Boleto (Deve ser RECUSADO) ---");

        Pedido pedido2 = new Pedido(cliente);
        System.out.println("Status inicial do Pedido 2: " + pedido2.getStatusPedido());

        pedido2.adicionarItem(notebook, 1);
        System.out.println(String.format("Valor total do Pedido 2: R$ %.2f", pedido2.calcularTotal()));

        IPagamento boletoRecusado = new PagamentoBoleto(
                "123456 789012 345678 901234",
                LocalDate.now().plusDays(5),
                100.00
        );
        pedido2.setMetodoPagamento(boletoRecusado);
        System.out.println("Método de pagamento definido como: Boleto");

        boolean confirmado2 = pedido2.confirmarPedido();
        System.out.println("Resultado da confirmação: " + (confirmado2 ? "SUCESSO" : "FALHA"));
        System.out.println("Status do Pagamento: " + pedido2.getMetodoPagamento().getStatus());
        System.out.println("Status final do Pedido 2: " + pedido2.getStatusPedido() + "\n");
    }
}