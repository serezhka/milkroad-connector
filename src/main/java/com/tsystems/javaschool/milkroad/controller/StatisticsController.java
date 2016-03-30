package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.ejb.StatisticsEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Sergey on 30.03.2016.
 */
@Named("statistics")
@SessionScoped
public class StatisticsController implements Serializable {
    @EJB
    private StatisticsEJB statisticsEJB;

    private Object object;

    @PostConstruct
    public void init() {
        object = statisticsEJB.getStatistics();
    }

    public void generateStatisticsPDF() {
        System.out.println("Debug");
    }
}
