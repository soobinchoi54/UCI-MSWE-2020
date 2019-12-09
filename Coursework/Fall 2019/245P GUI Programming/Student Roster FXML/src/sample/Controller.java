package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    public TextField textID;
    public TextField textLastName;
    public TextField textFirstName;
    public ComboBox listMajor;
    public ComboBox listGrade;
    public CheckBox cbStatus;
    public TextArea areaNotes;
    public ImageView imageView;
    public RadioButton rbGrade;
    public RadioButton rbNoGrade;
    public RadioButton rbHonor;
    public RadioButton rbNonHonor;
    public Button addButton;
    public Button deleteButton;
    public Button saveButton;
    public Button nextButton;
    public Button prevButton;
    public TableView<Student> table = new TableView<>();
    public ScrollPane sp;
    public TableColumn<Student, String> columnID;
    public TableColumn<Student, String> columnLastName;
    public TableColumn<Student, String> columnFirstName;
    public TableColumn<Student, String> columnMajor;
    public TableColumn<Student, String> columnGrade;

    private String id;
    private String lastName;
    private String firstName;
    private Stage primaryStage;
    private File fileImage;
    private File nextFile = null;
    private File prevFile = null;
    private FileWriter fw;
    private Image image;
    private Text tgGOval = new Text();
    private Text tgHSval = new Text();
    private RadioButton rbGOSelected, rbHSSelected;

    @FXML
    private ObservableList<Student> students =
            FXCollections.observableArrayList(new Student("101", "Choi", "Soobin", "Software Engineering", "A", "Letter Grade", "Honor", true, "Notes", "file:/Users/soobinchoi/Desktop/Screen%20Shot%202019-09-29%20at%202.42.35%20PM.png"));

    FileChooser chooseImg;
    Student student;

    private ObservableList<PieChart.Data> pieMajor = FXCollections.observableArrayList();
    private int cs = 0, swe = 0, ce = 0;
    @FXML
    private PieChart chartMajor = new PieChart();
    // BAR GRAPH
    private ObservableList<XYChart.Series<String, Double>> barGrade = FXCollections.observableArrayList();
    private int A = 0, B = 0, C = 0, D = 0, F = 0;
    private XYChart.Series<String, Double> a = new XYChart.Series<>();
    private XYChart.Series<String, Double> b = new XYChart.Series<>();
    private XYChart.Series<String, Double> c = new XYChart.Series<>();
    private XYChart.Series<String, Double> d = new XYChart.Series<>();
    private XYChart.Series<String, Double> f = new XYChart.Series<>();
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private BarChart chartGrade = new BarChart<>(xAxis, yAxis);



    public void initialize(Stage stage) {
        prepopulate();

        chooseImg = new FileChooser();
        chooseImg.setInitialDirectory(new File("./Student_Roster/"));

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

        table.prefWidthProperty().bind(sp.widthProperty());
        student = table.getSelectionModel().getSelectedItem();
        table.getColumns().addAll(columnID, columnLastName, columnFirstName, columnMajor, columnGrade, columnGradeOption, columnHonorStatus, columnStatus, columnNotes, columnImages);

        table.getItems();

        getMajorData();
        a.setName("A");
        b.setName("B");
        c.setName("C");
        d.setName("D");
        f.setName("F");
        getGradeData();
    }

    private void prepopulate() {
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

    public void handleAddButtonAction(ActionEvent actionEvent) {
        addButtonClicked();
        try {
            String studentFile = textID.getText() +
                    "_" + textLastName.getText() +
                    "_" + textFirstName.getText();
            fw = new FileWriter("./Student_Roster/" + studentFile + ".txt");
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
            fw.write(fileImage.toURI().toString());
            fw.close();

            clearFields();

            System.out.println("Student File Added");

        } catch (IOException e) {
            System.out.println("ERROR: MISSING FIELDS");
            e.printStackTrace();
        }
        prepopulate();
        getMajorData();
        getGradeData();
    }

    private void addButtonClicked() {
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
            String img = fileImage.toURI().toString();

            students.add(new Student(studentID, lastName, firstName, major, grade, gradeOption, honorStatus, status, notes, img));

        } catch (Exception e) {
            System.out.println("ERROR: MISSING FIELDS / on addButtonClicked");
        }
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

        // chartGrade = new BarChart<>(xAxis, yAxis);
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

    private void getMajorData() {
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

    public void handleLetterGradeRB(ActionEvent actionEvent) {
        if (rbGrade.isSelected()) {
            tgGOval.setText("Letter Grade");
        }
    }

    public void handlePassFailRB(ActionEvent actionEvent) {
        if(rbNoGrade.isSelected()) {
            tgGOval.setText("Pass/Fail");
        }
    }

    public void handleHonorRB(ActionEvent actionEvent) {
        if(rbHonor.isSelected()) {
            tgHSval.setText("Honor");
        }
    }

    public void handleNonHonorRB(ActionEvent actionEvent) {
        if(rbNonHonor.isSelected()) {
            tgHSval.setText("Non-Honor");
        }
    }

    public void handleUploadButtonAction(ActionEvent actionEvent) {
        fileImage = chooseImg.showOpenDialog(primaryStage);
        if (fileImage != null) {
            Image img = new Image(fileImage.toURI().toString());
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            imageView.setImage(img);
            System.out.println("Uploaded Image From File: " + fileImage.toURI().toString());
        }
    }

    public void handleTableEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            int index = table.getSelectionModel().getSelectedIndex();
            student = table.getItems().get(index);
            id = student.getStudentID();
            firstName = student.getFirstName();
            lastName = student.getLastName();
            System.out.println(id + " " + firstName + " " + lastName);
            populate();
        }
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
                image = new Image(student.getImg(), 100, 100, true, true);
                imageView.setImage(image);
            }
        }
    }

    public void handleSaveButtonAction(ActionEvent actionEvent) {
        try {
            String studentFile = textID.getText() +
                    "_" + textLastName.getText() +
                    "_" + textFirstName.getText() + ".txt";

            fw = new FileWriter("./Student_Roster/" + studentFile);
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
                fw.write(fileImage.toURI().toString());
                fw.close();

                clearFields();

                System.out.println("Saved Student Data");

            } else {
                clearFields();
                System.out.println("Student Not Found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepopulate();
        getMajorData();
        getGradeData();
    }

    public void handleNewFileAction(ActionEvent actionEvent) {
        clearFields();
        System.out.println("Add New Student");
    }

    private void clearFields() {
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

    public void handleOpenFileAction(ActionEvent actionEvent) {
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
        image = new Image(lines.get(9), 100, 100, true, true);
        imageView.setImage(image);
    }

    public void handleSaveAsButtonAction(ActionEvent actionEvent) {
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
            fw.write(fileImage.toURI().toString());
            fw.close();

            clearFields();

            System.out.println("Saved Student Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepopulate();
        getMajorData();
        getGradeData();
    }

    public void handleCloseButtonAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void handleExitButtonAction(ActionEvent actionEvent) {
        System.out.println("Exit Application");
        primaryStage.close();
    }

    public void handleDeleteButtonAction(ActionEvent actionEvent) {

        deleteButtonClicked();

        String dirName = "./Student_Roster/";
        String fileDir = dirName + textID.getText() + "_" + textLastName.getText() + "_" + textFirstName.getText() + ".txt";
        File file = new File(fileDir);
        boolean check = file.exists();
        System.out.println("File Exists: " + check);
        boolean bool = file.delete();
        System.out.println("File Deleted: " + bool);

        clearFields();

        getMajorData();
        getGradeData();
    }

    private void deleteButtonClicked() {
        ObservableList<Student> studentSelected, allStudents;
        allStudents = table.getItems();
        studentSelected = table.getSelectionModel().getSelectedItems();

        studentSelected.forEach(allStudents::remove);
    }

    public void handleNextButtonAction(ActionEvent actionEvent) {
        String dirName = "./Student_Roster/";
        File dir = new File(dirName);
        File[] dir_contents = dir.listFiles();
        Arrays.sort(dir_contents);
        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().equals(textID.getText() + "_" + textLastName.getText() + "_" + textFirstName.getText() + ".txt")) {
                nextFile = new File(dir_contents[++i].getName());
                System.out.println("Opening next student file: " + nextFile);

                clearFields();

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
                image = new Image(lines.get(9), 100, 100, true, true);
                imageView.setImage(image);
                return;
            }
        }
    }

    public void handlePrevButtonAction(ActionEvent actionEvent) {
        String dirName = "./Student_Roster/";
        File dir = new File(dirName);
        File[] dir_contents = dir.listFiles();
        Arrays.sort(dir_contents);
        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().equals(textID.getText() + "_" + textLastName.getText() + "_" + textFirstName.getText() + ".txt")) { ;
                prevFile = new File(dir_contents[--i].getName());
                System.out.println("Opening previous student file: " + prevFile);

                clearFields();

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
                image = new Image(lines.get(9), 100, 100, true, true);
                imageView.setImage(image);
                return;
            }
        }
    }

    public void handleStatsMouseClickAction(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            pieMajor = FXCollections.observableArrayList();
            getMajorData();
            System.out.println("CLICKED");
        }
    }
}
