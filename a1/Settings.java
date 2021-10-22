public class Settings
{
    /*
     * This is the Settings Menu
     * 
     * Tweak these to your likings
    */
    final boolean CLS_SCREEN_ON_OPENING = true;
    final boolean AUTOSOLVE = true;
    public Settings()
    {
    }
    public boolean[] getSettings(){
        boolean[] settings={CLS_SCREEN_ON_OPENING, AUTOSOLVE};
        return settings;
    }
}