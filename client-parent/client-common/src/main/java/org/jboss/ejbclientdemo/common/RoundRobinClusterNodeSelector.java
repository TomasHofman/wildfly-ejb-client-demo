package org.jboss.ejbclientdemo.common;

import java.util.Arrays;

import org.jboss.ejb.client.ClusterNodeSelector;
import org.jboss.logging.Logger;

/**
 * Custom node selector.
 *
 * TODO: difference between ClusterNodeSelector and DeploymentNodeSelector?
 *
 * @author Tomas Hofman (thofman@redhat.com)
 */
public class RoundRobinClusterNodeSelector implements ClusterNodeSelector {

    private Logger logger = Logger.getLogger(getClass());
    private byte lastIndex = 0;

    @Override
    public String selectNode(String clusterName, String[] connectedNodes, String[] totalAvailableNodes) {
        int currentIdx = lastIndex++ % totalAvailableNodes.length;
        logger.infof("Available nodes: %s; returning index %d", Arrays.toString(totalAvailableNodes), lastIndex);
        return totalAvailableNodes[currentIdx];
    }
}
