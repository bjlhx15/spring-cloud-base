package com.github.bjlhx15.eshelper;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/12/3 上午10:55
 */
public abstract class AbstractEsBaseUtils implements IEsBaseUtils {
    private Client client;

    public Client getClient() {
        return client;
    }

    //region 客户端

    /**
     * 外面传入构造方法
     *
     * @param client
     */
    public void init(Client client){
        this.client=client;
    }

    /**
     * 客户端
     */
    public void init(Settings settings, TransportAddress... transportAddresses){
        client = new PreBuiltTransportClient(settings)//Settings.EMPTY
                .addTransportAddresses(transportAddresses);
    }
    //endregion
}

