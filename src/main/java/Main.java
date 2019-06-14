import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import scala.collection.Seq;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void toCSV(String fileName, Map<String, Integer> map) {

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("\\C:\\" + fileName + ".csv"), "UTF8"));
            for (String key : map.keySet()) {
                out.write(key + ", " + map.get(key) + "\n");
            }

            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        String fileName = "403960817";
        File file = new File("\\C:\\TwitchVideoRecommender\\Data\\lck_korea\\" + fileName + ".txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String text;
            while ((text = br.readLine()) != null) {
                text = text.split(">", 2)[1];
                CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);

                Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);

                List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
                if (!phrases.isEmpty())
                    for (KoreanPhraseExtractor.KoreanPhrase phrase : phrases) {
                        String temp = phrase.toString().split("\\(")[0];
                        map.put(temp, map.getOrDefault(temp, 0) + 1);
                    }
            }

            toCSV(fileName, map);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
