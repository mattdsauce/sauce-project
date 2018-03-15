package com.saucelabs.watcher;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.common.Utils;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import org.json.simple.JSONObject;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattdunn on 03/11/2017.
 */
public class MattSauceTestWatcher extends SauceOnDemandTestWatcher {

    /**
     * The underlying {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} instance which contains the Selenium session id.  This is typically
     * the unit test being executed.
     */
    private final SauceOnDemandSessionIdProvider sessionIdProvider;

    /**
     * The instance of the Sauce OnDemand Java REST API client.
     */
    private final SauceREST sauceREST;

    public MattSauceTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider) {
        this(sessionIdProvider, new SauceOnDemandAuthentication());
    }

    public MattSauceTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, boolean verboseMode) {
        this(sessionIdProvider, new SauceOnDemandAuthentication(), verboseMode);
    }

    public MattSauceTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, SauceOnDemandAuthentication authentication) {
        this(sessionIdProvider,
                authentication.getUsername(),
                authentication.getAccessKey(), true);
    }

    public MattSauceTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, SauceOnDemandAuthentication authentication, boolean verboseMode) {
        this(sessionIdProvider,
                authentication.getUsername(),
                authentication.getAccessKey(),
                verboseMode);
    }

    public MattSauceTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, String username, String accessKey, boolean verboseMode) {
        super(sessionIdProvider, username, accessKey, verboseMode);
        this.sessionIdProvider = sessionIdProvider;
        sauceREST = new SauceREST(username, accessKey);
    }

    /**
     * Invoked if the unit test either throws an error or fails.
     *
     * Invokes the Sauce REST API to mark the Sauce Job as 'failed'.
     *
     * @param e
     * @param description
     */
    protected void failed(Throwable e, Description description) {

        super.failed(e, description);

        String failReason = e.getClass().getName() + ": " + e.getMessage().split("\n")[0]; // class name of throwable and first line of exception message

        System.out.println("failReason: " + failReason);

        JSONObject customData = new JSONObject();
        customData.put("failReason", failReason);

        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("custom-data", customData);
        sauceREST.updateJobInfo(sessionIdProvider.getSessionId(), updates);

    }
}
