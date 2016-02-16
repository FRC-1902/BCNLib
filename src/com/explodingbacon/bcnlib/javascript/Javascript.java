package com.explodingbacon.bcnlib.javascript;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.framework.RobotCore;
import com.explodingbacon.bcnlib.utils.Utils;

import javax.script.*;

/**
 * A class that makes it easier to run and use Javascript code.
 *
 * @author Ryan Shavell
 * @version 2016.2.15
 */

public class Javascript {

    private static boolean init = false;
    private static String imports = "";
    private static ScriptEngineManager engineManager = null;
    private static ScriptEngine engine = null;

    /**
     * Initializes the Javascript engine.
     */
    public static void init() {
        init = true;
        engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("nashorn");
    }

    /**
     * Checks if the Javascript engine is initialized.
     * @return If the Javascript engine is initialized.
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * Takes a class and imports it as a variable.
     * @param c The class to import.
     */
    public static void importClass(Class c) {
        String s = "var " + c.getSimpleName() + " = Java.type('" + c.getPackage().getName() + "." + c.getSimpleName() + "');";
        imports = imports + s + Utils.newLine();
    }

    /**
     * Runs Javascript code in the current context.
     * @param js The Javascript code to run.
     */
    public static void run(String js) { //TODO: Only add the needed imports?
        try {
            engine.eval(imports + js);
        } catch (Exception e) {
            Log.e("Javascript.run() error!");
        }
    }

    /**
     * Clears the imports
     */
    public static void clearImports() {
        imports = "";
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
