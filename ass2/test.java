package comp1110.ass2;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class test extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);

        Text hi = new Text("Hello World!");
        hi.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        hi.setFill(Color.RED);
        hi.setOpacity(1.0);
        stage.setOpacity(0.25); // 修改透明度为0.25

        root.getChildren().add(hi);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
