/*
 * Copyright © 2016 Cisco Systems, Inc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.cisco.ctao.sparkapi;

import com.ciscospark.Spark;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.sparkbot.rev150105.spark.bot.master.config.parms.SparkBotMasterSessionDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Holds authentication data (access token) to access the Spark service.
 * @author jmedved
 *
 */
public final class SparkClient {
    private static final Logger LOG = LoggerFactory.getLogger(SparkClient.class);
    private static final String BEARER_TOKEN = "Bearer ";
    private static Spark spark = Spark.builder().accessToken(BEARER_TOKEN).build();

    private SparkClient() {
        LOG.info("SparkClient created");
    }

    /** Handles setting of configuration parameters (on data change).
     * @param before: data before the change (old data)
     * @param after: data after the change (new data)
     */
    public static void handleConfigParmsChange(final SparkBotMasterSessionDesc before,
            final SparkBotMasterSessionDesc after) {
        LOG.info("SparkClient handleConfigParmsChange: before {}, after {}", before, after);

        if (after != null) {
            spark = Spark
                    .builder()
                    .accessToken(BEARER_TOKEN + after.getAccessToken())
                    .build();
            LOG.info("SparkClient: accessToken set to '{}'", after.getAccessToken());
        }
    }

    /** Handles the deletion for the access token from the MD-SAL data store.
     *
     */
    public static void handleConfigParmsDelete() {
        spark = Spark
                .builder()
                .accessToken(BEARER_TOKEN)
                .build();
        LOG.info("SparkClient: accessToken deleted");
    }

    /** Gets the spark client.
     * @return: the Spark client
     */
    public static Spark getSpark() {
        return spark;
    }
}
