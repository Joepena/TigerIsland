/**
 * Created by WIIZZARD on 3/24/2017.
 */
public class TempHexHelpers {


    public static boolean isSameTeam(Hex hex1, Hex hex2){

        Hex.Team team1 = hex1.getTeam();
        Hex.Team team2 = hex2.getTeam();

        return (team1 == team2);
    }

    public static boolean hasTigerTotoro(Hex hex){
        return hex.getOccupiedBy() == Hex.gamePieces.Totoro || hex.getOccupiedBy() == Hex.gamePieces.Tiger;

    }

    public static boolean isEmpty(Hex hex) {
        return hex.getOccupiedBy() == Hex.gamePieces.empty;
    }
}
