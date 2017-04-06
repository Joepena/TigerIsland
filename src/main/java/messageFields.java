import java.util.Scanner;

/**
 * Created by T K Vicious on 4/5/2017.
 */
public class messageFields {
    private String pid;
    private String gid;
    private double time;
    private int moveNumber;
    private Tile tile;
    private String tileString;
    private Orientation.Orientations orientation;
    private Tuple placementLocation;
    private Tuple moveLocation;
    boolean gameOver;
    boolean lost;
    boolean won;
    boolean forfeited;
    boolean totoro;
    boolean tiger;


    public messageFields(String message){
        this.pid = "";
        this.gid = "";
        this.time = 0;
        this.moveNumber = 0;
        this.tile = new Tile(0, Terrain.terrainType.Grassland, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        this.tileString = "";
        this.orientation = Orientation.Orientations.downLeft;
        this.placementLocation = new Tuple(0,0,0);
        this.moveLocation = new Tuple(0,0,0);
        this.gameOver = false;
        this.lost = false;
        this.won = false;
        this.forfeited = false;
        this.totoro = false;
        this.tiger = false;
        parseString(message);

    }

    private void parseString (String message) {
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        String messageStart = scanner.next();
        System.out.println(messageStart);
        switch(messageStart){
            case "GAME":
                this.gid = "" + scanner.nextInt();
                move(scanner);

        }
        scanner.close();

    }

    private void move(Scanner scanner){
        this.gid = scanner.next();
        switch(scanner.next()) {
            case "MOVE":
                this.moveNumber = scanner.nextInt();
                scanner.next();
                this.pid = scanner.next();
                switch(scanner.next()){
                    case "FORFEITED:":
                        this.forfeited = true;
                        this.gameOver = true;
                        break;
                    case "LOST:":
                        this.lost = true;
                        this.gameOver = true;
                        break;
                    default:
                        scanner.next();
                        this.tileString = scanner.next();
                        scanner.next();
                        this.placementLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                        switch(scanner.next()){
                            case "FOUNDED":
                                scanner.next();
                                scanner.next();
                                this.moveLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                                break;
                            case "EXPANDED":
                                scanner.next();
                                scanner.next();
                                this.moveLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                                break;
                            case "BUILT":
                                if(scanner.next().equals("TIGER")){
                                    this.tiger = true;
                                }
                                else
                                    this.totoro = true;
                                this.moveLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                                break;
                        }

                }
        }

    }


    public String toString(){
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("pid:  " + pid + "\n");
        sbuf.append("gid:  " + gid + "\n");
        sbuf.append("time:  " + time + "\n");
        sbuf.append("moveNumber:  " + moveNumber + "\n");
        sbuf.append("tile:  " + tile.toString() + "\n");
        sbuf.append("Orientation:  " + orientation + "\n");
        sbuf.append("placementLocation:  " + placementLocation.toString() + "\n");

        return sbuf.toString();
    }
}
