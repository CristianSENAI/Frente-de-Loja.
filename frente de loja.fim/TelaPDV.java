import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaPDV extends JFrame {


    private ArrayList<Produto> produtos = new ArrayList<>();
    private Venda venda = new Venda();
    private Caixa caixa = new Caixa();
    private ArrayList<HistoricoCompra> historico = new ArrayList<>();
    private int numeroCompra = 1;
    private JTextField textCodigo;
    private JTextField textNome;
    private JTextField textPreco;
    private JTextField textEstoque;
    private JTextField textPesquisa;


    private JTextField txtCodigoVenda;
    private JTextField txtQuantidade;

    private JTextField textTotal;
    private JTextField textValopago;
    private JTextField textTroco;
    private JTextField textDesconto;


    private JTable tabelaProdutos;
    private javax.swing.table.DefaultTableModel modeloTabela;




    public TelaPDV (){
        setTitle("Sistema PDV - Frente de Caixa");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(96, 151, 205));


        setLayout(new GridLayout(5,1,2,2));
        ;JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(230,235,245));
        JLabel titulo = new JLabel("SISTEMA PDV");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(40,80,160));


        painelTitulo.add(titulo);

        add(painelTitulo);

        JPanel panelProduto = new JPanel();
        panelProduto.setBorder(BorderFactory.createTitledBorder("Produto"));
        panelProduto.setLayout(new GridLayout(6, 2));

        panelProduto.add(new JLabel("Código:"));

        textCodigo = new JTextField();
        panelProduto.add(textCodigo);

        panelProduto.add(new JLabel("Nome:"));
        textNome = new JTextField();
        panelProduto.add(textNome);

        panelProduto.add(new JLabel("Preço:"));
        textPreco = new JTextField();
        panelProduto.add(textPreco);

        panelProduto.add(new JLabel("Estoque:"));
        textEstoque = new JTextField();
        panelProduto.add(textEstoque);

        JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
        btnCadastrarProduto.setBackground(new Color(52,152,219));
        btnCadastrarProduto.setForeground(Color.WHITE);
        btnCadastrarProduto.setFont(new Font("Segoe UI",Font.BOLD,14));
        panelProduto.add(btnCadastrarProduto);

        btnCadastrarProduto.addActionListener(e -> {

            if (textCodigo.getText().isEmpty() ||
                    textNome.getText().isEmpty() ||
                    textPreco.getText().isEmpty() ||
                    textEstoque.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Preencha todos os campos!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            try {

                int codigo = Integer.parseInt(textCodigo.getText());
                String nome = textNome.getText();
                double preco = Double.parseDouble(textPreco.getText());
                int estoque = Integer.parseInt(textEstoque.getText());

                Produto produto = new Produto(codigo, nome, preco, estoque);

                produtos.add(produto);

                modeloTabela.addRow(new Object[]{
                        produto.getCodigo(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getEstoque()
                });

                textCodigo.setText("");
                textNome.setText("");
                textPreco.setText("");
                textEstoque.setText("");

                JOptionPane.showMessageDialog(
                        null,
                        "Produto cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        null,
                        "Código, preço e estoque devem ser números válidos!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        });
        modeloTabela = new javax.swing.table.DefaultTableModel(
                new String[]{"Código", "Nome", "Preço", "Estoque"}, 0);

        tabelaProdutos = new JTable(modeloTabela);

        JScrollPane scroll = new JScrollPane(tabelaProdutos);
        add(scroll);

        add(panelProduto);


        JPanel panelVenda = new JPanel();
        panelVenda.setBorder(BorderFactory.createTitledBorder("Venda"));
        panelVenda.setLayout(new GridLayout(4, 2));

        panelVenda.add(new JLabel("Código do Produto"));
        txtCodigoVenda = new JTextField();
        panelVenda.add(txtCodigoVenda);

        panelVenda.add(new JLabel("Quantidade"));
        txtQuantidade = new JTextField();
        panelVenda.add(txtQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(new Color(52,152,219));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFont(new Font("Segoe UI",Font.BOLD,14));
        panelVenda.add(btnAdicionar);

        btnAdicionar.addActionListener(e -> {

            if (txtCodigoVenda.getText().isEmpty() ||
                    txtQuantidade.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Preencha o código e a quantidade!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            try {

                int codigo = Integer.parseInt(txtCodigoVenda.getText());
                int quantidade = Integer.parseInt(txtQuantidade.getText());

                for (Produto produto : produtos) {

                    if (produto.getCodigo() == codigo) {

                        if (quantidade <= 0) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "A quantidade deve ser maior que zero!",
                                    "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        if (quantidade > produto.getEstoque()) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Estoque insuficiente!",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ItemVenda item = new ItemVenda(produto, quantidade);

                        venda.adicionarItem(item);

                        textTotal.setText(String.format("%.2f", venda.calcularTotal()));

                        txtCodigoVenda.setText("");
                        txtQuantidade.setText("");

                        JOptionPane.showMessageDialog(
                                null,
                                "Produto adicionado à venda!",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);

                        return;
                    }
                }

                JOptionPane.showMessageDialog(
                        null,
                        "Produto não encontrado!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        null,
                        "Digite apenas números no código e na quantidade!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        add(panelVenda);

        JPanel panelFinaceiro = new JPanel();
        panelFinaceiro.setBorder(BorderFactory.createTitledBorder("Financeiro"));
        panelFinaceiro.setLayout(new GridLayout(6, 2));

        panelFinaceiro.add(new JLabel("Total:"));
        textTotal = new JTextField();
        panelFinaceiro.add(textTotal);

        panelFinaceiro.add(new JLabel("Desconto (%):"));
        textDesconto = new JTextField("0");
        panelFinaceiro.add(textDesconto);
        textDesconto.setEditable(false);


        panelFinaceiro.add(new JLabel("Valor Pago:"));
        textValopago = new JTextField();
        panelFinaceiro.add(textValopago);

        panelFinaceiro.add(new JLabel("Troco:"));
        textTroco = new JTextField();
        panelFinaceiro.add(textTroco);

        textTotal.setEditable(false);
        textTroco.setEditable(false);


        JButton btnNovavenda = new JButton("Nova Venda");
        btnNovavenda.setBackground(new Color(52,152,219));
        btnNovavenda.setForeground(Color.WHITE);
        btnNovavenda.setFont(new Font("Segoe UI",Font.BOLD,14));

        btnNovavenda.addActionListener(e -> {

            venda = new Venda();

            txtCodigoVenda.setText("");
            txtQuantidade.setText("");

            textTotal.setText("");
            textValopago.setText("");
            textTroco.setText("");

            JOptionPane.showMessageDialog(null, "Nova venda iniciada!");
            textDesconto.setText("0");
        });

        JButton btnFimCompra = new JButton("Finalizar Compra");
        btnFimCompra.setBackground(new Color(52,152,219));
        btnFimCompra.setForeground(Color.WHITE);
        btnFimCompra.setFont(new Font("Segoe UI",Font.BOLD,14));

        btnFimCompra.addActionListener(e -> {

            if (textValopago.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite o valor pago!");
                return;
            }

            double valorPago = Double.parseDouble(textValopago.getText());

            double total = venda.calcularTotal();

            double desconto = 0;

            if (total >= 300) {
                desconto = 30;
            } else if (total >= 200) {
                desconto = 20;
            } else if (total >= 100) {
                desconto = 10;
            }

            textDesconto.setText(desconto + "%");

            total = total - (total * desconto / 100);


            textTotal.setText(String.format("R$ %.2f", total));

            if (valorPago < total) {
                JOptionPane.showMessageDialog(null, "Valor insuficiente!");
                return;
            }

            double troco = caixa.calcularTroco(valorPago, total);

            HistoricoCompra compra = new HistoricoCompra(
                    numeroCompra,
                    total,
                    valorPago,
                    troco
            );
            historico.add(compra);

            numeroCompra++;


            for (ItemVenda item : venda.getItens()) {
                item.getProduto().baixarEstoque(item.getQuantidade());
            }

            modeloTabela.setRowCount(0);

            for (Produto produto : produtos) {
                modeloTabela.addRow(new Object[]{
                        produto.getCodigo(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getEstoque()
                });
            }

            textTroco.setText(String.format("%.2f", troco));

            JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso!");

            venda = new Venda();

            txtCodigoVenda.setText("");
            txtQuantidade.setText("");

            textTotal.setText("");
            textValopago.setText("");
            textTroco.setText("");

        });
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(52,152,219));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI",Font.BOLD,14));
        btnCancelar.addActionListener(e -> {

            venda = new Venda();

            txtCodigoVenda.setText("");
            txtQuantidade.setText("");

            textTotal.setText("");
            textValopago.setText("");
            textTroco.setText("");

            JOptionPane.showMessageDialog(null, "Venda cancelada!");
            textDesconto.setText("0");
        });

        JButton btnHistorico = new JButton("Histórico");
        btnHistorico.setBackground(new Color(52,152,219));
        btnHistorico.setForeground(Color.WHITE);
        btnHistorico.setFont(new Font("Segoe UI", Font.BOLD, 14));

        ;btnHistorico.addActionListener(e -> {

            JFrame telaHistorico = new JFrame("Histórico de Compras");
            telaHistorico.setSize(500, 300);
            telaHistorico.setLocationRelativeTo(null);

            DefaultTableModel modeloHistorico = new DefaultTableModel(
                    new String[]{"Compra", "Total", "Pago", "Troco"}, 0);

            JTable tabelaHistorico = new JTable(modeloHistorico);

            for (HistoricoCompra compra : historico) {

                modeloHistorico.addRow(new Object[]{
                        compra.getNumeroCompra(),
                        String.format("R$ %.2f", compra.getTotal()),
                        String.format("R$ %.2f", compra.getValorPago()),
                        String.format("R$ %.2f", compra.getTroco())
                });

            }

            JScrollPane scrollHistorico = new JScrollPane(tabelaHistorico);

            telaHistorico.add(scrollHistorico);

            telaHistorico.setVisible(true);

        });

        panelProduto.add(new JLabel("Pesquisar:"));

        textPesquisa = new JTextField();
        textPesquisa.setText("Digite o código ou nome");
        textPesquisa.setForeground(Color.GRAY);

        textPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (textPesquisa.getText().equals("Digite o código ou nome")) {
                    textPesquisa.setText("");
                    textPesquisa.setForeground(Color.BLACK);
                }

            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {

                if (textPesquisa.getText().isEmpty()) {
                    textPesquisa.setText("Digite o código ou nome");
                    textPesquisa.setForeground(Color.GRAY);
                }

            }

        });

        panelProduto.add(textPesquisa);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBackground(new Color(52,152,219));
        btnPesquisar.setForeground(Color.WHITE);
        btnPesquisar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnPesquisar.addActionListener(e -> {

            String pesquisa = textPesquisa.getText().trim();

            if (pesquisa.equals("Digite o código ou nome") || pesquisa.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Digite o código ou nome do produto!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (Produto produto : produtos) {

                if (String.valueOf(produto.getCodigo()).equals(pesquisa)
                        || produto.getNome().equalsIgnoreCase(pesquisa)) {

                    textCodigo.setText(String.valueOf(produto.getCodigo()));
                    textNome.setText(produto.getNome());
                    textPreco.setText(String.valueOf(produto.getPreco()));
                    textEstoque.setText(String.valueOf(produto.getEstoque()));

                    JOptionPane.showMessageDialog(
                            null,
                            "Produto encontrado!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Produto não encontrado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

        });

        textDesconto = new JTextField("0%");
        textDesconto.setEditable(false);





        panelProduto.add(btnPesquisar);
        panelFinaceiro.add(btnNovavenda);
        panelFinaceiro.add(btnFimCompra);
        panelFinaceiro.add(btnCancelar);
        panelFinaceiro.add(btnHistorico);

        add(panelFinaceiro);
        setVisible(true);
    }
}
