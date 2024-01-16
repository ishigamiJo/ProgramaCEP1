package br.com.Breno.Projects.principal;

import br.com.Breno.Projects.modelos.CEP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class API {
    private Scanner scanner;
    private final String https="https://viacep.com.br/ws/";
    private final String end ="/json/";


    public void RequisicaoCEP() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .create();
        Scanner scanner = new Scanner(System.in);
        var listaDeCEP = new ArrayList<CEP>();

        System.out.println("*****************************************");
        System.out.println("Bem vindo ao programa BuscaCep");
        System.out.println("*****************************************");
        String busca = "";
        while (!busca.equalsIgnoreCase("sair")) {
            System.out.println("Nos informe o seu cep: ");
            busca = scanner.nextLine();
            if (busca.equalsIgnoreCase("sair")) {
                break;
            }
            if (busca.length() < 8) {
                System.out.println("Escreva um CPF válido.");
                busca = scanner.nextLine();
            }
            String endereço = "https://viacep.com.br/ws/" + busca + "/json/";

            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create(endereço))
                    .build();
            try {
                HttpResponse<String> response = HttpClient
                        .newHttpClient()
                        .send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println(response.body());
                CEP cep = gson.fromJson(response.body(), CEP.class);
                System.out.println(cep);
                 listaDeCEP.add(cep);
            } catch (IOException e) {
                throw new RuntimeException("Não consegui obter o br.com.Breno.Projects.modelos.CEP a partir deste endereço");
            } catch (InterruptedException e) {
                throw new RuntimeException("Não consegui obter o br.com.Breno.Projects.modelos.CEP a partir deste endereço");
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("Erro, CPF sem a quantidade correta de números");
            }


        }
        FileWriter arquivo = new FileWriter("CEPs.json");
        arquivo.write(gson.toJson(listaDeCEP));
        arquivo.close();


    }
}
