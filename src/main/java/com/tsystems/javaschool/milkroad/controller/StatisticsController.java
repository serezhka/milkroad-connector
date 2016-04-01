package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.ejb.StatisticsEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by Sergey on 30.03.2016.
 */
@Named("statistics")
@SessionScoped
public class StatisticsController implements Serializable {
    @EJB
    private StatisticsEJB statisticsEJB;

    @PostConstruct
    public void init() {
    }

    public void generateStatisticsPDF() {
        ByteArrayOutputStream docStream = null;
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            docStream = statisticsEJB.generatePDFDocumentBytes();
            response.setHeader("Cache-Control", "max-age=30");
            response.setContentType("application/pdf");
            response.setContentLength(docStream.size());
            final ServletOutputStream servletOutputStream = response.getOutputStream();
            docStream.writeTo(servletOutputStream);
            servletOutputStream.flush();
        } catch (final Exception e) {
            // TODO Implement me
        } finally {
            if (docStream != null) {
                docStream.reset();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }
}
