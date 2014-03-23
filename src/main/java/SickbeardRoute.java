package infernozeus.neulion.spark;

import spark.*;

public class SickbeardRoute extends Route {

    private static final String PATH = "/sickbeard";
    private static final String HOST = "localhost";
    private static final String PORT = "8081";

    public SickbeardRoute() {
        super(PATH);
    }

    @Override
    public Object handle(Request request, Response response) {
        response.redirect("http://" + HOST + ":" + PORT + "/");
        return null;
    }
}
