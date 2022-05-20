package control.work;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class Controller implements Initializable {

    private static final int NUBMER_OF_QUESTIONS = 3;

    private static final String RIGHT_THIRD_ANSWER = "fortius\"";

    @FXML
    private CheckBox marselId;

    @FXML
    private CheckBox moscowId;

    @FXML
    private Button nextButtonId;

    @FXML
    private CheckBox osloId;

    @FXML
    private CheckBox pragaId;

    @FXML
    private CheckBox romeId;

    @FXML
    private CheckBox shanghaiId;

    @FXML
    private Text resultTextId;

    @FXML
    private RadioButton var1;

    @FXML
    private RadioButton var2;

    @FXML
    private RadioButton var3;

    @FXML
    private RadioButton var4;

    @FXML
    private ComboBox<String> thirdQuestionComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        var1.setToggleGroup(group);
        var2.setToggleGroup(group);
        var3.setToggleGroup(group);
        var4.setToggleGroup(group);

        ObservableList<String> variants = FXCollections.observableArrayList("ergo\"", "est\"", RIGHT_THIRD_ANSWER,
                "cogito\"");
        thirdQuestionComboBox.setItems(variants);
    }

    private int[] numberOfWrongAnswers = new int[NUBMER_OF_QUESTIONS];
    private int numberOfQuestion = 1;

    public void processClick() {
        if (checkAnswers()) {
            resultTextId.setText("Правильный ответ!");
        } else {
            resultTextId.setText("Подумайте еще!");
        }

        if (numberOfQuestion == 4) {
            int numberOfDuplicatedAnswers = 0;
            for (int i = 0; i < numberOfWrongAnswers.length; i++) {
                if (numberOfWrongAnswers[i] != 0)
                    numberOfDuplicatedAnswers++;
            }
            resultTextId.setText(String.format(
                    "Поздравляем с успешным прохождением теста!%nКоличество неправильных овтетов на первый вопрос - %d, на второй - %d, на третий - %d.%nКоличество вопросов, на которые ответ дан не с первого раза: %d.",
                    numberOfWrongAnswers[0], numberOfWrongAnswers[1], numberOfWrongAnswers[2],
                    numberOfDuplicatedAnswers));

        }
    }

    private boolean checkAnswers() {
        switch (numberOfQuestion) {
            case 1:
                if (moscowId.isSelected() && !shanghaiId.isSelected() && !marselId.isSelected() && romeId.isSelected()
                        && pragaId.isSelected() && osloId.isSelected()) {
                    numberOfQuestion++;
                    switchToSecondQuestion();
                    return true;
                } else {
                    numberOfWrongAnswers[0]++;
                    return false;
                }
            case 2:
                if (var2.isSelected()) {
                    numberOfQuestion++;
                    switchToThirdQuestion();
                    nextButtonId.setText("Завершить");
                    return true;
                } else {
                    numberOfWrongAnswers[1]++;
                    return false;
                }
            case 3:
                if (thirdQuestionComboBox.getValue().equals(RIGHT_THIRD_ANSWER)) {
                    numberOfQuestion++;
                    return true;
                } else {
                    numberOfWrongAnswers[2]++;
                    return false;
                }
            default:
                return false;
        }

    }

    private void switchToThirdQuestion() {
        disableNode(var1);
        disableNode(var2);
        disableNode(var3);
        disableNode(var4);

        enableNode(thirdQuestionComboBox);
    }

    private void switchToSecondQuestion() {
        enableNode(var1);
        enableNode(var2);
        enableNode(var3);
        enableNode(var4);

        disableNode(moscowId);
        disableNode(shanghaiId);
        disableNode(marselId);
        disableNode(romeId);
        disableNode(pragaId);
        disableNode(osloId);
    }

    private void enableNode(Node but) {
        but.setDisable(false);
    }

    private void disableNode(Node but) {
        but.setDisable(true);
    }
}
