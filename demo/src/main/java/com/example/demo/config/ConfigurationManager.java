public class ConfigurationManager {
    private static ConfigurationManager instance;

    private ConfigurationManager() {
        
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getConfig(String key) {
        return "SampleConfigValue"; 
    }
}
