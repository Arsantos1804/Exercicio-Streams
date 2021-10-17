package br.letscode.java;

import lombok.Value;

import static java.lang.Integer.parseInt;

@Value
public class Oscar {
    Integer index;
    Integer year;
    Integer age;
    String name;
    String movie;



    static Oscar fromLine(String line) {
        String [] split = line.split("; ");   //transformar de linha para texto
        return new Oscar(
                parseInt(split[0]), //index
                parseInt(split[1]), //year
                parseInt(split[2]),//age
                split[3],//name
                split[4]); //movie

    }
}

