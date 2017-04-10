/**
 * Created by Nick on 4/8/2017.
 */
public class BuildDecision {
    public static enum buildDecisions {
        Found, Expand, Totoro, Tiger, Unable;
    }
    Tuple location;
    BuildDecision buildDecision;

    public BuildDecision(Tuple location, BuildDecision.buildDecisions buildDecisions) {
        this.location = location;
        this.buildDecision = buildDecision;
    }
}
