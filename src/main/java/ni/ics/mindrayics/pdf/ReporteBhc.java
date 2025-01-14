package ni.ics.mindrayics.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import ni.ics.mindrayics.modelo.BHCParticipanteDto;
import ni.ics.mindrayics.modelo.BhcResultado;
import ni.ics.mindrayics.modelo.DatosParticipante;
import ni.ics.mindrayics.modelo.Personal;
import ni.ics.mindrayics.utils.DateUtils;
import ni.ics.mindrayics.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.DocPrintJob;
import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.Locale;

public class ReporteBhc {
    private static final Logger logger = Logger.getLogger(ReporteBhc.class);
    private static Font titulo = new Font(Font.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font labelReport = new Font(Font.TIMES_ROMAN, 12);
    private static Font responseReportBold = new Font(Font.TIMES_ROMAN, 12,
            Font.BOLD);

    public static void pdf(BhcResultado bhcResultado, DatosParticipante datosParticipante,
                           BHCParticipanteDto bhcParticipanteDto, Personal personal) {
        Document document = new Document(PageSize.LETTER);
        try {
            File file = new File("/opt/procesos/mindrayIcs/ReporteBhc.pdf");
            if (file.exists()) {
                file.delete();
            }
            file = new File("/opt/procesos/mindrayIcs/ReporteBhc.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            agregarContenido(document, bhcResultado, datosParticipante, bhcParticipanteDto, personal);
            document.close();
            PrintService[] service = PrinterJob.lookupPrintServices();
            DocPrintJob docPrintJob = null;
            for (int i = 0; i < service.length; i++) {
                if (service[i].getName().equals("Lab")) {
                    logger.info("Reporte bhc impreso: " + service[i].getName());
                    docPrintJob = service[i].createPrintJob();
                }
            }
            PrinterJob pjob = PrinterJob.getPrinterJob();
            if (docPrintJob != null) {
                PDDocument pdf = PDDocument.load(file);
                pjob.setPrintService(docPrintJob.getPrintService());
                pjob.setJobName("job");
                pdf.silentPrint(pjob);
                pdf.close();
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private static void agregarContenido(Document document, BhcResultado bhcResultado, DatosParticipante participante,
                                         BHCParticipanteDto participanteA2cares, Personal personal) throws Exception {
        PdfPTable table;
        PdfPCell cell;
        table = new PdfPTable(new float[]{100});
        table.setWidthPercentage(100f);
        table.setSpacingBefore(40);
        cell = new PdfPCell(new Phrase("Laboratorio Clínico.", titulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);
        table = new PdfPTable(new float[]{100});
        table.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase("Centro de Salud Sócrates Flores Vivas.", titulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);
        table = new PdfPTable(new float[]{100});
        table.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase("Resultados Biometría Hemática Completa.", titulo));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(40);
        document.add(table);
        LineSeparator ls1 = new LineSeparator();
        ls1.setLineWidth(0.3f);
        document.add(new Chunk(ls1));
        document.add(Chunk.NEWLINE);
        table = new PdfPTable(new float[]{9, 66, 9, 16});
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10f);
        cell = new PdfPCell(new Phrase("Nombre:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        /*Datos de Participante*/
        String nombreCompleto = "";
        if (participante != null) {
            nombreCompleto = participante.getNombre1().toUpperCase();
            if (participante.getNombre2()!=null) nombreCompleto = nombreCompleto + " "+  participante.getNombre2().toUpperCase();
            nombreCompleto = nombreCompleto +" "+ participante.getApellido1().toUpperCase();
            if (participante.getApellido2()!=null) nombreCompleto = nombreCompleto + " "+  participante.getApellido2().toUpperCase();
        }
        if (participanteA2cares != null) {
            nombreCompleto = participanteA2cares.getNombre1().toUpperCase();
            if (participanteA2cares.getNombre2()!=null) nombreCompleto = nombreCompleto + " "+  participanteA2cares.getNombre2().toUpperCase();
            nombreCompleto = nombreCompleto +" "+ participanteA2cares.getApellido1().toUpperCase();
            if (participanteA2cares.getApellido2()!=null) nombreCompleto = nombreCompleto + " "+  participanteA2cares.getApellido2().toUpperCase();
        }
        cell = new PdfPCell(new Phrase(nombreCompleto, responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Código:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        String codigo = "";
        String codA2care = "100";
        if (participante!=null) codigo = participante.getCodigo().toString();
        if (participanteA2cares!=null) codigo = codA2care + participanteA2cares.getCodigo();

        cell = new PdfPCell(new Phrase(codigo, responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);

        table = new PdfPTable(new float[]{15, 14, 5, 5, 7, 35});
        table.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase("Fecha Nacimiento: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        String fechaNacimiento = "";
        if (participante!=null) fechaNacimiento = DateUtils.DateToString(participante.getFechaNac(),"dd/MM/yyyy", Locale.ENGLISH);
        if (participanteA2cares!=null) fechaNacimiento = DateUtils.DateToString(participanteA2cares.getFechaNac(),"dd/MM/yyyy", Locale.ENGLISH);

        cell = new PdfPCell(new Phrase(fechaNacimiento, responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);

        cell = new PdfPCell(new Phrase("Sexo: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        String sexoParticipante="";
        if (participante!=null) sexoParticipante = participante.getSexo();
        if (participanteA2cares!=null) sexoParticipante = participanteA2cares.getSexo();

        cell = new PdfPCell(new Phrase(sexoParticipante, responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        document.add(table);

        cell = new PdfPCell(new Phrase("Médico: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", responseReportBold));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{30, 10, 25, 10, 25});
        table.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase("Hora que se recibe la muestra:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("AM / PM", labelReport));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Modo: ", labelReport));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Sangre Total.", responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{40, 30, 30});
        table.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase("Fecha y Hora de realización del Exámen:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        String fechaProcesoBhc = "";
        Date fechaBhc = bhcResultado.getFec();
        fechaProcesoBhc = DateUtils.DateToString(fechaBhc,"dd/MM/yyyy", Locale.ENGLISH).concat(" "+bhcResultado.getHora()); //DateUtil.DateToString();
        cell = new PdfPCell(new Phrase(fechaProcesoBhc, responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{18, 15, 52});
        table.setWidthPercentage(100f);
        cell = new PdfPCell(new Phrase("Número de Exámen: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""+bhcResultado.getID_CONSECUTIVO(), responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        document.add(Chunk.NEWLINE);
        LineSeparator ls2 = new LineSeparator();
        ls2.setLineWidth(0.3f);
        document.add(new Chunk(ls2));
        document.add(Chunk.NEWLINE);
        table = new PdfPTable(new float[]{10, 6, 15, 23, 9, 8, 15});
        table.setWidthPercentage(90f);
        table.setSpacingBefore(10f);
        cell = new PdfPCell(new Phrase("LEU: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getWBC(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("x 10^3/µL", labelReport));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("ERI: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getRBC(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("x10^6/µL ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{10, 8, 15, 20, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("HGB: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getHGB(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("g/dL", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{6, 15, 10, 22, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("x10^3/µL", responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("%", responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("HCT: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getHCT(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("%", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{10, 8, 15, 20, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("LIN: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getLinf_numeral(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getLinf_porcentaje(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("VCM: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getMCV(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("fL", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{10, 8, 15, 20, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("NEU: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getNeu_numeral(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getNeu_porcentaje(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("HCM: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getMCH(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("pg", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{10, 8, 15, 20, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("MON: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getMon_numeral(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getMon_porcentaje(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("CHCM: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getMCHC(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("g/dL", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{10, 8, 15, 20, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("EOS: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getEos_numeral(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getEos_porcentaje(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("PLT: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getPLT(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("x10^3/µL", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        table = new PdfPTable(new float[]{10, 8, 15, 20, 9, 8, 15});
        table.setWidthPercentage(90f);
        cell = new PdfPCell(new Phrase("BAS: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getBas_numeral(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getBas_porcentaje(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("VPM: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bhcResultado.getMPV(), responseReportBold));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("fL", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.setSpacingAfter(10f);
        document.add(table);
        document.add(Chunk.NEWLINE);
        LineSeparator ls3 = new LineSeparator();
        ls3.setLineWidth(0.3f);
        document.add(new Chunk(ls3));
        document.add(Chunk.NEWLINE);
        table = new PdfPTable(new float[]{28, 15, 30, 15});
        table.setWidthPercentage(100f);
        table.setSpacingBefore(40f);
        cell = new PdfPCell(new Phrase("Hora que se entrega el resultado:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", responseReportBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(" AM / PM", labelReport));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);
        table = new PdfPTable(new float[]{38, 62});
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10f);
        cell = new PdfPCell(new Phrase("Persona a quien se entrega el resultado:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", responseReportBold));
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        document.add(table);

        String bionalista = "";
        String codigoPersonal = "";
        if (personal != null) {
            bionalista = personal.getNombreApellido();
            codigoPersonal = personal.getIdpersonal().toString();
        }
        table = new PdfPTable(new float[]{50, 50});
        table.setWidthPercentage(90f);
        table.setSpacingBefore(30f);
        cell = new PdfPCell(new Phrase("Bioanalista:", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(bionalista, labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);
        table = new PdfPTable(new float[]{50, 50});
        table.setWidthPercentage(90f);
        table.setSpacingBefore(10f);
        cell = new PdfPCell(new Phrase("Número: ", labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(codigoPersonal, labelReport));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        document.add(table);
    }
}
