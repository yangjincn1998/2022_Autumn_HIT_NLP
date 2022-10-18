import Tokenizer.FMMTokenizer;
import Tokenizer.Tokenizer;
import Tokenizer.Tokenizer.*;
import dictionary.ConcreteDictionary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FMMTokenizerTest {
    Tokenizer fmm= new FMMTokenizer(new ConcreteDictionary(ConcreteDictionary.srcPath));

    public FMMTokenizerTest() throws IOException {
    }

    @BeforeAll
    public void Test() throws IOException {
        fmm.readPassage(Tokenizer.readPath);
        fmm.participle();
    }
    @Test
    public void writeTest() throws IOException {
        fmm.writeResult(FMMTokenizer.outPath);
    }
    @Test
    public void coverTest() throws IOException {
        fmm.readPassage(Tokenizer.readPath);
    }

}
