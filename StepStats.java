// The StepStats class represents the step quantities in thousands, hundreds, and tens.
// Its function is for storing step values and formatting them into
// simple, clear and readable English description for the user. 

public class StepStats {

    private int thousands;
    private int hundreds;
    private int tens;

    /**
     * Creating a StepStats object with the given units.
     * These values are to be non-negative values.
     */
    public StepStats(int thousands, int hundreds, int tens) {
        this.thousands = thousands;
        this.hundreds = hundreds;
        this.tens = tens;
    }
    /**
     * This returns the total number of steps represented by the thousands,
     * hundreds, and tens. This is also used in UserInterfaceMain class to compare
     * the user's converted steps against their personal step goal.
     */
    public int getTotalSteps() {
        return (thousands * 1000) + (hundreds * 100) + (tens * 10);
    }

    /**
     * This builds a human-readable description of the step count.
     * Zero-valued units are skipped and plural words
     * are handled naturally, for example, "1 thousand" and "9 thousands").
     * This also improves the general logic of the program
     */
    public String prettyPrint() {
    StringBuilder output = new StringBuilder();

    if (thousands > 0) {
        output.append(thousands).append(" thousand");
        if (thousands > 1) output.append("s");
    }

    if (hundreds > 0) {
        if (output.length() > 0) output.append(", ");
        output.append(hundreds).append(" hundred");
        if (hundreds > 1) output.append("s");
    }

    if (tens > 0) {
        if (output.length() > 0) output.append(" and ");
        output.append(tens).append(" ten");
        if (tens > 1) output.append("s");
    }

    if (output.length() == 0) {
        return "0 steps";
    }

    return output.toString();
  }
}
