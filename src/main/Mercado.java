package main;

import modelo.Produto;
import utils.Utils;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Mercado {

        private static Scanner input = new Scanner(System.in);
        private static ArrayList<Produto> produtos;
        private static Map<Produto, Integer> carrinho;

        public static void main (String args[]) {

            produtos = new ArrayList<>();
            carrinho = new HashMap<>();
            menu();
        }

        private static void menu() {

            System.out.println("-----------------------------------------------");
            System.out.println("-------- Bem-vindo ao Mercado do Marco --------");
            System.out.println("-----------------------------------------------");
            System.out.println();
            System.out.println("==== Menu ====");
            System.out.println("Selecione a općão desejada:");
            System.out.println("\t1. Cadastrar");
            System.out.println("\t2. Listar");
            System.out.println("\t3. Comprar");
            System.out.println("\t4. Carrinho");
            System.out.println("\t5. Sair");

            int option = input.nextByte();

            switch (option) {
                case 1:
                    cadastrarProdutos();
                    break;

                case 2:
                    listarProdutos();
                    break;

                case 3:
                    comprarProdutos();
                    break;

                case 4:
                    verCarrinho();
                    break;

                case 5:
                    System.out.println("Obrigado pela preferência");
                    System.exit(0);

                default:
                    System.out.println("Općão inválida");
                    menu();
                    break;
                }
            }


        private static void cadastrarProdutos() {
            System.out.println("Nome do produto: ");
            String nome = input.next();

            System.out.println("Prećo do produto: ");
            Double preco = input.nextDouble();

            Produto produto = new Produto(nome, preco);
            produtos.add(produto);

            System.out.printf("Produto %d cadastrado com sucesso.\n", produto.getNome());

            menu();
        }

        private static void listarProdutos(){
            if(produtos.size() > 0){
                System.out.println("Lista de produtos: \n");
                for(Produto p: produtos){
                    System.out.println(p);
                }
            } else {
                System.out.println("Nenhum produto cadastrado.");
            }
            menu();
        }

        private static void comprarProdutos(){
            if (produtos.size() > 0){
                System.out.println("Código do produto: \n");
                System.out.println("------------ Produtos Disponíveis ------------");

                for(Produto p: produtos){
                    System.out.println(p + "\n");
                }

                int id = Integer.parseInt(input.next());
                boolean isPresent = false;

                for(Produto p: produtos){
                    if(p.getId() == id){
                        int qtd = 0;
                        try{
                            qtd = carrinho.get(p);
                            // verifica se o produto já está no carrinho, incrementa a quantidade
                            carrinho.put(p, qtd + 1);
                        } catch (NullPointerException e) {
                            // se o produto for o primeiro no carrinho
                            carrinho.put(p, 1);
                        }
                        System.out.println(p.getNome() + " adicionado ao carrinho.");
                        isPresent = true;

                        if(isPresent){
                            System.out.println("Deseja adicionar outro produto?");
                            System.out.println("Digite 1 para sim ou 0 para finalizar.");
                            int option = Integer.parseInt(input.next());
                            if(option == 1){
                                comprarProdutos();
                            } else{
                                finalizarCompra();
                            }
                        }
                    } else {
                        System.out.println("Produto não encontrado.");
                        menu();
                    }
                }
            } else {
                System.out.println("Não existem produtos cadastrados.");
                menu();
            }
        }

        private static void verCarrinho(){
            System.out.println("--- Produtos no seu carrinho ---");
            if(carrinho.size() > 0){
                for(Produto p: carrinho.keySet()){
                    System.out.println("Produto " + p + "\nQuantidade: " + carrinho.get(p));
                }
            } else {
                System.out.println("Seu carrinho está vazio.");
            }
            menu();
        }

        private static void finalizarCompra(){
            Double valorDaCompra = 0.0;

            System.out.println("Meus produtos.");
            for(Produto p: carrinho.keySet()){
                int qtd = carrinho.get(p);
                valorDaCompra += p.getPreco() * qtd;
                System.out.println(p);
                System.out.println("Quantidade: " + qtd);
                System.out.println("---------------");
            }
            System.out.println("O valor de sua compra é: " + Utils.doubleToString(valorDaCompra));
            carrinho.clear();
            System.out.println("Volte sempre.");
            menu();
        }
}
