package backend.academy;

import backend.academy.input.ConsoleInputReader;
import backend.academy.input.InputInterface;
import backend.academy.logic.DefaultGameLogic;
import backend.academy.output.ConsoleOutputWriter;
import backend.academy.output.OutputInterface;
import backend.academy.random.DefaultRandomGenerator;
import backend.academy.random.RandomGeneratorInterface;
import backend.academy.render.DefaultGallowsRender;
import backend.academy.render.DefaultGameIORender;
import backend.academy.render.GallowsRenderInterface;
import backend.academy.render.GameIORenderInterface;
import backend.academy.wordloader.CSVFileWordLoader;
import backend.academy.wordloader.WordLoaderInterface;
import backend.academy.wordrepository.DefaultWordRepository;
import backend.academy.wordrepository.WordRepositoryInterface;
import lombok.experimental.UtilityClass;

@UtilityClass public class Main {
    public static void main(String[] args) {
        OutputInterface consoleOutput = new ConsoleOutputWriter();
        InputInterface consoleInput = new ConsoleInputReader();
        RandomGeneratorInterface randomGeneratorInterface = new DefaultRandomGenerator();
        GallowsRenderInterface gallowsRenderInterface = new DefaultGallowsRender(consoleOutput);
        GameIORenderInterface gameIoRenderInterface =
            new DefaultGameIORender(consoleInput, consoleOutput, randomGeneratorInterface);
        WordLoaderInterface wordLoaderInterface = new CSVFileWordLoader("words.csv");
        WordRepositoryInterface wordRepositoryInterface =
            new DefaultWordRepository(wordLoaderInterface, randomGeneratorInterface);
        DefaultGameLogic logicRender =
            new DefaultGameLogic(gameIoRenderInterface, gallowsRenderInterface, wordRepositoryInterface);
        logicRender.game();
    }
}
