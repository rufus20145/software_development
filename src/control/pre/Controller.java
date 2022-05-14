package control.pre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jsoup.select.Elements;

public class Controller {
    private static final String NAME_CLASS = "audio_row__inner";

    @FXML
    private TextField textFieldId;

    @FXML
    private Button saveButtonId;

    @FXML
    private Text textId;

    @FXML
    private TextArea textAreaId;

    @FXML
    public void startProcessing() {
        System.out.println("Started processing");
        textId.setText("Строка принята");// todo поменять цвет
        String scannedString = textFieldId.getText();
        List<Track> tracks = new ArrayList<>();
        Document page = null;
        try {
            page = Jsoup.connect(scannedString).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Elements tracksElements = page.getElementsByClass(NAME_CLASS);
        for (Element track : tracksElements) {
            String trackName = track.getElementsByClass("audio_row__title _audio_row__title").first().text();
            Elements formers = track.getElementsByClass("audio_row__performers");
            List<String> performers = new ArrayList<>();
            for (Element former : formers) {
                performers.add(former.text());
            }
            String durationString = track
                    .getElementsByClass("audio_row__duration audio_row__duration-s _audio_row__duration").first()
                    .text();
            int duration = parseDuration(durationString);
            tracks.add(new Track(trackName, Arrays.toString(performers.toArray()).replace("[", "").replace("]", ""),
                    duration));
        }

        textAreaId.setText(Track.getAllTracksInfo(tracks));
        textFieldId.clear();
        System.out.println("Ended processing. Обработано треков: " + tracks.size());

    }

    private int parseDuration(String durationString) {
        String[] splStrings = durationString.split(":");
        return Integer.parseInt(splStrings[0]) * 60 + Integer.parseInt(splStrings[1]);
    }

    public void clearText() {
        if (textFieldId.getText().isBlank()) {
            textId.setText("");
        }
    }

    private class Track {
        private String name;
        private String performers;
        private int durationString;

        public static String getAllTracksInfo(List<Track> list) {
            StringBuilder sb = new StringBuilder();
            list.forEach(track -> sb.append(track.getTrackInfo()).append("\n"));
            return sb.toString().substring(0, sb.length() - 1);
        }

        public Track(String name, String performers, int durationString) {
            this.name = name;
            this.performers = performers;
            this.durationString = durationString;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPerformers() {
            return performers;
        }

        public void setPerformers(String performers) {
            this.performers = performers;
        }

        public int getDuration() {
            return durationString;
        }

        public void setDuration(int durationString) {
            this.durationString = durationString;
        }

        public String getTrackInfo() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getName()).append(" — ").append(this.getPerformers()).append(" — ")
                    .append(this.getFormattedDuration());
            return sb.toString();
        }

        private String getFormattedDuration() {
            int duration = this.getDuration();
            return String.format("%d:%02d", duration / 60, duration % 60);
        }

    }
}
