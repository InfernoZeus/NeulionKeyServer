package infernozeus.neulion.spark;

import spark.*;

public class SparkMain {

    public static void main(String[] args) throws Exception {
        Spark.setPort(80);

        Spark.get(new KeyRequestRoute());
        Spark.get(new SickbeardRoute());
    }
}
