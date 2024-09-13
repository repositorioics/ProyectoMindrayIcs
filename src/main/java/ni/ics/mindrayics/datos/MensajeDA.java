package ni.ics.mindrayics.datos;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_RESPONSE;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.OBR;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ni.ics.mindrayics.modelo.BHCParticipanteDto;
import ni.ics.mindrayics.modelo.BhcResultado;
import ni.ics.mindrayics.modelo.DatosParticipante;
import ni.ics.mindrayics.modelo.Personal;
import ni.ics.mindrayics.pdf.ReporteBhc;
import ni.ics.mindrayics.servicios.MensajeService;
import ni.ics.mindrayics.utils.DateUtils;
import ni.ics.mindrayics.utils.StringUtils;
import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/*
 * An HL7 message has the following components:
 *
 * MSH - Message Header Segment
 * RESPONSE (repeating)
 * PATIENT
 * PID - Patient Identification Segment
 * PV1 – Patient Visit Segment
 * ORDER OBSERVATION (repeating)
 * OBR - Observation Request Segment
 * ORC - Common Order Segment
 * OBR - Observation Request Segment
 * OBX – Observation Segment (repeating)
 *
 */
public class MensajeDA implements MensajeService {
    private final Logger logger = Logger.getLogger(this.getClass());
    private static DatosParticipante participante = null;
    private static BHCParticipanteDto participanteA2cares = null;
    private static Personal personal = null;
    public void transformarMensajeRecibido(String mensaje) {
        try {
            if(!StringUtils.isNullOrEmpty(mensaje)) {
                String resultado = mensaje.replaceAll("\n", "\r");
                HapiContext context = new DefaultHapiContext();
                CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.3");
                context.getParserConfiguration().setValidating(false);
                context.setModelClassFactory(mcf);
                PipeParser pipeParser = context.getPipeParser();

                Message message = pipeParser.parse(resultado);
                //System.setProperty(Varies.INVALID_OBX2_TYPE_PROP, "ST");
                ORU_R01 oru = (ORU_R01) message;
                MSH msh = oru.getMSH();

                BhcResultado bhcResultadoDto = new BhcResultado();
                bhcResultadoDto.setProcesoAutomatico(true);
                String sendingApp = msh.getSendingApplication().encode(); //BC-6000
                String sendingFacility = msh.getSendingFacility().encode(); //Mindray

                bhcResultadoDto.setNombre_equipo(sendingFacility + " " +sendingApp);
                bhcResultadoDto.setMensaje(mensaje);
                for (ORU_R01_RESPONSE response : oru.getRESPONSEAll()) {
                    for (ORU_R01_ORDER_OBSERVATION orderObservation : response.getORDER_OBSERVATIONAll()) {
                        OBR obr = orderObservation.getOBR();
                        String idMuestra = obr.getObr3_FillerOrderNumber().encode();
                        logger.info("Código participante: " + idMuestra);
                        String operador = obr.getObr32_PrincipalResultInterpreter().getOPName().encode();

                        String fechaHora = obr.getObr7_ObservationDateTime().getTimeOfAnEvent().getValue();
                        Date fecha = DateUtils.StringToDate(fechaHora, "yyyyMMddHHmmss", Locale.ENGLISH);
                        String hora = DateUtils.DateToString(fecha, "HH:mm", Locale.ENGLISH);

                        String fecha2 = DateUtils.DateToString(fecha, "dd-MM-yyyy", Locale.ENGLISH);
                        bhcResultadoDto.setFechaPa(fechaHora);
                        bhcResultadoDto.setFecha(fecha2);
                        bhcResultadoDto.setFec(fecha);

                        bhcResultadoDto.setHora(hora);
                        bhcResultadoDto.setIdMuestra(idMuestra);
                        bhcResultadoDto.setOperador(operador);
                        for (ORU_R01_OBSERVATION observation : orderObservation.getOBSERVATIONAll()) {
                            OBX obx = observation.getOBX();
                            String type = obx.getObx3_ObservationIdentifier().getCe2_Text().getValue();
                            for (Varies varies : obx.getObx5_ObservationValue()) {
                                String value = varies.encode();
                                if (type.trim().equals("WBC")) {
                                    bhcResultadoDto.setWBC(value);
                                } else if (type.trim().equals("BAS#")) {
                                    bhcResultadoDto.setBas_numeral(value);
                                } else if (type.trim().equals("BAS%")) {
                                    bhcResultadoDto.setBas_porcentaje(value);
                                } else if (type.trim().equals("NEU#")) {
                                    bhcResultadoDto.setNeu_numeral(value);
                                } else if (type.trim().equals("NEU%")) {
                                    bhcResultadoDto.setNeu_porcentaje(value);
                                } else if (type.trim().equals("EOS#")) {
                                    bhcResultadoDto.setEos_numeral(value);
                                } else if (type.trim().equals("EOS%")) {
                                    bhcResultadoDto.setEos_porcentaje(value);
                                } else if (type.trim().equals("LYM#")) {
                                    bhcResultadoDto.setLinf_numeral(value);
                                } else if (type.trim().equals("LYM%")) {
                                    bhcResultadoDto.setLinf_porcentaje(value);
                                } else if (type.trim().equals("MON#")) {
                                    bhcResultadoDto.setMon_numeral(value);
                                } else if (type.trim().equals("MON%")) {
                                    bhcResultadoDto.setMon_porcentaje(value);
                                } else if (type.trim().equals("RBC")) {
                                    bhcResultadoDto.setRBC(value);
                                } else if (type.trim().equals("HGB")) {
                                    bhcResultadoDto.setHGB(value);
                                } else if (type.trim().equals("MCV")) {
                                    bhcResultadoDto.setMCV(value);
                                } else if (type.trim().equals("MCH")) {
                                    bhcResultadoDto.setMCH(value);
                                } else if (type.trim().equals("MCHC")) {
                                    bhcResultadoDto.setMCHC(value);
                                } else if (type.trim().equals("RDW-CV")) {
                                    bhcResultadoDto.setRDW_CV(value);
                                } else if (type.trim().equals("RDW-SD")) {
                                    bhcResultadoDto.setRDW_SD(value);
                                } else if (type.trim().equals("HCT")) {
                                    bhcResultadoDto.setHCT(value);
                                } else if (type.trim().equals("PLT")) {
                                    bhcResultadoDto.setPLT(value);
                                } else if (type.trim().equals("MPV")) {
                                    bhcResultadoDto.setMPV(value);
                                } else if (type.trim().equals("PDW")) {
                                    bhcResultadoDto.setPDW(value);
                                } else if (type.trim().equals("PCT")) {
                                    bhcResultadoDto.setPCT(value);
                                } else if (type.trim().equals("NRBC#")) {
                                    bhcResultadoDto.setNRBC_numeral(value);
                                } else if (type.trim().equals("NRBC%")) {
                                    bhcResultadoDto.setNRBC_porcentaje(value);
                                } else if (type.trim().equals("PLT-I")) {
                                    bhcResultadoDto.setPLT_I(value);
                                } else if (type.trim().equals("IMG#")) {
                                    bhcResultadoDto.setIMG_numeral(value);
                                } else if (type.trim().equals("IMG%")) {
                                    bhcResultadoDto.setIMG_porcentaje(value);
                                } else if (type.trim().equals("HFC#")) {
                                    bhcResultadoDto.setHFC_numeral(value);
                                } else if (type.trim().equals("HFC%")) {
                                    bhcResultadoDto.setHFC_porcentaje(value);
                                } else if (type.trim().equals("WBC-D")) {
                                    bhcResultadoDto.setWBC_D(value);
                                } else if (type.trim().equals("WBC-N")) {
                                    bhcResultadoDto.setWBC_N(value);
                                } else if (type.trim().equals("PLCC")) {
                                    bhcResultadoDto.setPLC_C(value);
                                } else if (type.trim().equals("PLCR")) {
                                    bhcResultadoDto.setP_LCR(value);
                                } else if (type.trim().equals("InR#")) {
                                    bhcResultadoDto.setInR_numeral(value);
                                } else if (type.trim().equals("InR‰")) {
                                    bhcResultadoDto.setInR_porcentaje(value);
                                } else if (type.trim().equals("Micro#")) {
                                    bhcResultadoDto.setMicro_numeral(value);
                                } else if (type.trim().equals("Micro%")) {
                                    bhcResultadoDto.setMicro_porcentaje(value);
                                } else if (type.trim().equals("Macro#")) {
                                    bhcResultadoDto.setMacro_numeral(value);
                                } else if (type.trim().equals("Macro%")) {
                                    bhcResultadoDto.setMacro_porcentaje(value);
                                } else if (type.trim().equals("PDW-SD")) {
                                    bhcResultadoDto.setPDW_SD(value);
                                } else if (type.trim().equals("TNC-D")) {
                                    bhcResultadoDto.setTNC_D(value);
                                } else if (type.trim().equals("IME%")) {
                                    bhcResultadoDto.setIME_porcentaje(value);
                                } else if (type.trim().equals("IME#")) {
                                    bhcResultadoDto.setIME_numeral(value);
                                } else if (type.trim().equals("H-NR%")) {
                                    bhcResultadoDto.setHNR_porcentaje(value);
                                } else if (type.trim().equals("L-NR%")) {
                                    bhcResultadoDto.setLNR_porcentaje(value);
                                } else if (type.trim().equals("NLR")) {
                                    bhcResultadoDto.setNLR(value);
                                } else if (type.trim().equals("PLR")) {
                                    bhcResultadoDto.setPLR(value);
                                } else if (type.trim().equals("TNC-N")) {
                                    bhcResultadoDto.setTNC_N(value);
                                } else {
                                    logger.info("NO EXISTE EN EL MODELO: " + type + " " +value);
                                }
                            }
                        }
                    }
                }
                saveDataBhcEstudiosIcs(bhcResultadoDto);
            }
        } catch (HL7Exception | ParseException e) {
            logger.info("HL7Exception | ParseException transformarMensajeRecibido: %s%n", e);
            throw new RuntimeException(e);
        }
    }

    public DatosParticipante getParticipanteEstudio(String codigo) {
        try {
            String codigoParticipante = "?codigo="+codigo;
            String URL_UMICH = "http://nicaumich4.miserver.it.umich.edu:8080/estudios-ics/api/v1/participanteByCodigo"+codigoParticipante;
            String user = "hojaConsultaUpd";
            String password = "Hc2022*";

            String userPassword = user + ":" + password;
            String encodedAuth = Base64.encode(userPassword.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<DatosParticipante> entity = new HttpEntity<DatosParticipante>(null, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<String> response = restTemplate.exchange(URL_UMICH, HttpMethod.GET, entity,
                    String.class);
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode.value() == 200) {
                //Convertir el body to Json
                String string = response.getBody();
                JsonObject convertedObject = new Gson().fromJson(string, JsonObject.class);
                byte[] jsonData = convertedObject.toString().getBytes();
                ObjectMapper mapper = new ObjectMapper();
                participante = mapper.readValue(jsonData, DatosParticipante.class);
            }
        } catch (Exception e) {
            logger.error("Exception getParticipanteEstudio: %s%n", e);
        }
        return participante;
    }

    public BHCParticipanteDto getParticipanteA2cares(String codigoParticipante) {
        try {
            String codigo = "?codigo="+codigoParticipante;
            String URL_UMICH = "http://nicaumich5.miserver.it.umich.edu:8080/a2cares/a2caresDatosGenerales/Bhc/participantes/"+codigo;
            String user = "bhcMindray";
            String password = "b6000m";

            String userPassword = user + ":" + password;
            String encodedAuth = Base64.encode(userPassword.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BHCParticipanteDto> entity = new HttpEntity<BHCParticipanteDto>(null, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<String> response = restTemplate.exchange(URL_UMICH, HttpMethod.GET, entity,
                    String.class);
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode.value() == 200) {
                //Convertir el body to Json
                String string = response.getBody();
                JsonObject convertedObject = new Gson().fromJson(string, JsonObject.class);
                byte[] jsonData = convertedObject.toString().getBytes();
                ObjectMapper mapper = new ObjectMapper();
                participanteA2cares = mapper.readValue(jsonData, BHCParticipanteDto.class);
            }
        } catch (Exception e) {
            logger.error("Exception getParticipanteA2cares: %s%n", e);
        }
        return participanteA2cares;
    }

    public Personal getPersonalByNombreApellido(String nombre) {
        try {
            String nombreApellido = "?nombreApellido="+nombre;
            String URL_UMICH = "http://nicaumich4.miserver.it.umich.edu:8080/estudios-ics/api/v1/usuarioByNombreApellido"+nombreApellido;;
            String user = "hojaConsultaUpd";
            String password = "Hc2022*";

            String userPassword = user + ":" + password;
            String encodedAuth = Base64.encode(userPassword.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Personal> entity = new HttpEntity<Personal>(null, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<String> response = restTemplate.exchange(URL_UMICH, HttpMethod.GET, entity,
                    String.class);
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode.value() == 200) {
                //Convertir el body to Json
                String string = response.getBody();
                JsonObject convertedObject = new Gson().fromJson(string, JsonObject.class);
                byte[] jsonData = convertedObject.toString().getBytes();
                ObjectMapper mapper = new ObjectMapper();
                personal = mapper.readValue(jsonData, Personal.class);
            }
        } catch (Exception e) {
            logger.error("Exception getPersonalByNombreApellido: %s%n", e);
        }
        return personal;
    }

    public void saveDataBhcEstudiosIcs(BhcResultado bhcResultadoDto) {
        participante = null;
        participanteA2cares = null;
        try {
            String URL_UMICH = "http://nicaumich4.miserver.it.umich.edu:8080/estudios-ics/api/v1/muestra_bhc_mindray";
            String user = "hojaConsultaUpd";
            String password = "Hc2022*";

            String userPassword = user + ":" + password;
            String encodedAuth = Base64.encode(userPassword.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BhcResultado> entity = new HttpEntity<BhcResultado>(bhcResultadoDto,headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<String> response = restTemplate.exchange(URL_UMICH, HttpMethod.POST, entity,
                    String.class);

            HttpStatus codigo = response.getStatusCode();
            if (codigo.value() == 400) {
                logger.info("StatusCode " + codigo.value() + " Bad Request");
            } else {
                if (codigo.value() == 200) {
                    //Convertir el body to Json
                    String string = response.getBody();
                    JsonObject convertedObject = new Gson().fromJson(string, JsonObject.class);
                    byte[] jsonData = convertedObject.toString().getBytes();
                    ObjectMapper mapper = new ObjectMapper();
                    BhcResultado bhcResultado = mapper.readValue(jsonData, BhcResultado.class);
                    personal = getPersonalByNombreApellido(bhcResultado.getOperador());
                    String pattern = "^100\\d\\d\\d\\d$";
                    String pattern2 = "^9{5}";

                    if (bhcResultado.getId() != null) {
                        if (bhcResultado.getId() > 0) {
                            boolean esParticipanteMinsa = Pattern.compile(pattern2).matcher(bhcResultadoDto.getIdMuestra().trim()).find();
                            if (esParticipanteMinsa) {
                                logger.info("StatusCode " + codigo.value() + " Muestra almacenada en la base de datos");
                                logger.info("Muestra Minsa - No se imprime el pdf automatico");
                            } else {
                                boolean esParticipanteA2cares = Pattern.compile(pattern).matcher(bhcResultadoDto.getIdMuestra().trim()).find();
                                if (esParticipanteA2cares) {
                                    participanteA2cares = getParticipanteA2cares(bhcResultado.getIdMuestra());
                                } else {
                                    participante = getParticipanteEstudio(bhcResultado.getIdMuestra());
                                }
                                ReporteBhc.pdf(bhcResultado, participante, participanteA2cares, personal);
                                logger.info("StatusCode " + codigo.value() + " Muestra almacenada en la base de datos");
                            }
                        }
                    }
                } else {
                    logger.error("Error al guardar la muestra en la base de datos " +response.getStatusCode());
                }
            }
        } catch (Exception e) {
            logger.error("Exception saveDataEstudiosIcs: %s%n", e);
        }
    }
}