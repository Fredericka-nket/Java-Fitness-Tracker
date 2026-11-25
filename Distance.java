// This Distance class stores a distance in kilometres and converts it into steps
// using the constant rate of 1312 steps per kilometre, meaning it multiplies every kilometre by 1312.
//  It performs rounding
// and breaks the total into thousands, hundreds, and tens.

public class Distance {

    private double kilometres;

    /**
     * Stores the distance in km as entered by the user.
     */
    public Distance(double kilometres) {
        this.kilometres = kilometres;
    }

    // Converts the stored distance into total steps, rounds to the nearest whole number,
    // and decomposes the result into thousands, hundreds, and tens. The ones place is
   //  ignored because the assignment specifies only these three units.

    public StepStats convert() {
        final int STEPS_PER_KM = 1312;

        long totalSteps = Math.round(kilometres * STEPS_PER_KM);

        int thousands = (int) (totalSteps / 1000);
        int hundreds = (int) ((totalSteps % 1000) / 100);
        int tens = (int) ((totalSteps % 100) / 10);

        return new StepStats(thousands, hundreds, tens);
    }
}
