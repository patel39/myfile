package com.controller.leave;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public abstract class AbstractPdfView extends AbstractView {

	final static String USER_PASSWORD = "user";
	final static String OWNER_PASSWORD = "owner";
	
	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		super.prepareResponse(request, response);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ByteArrayOutputStream out = createTemporaryOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, out);
        writer.setEncryption(USER_PASSWORD.getBytes(),
				OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
        writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
        buildPdfMetadata(model, document, request);

        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();
        writeToResponse(response, out);
		
	}

	 protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {}

	 protected abstract void buildPdfDocument(Map<String, Object> model,
	                                             Document document,
	                                             PdfWriter writer,
	                                             HttpServletRequest request,
	                                             HttpServletResponse response) throws Exception;

	@Override
	protected void setResponseContentType(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		super.setResponseContentType(request, response);
	}

	@Override
	protected void writeToResponse(HttpServletResponse response, ByteArrayOutputStream baos) throws IOException {
		// TODO Auto-generated method stub
		super.writeToResponse(response, baos);
	}

}
