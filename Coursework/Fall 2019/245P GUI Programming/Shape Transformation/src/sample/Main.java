package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class Main extends Application implements KeyListener {

    private Button rButton;
    private Button lButton;
    private int angle = 0;
    private int X = 0;
    private int Y = 0;
    private TextField textField;
    private boolean preserveRatio = true;
    private double INIT_VALUE = 1;

    @Override
    public void init() {
        System.out.println("Before");
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        Text name = new Text("soobin choi");
        name.setFont(Font.font("Times New Roman", FontWeight.LIGHT, 15));
        name.setFill(Color.WHITE);

        Polygon polygon = new Polygon();
        polygon.setFill(Color.LIGHTGREY);
        polygon.getPoints().addAll(
                0.0, 0.0,
                100.0, 60.0,
                20.0, 120.0);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(polygon, name);
        stack.setLayoutX(X);
        stack.setLayoutY(Y);

        FileInputStream leftFile = new FileInputStream("src/left_rotate.png");
        Image leftImage = new Image(leftFile);
        ImageView leftImageView = new ImageView(leftImage);

        FileInputStream rightFile = new FileInputStream("src/right_rotate.png");
        Image rightImage = new Image(rightFile);
        ImageView rightImageView = new ImageView(rightImage);

        rButton = new Button("Rotate Right", rightImageView);
        lButton = new Button("Rotate Left", leftImageView);

        rightImageView.setPreserveRatio(preserveRatio);
        leftImageView.setPreserveRatio(preserveRatio);
        rightImageView.setFitHeight(20);
        leftImageView.setFitHeight(20);

        rButton.setContentDisplay(ContentDisplay.RIGHT);
        lButton.setContentDisplay(ContentDisplay.LEFT);
        rButton.setPrefSize(120,30);
        lButton.setPrefSize(120,30);

        HBox hb = new HBox(10);

        Text sliderText = new Text("Size: ");

        Slider sliderScale = new Slider(1, 2, 1);
        sliderScale.setMajorTickUnit(1);
        sliderScale.setValue(INIT_VALUE);
        sliderScale.setMinWidth(500);

        textField = new TextField();
        textField.setPrefSize(50,20);
        textField.setText(Double.toString(INIT_VALUE));
        textField.textProperty().bindBidirectional(sliderScale.valueProperty(), NumberFormat.getNumberInstance());

        stack.scaleXProperty().bind(sliderScale.valueProperty());
        stack.scaleYProperty().bind(sliderScale.valueProperty());

        VBox rb = new VBox(5);
        ToggleGroup tg = new ToggleGroup();

        RadioButton rbGreen = new RadioButton ("Green");
        RadioButton rbRed = new RadioButton ("Red");
        RadioButton rbBlue = new RadioButton ("Blue");
        RadioButton rbYellow = new RadioButton ("Yellow");
        RadioButton rbViolet = new RadioButton ("Violet");

        rbGreen.setToggleGroup(tg);
        rbRed.setToggleGroup(tg);
        rbBlue.setToggleGroup(tg);
        rbYellow.setToggleGroup(tg);
        rbViolet.setToggleGroup(tg);

        rb.getChildren().addAll(rbGreen, rbRed, rbBlue, rbYellow, rbViolet);



        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton rbSelected = (RadioButton)tg.getSelectedToggle();
                if (rbSelected.getText().equals("Green")) {
                    polygon.setFill(Color.LIGHTGREEN);
                }
                if (rbSelected.getText().equals("Red")) {
                    polygon.setFill(Color.PALEVIOLETRED);
                }
                if (rbSelected.getText().equals("Blue")) {
                    polygon.setFill(Color.LIGHTSKYBLUE);
                }
                if (rbSelected.getText().equals("Yellow")) {
                    polygon.setFill(Color.LIGHTYELLOW);
                }
                if (rbSelected.getText().equals("Violet")) {
                    polygon.setFill(Color.VIOLET);
                }
                stack.requestFocus();
            }
        });

        hb.getChildren().addAll(sliderText, textField, sliderScale, rb);

        rButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Rotate right");
                stack.setRotate(angle + 90);
                angle += 90;
                stack.requestFocus();
            }
        });

        lButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Rotate left");
                stack.setRotate(angle - 90);
                angle -= 90;
                stack.requestFocus();
            }
        });

        stack.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                if (keyEvent.getCode()==KeyCode.DOWN) {
                    System.out.println("Moving down");
                    stack.setTranslateY(Y + 10);
                    Y += 10;
                    keyEvent.consume();
                }
                if (keyEvent.getCode()==KeyCode.UP) {
                    System.out.println("Moving up");
                    stack.setTranslateY(Y - 10);
                    Y -= 10;
                    keyEvent.consume();
                }
                if (keyEvent.getCode()==KeyCode.RIGHT) {
                    System.out.println("Moving right");
                    stack.setTranslateX(X + 10);
                    X += 10;
                    keyEvent.consume();
                }
                if (keyEvent.getCode()==KeyCode.LEFT) {
                    System.out.println("Moving left");
                    stack.setTranslateX(X - 10);
                    X -= 10;
                    keyEvent.consume();
                }
                stack.requestFocus();
            }
        });

        name.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Mouse clicked");
                KeyValue kvX = new KeyValue(stack.translateXProperty(), 0);
                KeyValue kvY = new KeyValue(stack.translateYProperty(), 0);
                KeyValue kvRotate = new KeyValue(stack.rotateProperty(), 0);
                X=0;
                Y=0;
                KeyFrame kfX = new KeyFrame(Duration.seconds(1), kvX);
                KeyFrame kfY = new KeyFrame(Duration.seconds(1), kvY);
                KeyFrame kfRotate = new KeyFrame(Duration.seconds(1), kvRotate);

                FillTransition ft = new FillTransition(Duration.seconds(1), polygon, (Color) polygon.getFill(), Color.LIGHTGREY);

                sliderScale.setValue(1);
//                ScaleTransition st = new ScaleTransition(Duration.seconds(1), polygon);
//                ScaleTransition stName = new ScaleTransition(Duration.seconds(1), name);
//                stack.scaleXProperty().bind(sliderScale.valueProperty());
//                stack.scaleYProperty().bind(sliderScale.valueProperty());
//                textField.textProperty().bindBidirectional(sliderScale.valueProperty(), NumberFormat.getNumberInstance());
//                st.setToX(0.5f);
//                st.setToY(0.5f);
//                stName.setToX(0.5f);
//                stName.setToY(0.5f);

                Timeline tl = new Timeline (kfX, kfY, kfRotate);
                tl.play();
                ft.play();
//                st.play();
//                stName.play();
                stack.requestFocus();

            }
        });


        sliderScale.setFocusTraversable(false);
        hb.setFocusTraversable(false);
        rButton.setFocusTraversable(false);
        lButton.setFocusTraversable(false);
        rb.setFocusTraversable(false);


        BorderPane root = new BorderPane();
        root.setLeft(lButton);
        root.setRight(rButton);
        root.setCenter(stack);
        root.setBottom(hb);
        root.setPadding(new Insets(20,20,20,20));

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.show();
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
    }
}
