package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private static File prevFile = null;
    private static File nextFile = null;
    private static File image;
    private TextField textID;
    private TextField textLastName;
    private TextField textFirstName;
    private ComboBox listMajor;
    private ComboBox listGrade;
    private TextArea areaNotes;
    private RadioButton rbGrade;
    private RadioButton rbNoGrade;
    private CheckBox cbStatus;
    private RadioButton rbHonor;
    private RadioButton rbNonHonor;
    private RadioButton rbGOSelected;
    private RadioButton rbHSSelected;
    private ImageView imageView;
    private Image img;
    private String id;
    private String firstName;
    private String lastName;
    private TableView<Student> table = new TableView<>();
    private ObservableList<Student> students =
            FXCollections.observableArrayList(new Student("101", "Choi", "Soobin", "Software Engineering", "A", "Letter Grade", "Honor", true, "Notes", "file:/Users/soobinchoi/Desktop/Screen%20Shot%202019-09-29%20at%202.42.35%20PM.png"));
    // PIE CHART
    private ObservableList<PieChart.Data> pieMajor = FXCollections.observableArrayList();
    private int cs = 0, swe = 0, ce = 0;
    private PieChart chartMajor = new PieChart();
    // BAR GRAPH
    private ObservableList<XYChart.Series<String, Double>> barGrade = FXCollections.observableArrayList();
    private int A = 0, B = 0, C = 0, D = 0, F = 0;
    private XYChart.Series<String, Double> a = new XYChart.Series<>();
    private XYChart.Series<String, Double> b = new XYChart.Series<>();
    private XYChart.Series<String, Double> c = new XYChart.Series<>();
    private XYChart.Series<String, Double> d = new XYChart.Series<>();
    private XYChart.Series<String, Double> f = new XYChart.Series<>();
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private BarChart chartGrade = new BarChart<>(xAxis, yAxis);

    private Student student;

    public Main() throws FileNotFoundException {
        prePopulate ();
        // students.add(new Student("102", "Choi", "Yongik", "Computer Science", "A", "Letter Grade", "Honor", "Notes", "URL"));
    }

    public void prePopulate() throws FileNotFoundException {
        students = FXCollections.observableArrayList();
        table.setItems(students);

        String dirName = "./Student_Roster/";
        File dir = new File(dirName);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i ++) {
            try {
                File file = new File(String.valueOf(dir_contents[i]));
                List<String> lines = new ArrayList<String>();
                String line;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String studentID = lines.get(0);
                String lastName = lines.get(1);
                String firstName = lines.get(2);
                String major = lines.get(3);
                String grade = lines.get(4);
                String gradeOption = lines.get(5);
                String honorStatus = lines.get(6);
                Boolean status = Boolean.parseBoolean(lines.get(7));
                String notes = lines.get(8);
                String img = lines.get(9);

                students.add(new Student(studentID, lastName, firstName, major, grade, gradeOption, honorStatus, status, notes, img));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*
         Initializers for tab and stats pane
         */

        TabPane tabpane = new TabPane(); // DONE
        Tab tab1 = new Tab("Roster"); // DONE
        Tab tab2 = new Tab("Stats"); // DONE

        tabpane.getTabs().add(tab1); // DONE
        tabpane.getTabs().add(tab2); // DONE

        Tab selectedTab = tabpane.getSelectionModel().getSelectedItem();

        Menu menu = new Menu("File"); // DONE
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem saveAsFile = new MenuItem("Save As");
        MenuItem closeFile = new MenuItem("Close");
        MenuItem exitFile = new MenuItem("Exit");
        MenuBar mb = new MenuBar(); // DONE
        menu.getItems().addAll(newFile, openFile, saveFile, saveAsFile, closeFile, exitFile);
        mb.getMenus().add(menu);

        VBox fieldBox = new VBox(5); // root
        VBox root = new VBox(5); // DONE
        VBox menuBox = new VBox(mb); // DONE
        HBox box = new HBox(5);
        HBox majorgradeBox = new HBox(5);
        HBox gradoptionBox = new HBox(5);
        HBox honorstatusBox = new HBox(5);
        HBox noteimageBox = new HBox(5);
        ScrollPane sp = new ScrollPane(); // DONE
        tab1.setContent(root); // DONE

        table.setItems(students);
        table.setEditable(true);

        // table.setItems(students);
        sp.setContent(table);

        // TABLE //
        // student ID column
        TableColumn<Student, String> columnID = new TableColumn<>("Student ID");
        columnID.setMinWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<Student, String>("studentID"));

        // last name column
        TableColumn<Student, String> columnLastName = new TableColumn<>("Last Name");
        columnLastName.setMinWidth(100);
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // first name column
        TableColumn<Student, String> columnFirstName = new TableColumn<>("First Name");
        columnFirstName.setMinWidth(100);
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        ObservableList<String> majorList = FXCollections.observableArrayList();
        majorList.add("Computer Science");
        majorList.add("Software Engineering");
        majorList.add("Computer Engineering");

        // major column
        TableColumn<Student, String> columnMajor = new TableColumn<>("Major");
        columnMajor.setMinWidth(100);
        columnMajor.setCellValueFactory(new PropertyValueFactory<>("major"));

        columnMajor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMajor.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            t.getRowValue().setMajor(t.getNewValue());
            student = table.getSelectionModel().getSelectedItem();
            listMajor.setValue(student.getMajor());
        });

        // grade column
        TableColumn<Student, String> columnGrade = new TableColumn<>("Grade");
        columnGrade.setMinWidth(30);
        columnGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        columnGrade.setCellFactory(TextFieldTableCell.forTableColumn());
        columnGrade.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            t.getRowValue().setGrade(t.getNewValue());
            student = table.getSelectionModel().getSelectedItem();
            listGrade.setValue(student.getGrade());
        });

        // grade option column
        TableColumn<Student, String> columnGradeOption = new TableColumn<>("Grade Option");
        columnGradeOption.setMinWidth(100);
        columnGradeOption.setCellValueFactory(new PropertyValueFactory<>("gradeOption"));
        columnGradeOption.setCellFactory(TextFieldTableCell.forTableColumn());
        columnGradeOption.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            t.getRowValue().setGradeOption(t.getNewValue());
            student = table.getSelectionModel().getSelectedItem();
            if (student.getGradeOption().equals(("Letter Grade"))) {
                rbGrade.setSelected(true);
            }
            if (student.getGradeOption().equals(("Pass/Fail"))) {
                rbNoGrade.setSelected(true);
            }
        });

        // honor status column
        TableColumn<Student, String> columnHonorStatus = new TableColumn<>("Honor Status");
        columnHonorStatus.setMinWidth(100);
        columnHonorStatus.setCellValueFactory(new PropertyValueFactory<>("honorStatus"));
        columnHonorStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        columnHonorStatus.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            t.getRowValue().setHonorStatus(t.getNewValue());
            student = table.getSelectionModel().getSelectedItem();
            if (student.getHonorStatus().equals(("Honor"))) {
                rbHonor.setSelected(true);
            }
            if (student.getHonorStatus().equals(("Non-Honor"))) {
                rbNonHonor.setSelected(true);
            }
        });

        // honor checkbox column
        TableColumn<Student, Boolean> columnStatus = new TableColumn<>("Status");
        columnStatus.setMinWidth(100);
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnStatus.setCellFactory(tc -> new CheckBoxTableCell<>());
        columnHonorStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        columnHonorStatus.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            t.getRowValue().setHonorStatus(t.getNewValue());
            student = table.getSelectionModel().getSelectedItem();
            if (student.getStatus() == true) {
                cbStatus.isSelected();
            }
        });

        // notes
        TableColumn<Student, String> columnNotes = new TableColumn<>("Notes");
        columnNotes.setMinWidth(100);
        columnNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        columnNotes.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNotes.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            t.getRowValue().setNotes(t.getNewValue());
            student = table.getSelectionModel().getSelectedItem();
            areaNotes.setText(student.getNotes());
        });

        // images
        TableColumn<Student, Image> columnImages = new TableColumn<>("Img");
        columnImages.setMinWidth(100);
        columnImages.setCellValueFactory(new PropertyValueFactory<>("img"));

        getMajorData();

        a.setName("A");
        b.setName("B");
        c.setName("C");
        d.setName("D");
        f.setName("F");

        getGradeData();

        HBox stats = new HBox(5);
        stats.getChildren().add(chartMajor);
        stats.getChildren().add(chartGrade);
        tab2.setContent(stats);

        // ObservableList<XYChart.Series<String, Double>>

        table.prefWidthProperty().bind(sp.widthProperty()); // DONE
        table.getColumns().add(columnID);
        table.getSortOrder().add(columnID);
        table.getColumns().add(columnLastName);
        table.getSortOrder().add(columnLastName);
        table.getColumns().add(columnFirstName);
        table.getSortOrder().add(columnFirstName);
        table.getColumns().add(columnMajor);
        table.getColumns().add(columnGrade);
        table.getColumns().add(columnGradeOption);
        table.getColumns().add(columnHonorStatus);
        table.getColumns().add(columnNotes);
        table.getColumns().add(columnImages);
        table.getColumns().add(columnStatus);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // DONE
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // DONE

        Circle circle = new Circle(); // DONE
        circle.setRadius(50); // DONE
        circle.setFill(Color.PALEVIOLETRED); // DONE

        Button addButton = new Button("Add New Student");
        Button deleteButton = new Button("Delete Student");
        Button saveButton = new Button("Save Changes");
        Button nextButton = new Button("Next Student");
        Button prevButton = new Button("Previous Student");

        Text lblID = new Text("Student ID");
        Text lblLastName = new Text("Last Name");
        Text lblFirstName = new Text("First Name");
        Text lblMajor = new Text("Major");
        Text lblGrade = new Text("Current Grade");
        Text lblGradeOption = new Text("Grade Option");
        Text lblHonorStatus = new Text("Honor Status");
        Text lblStatus = new Text("Honor Status");
        Text lblNotes = new Text("Notes: ");

        textID = new TextField(); // DONE
        textLastName = new TextField(); // DONE
        textFirstName = new TextField(); // DONE

        String[] major = new String[]{ // DONE
                "Computer Science",
                "Software Engineering",
                "Computer Engineering"
        };

        String[] grade = new String[]{
                "A",
                "B",
                "C",
                "D",
                "F"
        }; // DONE

        listMajor = new ComboBox(FXCollections.observableArrayList(major)); // DONE
        listGrade = new ComboBox(FXCollections.observableArrayList(grade)); // DONE

        ToggleGroup tgGO = new ToggleGroup(); // DONE
        rbGrade = new RadioButton("Letter Grade"); // DONE
        rbNoGrade = new RadioButton("Pass/Fail"); // DONE

        ToggleGroup tgHS = new ToggleGroup(); // DONE
        rbHonor = new RadioButton("Honor"); // DONE
        rbNonHonor = new RadioButton("Non-Honor"); // DONE

        areaNotes = new TextArea(); // DONE

        FileChooser chooseImg = new FileChooser();
        Button imgButton = new Button("Upload Image"); // DONE
        imageView = new ImageView(); // DONE

        imageView.maxWidth(100); // DONE
        imageView.setPreserveRatio(true); // DONE

        chooseImg.setInitialDirectory(new File("./Student_Roster/"));

        rbGrade.setToggleGroup(tgGO); // DONE
        rbNoGrade.setToggleGroup(tgGO); // DONE

        rbHonor.setToggleGroup(tgHS); // DONE
        rbNonHonor.setToggleGroup(tgHS); // DONE

        cbStatus = new CheckBox("Honor"); // DONE

        textID.setPrefSize(120, 30); // DONE
        textFirstName.setPrefSize(120, 30); // DONE
        textLastName.setPrefSize(120, 30); // DONE

        addButton.setPrefSize(120, 30);
        deleteButton.setPrefSize(120, 30);
        saveButton.setPrefSize(120, 30);
        nextButton.setPrefSize(120, 30);
        prevButton.setPrefSize(120, 30);

        fieldBox.setPadding(new Insets(10, 10, 10, 10)); // DONE
        fieldBox.getChildren().add(menuBox);
        fieldBox.getChildren().add(circle); // DONE
        fieldBox.getChildren().add(lblID); // DONE
        fieldBox.getChildren().add(textID); // DONE
        fieldBox.getChildren().add(lblLastName); // DONE
        fieldBox.getChildren().add(textLastName); // DONE
        fieldBox.getChildren().add(lblFirstName); // DONE
        fieldBox.getChildren().add(textFirstName); // DONE
        majorgradeBox.getChildren().add(lblMajor); // DONE
        majorgradeBox.getChildren().add(listMajor); // DONE
        majorgradeBox.getChildren().add(lblGrade); // DONE
        majorgradeBox.getChildren().add(listGrade); // DONE
        gradoptionBox.getChildren().add(lblGradeOption); // DONE
        gradoptionBox.getChildren().addAll(rbGrade, rbNoGrade); // DONE
        honorstatusBox.getChildren().add(lblHonorStatus); // DONE
        honorstatusBox.getChildren().addAll(rbHonor, rbNonHonor); // DONE
        honorstatusBox.getChildren().addAll(lblStatus, cbStatus); // DONE
        noteimageBox.getChildren().add(lblNotes); // DONE
        noteimageBox.getChildren().add(areaNotes); // DONE
        noteimageBox.getChildren().add(imgButton);
        noteimageBox.getChildren().add(imageView);
        fieldBox.getChildren().addAll(majorgradeBox, gradoptionBox, honorstatusBox, noteimageBox, box);
        box.getChildren().add(addButton);
        box.getChildren().add(deleteButton);
        box.getChildren().add(saveButton);
        box.getChildren().add(nextButton);
        box.getChildren().add(prevButton);
        root.getChildren().addAll(menuBox, sp, fieldBox);

        Scene scene = new Scene(tabpane, 1600, 900, Color.WHITE);
        primaryStage.setScene(scene);

        primaryStage.show();

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int index = table.getSelectionModel().getSelectedIndex();
                student = table.getItems().get(index);
                id = student.getStudentID();
                firstName = student.getFirstName();
                lastName = student.getLastName();
                System.out.println(id + " " + firstName + " " + lastName);
                populate();
            }
        });

        stats.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                pieMajor = FXCollections.observableArrayList();
                getMajorData();
                System.out.println("CLICKED");
            }
        });


        EventHandler<ActionEvent> upload =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        // get the file selected
                        image = chooseImg.showOpenDialog(primaryStage);
                        if (image != null) {
                            Image img = new Image(image.toURI().toString());
                            imageView.setFitWidth(100);
                            imageView.setPreserveRatio(true);
                            imageView.setImage(img);
                            System.out.println("Uploaded Image From File: " + image.toURI().toString());
                        }
                    }
                };

        imgButton.setOnAction(upload);

        Text tgGOval = new Text();
        Text tgHSval = new Text();

        tgGO.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                rbGOSelected = (RadioButton) tgGO.getSelectedToggle();
                if (rbGOSelected.getText().equals("Letter Grade")) {
                    tgGOval.setText("Letter Grade");
                }
                if (rbGOSelected.getText().equals("Pass/Fail")) {
                    tgGOval.setText("Pass/Fail");
                }
            }
        });

        tgHS.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                rbHSSelected = (RadioButton) tgHS.getSelectedToggle();
                if (rbHSSelected.getText().equals("Honor")) {
                    tgHSval.setText("Honor");
                }
                if (rbHSSelected.getText().equals("Non-Honor")) {
                    tgHSval.setText("Non-Honor");
                }
            }
        });

        cbStatus.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("CBSTATUS HANLDER" + student.getStatus());
                if (cbStatus.getText().equals("Honor")) {
                    cbStatus.isSelected();
                }
            }
        });

        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textID.clear();
                textLastName.clear();
                textFirstName.clear();
                listMajor.getSelectionModel().clearSelection();
                listGrade.getSelectionModel().clearSelection();
                rbGrade.setSelected(false);
                rbNoGrade.setSelected(false);
                rbHonor.setSelected(false);
                rbNonHonor.setSelected(false);
                cbStatus.setSelected(false);
                areaNotes.clear();
                imageView.setImage(null);

                System.out.println("Add New Student");
            }
        });

        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser chooseFile = new FileChooser();
                chooseFile.setInitialDirectory(new File("."));
                File chosenFile = chooseFile.showOpenDialog(primaryStage);
                System.out.println(chosenFile.getName());

                List<String> lines = new ArrayList<String>();
                String line;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(chosenFile.getAbsoluteFile()));
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textID.setText(lines.get(0));
                textLastName.setText(lines.get(1));
                textFirstName.setText(lines.get(2));
                if (lines.get(3).equals("Computer Science")) {
                    listMajor.getSelectionModel().select("Computer Science");
                }
                if (lines.get(3).equals("Software Engineering")) {
                    listMajor.getSelectionModel().select("Software Engineering");
                }
                if (lines.get(3).equals("Computer Engineering")) {
                    listMajor.getSelectionModel().select("Computer Engineering");
                }

                if (lines.get(4).equals("A")) {
                    listGrade.getSelectionModel().select("A");
                }
                if (lines.get(4).equals("B")) {
                    listGrade.getSelectionModel().select("B");
                }
                if (lines.get(4).equals("C")) {
                    listGrade.getSelectionModel().select("C");
                }
                if (lines.get(4).equals("D")) {
                    listGrade.getSelectionModel().select("D");
                }
                if (lines.get(4).equals("F")) {
                    listGrade.getSelectionModel().select("F");
                }

                if (lines.get(5).equals("Letter Grade")) {
                    rbGrade.setSelected(true);
                }
                if (lines.get(5).equals("Pass/Fail")) {
                    rbNoGrade.setSelected(true);
                }
                if (lines.get(6).equals("Honor")) {
                    rbHonor.setSelected(true);
                }
                if (lines.get(6).equals("Non-Honor")) {
                    rbNonHonor.setSelected(true);
                }
                if (lines.get(7).equals("true")) {
                    cbStatus.setSelected(true);
                }
                if (lines.get(7).equals("false")) {
                    cbStatus.setSelected(false);
                }
                areaNotes.setText(lines.get(8));
                img = new Image(lines.get(9), 100, 100, true, true);
                imageView.setImage(img);
            }
        });

        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent addStudent) {
                try {
                    String studentFile = textID.getText() +
                            "_" + textLastName.getText() +
                            "_" + textFirstName.getText();
                    FileWriter fw = new FileWriter("./Student_Roster/" + studentFile);
                    File temp = new File("./Student_Roster/" + studentFile);
                    if (temp.exists()) {

                        fw.write(textID.getText() + "\n");
                        fw.write(textLastName.getText() + "\n");
                        fw.write(textFirstName.getText() + "\n");
                        fw.write(listMajor.getValue() + "\n");
                        fw.write(listGrade.getValue() + "\n");
                        fw.write(tgGOval.getText() + "\n");
                        fw.write(tgHSval.getText() + "\n");
                        if (cbStatus.isSelected()) {
                            fw.write(true + "\n");
                        }
                        if (!cbStatus.isSelected()){
                            fw.write(false + "\n");
                        }
                        fw.write(areaNotes.getText() + "\n");
                        fw.write(image.toURI().toString());
                        fw.close();

                        textID.clear();
                        textLastName.clear();
                        textFirstName.clear();
                        listMajor.getSelectionModel().clearSelection();
                        listGrade.getSelectionModel().clearSelection();
                        rbGrade.setSelected(false);
                        rbNoGrade.setSelected(false);
                        rbHonor.setSelected(false);
                        rbNonHonor.setSelected(false);
                        cbStatus.setSelected(false);
                        areaNotes.clear();
                        imageView.setImage(null);

                        System.out.println("Saved Student Data");
                    } else {
                        FileChooser fileChooser = new FileChooser();

                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        fileChooser.getExtensionFilters().add(extFilter);

                        //Show save file dialog
                        File file = fileChooser.showSaveDialog(primaryStage);

                        System.out.println("Student Not Found");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getMajorData();
                getGradeData();
            }
        });

        saveAsFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent addStudent) {
                FileChooser fileChooser = new FileChooser();

                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);
                try {
                    String filePath[] = new String(file.toString()).split("/");
                    String saveAsFileName = filePath[filePath.length - 1];
                    String studentFile = textID.getText() +
                            "_" + textLastName.getText() +
                            "_" + textFirstName.getText();
                    FileWriter fw = new FileWriter("./Student_Roster/" + saveAsFileName);
                    File temp = new File("./Student_Roster/" + saveAsFileName);
                    fw.write(textID.getText() + "\n");
                    fw.write(textLastName.getText() + "\n");
                    fw.write(textFirstName.getText() + "\n");
                    fw.write(listMajor.getValue() + "\n");
                    fw.write(listGrade.getValue() + "\n");
                    fw.write(tgGOval.getText() + "\n");
                    fw.write(tgHSval.getText() + "\n");
                    fw.write(cbStatus.getText() + "\n");
                    fw.write(areaNotes.getText() + "\n");
                    fw.write(image.toURI().toString());
                    fw.close();

                    textID.clear();
                    textLastName.clear();
                    textFirstName.clear();
                    listMajor.getSelectionModel().clearSelection();
                    listGrade.getSelectionModel().clearSelection();
                    rbGrade.setSelected(false);
                    rbNoGrade.setSelected(false);
                    rbHonor.setSelected(false);
                    rbNonHonor.setSelected(false);
                    cbStatus.setSelected(false);
                    areaNotes.clear();
                    imageView.setImage(null);

                    System.out.println("Saved Student Data");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getMajorData();
                getGradeData();
            }
        });

        closeFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent addStudent) {
                textID.clear();
                textLastName.clear();
                textFirstName.clear();
                listMajor.getSelectionModel().clearSelection();
                listGrade.getSelectionModel().clearSelection();
                rbGrade.setSelected(false);
                rbNoGrade.setSelected(false);
                rbHonor.setSelected(false);
                rbNonHonor.setSelected(false);
                cbStatus.setSelected(false);
                areaNotes.clear();
                imageView.setImage(null);
            }
        });

        exitFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent addStudent) {
                System.out.println("Exit Application");
                primaryStage.close();
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent addStudent) {

                addButtonClicked();
                try {
                    String studentFile = textID.getText() +
                            "_" + textLastName.getText() +
                            "_" + textFirstName.getText();
                    FileWriter fw = new FileWriter("./Student_Roster/" + studentFile + ".txt");
                        fw.write(textID.getText() + "\n");
                        fw.write(textLastName.getText() + "\n");
                        fw.write(textFirstName.getText() + "\n");
                        fw.write(listMajor.getValue() + "\n");
                        fw.write(listGrade.getValue() + "\n");
                        fw.write(tgGOval.getText() + "\n");
                        fw.write(tgHSval.getText() + "\n");
                        if (cbStatus.isSelected()) {
                            fw.write(true + "\n");
                        }
                        if (!cbStatus.isSelected()){
                            fw.write(false + "\n");
                        }
                        fw.write(areaNotes.getText() + "\n");
                        fw.write(image.toURI().toString());
                        fw.close();

                        textID.clear();
                        textLastName.clear();
                        textFirstName.clear();
                        listMajor.getSelectionModel().clearSelection();
                        listGrade.getSelectionModel().clearSelection();
                        rbGrade.setSelected(false);
                        rbNoGrade.setSelected(false);
                        rbHonor.setSelected(false);
                        rbNonHonor.setSelected(false);
                        cbStatus.setSelected(false);
                        areaNotes.clear();
                        imageView.setImage(null);

                        System.out.println("Student File Added");

                } catch (IOException e) {
                    System.out.println("ERROR: MISSING FIELDS");
                    e.printStackTrace();
                }
                getMajorData();
                getGradeData();
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent deleteStudent) {

                deleteButtonClicked();

                String dirName = "./Student_Roster/";
                String fileDir = dirName + textID.getText() + "_" + textLastName.getText() + "_" + textFirstName.getText() + ".txt";
                File file = new File(fileDir);
                boolean check = file.exists();
                System.out.println("File Exists: " + check);
                boolean bool = file.delete();
                System.out.println("File Deleted: " + bool);

                getMajorData();
                getGradeData();

                textID.clear();
                textLastName.clear();
                textFirstName.clear();
                listMajor.getSelectionModel().clearSelection();
                listGrade.getSelectionModel().clearSelection();
                rbGrade.setSelected(false);
                rbNoGrade.setSelected(false);
                rbHonor.setSelected(false);
                rbNonHonor.setSelected(false);
                cbStatus.setSelected(false);
                areaNotes.clear();
                imageView.setImage(null);
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent addStudent) {
                try {
                    String studentFile = textID.getText() +
                            "_" + textLastName.getText() +
                            "_" + textFirstName.getText() + ".txt";
                    FileWriter fw = new FileWriter("./Student_Roster/" + studentFile);
                    File temp = new File("./Student_Roster/" + studentFile);
                    if (temp.exists()) {

                        fw.write(textID.getText() + "\n");
                        fw.write(textLastName.getText() + "\n");
                        fw.write(textFirstName.getText() + "\n");
                        fw.write(listMajor.getValue() + "\n");
                        fw.write(listGrade.getValue() + "\n");
                        fw.write(tgGOval.getText() + "\n");
                        fw.write(tgHSval.getText() + "\n");
                        if (cbStatus.isSelected()) {
                            fw.write(true + "\n");
                        }
                        if (!cbStatus.isSelected()){
                            fw.write(false + "\n");
                        }
                        fw.write(areaNotes.getText() + "\n");
                        fw.write(image.toURI().toString());
                        fw.close();

                        textID.clear();
                        textLastName.clear();
                        textFirstName.clear();
                        listMajor.getSelectionModel().clearSelection();
                        listGrade.getSelectionModel().clearSelection();
                        rbGrade.setSelected(false);
                        rbNoGrade.setSelected(false);
                        rbHonor.setSelected(false);
                        rbNonHonor.setSelected(false);
                        cbStatus.setSelected(false);
                        areaNotes.clear();
                        imageView.setImage(null);

                        System.out.println("Saved Student Data");

                        prePopulate();
                    } else {

                        textID.clear();
                        textLastName.clear();
                        textFirstName.clear();
                        listMajor.getSelectionModel().clearSelection();
                        listGrade.getSelectionModel().clearSelection();
                        rbGrade.setSelected(false);
                        rbNoGrade.setSelected(false);
                        rbHonor.setSelected(false);
                        rbNonHonor.setSelected(false);
                        cbStatus.setSelected(false);
                        areaNotes.clear();
                        imageView.setImage(null);

                        System.out.println("Student Not Found");

                        prePopulate();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getMajorData();
                getGradeData();
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent deleteStudent) {
                String dirName = "./Student_Roster/";
                File dir = new File(dirName);
                File[] dir_contents = dir.listFiles();
                Arrays.sort(dir_contents);
                for (int i = 0; i < dir_contents.length; i++) {
                    if (dir_contents[i].getName().equals(textID.getText() + "_" + textLastName.getText() + "_" + textFirstName.getText() + ".txt")) {
                        nextFile = new File(dir_contents[++i].getName());
                        System.out.println("Opening next student file: " + nextFile);

                        textID.clear();
                        textLastName.clear();
                        textFirstName.clear();
                        listMajor.getSelectionModel().clearSelection();
                        listGrade.getSelectionModel().clearSelection();
                        rbGrade.setSelected(false);
                        rbNoGrade.setSelected(false);
                        rbHonor.setSelected(false);
                        rbNonHonor.setSelected(false);
                        cbStatus.setSelected(false);
                        areaNotes.clear();
                        imageView.setImage(null);

                        List<String> lines = new ArrayList<String>();
                        String line;
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(dirName + nextFile));
                            while ((line = br.readLine()) != null) {
                                lines.add(line);
                            }
                            br.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        textID.setText(lines.get(0));
                        textLastName.setText(lines.get(1));
                        textFirstName.setText(lines.get(2));
                        if (lines.get(3).equals("Computer Science")) {
                            listMajor.getSelectionModel().select("Computer Science");
                        }
                        if (lines.get(3).equals("Software Engineering")) {
                            listMajor.getSelectionModel().select("Software Engineering");
                        }
                        if (lines.get(3).equals("Computer Engineering")) {
                            listMajor.getSelectionModel().select("Computer Engineering");
                        }

                        if (lines.get(4).equals("A")) {
                            listGrade.getSelectionModel().select("A");
                        }
                        if (lines.get(4).equals("B")) {
                            listGrade.getSelectionModel().select("B");
                        }
                        if (lines.get(4).equals("C")) {
                            listGrade.getSelectionModel().select("C");
                        }
                        if (lines.get(4).equals("D")) {
                            listGrade.getSelectionModel().select("D");
                        }
                        if (lines.get(4).equals("F")) {
                            listGrade.getSelectionModel().select("F");
                        }

                        if (lines.get(5).equals("Letter Grade")) {
                            rbGrade.setSelected(true);
                        }
                        if (lines.get(5).equals("Pass/Fail")) {
                            rbNoGrade.setSelected(true);
                        }
                        if (lines.get(6).equals("Honor")) {
                            rbHonor.setSelected(true);
                        }
                        if (lines.get(6).equals("Non-Honor")) {
                            rbNonHonor.setSelected(true);
                        }
                        if (lines.get(7).equals("true")) {
                            cbStatus.isSelected();
                        }
                        areaNotes.setText(lines.get(8));
                        img = new Image(lines.get(9), 100, 100, true, true);
                        imageView.setImage(img);
                        return;
                    }
                }

            }
        });

        prevButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent deleteStudent) {
                String dirName = "./Student_Roster/";
                File dir = new File(dirName);
                File[] dir_contents = dir.listFiles();
                Arrays.sort(dir_contents);
                for (int i = 0; i < dir_contents.length; i++) {
                    if (dir_contents[i].getName().equals(textID.getText() + "_" + textLastName.getText() + "_" + textFirstName.getText() + ".txt")) { ;
                        prevFile = new File(dir_contents[--i].getName());
                        System.out.println("Opening previous student file: " + prevFile);

                        textID.clear();
                        textLastName.clear();
                        textFirstName.clear();
                        listMajor.getSelectionModel().clearSelection();
                        listGrade.getSelectionModel().clearSelection();
                        rbGrade.setSelected(false);
                        rbNoGrade.setSelected(false);
                        rbHonor.setSelected(false);
                        rbNonHonor.setSelected(false);
                        cbStatus.setSelected(false);
                        areaNotes.clear();
                        imageView.setImage(null);

                        List<String> lines = new ArrayList<String>();
                        String line;
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(dirName + prevFile));
                            while ((line = br.readLine()) != null) {
                                lines.add(line);
                            }
                            br.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        textID.setText(lines.get(0));
                        textLastName.setText(lines.get(1));
                        textFirstName.setText(lines.get(2));
                        if (lines.get(3).equals("Computer Science")) {
                            listMajor.getSelectionModel().select("Computer Science");
                        }
                        if (lines.get(3).equals("Software Engineering")) {
                            listMajor.getSelectionModel().select("Software Engineering");
                        }
                        if (lines.get(3).equals("Computer Engineering")) {
                            listMajor.getSelectionModel().select("Computer Engineering");
                        }

                        if (lines.get(4).equals("A")) {
                            listGrade.getSelectionModel().select("A");
                        }
                        if (lines.get(4).equals("B")) {
                            listGrade.getSelectionModel().select("B");
                        }
                        if (lines.get(4).equals("C")) {
                            listGrade.getSelectionModel().select("C");
                        }
                        if (lines.get(4).equals("D")) {
                            listGrade.getSelectionModel().select("D");
                        }
                        if (lines.get(4).equals("F")) {
                            listGrade.getSelectionModel().select("F");
                        }
                        if (lines.get(5).equals("Letter Grade")) {
                            rbGrade.setSelected(true);
                        }
                        if (lines.get(5).equals("Pass/Fail")) {
                            rbNoGrade.setSelected(true);
                        }
                        if (lines.get(6).equals("Honor")) {
                            rbHonor.setSelected(true);
                        }
                        if (lines.get(6).equals("Non-Honor")) {
                            rbNonHonor.setSelected(true);
                        }
                        if (lines.get(7).equals("true")) {
                            cbStatus.isSelected();
                        }
                        areaNotes.setText(lines.get(8));
                        img = new Image(lines.get(9), 100, 100, true, true);
                        imageView.setImage(img);
                        return;
                    }
                }

            }
        });
    }

    private void populate() {
        String dirName = "./Student_Roster/";
        File dir = new File(dirName);
        File[] dir_contents = dir.listFiles();

        String studentID = id;
        String studentfirstName = firstName;
        String studentlastName = lastName;

        File matchFile = new File(dir + "/" + studentID + "_" + studentfirstName + "_" + studentlastName + ".txt");


        for (int i = 0; i < dir_contents.length; i ++) {
            File file = new File(dir_contents[i].toString());
            System.out.println(file + " : to : " + matchFile);
            if (file.exists()) {
                System.out.println("There is a match");
                textID.setText(student.getStudentID());
                textLastName.setText(student.getLastName());
                textFirstName.setText(student.getFirstName());
                listMajor.getSelectionModel().select(student.getMajor());
                listGrade.getSelectionModel().select(student.getGrade());
                if (student.getGradeOption().equals(("Letter Grade"))) {
                    rbGrade.setSelected(true);
                }
                if (student.getGradeOption().equals(("Pass/Fail"))) {
                    rbNoGrade.setSelected(true);
                }
                if (student.getHonorStatus().equals(("Honor"))) {
                    rbHonor.setSelected(true);
                }
                if (student.getHonorStatus().equals(("Honor"))) {
                    rbHonor.setSelected(true);
                }
                if (student.getStatus() == true) {
                    cbStatus.setSelected(true);
                }
                if (student.getStatus() == false) {
                    cbStatus.setSelected(false);
                }
                areaNotes.setText(student.getNotes());
                img = new Image(student.getImg(), 100, 100, true, true);
                imageView.setImage(img);
            }
        }

    }

    public ObservableList<Student> getStudent(){
        return students;
    }

    public void addButtonClicked(){
        try {
            String studentID = textID.getText();
            String lastName = textLastName.getText();
            String firstName = textFirstName.getText();
            String major = listMajor.getValue().toString();
            String grade = listGrade.getValue().toString();
            String gradeOption = rbGOSelected.getText();
            String honorStatus = rbHSSelected.getText();
            Boolean status;
            if (cbStatus.getText().equals("Honor")) {
                status = true;
            } else {
                status = false;
            }
            System.out.println("Honor Status " + status);
            String notes = areaNotes.getText();
            String img = image.toURI().toString();

            students.add(new Student(studentID, lastName, firstName, major, grade, gradeOption, honorStatus, status, notes, img));
            // table.getItems().add(student);

        } catch (Exception e) {
            System.out.println("ERROR: MISSING FIELDS / on addButtonClicked");
        }

    }

    public void deleteButtonClicked(){
        ObservableList<Student> studentSelected, allStudents;
        allStudents = table.getItems();
        studentSelected = table.getSelectionModel().getSelectedItems();

        studentSelected.forEach(allStudents::remove);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void getMajorData() {
        String dirName = "./Student_Roster/";
        File dir = new File(dirName);
        File[] dir_contents = dir.listFiles();
        cs = 0;
        swe = 0;
        ce = 0;
        for (int i = 0; i < dir_contents.length; i ++) {
            try {
                File file = new File(String.valueOf(dir_contents[i]));
                List<String> lines = new ArrayList<String>();
                String line;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while ((line = br.readLine()) != null) {
                        if (line.contains("Computer Science")) {
                            cs++;
                        }
                        if (line.contains("Software Engineering")) {
                            swe++;
                        }
                        if (line.contains("Computer Engineering")) {
                            ce++;
                        }
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("CS: " +cs + " SWE: " + swe + " CE: " + ce );
        addNewPieData();
    }

    private void addNewPieData() {
        // chartMajor = new PieChart();
        pieMajor = FXCollections.observableArrayList();
        pieMajor.addAll(new PieChart.Data("Computer Science", cs),
                new PieChart.Data("Software Engineering", swe),
                new PieChart.Data("Computer Engineering", ce));
        chartMajor.setData(pieMajor);
        chartMajor.setTitle("Major Distribution");
        pieMajor.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );
    }

    private void getGradeData() {
        // retrieve grades from file
        String dirName = "./Student_Roster/";
        File dir = new File(dirName);
        File[] dir_contents = dir.listFiles();
        A = 0;
        B = 0;
        C = 0;
        D = 0;
        F = 0;
        for (int i = 0; i < dir_contents.length; i ++) {
            try {
                File file = new File(String.valueOf(dir_contents[i]));
                List<String> lines = new ArrayList<String>();
                String line;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }

                    if (lines.get(4).contains("A")) {
                        A++;
                    }
                    if (lines.get(4).contains("B")) {
                        B++;
                    }
                    if (lines.get(4).contains("C")) {
                        C++;
                    }
                    if (lines.get(4).contains("D")) {
                        D++;
                    }
                    if (lines.get(4).contains("F")) {
                        F++;
                    }


                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(A + " " + B + " " + C + " " + D + " " + F );
        addNewGraphData();
    }

    private void addNewGraphData() {
        a.getData().add(new XYChart.Data<String, Double> ("A", (double) A));
        b.getData().add(new XYChart.Data<String, Double> ("B", (double) B));
        c.getData().add(new XYChart.Data<String, Double> ("C", (double) C));
        d.getData().add(new XYChart.Data<String, Double> ("D", (double) D));
        f.getData().add(new XYChart.Data<String, Double> ("F", (double) F));

        barGrade.addAll(a,b,c,d,f);

        chartGrade = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Letter Grade");
        yAxis.setLabel("Number of Students");
        chartGrade.setData(barGrade);

        chartGrade.setTitle("Letter Grade Distribution");

        barGrade.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName()
                        )
                )
        );
    }

}
