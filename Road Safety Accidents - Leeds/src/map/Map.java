package map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Map {

	public static VBox getMapScreen() {
		TextField textField = new TextField();

		ObservableList<String> yearOptions = FXCollections.observableArrayList("Show all years", "2017", "2018");
		final ComboBox<String> yearBox = new ComboBox<String>(yearOptions);
		yearBox.getSelectionModel().selectFirst();

		ObservableList<String> ageOptions = FXCollections.observableArrayList("Age of Causality", "35", "25");
		final ComboBox<String> ageBox = new ComboBox<String>(ageOptions);
		ageBox.getSelectionModel().selectFirst();

		ObservableList<String> genderOptions = FXCollections.observableArrayList("Sex of Causality", "M", "F");
		final ComboBox<String> genderBox = new ComboBox<String>(genderOptions);
		genderBox.getSelectionModel().selectFirst();

		ObservableList<String> speedOptions = FXCollections.observableArrayList("Speed Limit", "20", "40");
		final ComboBox<String> speedBox = new ComboBox<String>(speedOptions);
		speedBox.getSelectionModel().selectFirst();

		ObservableList<String> weatherOptions = FXCollections.observableArrayList("Weather Conditions", "rainy", "sun");
		final ComboBox<String> weatherBox = new ComboBox<String>(weatherOptions);
		weatherBox.getSelectionModel().selectFirst();

		HBox row2 = new HBox();
		row2.getChildren().addAll(genderBox, speedBox, weatherBox);
		row2.setSpacing(4);

		CheckBox pedestrian = new CheckBox();
		pedestrian.textProperty().setValue("Pedestrian");
		pedestrian.setId("pedestrian");
		CheckBox bicycle = new CheckBox();
		bicycle.textProperty().setValue("bicycle");
		CheckBox motorcycle = new CheckBox();
		motorcycle.textProperty().setValue("Motorcycle");
		CheckBox car = new CheckBox();
		car.textProperty().setValue("Car");
		CheckBox van = new CheckBox();
		van.textProperty().setValue("Van");
		CheckBox busCoach = new CheckBox();
		busCoach.textProperty().setValue("Bus Coach");
		CheckBox lorry = new CheckBox();
		lorry.textProperty().setValue("Lorry");
		CheckBox other = new CheckBox();
		other.textProperty().setValue("Other");

		CheckBox claimsMade = new CheckBox();
		claimsMade.textProperty().setValue("Claims made with Accident Claims");

		HBox row1 = new HBox();
		row1.setSpacing(5);
		row1.getChildren().addAll(textField, yearBox, genderBox, ageBox);

		HBox row3 = new HBox();
		row3.getChildren().addAll(other, claimsMade, busCoach, lorry);
		row3.setSpacing(4);
		row2.getChildren().addAll(pedestrian, bicycle, motorcycle, car, van);

		VBox vBox = new VBox();
		vBox.setSpacing(2);
		vBox.getChildren().addAll(row1, row2, row3);
		// return vBox;

		VBox root = new VBox();

		root.setPadding(new Insets(5));
		root.setSpacing(10);

		root.getChildren().addAll(row1, row2, row3);

		return root;
	}
}
