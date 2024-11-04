package brain.adn.adn.servicio;

public class AdnTestConstants {

    public static final String[] DNA_MUTANT_ROW = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
    };

    public static final String[] DNA_MUTANT_ROW_3 = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "CCCCTG"
    };

    public static final String[] DNA_MUTANT_COLUMN_ERROR = {
            "ATGCGA",
            "CAGTGC",
            "TTAGT",
            "AGAAGG",
            "CCCCTA",
            "TCACT"
    };

    public static final String[] DNA_NOT_MUTANT_1 = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
    };

    public static final String[] DNA_NOT_MUTANT_2 = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
    };

    public static final String[] DNA_NOT_VALID = {
            "ATGCGA",
            "CAGLGC",
            "TTATTJ",
            "AGAOGG",
            "GCXTCA",
            "TCICTG"
    };
}