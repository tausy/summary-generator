package map;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    //private Scene scene;
     Browser browser;

    @Override
    public void start(Stage stage) throws Exception {
        /*stage.setTitle("Web View");
        browser = new Browser(getLocation());
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/resources/map.fxml"));
        AnchorPane anchorPane=(AnchorPane)loader.load();
        VBox vBox=(VBox) anchorPane.lookup("#mapBox");

      //  vBox.setPadding(new Insets(3));
        vBox.getChildren().addAll(browser);

        scene = new Scene(anchorPane, 750, 600, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }

 /*   public static String getLocation()
    {
        //initial loading
        LocationVO l1=new LocationVO();
        l1.setLat(-31.563910); l1.setLng(147.154312);
        LocationVO l2=new LocationVO();
        l2.setLat(-53.718234); l2.setLng(150.363181);
        ArrayList locationList=new ArrayList();
        locationList.add(l1);locationList.add(l2);
        Gson gson =new Gson();

        return gson.toJson(locationList);
    }*/



}