package org.jboss.ejbclientdemo.common;

import java.util.Arrays;

import org.jboss.ejb.client.DeploymentNodeSelector;
import org.jboss.logging.Logger;

/**
 * Custom deployment node selector.
 *
 * TODO: difference between ClusterNodeSelector and DeploymentNodeSelector?
 *
 * @author Tomas Hofman (thofman@redhat.com)
 */
public class RoundRobinDeploymentNodeSelector implements DeploymentNodeSelector {

    private Logger logger = Logger.getLogger(getClass());
    private int nextIndex = 0;

    @Override
    public String selectNode(String[] eligibleNodes, String appName, String moduleName, String distinctName) {
        if (nextIndex >= eligibleNodes.length) {
            nextIndex = 0;
        }
        logger.infof("Eligible nodes: %s; returning index %d", Arrays.toString(eligibleNodes), nextIndex);
        return eligibleNodes[nextIndex++];
    }
}
