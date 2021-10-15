public class Settings
{
    /*
     * This is the Settings Menu
     * 
     * Tweak these to your likings
    */
    final boolean[] settings={true, true};
    final boolean CLS_SCREEN_ON_OPENING = true;
    final boolean AUTOSOLVE = true;
    public Settings()
    {
    }
    public boolean[] getSettings(){
        return settings;
    }
}