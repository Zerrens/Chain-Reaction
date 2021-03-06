package chainreaction.api.recipe;

import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class HeatingFluid {

    private static List<HeatingFluid> heatingFluid = new ArrayList<HeatingFluid>();

    public final Fluid input;
    public final Fluid output;
    public final int heat;

    private HeatingFluid(Fluid input, Fluid output, int heat){
        this.input = input;
        this.output = output;
        this.heat = heat;
    }

    private Fluid getInput(){
        return input;
    }

    @Nullable
    public static Fluid getOutput(Fluid input){
        for(HeatingFluid fluid : heatingFluid) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static int getHeat(Fluid input) {
        for(HeatingFluid fluid : heatingFluid) {
            if (input == fluid.getInput()) {
                return fluid.heat;
            }
        }
        return 0;
    }

    public static boolean validHeatingFluid(Fluid input) {
        return getOutput(input) != null;
    }

    /**
     * Adds a fluid to the heating registry and its output, as well as how many TUs each bucket will generate (IC2 Hot->Cold coolant is 620).
     * @param input Input fluid
     * @param output Output fluid
     * @param heat TUs per 1000mb input fluid
     */
    public static void addHeatingFluid(Fluid input, Fluid output, int heat) {
        heatingFluid.add(new HeatingFluid(input, output, heat));
    }
}
