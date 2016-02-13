package com.explodingbacon.bcnlib.javascript;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.framework.RobotCore;
import javax.script.*;

/**
 * A class that makes it easier to run and use Javascript code.
 *
 * @author Ryan Shavell
 * @version 2016.2.13
 */

public class Javascript {

    private static boolean init = false;
    private static ScriptEngineManager engineManager = null;
    private static ScriptEngine engine = null;

    private static Bindings scope = null;
    private static ScriptContext context = null;

    /**
     * Initializes the Javascript engine.
     */
    public static void init() {
        init = true;
        engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("bcnlib");

        resetContext();

        importToEngine(RobotCore.class); //TODO: Delete and instead add Robot.class in the 2016 project?
    }

    /**
     * Checks if the Javascript engine is initialized.
     * @return If the Javascript engine is initialized.
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * Takes a class and imports it into the Javascript engine as a variable.
     * @param c The class to import.
     */
    public static void importToEngine(Class c) {
        runGlobal(String.format("var %s = Java.type('%s');", c.getSimpleName(), c.getPackage()));
    }

    /**
     * Runs Javascript code in the current context.
     * @param js The Javascript code to run.
     */
    public static void run(String js) {
        try {
            engine.eval(js, context);
        } catch (Exception e) {
            Log.e("Javascript.run() error!");
            //Javascript.resetContext(); TODO: If running code with an error in it ruins the context, reset the context
        }
    }

    /**
     * Runs Javascript code in the entire Javascript engine. Only use for function/global variable declarations.
     * @param js The Javascript code to run.
     */
    public static void runGlobal(String js) {
        try {
            engine.eval(js);
        } catch (Exception e) {
            Log.e("Javascript.runGlobal() error!");
            //TODO: If running code with an error in it kills the whole engine, what do?
        }
    }

    /**
     * Resets the Context of the Javascript engine, reverting any changes that were made to the specific Context being used.
     */
    public static void resetContext() {
        context = new SimpleScriptContext();
        context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        scope = context.getBindings(ScriptContext.ENGINE_SCOPE);
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
