package lastorbit.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HighScoreStore {

    private static final int MAX_ENTRIES = 5;
    private final String filePath;
    private final List<ScoreEntry> entries;

    public HighScoreStore(String filePath) {
        this.filePath = filePath;
        this.entries = new ArrayList<>();
        load();
    }
    private void load() {
        entries.clear();
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                ScoreEntry entry = ScoreEntry.fromLine(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        } catch (IOException e) {
        }
        sortAndTrim();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ScoreEntry entry : entries) {
                writer.write(entry.toLine());
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }

    public boolean qualifies(int score) {
        if (entries.size() < MAX_ENTRIES) {
            return true;
        }
        int lowest = entries.get(entries.size() - 1).getScore();
        return score > lowest;
    }

    public void addScore(ScoreEntry entry) {
        entries.add(entry);
        sortAndTrim();
        save();
    }

    private void sortAndTrim() {
        Collections.sort(entries, new Comparator<ScoreEntry>() {
            @Override
            public int compare(ScoreEntry a, ScoreEntry b) {
                return Integer.compare(b.getScore(), a.getScore());
            }
        });
        while (entries.size() > MAX_ENTRIES) {
            entries.remove(entries.size() - 1);
        }
    }

    public List<ScoreEntry> getEntries() {
        return entries;
    }
}