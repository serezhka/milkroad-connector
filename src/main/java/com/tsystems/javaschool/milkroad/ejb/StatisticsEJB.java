package com.tsystems.javaschool.milkroad.ejb;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

/**
 * Created by Sergey on 30.03.2016.
 */
@Stateless
public class StatisticsEJB {

    public Object getStatistics() {

        try {
            final ResteasyClient client = new ResteasyClientBuilder().build();
            final ResteasyWebTarget target = client.target("http://localhost:8081/rest/topCustomers");
            final Response response = target.request().get();

            System.out.println("Debug");


            response.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
