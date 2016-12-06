package api;

import handlers.GraphWSHandler;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import java.util.HashMap;

import static spark.Spark.*;

/**
 * Created by goutham on 06/12/16.
 */
public class MainRoutes {
   public static void main(String[] args) {
       webSocket("/socket", GraphWSHandler.class);
       get("/hello", (req, res) -> new ModelAndView(new HashMap(), "main.html"), new HandlebarsTemplateEngine());
       get("/ws", (req, res) -> new ModelAndView(new HashMap(), "WSTest.html"), new HandlebarsTemplateEngine());
   }
}
