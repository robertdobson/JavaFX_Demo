package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The JavaFXDemo class contains the main method. This class demonstrates a JavaFX application using a BorderPane,
 * buttons, and 3D sphere. The application begins with the sphere bouncing around within the Pane element embedded
 * in the center of the BorderPane. When the user clicks the rightButton, the label on the left changes. When the user
 * clicks the bottomButton, the sphere changes color. Motion logic for the sphere, shading/lighting, and sound have
 * been added to demonstrate additional capabilities.
 * @author Robert Dobson
 */
public class JavaFXDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("JavaFX Demo");
        window.setResizable(false);

        // Create buttons
        Button rightButton = new Button("Change Label");
        rightButton.setMaxHeight(Double.MAX_VALUE);
        rightButton.setPadding(new Insets(0, 10, 0, 10));
        Button bottomButton = new Button("Change Circle");
        bottomButton.setMaxWidth(Double.MAX_VALUE);

        // Create label
        Label label = new Label("I'm a label");
        label.setMaxHeight(Double.MAX_VALUE);
        label.setPadding(new Insets(0, 10, 0, 0));

        // Create BorderPane layout
        BorderPane border = new BorderPane();
        Pane canvas = new Pane();
        canvas.setPadding(new Insets(0, 10, 10, 0));
        Scene scene = new Scene(border, 820, 530);

        // Background image
        Image image = new Image("File:AmigaWB_640x480.jpg");

        // Add background image to canvas
        canvas.getChildren().add(new ImageView(image));

        // Image for texture applied to the sphere
        Image pattern = new Image("File:checker pattern.png");

        // Create color pairs and texture applied to sphere
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        redMaterial.setDiffuseMap(pattern);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKSLATEBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        blueMaterial.setDiffuseMap(pattern);
        final PhongMaterial yellowMaterial = new PhongMaterial();
        yellowMaterial.setDiffuseColor(Color.DARKGOLDENROD);
        yellowMaterial.setSpecularColor(Color.YELLOW);
        yellowMaterial.setDiffuseMap(pattern);
        final PhongMaterial purpleMaterial = new PhongMaterial();
        purpleMaterial.setDiffuseColor(Color.DARKVIOLET);
        purpleMaterial.setSpecularColor(Color.PURPLE);
        purpleMaterial.setDiffuseMap(pattern);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        greenMaterial.setDiffuseMap(pattern);

        // Create sphere
        Sphere ball = new Sphere(70);
        ball.setMaterial(redMaterial);
        ball.relocate(5, 5);

        // Add components to the desired position in the BorderLayout
        border.setPadding(new Insets(10, 10, 10, 10));
        border.setRight(rightButton);
        border.setBottom(bottomButton);
        border.setLeft(label);
        border.setCenter(canvas);

        // Add sphere to canvas
        canvas.getChildren().add(ball);

        // Create AudioClip object for bounce sound effect
        AudioClip clip = new AudioClip("File:bounce.mp3");

        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        window.setOpacity(0.90);

        // Handle action event for rightButton using Lambda expression
        rightButton.setOnAction(event -> {
            if (label.getText() == "I'm a label") {
                label.setText("Gadzooks!");
            } else {
                label.setText("I'm a label");
            }
        });

        // Handle action event for bottomButton using Lambda expression
        bottomButton.setOnAction(event -> {
            if (ball.getMaterial().equals(redMaterial))
                ball.setMaterial(blueMaterial);
            else if (ball.getMaterial().equals(blueMaterial))
                ball.setMaterial(yellowMaterial);
            else if (ball.getMaterial().equals(yellowMaterial))
                ball.setMaterial(purpleMaterial);
            else if (ball.getMaterial().equals(purpleMaterial))
                ball.setMaterial(greenMaterial);
            else if (ball.getMaterial().equals(greenMaterial))
                ball.setMaterial(redMaterial);
        });

        window.setScene(scene);
        window.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            double dx = 7; //Step on x or velocity
            double dy = 3; //Step on y

            @Override
            public void handle(ActionEvent t) {
                //move the ball
                ball.setLayoutX(ball.getLayoutX() + dx);
                ball.setLayoutY(ball.getLayoutY() + dy);

                Bounds bounds = canvas.getBoundsInLocal();

                //If the ball reaches the left or right border make the step negative
                if(ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius()) ||
                        ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius()) ) {

                    dx = -dx;
                    clip.play();

                }

                //If the ball reaches the bottom or top border make the step negative
                if((ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius())) ||
                        (ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius()))) {

                    dy = -dy;
                    clip.play();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
