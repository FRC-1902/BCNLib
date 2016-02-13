package com.explodingbacon.bcnlib.javascript;

import com.explodingbacon.bcnlib.framework.Log;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * A class that makes it easier to run and use Javascript code.
 *
 * @author Ryan Shavell
 * @version 2016.2.13
 */

public class Javascript {

    private static boolean init = false;
    private static ScriptEngineManager engineManager = null
    private static ScriptEngine engine = null;

    /**
     * Initializes the Javascript engine.
     */
    public static void init() {
        init = true;
        engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("bcnlib");
        run("var RobotCore = Java.type('com.explodingbacon.bcnlib.framework.RobotCore');");
    }

    /**
     * Checks if the Javascript engine is initialized.
     * @return If the Javascript engine is initialized.
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * Runs Javascript code. Functions previously defined through run() are available for use.
     * @param js The Javascript code to run.
     */
    public static void run(String js) {
        try {
            engine.eval(js);
        } catch (Exception e) {
            Log.e("Javascript.run(String) error!");
            e.printStackTrace();
        }
    }

    /**
     * Runs Javascript code. Functions previously defined through run() are available for use.
     * @param r A FileReader pointing to a Javascript file.
     */
    public static void run(FileReader r) {
        try {
            engine.eval(r);
        } catch (Exception e) {
            Log.e("Javascript.run(FileReader) error!");
            e.printStackTrace();
        }
    }

    /**
     * Calls a Javascript function.
     * @param func The name of the function.
     * @param args The function's arguments.
     * @return The return value of the function, or null if there isn't one.
     */
    public static Object function(String func, Object...args) {
        Invocable invocable = (Invocable) engine;
        try {
            return invocable.invokeFunction(func, args);
        } catch (Exception e) {
            Log.e("Javascript.function() error!");
            e.printStackTrace();
        }
        return null;
    }
}
