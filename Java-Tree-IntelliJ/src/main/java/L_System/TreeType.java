package L_System;

import java.util.HashMap;
/**
 * TreeType, that holds the axiom and the rules for stringRecursion.
 */
public enum TreeType {

    FRACTAL_TREE("X", "F[+X][-X]FX", "", "FF",        25),
    SPIRAL_PLANT("X", "F[+X]F[-X]+X", "","FF",        20),
    PALM_TREE("X", "F[+X][-X]F[-X]+X","","FF",        22.5),
    SNOWFLAKE_FRACTAL("F++F++F", "", "", "F-F++F-F",  60),
    WINDY_PLANT("X", "F[+X]F[-X]+X", "", "FF",        20),
    DRAGON_CURVE("FX", "X+YF+", "-FX-Y", "F",         90 ),
    EXPLODING_TREE_BUSH("F", "", "", "F[+F]F[-F]F[+F[-F]]", 22.5),
    BRAIN_CORAL("F", "", "", "F[+F[-F[+F]]][-F[+F[-F]]]F", 20),
    MACHINERY_VINE("F", "", "", "F+F-F[+F][-F]F[+F-F]", 30),
    DOUBLE_SPIRAL_SPLITTER("F", "", "", "F[+F[+F]]F[-F[-F]]F", 17.5),
    THE_MELTER("F", "", "", "[-F+F]+F[+F-F]-F", 15),
    SUNFLOWER("F","","","F+F[-F+F][+F-F]F-F", 137.5) ,
    BROKEN_MIRROR("F", "", "", "F[+F][-F]+F[-F+F[+F[-F]]]", 33.3),
    ANGULAR_FRACTAL_MAZE("F+F+F+F", "", "", "F+F-F-FF+F+F-F",90),
    LIGHTNING("F", "", "", "FF[+F][-F][-F[+F]]F", 25),
    TRIANGLE_COLLAPSE("F+F+F", "", "", "F-F+F-F[+F-F-F][-F+F+F]", 60),
    RECURSIVE_CRYSTAL("F", "", "","F+F-F[-F+F]+F-F", 45),
    NERVOUS_SYSTEM("X", "F[+Y]X[-Y]+X", "F[-X]+Y[+F]-Y", "FF", 18.7),
    BLOOM("X", "F[+Y][-Y]FX", "F[+X][-X]FY", "FF", 30),
    COIL("X", "F[+X][-X]FX[+X[-X]]", "", "FF", 22.5),
    SPHERE_BLOOM("X", "F[+X][-X][+FX][-FX]X", "FF", "", 137.5),
    CIRCLE("F","", "","F+F", 22.5),
    ARCANE_FLAMES("X", "F[+X][-X]FX", "","F[-F]+F", 33 ),
    MYSTIC_VINES("X", "F[+Y][-Y]FX", "FY[-X][+X]", "FF",28),
    ANCIENT_MYSTIC_TREE("X","F[+X][-X]FX[-Y][+Y]","F[-X][+X]FY","FF",22.5),
    WEB_TREE("X","F[-X][+X]YF[+XF][-Y]","F[+Y][-Y]XF[-X][+Y]","FF",17),
    TWILIGHT_SERPENT("X","F[+YFX][-YFX]FX","F[-XFY][+XFY]FY","",24),
    TWILIGHT_SERPENT_V2("X","F[+YFX][-YFX][+FX][-FX]FYX","F[-XFY][+XFY][-FY][+FY]XYF","",22),
    TWILIGHT_SERPENT_V3("X","F[[+YFX][-YFX]][[+FX][-FX]]FYX","F[[-XFY][+XFY]][[-FY][+FY]]XYF","",18);

    /**
     * Axiom, starting string of the seed string.
     */
    private final String axiom;
    /**
     * HashMap of the rules used by selected type.
     */
    private final HashMap<Character, String> rules;
    /**
     * The Degree by which the current angle in the branch is modified.
     */
    private final double angleModifier;
    /**
     * Constructor,
     * Sets the variables up.
     * @param  axiom Sets the axiom.
     * @param ruleX Puts ruleX in the rules HashMap.
     * @param ruleY Puts ruleY in the rules HashMap.
     * @param ruleF Puts ruleF in the rules HashMap.
     * @param angleModifier Sets angleModifier.
     */
    TreeType(String axiom, String ruleX, String ruleY, String ruleF, double angleModifier){

        this.axiom = axiom;
        this.rules = new HashMap<>();
        this.rules.put('X',ruleX);
        this.rules.put('Y',ruleY);
        this.rules.put('F',ruleF);
        this.angleModifier = angleModifier;
    }
    /**
     * Basic getAxiom.
     * @return Returns axiom string.
     */
    public String getAxiom(){
        return axiom;
    }
    /**
     * Basic getRules.
     * @return Returns rules.
     */
    public HashMap<Character, String> getRules(){
        return rules;
    }
    /**
     * Basic getAngleModifier.
     * @return Returns angleModifier.
     */
    public double getAngleModifier() {
        return angleModifier;
    }
}
