package Controller;

import Other.DisjointSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

import static Other.Main.ps;

public class Controller {
    @FXML public Button selectImage, search, noiseReduction;
    @FXML public ImageView imageView, triImageView;
    @FXML public TextField numOfCellText;

    ArrayList<String> numOfCells = new ArrayList<>();

    public Image originalImage;

    /**
     * opens the image chosen as well as calling tricolour
     */
    public void openImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(ps);
        if (file != null) {
            originalImage = new Image(file.toURI().toString());
            imageView.setImage(originalImage);
            triImageView.setImage(tricolour(originalImage));
        }
    }

    /**
     * reduces the noise in the image by changing pixels to white
     */
    public void reduceNoise() {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        PixelReader original = originalImage.getPixelReader();
        WritableImage reducedNoiseImage = new WritableImage(width, height);
        PixelWriter reducedNoise = reducedNoiseImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double redColor = original.getColor(x, y).getRed();
                double blueColor = original.getColor(x, y).getBlue();
                double greenColor = original.getColor(x, y).getGreen();

                if ((redColor > 0.9))
                    reducedNoise.setColor(x, y, new Color(1, 1, 1, 1.0));
                else if ((blueColor > 0.9))
                    reducedNoise.setColor(x, y, new Color(1, 1, 1, 1.0));
                else if ((greenColor > 0.9))
                    reducedNoise.setColor(x, y, new Color(1, 1, 1, 1.0));
                else
                    reducedNoise.setColor(x, y, new Color(redColor, greenColor, blueColor, 1.0));

            }
        }
        imageView.setImage(reducedNoiseImage);
        triImageView.setImage(tricolour(reducedNoiseImage));
    }

    /**
     * change the image to tricolour
     * @param originalImage the original image to be changed
     * @return return the tricolour Image
     */
    public Image tricolour(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        PixelReader original = originalImage.getPixelReader();
        WritableImage tricolourImage = new WritableImage(width, height);
        PixelWriter tricolour = tricolourImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double redCol = original.getColor(x, y).getRed();
                double blueCol = original.getColor(x, y).getBlue();

                if (redCol < 0.9)
                    tricolour.setColor(x, y, new Color(redCol, 0, 0, 1.0));
                else if (blueCol < 0.9)
                    tricolour.setColor(x, y, new Color(0, 0, blueCol, 1.0));
                else
                    tricolour.setColor(x, y, new Color(1, 1, 1, 1.0));
            }
        }
        return tricolourImage;
    }

    /**
     *  calls searchThroughPicture
     */
    public void search() {
        searchThroughPicture(triImageView.getImage());
    }

    /**
     * searches through the image to set pixels, union them together and call checkAnyMatch on each pixel
     * @param originalImage the image to search through
     */
    public void searchThroughPicture(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        int size = width * height;
        int[] array = new int[size + 1];
        PixelReader original = originalImage.getPixelReader();
        int sizeSearch = 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (original.getColor(x, y).getRed() <= 0.9) {
                    array[sizeSearch] = 0; //red
                } else if (original.getColor(x, y).getBlue() <= 0.9) {
                    array[sizeSearch] = 1; //blue
                } else {
                    array[sizeSearch] = -1; //white
                }
                ++sizeSearch;
            }
        }
        for (int i = 0; i < size; i++) {
            if ((i + 1 < size) && (array[i] != -1) && (array[i + 1] != -1) && (array[i + 1] == array[i])) {
                DisjointSet.union(array, array[i], array[i + 1]);
            }
            if ((i + width < size) && (i + width > 0) && (array[i] != -1) && (array[i + width] == array[i]) && (array[i] == array[i + width])) {
                DisjointSet.union(array, array[i], array[i + width]);
            }
        }
        for (int i = 0; i < size; i++) {
            if (array[i] == -1)
                 System.out.println("Not added");
            else {
                checkIfAnyMatch(numOfCells, array, i);
            }
        }
        System.out.println(numOfCells.size());
        numOfCellText.setText(String.valueOf(numOfCells.size()));
    }

    /**
     * Dosent work- meant to count through the picture - only returns 0
     * @param arrayList array list to count
     * @param comparedArray the int array that counts all pixels
     * @param i the index of pixel its checking
     */
    private void checkIfAnyMatch(ArrayList<String> arrayList, int[] comparedArray, int i) {
        String disjoint = String.valueOf(DisjointSet.find(comparedArray, i));
        if (arrayList.size() > 0) {
            for (int x = 0; x <= arrayList.size(); x++) {
                if (arrayList.get(i).equals(disjoint))
                    arrayList.add(Integer.toString(i));
            }
        }
    }
}
