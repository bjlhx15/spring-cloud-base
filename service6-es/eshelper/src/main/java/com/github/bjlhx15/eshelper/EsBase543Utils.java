package com.github.bjlhx15.eshelper;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/12/3 上午10:55
 */
public class EsBase543Utils extends AbstractEsBaseUtils {
    public EsBase543Utils(Client client) {
        this.init(client);
    }
    public EsBase543Utils(Settings settings, TransportAddress... transportAddresses) {
        this.init(settings,transportAddresses);
    }
}

