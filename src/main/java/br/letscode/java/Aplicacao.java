package br.letscode.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplicacao {

    private List<Oscar> atriz;
    private List<Oscar> ator;


    public static void main(String[] args)  {

        Aplicacao app = new Aplicacao();

        app.testeLeituraArquivoMale();
        app.testeLeituraArquivoFemale();
        app.getAtorMaisJovem();
        app.getAtrizMaisPremiada();
        app.recebeuMaisDeUmOscar();


    }

    private void getAtorMaisJovem()  {


        System.out.println("O ator mais jovem a ganhar o oscar: ");
        String filepath = getFilepathFromResourceAsStream("oscar_age_male.csv");
        try(Stream <String> lines = Files.lines(Path.of(filepath))){
            lines
                    .skip(1)
                    .map(Oscar::fromLine)
                    .min(Comparator.comparingInt(Oscar::getAge))
                    .ifPresent(System.out::println);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void getAtrizMaisPremiada() {
        System.out.println("A atriz mais premiada: ");

        Map<String, Long> nome = this.atriz.stream()
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nome.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(m -> System.out.println(m.getKey()));

    }
    private void recebeuMaisDeUmOscar (){
        System.out.println("Os atores/atrizes que receberam mais de um Oscar: ");
        System.out.println("Atrizes: ");
        Map<String, Long> nomeAtriz = this.atriz.stream()
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtriz.entrySet().stream()
                .filter(n -> n.getValue() > 1)
                .forEach(System.out::println);
        System.out.println("Atores: ");
        Map<String, Long> nameAtor = this.ator.stream()
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtriz.entrySet().stream()
                .filter(n -> n.getValue() > 1)
                .forEach(System.out::println);
    }



    private void testeLeituraArquivoMale()  {
        String filepath = getFilepathFromResourceAsStream("oscar_age_male.csv");
        try(Stream <String> lines = Files.lines(Path.of(filepath))){
            this.ator = lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private void maisVezesVencedora() {
        System.out.println("A atriz entre 20 e 30 anos que mais vezes venceu:" );
        Map<String, Long> vencedora = this.atriz.stream()
                .filter(a -> a.getAge() > 20 && a.getAge()<30)
                .map(Oscar :: getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        vencedora.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)).
                ifPresent((m -> System.out.println(m.getKey() + "é uma atriz ")));
    }

        private void testeLeituraArquivoFemale()  {
        String filepath = getFilepathFromResourceAsStream("oscar_age_female.csv");
        try(Stream<String> lines = Files.lines(Path.of(filepath))){
            this.atriz = lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void resumoQuantosPremiosMale(String name){
        System.out.println("A atriz" + name);
        this.ator.stream()
                .filter(m -> Objects.equals(m.getName(), name))
                .forEach(System.out::println);
    }


    private String getFilepathFromResourceAsStream(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        return file.getPath();
    }

}

