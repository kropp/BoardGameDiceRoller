package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class VersionManager {
    private static VersionManager ourInstance;

    private VersionManager()
    {}

    public static VersionManager getInstance()
    {
        if (ourInstance == null)
            ourInstance = new VersionManager();
        return ourInstance;
    }

    public Version getVersion()
    {
        return Version.LITE;
    }
}
