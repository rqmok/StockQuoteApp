
import java.util.ArrayList;

public abstract class Controller {

    public abstract void update(ArrayList<Monitor> monitors);

    public abstract ArrayList<Monitor> getSelectedMonitors();

}
