//package web;
//
//import domain.Task;
//import io.javalin.Javalin;
//
//public class TaskServer {
//
//    private final Javalin app;
//
//    public TaskServer() {
//        app = Javalin.create(config -> {
//            config.http.defaultContentType = "application/json";
//        });
//
//        app.get("/quotes", context -> QuoteApiHandler.getAll(context));
//
//        setUpEndpoints(app);
//    }
//
//    private void setUpEndpoints(Javalin server) {
//        server.jetty
//    }
//}
