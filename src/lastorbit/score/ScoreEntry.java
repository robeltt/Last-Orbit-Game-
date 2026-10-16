package lastorbit.score;


public class ScoreEntry {

    private final String name;
    private final int score;
    private final int wave;

    public ScoreEntry(String name, int score, int wave) {
        this.name = name;
        this.score = score;
        this.wave = wave;
    }

    public String getName() { return name; }
    public int getScore()   { return score; }
    public int getWave()    { return wave; }

    public String toLine() {
        return name + "," + score + "," + wave;
    }

    public static ScoreEntry fromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            return null;
        }
        try {
            String name = parts[0];
            int score = Integer.parseInt(parts[1].trim());
            int wave = Integer.parseInt(parts[2].trim());
            return new ScoreEntry(name, score, wave);
        } catch (NumberFormatException e) {
            return null;   
        }
    }
}