package com.tsystems.javaschool.milkroad.ejb;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsystems.javaschool.milkroad.dto.IncomeDTO;
import com.tsystems.javaschool.milkroad.dto.TopCustomerDTO;
import com.tsystems.javaschool.milkroad.dto.TopProductDTO;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Sergey on 30.03.2016.
 */
@Stateless
public class StatisticsEJB {
    private final static String TOP_CUSTOMERS_URL = "http://localhost:8081/rest/topCustomers";
    private final static String TOP_PRODUCTS_URL = "http://localhost:8081/rest/topProducts";
    private final static String INCOME_URL = "http://localhost:8081/rest/income";

    public List<TopCustomerDTO> getTopCustomers() {
        return getResponseEntity(TOP_CUSTOMERS_URL).readEntity(new GenericType<List<TopCustomerDTO>>() {
        });
    }

    public List<TopProductDTO> getTopProducts() {
        return getResponseEntity(TOP_PRODUCTS_URL).readEntity(new GenericType<List<TopProductDTO>>() {
        });
    }

    public IncomeDTO getIncome() {
        return getResponseEntity(INCOME_URL).readEntity(new GenericType<IncomeDTO>() {
        });
    }

    public ByteArrayOutputStream generatePDFDocumentBytes() throws DocumentException {
        final List<TopCustomerDTO> topCustomers = getTopCustomers();
        final List<TopProductDTO> topProducts = getTopProducts();
        final IncomeDTO income = getIncome();

        final Document doc = new Document();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter docWriter = null;
        try {
            docWriter = PdfWriter.getInstance(doc, stream);
            doc.addAuthor("Milkroad");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("Milkroad");
            doc.addTitle("Milkroad statistics report");
            doc.setPageSize(PageSize.LETTER);

            doc.open();

            /* TITTLE */
            final Paragraph tittle = (new Paragraph("Milkroad statistics report"));
            tittle.setAlignment(Element.ALIGN_CENTER);
            doc.add(tittle);

            /* TOP PRODUCTS */
            doc.add(new Paragraph("Top products"));
            doc.add(new Paragraph("\n"));
            final PdfPTable topProductsTable = new PdfPTable(6);
            topProductsTable.addCell(new PdfPCell(new Paragraph("Sales count")));
            topProductsTable.addCell(new PdfPCell(new Paragraph("Name")));
            topProductsTable.addCell(new PdfPCell(new Paragraph("Description")));
            topProductsTable.addCell(new PdfPCell(new Paragraph("Category")));
            topProductsTable.addCell(new PdfPCell(new Paragraph("Price")));
            topProductsTable.addCell(new PdfPCell(new Paragraph("Remain count")));
            for (final TopProductDTO topProduct : topProducts) {
                topProductsTable.addCell(new PdfPCell(new Paragraph(topProduct.getSalesCount().toString())));
                topProductsTable.addCell(new PdfPCell(new Paragraph(topProduct.getName())));
                topProductsTable.addCell(new PdfPCell(new Paragraph(topProduct.getDescription())));
                topProductsTable.addCell(new PdfPCell(new Paragraph(topProduct.getCategory())));
                topProductsTable.addCell(new PdfPCell(new Paragraph(topProduct.getPrice().toString())));
                topProductsTable.addCell(new PdfPCell(new Paragraph(topProduct.getRemainCount().toString())));
            }
            doc.add(topProductsTable);
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));

            /* TOP CUSTOMERS */
            doc.add(new Paragraph("Top customers"));
            doc.add(new Paragraph("\n"));
            final PdfPTable topCustomersTable = new PdfPTable(5);
            topCustomersTable.addCell(new PdfPCell(new Paragraph("Cash")));
            topCustomersTable.addCell(new PdfPCell(new Paragraph("First name")));
            topCustomersTable.addCell(new PdfPCell(new Paragraph("Last name")));
            topCustomersTable.addCell(new PdfPCell(new Paragraph("Birth date")));
            topCustomersTable.addCell(new PdfPCell(new Paragraph("Email")));
            for (final TopCustomerDTO topCustomer : topCustomers) {
                topCustomersTable.addCell(new PdfPCell(new Paragraph(topCustomer.getCash().toString())));
                topCustomersTable.addCell(new PdfPCell(new Paragraph(topCustomer.getFirstName())));
                topCustomersTable.addCell(new PdfPCell(new Paragraph(topCustomer.getLastName())));
                topCustomersTable.addCell(new PdfPCell(new Paragraph(topCustomer.getBirthday().toString())));
                topCustomersTable.addCell(new PdfPCell(new Paragraph(topCustomer.getEmail())));
            }
            doc.add(topCustomersTable);

            /* INCOME */
            doc.add(new Paragraph("Income"));
            doc.add(new Paragraph("\n"));
            final PdfPTable incomeTable = new PdfPTable(3);
            incomeTable.addCell(new PdfPCell(new Paragraph("Total cash")));
            incomeTable.addCell(new PdfPCell(new Paragraph("This month")));
            incomeTable.addCell(new PdfPCell(new Paragraph("Last 7 days")));
            incomeTable.addCell(new PdfPCell(new Paragraph(income.getTotalCash().toString())));
            incomeTable.addCell(new PdfPCell(new Paragraph(income.getTotalCashThisMonth().toString())));
            incomeTable.addCell(new PdfPCell(new Paragraph(income.getTotalCashLast7Days().toString())));
            doc.add(incomeTable);

        } catch (final DocumentException e) {
            stream.reset();
            throw e;
        } finally {
            //noinspection ConstantConditions
            if (doc != null) {
                doc.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
        if (stream.size() < 1) {
            throw new DocumentException("Document is empty");
        }
        return stream;
    }

    private Response getResponseEntity(final String url) {
        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client.target(url);
        final Response response = target.request().get();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException(String.format("Invalid response status: %d\n%s", response.getStatus(), response));
        }
        return response;
    }
}
